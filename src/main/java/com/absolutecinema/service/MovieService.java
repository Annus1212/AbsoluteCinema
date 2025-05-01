package com.absolutecinema.service;

import com.absolutecinema.entity.Movie;
import com.absolutecinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(movieDetails.getTitle());
        movie.setDescription(movieDetails.getDescription());
        movie.setDuration(movieDetails.getDuration());
        movie.setGenre(movieDetails.getGenre());
        movie.setReleaseDate(movieDetails.getReleaseDate());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    // Find all Movies
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    // Find Movie by ID
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    // Save Movie
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    // Update Movie
    public Movie update(Long id, Movie movie) {
        Movie existingMovie = movieRepository.findById(id).orElse(null);
        if (existingMovie != null) {
            existingMovie.setTitle(movie.getTitle());
            existingMovie.setGenre(movie.getGenre());
            existingMovie.setDirector(movie.getDirector());
            existingMovie.setReleaseDate(movie.getReleaseDate());
            existingMovie.setDescription(movie.getDescription());
            existingMovie.setDuration(movie.getDuration());
            return movieRepository.save(existingMovie);
        }
        return null;
    }

    // Delete Movie
    public boolean delete(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        }
        return false;
    }
}