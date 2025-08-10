package com.example.moviecatalogue.controller;

import com.example.moviecatalogue.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/favorites")
    public String favorites(Model model) {
        model.addAttribute("favorites", favoriteService.listAll());
        model.addAttribute("imgBase", "https://image.tmdb.org/t/p/w500");
        return "favorites";
    }

    // Add from list/detail (we post raw fields to avoid extra API calls)
    @PostMapping("/favorites/add")
    public String add(
            @RequestParam("tmdbId") Long tmdbId,
            @RequestParam("title") String title,
            @RequestParam(value = "posterPath", required = false) String posterPath,
            @RequestParam(value = "rating", required = false) Double rating,
            @RequestParam(value = "releaseDate", required = false) String releaseDate,
            @RequestParam(value = "overview", required = false) String overview,
            @RequestParam(value = "redirect", required = false, defaultValue = "/") String redirectTo
    ) {
        favoriteService.addIfAbsent(tmdbId, title, posterPath, rating, releaseDate, overview);
        return "redirect:" + redirectTo;
    }

    @PostMapping("/favorites/remove")
    public String remove(
            @RequestParam("tmdbId") Long tmdbId,
            @RequestParam(value = "redirect", required = false, defaultValue = "/favorites") String redirectTo
    ) {
        favoriteService.removeByTmdbId(tmdbId);
        return "redirect:" + redirectTo;
    }
}
