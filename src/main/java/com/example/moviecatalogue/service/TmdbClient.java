package com.example.moviecatalogue.service;

import com.example.moviecatalogue.dto.MovieDetailsDto;
import com.example.moviecatalogue.dto.TmdbSearchResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TmdbClient {

    private static final Logger log = LoggerFactory.getLogger(TmdbClient.class);

    private final RestTemplate restTemplate;

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    @Value("${tmdb.api.key}")
    private String apiKey;

    @PostConstruct
    void logAuthMode() {
        // Detect if you're using a v4 token (JWT) or a v3 API key
        boolean v4 = apiKey != null && apiKey.trim().startsWith("eyJ");
        String mode = v4 ? "v4 (Bearer header)" : "v3 (api_key query param)";

        // Print a safe preview of the key/token so you know it was read (without leaking it)
        String preview;
        if (apiKey == null) {
            preview = "null";
        } else if (apiKey.length() <= 8) {
            preview = "short(" + apiKey.length() + ")";
        } else {
            preview = apiKey.substring(0, 4) + "..." + apiKey.substring(apiKey.length() - 4);
        }

        log.info("TMDb auth mode: {} | key preview: {}", mode, preview);
    }

    private boolean isV4Token() {
        return apiKey != null && apiKey.trim().startsWith("eyJ");
    }

    private String buildUrl(String path, String queryParamKey, String queryParamValue) {
        UriComponentsBuilder b = UriComponentsBuilder
                .fromUriString(baseUrl + path)
                .queryParam("language", "en-US");

        // Only add param if both key and value are non-empty
        if (queryParamKey != null && !queryParamKey.isBlank()
                && queryParamValue != null && !queryParamValue.isBlank()) {
            b.queryParam(queryParamKey, queryParamValue);
        }
        return b.build().toUriString();
    }

    private <T> T get(String path, Class<T> type, String queryParamKey, String queryParamValue) {
        try {
            String url = buildUrl(path, queryParamKey, queryParamValue);

            if (isV4Token()) {
                // v4: Bearer token in header, no api_key param
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(apiKey.trim());
                headers.set("accept", "application/json");
                HttpEntity<Void> entity = new HttpEntity<>(headers);
                ResponseEntity<T> resp = restTemplate.exchange(url, HttpMethod.GET, entity, type);
                return resp.getBody();
            } else {
                // v3: use api_key as query param
                url = UriComponentsBuilder
                        .fromUriString(url)
                        .queryParam("api_key", apiKey)
                        .build()
                        .toUriString();

                return restTemplate.getForObject(url, type);
            }
        } catch (HttpClientErrorException e) {
            log.warn("TMDb call failed: {} {} -> {}", e.getStatusCode(), path, e.getResponseBodyAsString());
            return null; // let controllers render a friendly message
        } catch (Exception e) {
            log.error("TMDb call error on {}: {}", path, e.toString());
            return null;
        }
    }

    public TmdbSearchResponse getTrending() {
        return get("/trending/movie/week", TmdbSearchResponse.class, null, null);
    }

    public TmdbSearchResponse searchMovies(String query) {
        return get("/search/movie", TmdbSearchResponse.class, "query", query);
    }

    public MovieDetailsDto getMovieDetails(long movieId) {
        return get("/movie/" + movieId, MovieDetailsDto.class, null, null);
    }
}
