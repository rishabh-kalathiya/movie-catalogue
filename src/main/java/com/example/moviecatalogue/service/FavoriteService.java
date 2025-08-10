package com.example.moviecatalogue.service;

import com.example.moviecatalogue.entity.FavoriteMovie;
import com.example.moviecatalogue.repository.FavoriteMovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteMovieRepository repo;

    public List<FavoriteMovie> listAll() {
        return repo.findAll();
    }

    public boolean addIfAbsent(Long tmdbId, String title, String posterPath,
                               Double rating, String releaseDate, String overview) {
        if (tmdbId == null || title == null || title.isBlank()) return false;
        if (repo.existsByTmdbId(tmdbId)) return false;

        LocalDate rd = null;
        if (releaseDate != null && !releaseDate.isBlank()) {
            try { rd = LocalDate.parse(releaseDate); } catch (DateTimeParseException ignored) {}
        }

        FavoriteMovie f = FavoriteMovie.builder()
                .tmdbId(tmdbId)
                .title(title)
                .posterPath(posterPath)
                .rating(rating)
                .releaseDate(rd)
                .overview(overview)
                .build();
        repo.save(f);
        return true;
    }

    public void removeByTmdbId(Long tmdbId) {
        if (tmdbId != null) repo.deleteByTmdbId(tmdbId);
    }
}