package com.example.moviecatalogue.controller;

import com.example.moviecatalogue.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequiredArgsConstructor
public class HighlightController {

    private static final Logger log = LoggerFactory.getLogger(HighlightController.class);
    private final FavoriteService favoriteService;

    @GetMapping("/highlights")
    public String highlights(Model model) {
        var list = favoriteService.getHighlights();
        log.info("GET /highlights size={}", (list == null ? 0 : list.size()));
        model.addAttribute("highlights", list);
        model.addAttribute("imgBase", "https://image.tmdb.org/t/p/w500");
        return "highlights";
    }
}