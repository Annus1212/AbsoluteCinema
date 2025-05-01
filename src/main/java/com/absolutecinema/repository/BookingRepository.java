package com.absolutecinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.absolutecinema.entity.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b JOIN FETCH b.movie WHERE b.user.username = :username ORDER BY b.bookingTime DESC")
    List<Booking> findByUserUsernameOrderByBookingTimeDesc(@Param("username") String username);
}