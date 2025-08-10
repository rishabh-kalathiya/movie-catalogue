package com.example.moviecatalogue.dto;

import lombok.Data;

@Data
public class MovieSummaryDto {
    private long id;
    private String title;
    private String poster_path; // URL to the movie poster
    private String release_date;
    private Double vote_average;
    private String overview;
}

