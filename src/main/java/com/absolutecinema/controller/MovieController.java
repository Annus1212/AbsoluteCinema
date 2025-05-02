package com.absolutecinema.controller;

import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.service.MovieService;
import com.absolutecinema.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

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

    @GetMapping("/search")
    public String searchMovies(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String genre,
            Model model) {

        logger.info("Search request - Query: {}, Genre: {}", query, genre);

        List<Movie> movies;

        if (query == null || query.trim().isEmpty()) {
            query = "";
        }

        // Handle search with or without genre filter
        if (genre != null && !genre.isEmpty()) {
            logger.info("Searching by title and genre");
            movies = movieRepository.findByTitleContainingIgnoreCaseAndGenre(query, genre);
            logger.info("Found {} movies with genre {}", movies.size(), genre);
        } else if (!query.isEmpty()) {
            logger.info("Searching by title only");
            movies = movieRepository.findByTitleContainingIgnoreCase(query);
            logger.info("Found {} movies with title containing {}", movies.size(), query);
        } else {
            logger.info("No filters applied, returning all movies");
            movies = movieRepository.findAll();
            logger.info("Found {} total movies", movies.size());
        }

        model.addAttribute("movies", movies);
        model.addAttribute("currentQuery", query);
        model.addAttribute("currentGenre", genre);
        return "movies/list";
    }
}