package com.absolutecinema.repository;

import com.absolutecinema.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    // @Query("SELECT MAX(u.id) FROM User u")
    // Optional<Long> findMaxId();
}