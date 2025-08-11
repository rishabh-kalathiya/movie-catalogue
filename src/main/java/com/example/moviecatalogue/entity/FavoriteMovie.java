package com.example.moviecatalogue.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Represents a favorite movie entity.
 * This class is mapped to the 'favorite_movies' table in the database.
 * It includes fields for TMDb ID, title, poster path, rating, release date, and
 * overview.
 */
@Entity
@Table(name = "favorite_movies", uniqueConstraints = {
        @UniqueConstraint(name = "uk_favorite_tmdb_id", columnNames = "tmdbId")
}, indexes = {
        @Index(name = "ix_favorite_tmdb_id", columnList = "tmdbId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "tmdbId") // consider movies unique by their TMDb ID
public class FavoriteMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // surrogate key for JPA convenience

    @NotNull
    private Long tmdbId; // TMDb numeric id

    @NotBlank
    private String title;

    private String posterPath;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double rating; // TMDb vote_average (0..10)

    private LocalDate releaseDate;

    @Column(length = 2000)
    private String overview;

    @Builder.Default
    @Column(nullable = false)
    private boolean highlight = false;

}
