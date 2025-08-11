package com.example.moviecatalogue.dto;

import lombok.Data;

import java.util.List;

@Data
public class TmdbSearchResponse {
    private int page;
    private List<MovieSummaryDto> results;
    private int total_pages;
    private int total_results;
}

