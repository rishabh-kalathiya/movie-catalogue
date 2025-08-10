package com.example.moviecatalogue.repository;

import com.example.moviecatalogue.entity.FavoriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing favorite movies.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {
    Optional<FavoriteMovie> findByTmdbId(Long tmdbId);
    boolean existsByTmdbId(Long tmdbId);
    void deleteByTmdbId(Long tmdbId);
}