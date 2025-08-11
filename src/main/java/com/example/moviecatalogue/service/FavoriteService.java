package com.example.moviecatalogue.service;

import com.example.moviecatalogue.entity.FavoriteMovie;
import com.example.moviecatalogue.repository.FavoriteMovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteMovieRepository repo;

    public List<FavoriteMovie> listAll() {
        return repo.findAll();
    }

    @Transactional
    public boolean addIfAbsent(Long tmdbId, String title, String posterPath,
            Double rating, String releaseDate, String overview) {
        if (tmdbId == null || title == null || title.isBlank())
            return false;
        if (repo.existsByTmdbId(tmdbId))
            return false;

        LocalDate rd = null;
        if (releaseDate != null && !releaseDate.isBlank()) {
            try {
                rd = LocalDate.parse(releaseDate);
            } catch (DateTimeParseException ignored) {
            }
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

    @Transactional
    public void removeByTmdbId(Long tmdbId) {
        if (tmdbId == null)
            return;
        long before = repo.count();
        int rows = repo.deleteByTmdbIdExact(tmdbId);
        long after = repo.count();
        log.info("removeByTmdbId({}) rowsDeleted={} count: {} -> {}", tmdbId, rows, before, after);
    }

    @Transactional
    public void setHighlight(Long tmdbId) {
        repo.clearHighlight();
        repo.findByTmdbId(tmdbId).ifPresent(movie -> {
            movie.setHighlight(true);
            repo.save(movie);
        });
    }

    public boolean isFavorite(Long tmdbId) {
        return tmdbId != null && repo.existsByTmdbId(tmdbId);
    }

    public java.util.Set<Long> getFavoriteIds() {
        return repo.findAll().stream()
                .map(FavoriteMovie::getTmdbId)
                .collect(java.util.stream.Collectors.toSet());
    }
}