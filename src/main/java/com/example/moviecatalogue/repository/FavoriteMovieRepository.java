package com.example.moviecatalogue.repository;

import com.example.moviecatalogue.entity.FavoriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository interface for managing favorite movies.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {
    Optional<FavoriteMovie> findByTmdbId(Long tmdbId);

    boolean existsByTmdbId(Long tmdbId);

    @Modifying
    @Transactional
    @Query("delete from FavoriteMovie f where f.tmdbId = :tmdbId")
    int deleteByTmdbIdExact(@Param("tmdbId") Long tmdbId);

    Optional<FavoriteMovie> findByHighlightTrue();

    @Modifying
    @Query("UPDATE FavoriteMovie f SET f.highlight = false WHERE f.highlight = true")
    void clearHighlight();
}