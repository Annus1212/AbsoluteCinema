package com.absolutecinema.service;

import com.absolutecinema.entity.Session;
import com.absolutecinema.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> getAllAvailableSessions() {
        return sessionRepository.findNextSessions(LocalDateTime.now());
    }

    public List<Session> getAvailableSessions(Long movieId) {
        return sessionRepository.findByMovieId(movieId);
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    public Session updateSession(Long id, Session sessionDetails) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setSessionTime(sessionDetails.getSessionTime());
        session.setAvailableSeats(sessionDetails.getAvailableSeats());

        return sessionRepository.save(session);
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public void updateAvailableSeats(Long id, int seatsToReserve) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (session.getAvailableSeats() < seatsToReserve) {
            throw new RuntimeException("Not enough seats available");
        }

        session.setAvailableSeats(session.getAvailableSeats() - seatsToReserve);
        sessionRepository.save(session);
    }

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Optional<Session> findById(Long id) {
        return sessionRepository.findById(id);
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public void deleteById(Long id) {
        sessionRepository.deleteById(id);
    }

    public List<Session> findByMovieId(Long movieId) {
        return sessionRepository.findByMovieId(movieId);
    }
}