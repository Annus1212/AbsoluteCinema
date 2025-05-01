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

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie update(Long id, Movie movie) {
        Movie existingMovie = movieRepository.findById(id).orElse(null);
        if (existingMovie != null) {
            existingMovie.setTitle(movie.getTitle());
            existingMovie.setGenre(movie.getGenre());
            existingMovie.setDirector(movie.getDirector());
            existingMovie.setReleaseDate(movie.getReleaseDate());
            existingMovie.setDescription(movie.getDescription());
            existingMovie.setDuration(movie.getDuration());
            existingMovie.setLanguage(movie.getLanguage());
            existingMovie.setTicketssold(movie.getTicketssold());
            if (movie.getImages() != null) {
                existingMovie.setImages(movie.getImages());
            }
            return movieRepository.save(existingMovie);
        }
        return null;
    }

    public boolean delete(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        }
        return false;
    }
}