package com.example.moviecatalogue.controller;

import com.example.moviecatalogue.dto.TmdbSearchResponse;
import com.example.moviecatalogue.service.TmdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TmdbClient tmdbClient;

    // TMDb image base (we'll use w500 for posters). Could be externalized later.
    private static final String IMG_BASE = "https://image.tmdb.org/t/p/w500";

    @GetMapping("/")
    public String home(Model model) {
        TmdbSearchResponse resp = tmdbClient.getTrending();
        model.addAttribute("movies", resp != null ? resp.getResults() : null);
        model.addAttribute("imgBase", IMG_BASE);
        model.addAttribute("query", "");
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String q, Model model) {
        if (q == null || q.isBlank()) {
            return "redirect:/";
        }
        TmdbSearchResponse resp = tmdbClient.searchMovies(q);
        model.addAttribute("movies", resp != null ? resp.getResults() : null);
        model.addAttribute("imgBase", IMG_BASE);
        model.addAttribute("query", q);
        return "index";
    }
}
