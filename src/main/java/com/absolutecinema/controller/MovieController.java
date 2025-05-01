package com.absolutecinema.controller;

import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieRepository movieRepository, MovieService movieService) {
        this.movieRepository = movieRepository;
        this.movieService = movieService;
    }

    @GetMapping
    public String listMovies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movies/list";
    }

    @GetMapping("/{id}")
    public String showMovieDetails(@PathVariable Long id, Model model) {
        var movie = movieService.findById(id);
        if (movie == null) {
            return "redirect:/movies";
        }
        model.addAttribute("movie", movie);
        return "movies/movie-details";
    }
}