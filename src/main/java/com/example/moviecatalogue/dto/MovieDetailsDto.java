package com.example.moviecatalogue.dto;

import lombok.Data;

@Data
public class MovieDetailsDto {
    private long id;
    private String title;
    private String poster_path;
    private String backdrop_path;
    private String overview;
    private String release_date;
    private Double vote_average;
    private int runtime;         // minutes (may be 0 if unknown)
    private String status;       // e.g., "Released"
    private String tagline;
}
