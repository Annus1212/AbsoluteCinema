package com.absolutecinema.controller;

import com.absolutecinema.entity.Snack;
import com.absolutecinema.service.SnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/snacks")
public class SnackController {

    private final SnackService snackService;

    @Autowired
    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping
    public ResponseEntity<List<Snack>> getAllSnacks() {
        return ResponseEntity.ok(snackService.getAllSnacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Snack> getSnackById(@PathVariable Long id) {
        Snack snack = snackService.getSnackById(id);
        if (snack != null) {
            return ResponseEntity.ok(snack);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Snack> createSnack(@RequestBody Snack snack) {
        return ResponseEntity.ok(snackService.addSnack(snack));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Snack> updateSnack(@PathVariable Long id, @RequestBody Snack snackDetails) {
        try {
            Snack updatedSnack = snackService.updateSnack(id, snackDetails);
            return ResponseEntity.ok(updatedSnack);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSnack(@PathVariable Long id) {
        snackService.deleteSnack(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/quantity")
    public ResponseEntity<Snack> updateSnackQuantity(@PathVariable Long id, @RequestParam int quantity) {
        Snack snack = snackService.getSnackById(id);
        if (snack != null) {
            snackService.updateSnackQuantity(id, quantity);
            return ResponseEntity.ok(snack);
        }
        return ResponseEntity.notFound().build();
    }
}