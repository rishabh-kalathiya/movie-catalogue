package com.example.moviecatalogue.controller;

import com.example.moviecatalogue.dto.MovieDetailsDto;
import com.example.moviecatalogue.service.TmdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.moviecatalogue.service.FavoriteService;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final TmdbClient tmdbClient;
    private final FavoriteService favoriteService;

    private static final String IMG_BASE = "https://image.tmdb.org/t/p/w500";

    @GetMapping("/movie/{id}")
    public String details(@PathVariable long id, Model model) {
        MovieDetailsDto details = tmdbClient.getMovieDetails(id);
        model.addAttribute("m", details);
        model.addAttribute("imgBase", IMG_BASE);
        model.addAttribute("pageTitle", details != null ? details.getTitle() : "Details");
        model.addAttribute("isFavorite", favoriteService.isFavorite(id));
        return "details";
    }
}
