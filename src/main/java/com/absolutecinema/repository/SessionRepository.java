package com.absolutecinema.repository;

import com.absolutecinema.entity.Session;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    // Additional query methods can be defined here if needed

    List<Session> findByMovieId(Long movieId);
}