package com.absolutecinema.repository;

import com.absolutecinema.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COALESCE(MAX(f.id), 20) FROM Feedback f")
    Long findMaxId();
}