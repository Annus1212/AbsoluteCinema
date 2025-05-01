package com.absolutecinema.controller;

import com.absolutecinema.entity.Snack;
import com.absolutecinema.service.SnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/snacks")
public class SnackAPIController {

    private static final Logger logger = LoggerFactory.getLogger(SnackAPIController.class);
    private final SnackService snackService;

    @Autowired
    public SnackAPIController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping
    public ResponseEntity<List<Snack>> getAllSnacks() {
        return ResponseEntity.ok(snackService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Snack> getSnackById(@PathVariable Long id) {
        return snackService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Snack> createSnack(@RequestBody Snack snack) {
        try {
            logger.info("Creating new snack: {}", snack);
            // Set default value for snackssold if not provided
            if (snack.getSnackssold() == 0) {
                snack.setSnackssold(0);
            }
            Snack createdSnack = snackService.save(snack);
            logger.info("Successfully created snack with ID: {}", createdSnack.getId());
            return ResponseEntity.status(201).body(createdSnack);
        } catch (Exception e) {
            logger.error("Error creating snack: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Snack> updateSnack(@PathVariable Long id, @RequestBody Snack snack) {
        try {
            logger.info("Updating snack with ID {}: {}", id, snack);
            if (!snackService.findById(id).isPresent()) {
                logger.warn("Snack with ID {} not found", id);
                return ResponseEntity.notFound().build();
            }
            snack.setId(id);
            Snack updatedSnack = snackService.save(snack);
            logger.info("Successfully updated snack with ID: {}", id);
            return ResponseEntity.ok(updatedSnack);
        } catch (Exception e) {
            logger.error("Error updating snack with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSnack(@PathVariable Long id) {
        try {
            if (!snackService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            snackService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting snack with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
} 