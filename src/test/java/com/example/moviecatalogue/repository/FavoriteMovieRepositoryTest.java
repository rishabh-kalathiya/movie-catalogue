package com.example.moviecatalogue.repository;

import com.example.moviecatalogue.entity.FavoriteMovie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteMovieRepositoryTest {

    @Autowired
    private FavoriteMovieRepository repo;

    @Test
    void saveAndFindByTmdbId() {
        FavoriteMovie saved = repo.save(FavoriteMovie.builder()
                .tmdbId(550L) // Fight Club id on TMDb, just for testing reference
                .title("Test Movie")
                .posterPath("/test.jpg")
                .rating(8.5)
                .releaseDate(LocalDate.of(1999, 10, 15))
                .overview("Testing JPA save/find")
                .build());

        assertThat(saved.getId()).isNotNull();
        assertThat(repo.existsByTmdbId(550L)).isTrue();
        assertThat(repo.findByTmdbId(550L)).isPresent();

        repo.deleteByTmdbId(550L);
        assertThat(repo.findByTmdbId(550L)).isNotPresent();
    }
}

