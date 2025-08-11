package com.example.moviecatalogue.controller;

import com.example.moviecatalogue.service.FavoriteService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private static final Logger log = LoggerFactory.getLogger(FavoriteController.class);
    private final FavoriteService favoriteService;

    @PostConstruct
    void loaded() {
        log.info("FavoriteController loaded and mappings registered.");
    }

    @GetMapping
    public String favorites(Model model) {
        model.addAttribute("favorites", favoriteService.listAll());
        model.addAttribute("imgBase", "https://image.tmdb.org/t/p/w500");
        return "favorites";
    }

    @PostMapping("/add")
    public String add(
            @RequestParam("tmdbId") Long tmdbId,
            @RequestParam("title") String title,
            @RequestParam(value = "posterPath", required = false) String posterPath,
            @RequestParam(value = "rating", required = false) Double rating,
            @RequestParam(value = "releaseDate", required = false) String releaseDate,
            @RequestParam(value = "overview", required = false) String overview,
            @RequestParam(value = "redirect", defaultValue = "/") String redirectTo) {
        log.info("ADD called tmdbId={}", tmdbId);
        favoriteService.addIfAbsent(tmdbId, title, posterPath, rating, releaseDate, overview);
        return "redirect:" + redirectTo;
    }

    // Handles POST /favorites/remove and POST /favorites/remove/{tmdbId}
    @PostMapping({ "/remove", "/remove/{tmdbId}" })
    public String remove(
            @RequestParam(value = "tmdbId", required = false) Long tmdbIdParam,
            @PathVariable(value = "tmdbId", required = false) Long tmdbIdPath,
            @RequestParam(value = "redirect", defaultValue = "/favorites") String redirectTo) {
        Long tmdbId = tmdbIdParam != null ? tmdbIdParam : tmdbIdPath;
        log.info("REMOVE called tmdbId={}", tmdbId);
        if (tmdbId != null) {
            favoriteService.removeByTmdbId(tmdbId);
        }
        return "redirect:" + redirectTo;
    }

    // Temporary GET to debug quickly from the browser
    @GetMapping("/remove-debug")
    public String removeDebug(@RequestParam("tmdbId") Long tmdbId,
            @RequestParam(value = "redirect", defaultValue = "/favorites") String redirectTo) {
        log.info("REMOVE-DEBUG (GET) tmdbId={}", tmdbId);
        favoriteService.removeByTmdbId(tmdbId);
        return "redirect:" + redirectTo;
    }

    @PostMapping("/favorites/highlight")
    public String highlight(@RequestParam Long tmdbId,
            @RequestParam(value = "redirect", required = false) String redirect) {
        favoriteService.setHighlight(tmdbId);
        return "redirect:" + (redirect != null ? redirect : "/favorites");
    }

    // Probe endpoint to prove this controller is registered
    @GetMapping("/_ping")
    @ResponseBody
    public String ping() {
        return "favorites-controller-up";
    }
}
