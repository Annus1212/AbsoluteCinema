package com.absolutecinema.repository;

import com.absolutecinema.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("SELECT s FROM Session s WHERE s.sessionTime > :currentDate ORDER BY s.sessionTime ASC")
    List<Session> findNextSessions(@Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT s FROM Session s WHERE s.movie.id = :movieId ORDER BY s.sessionTime ASC")
    List<Session> findByMovieId(@Param("movieId") Long movieId);
}