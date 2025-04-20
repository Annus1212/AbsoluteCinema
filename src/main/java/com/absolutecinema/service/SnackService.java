package com.absolutecinema.service;

import com.absolutecinema.entity.Snack;
import com.absolutecinema.repository.SnackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SnackService {

    @Autowired
    private final SnackRepository snackRepository;

    public SnackService(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    public List<Snack> getAllSnacks() {
        return snackRepository.findAll();
    }

    public Optional<Snack> getSnackById(Long id) {
        return snackRepository.findById(id);
    }

    public Snack addSnack(Snack snack) {
        return snackRepository.save(snack);
    }

    public Snack updateSnack(Long id, Snack snackDetails) {
        Snack snack = snackRepository.findById(id).orElseThrow(() -> new RuntimeException("Snack not found"));
        snack.setName(snackDetails.getName());
        snack.setPrice(snackDetails.getPrice());
        snack.setQuantity(snackDetails.getQuantity());
        return snackRepository.save(snack);
    }

    public void deleteSnack(Long id) {
        snackRepository.deleteById(id);
    }

    // Save Snack
    public Snack save(Snack snack) {
        return snackRepository.save(snack);
    }
}