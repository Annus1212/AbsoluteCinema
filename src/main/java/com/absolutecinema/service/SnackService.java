package com.absolutecinema.service;

import com.absolutecinema.entity.Snack;
import com.absolutecinema.repository.SnackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SnackService {

    private final SnackRepository snackRepository;

    @Autowired
    public SnackService(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    public List<Snack> findAll() {
        return snackRepository.findAll();
    }

    public Optional<Snack> findById(Long id) {
        return snackRepository.findById(id);
    }

    public Snack save(Snack snack) {
        return snackRepository.save(snack);
    }

    public void deleteById(Long id) {
        snackRepository.deleteById(id);
    }
}