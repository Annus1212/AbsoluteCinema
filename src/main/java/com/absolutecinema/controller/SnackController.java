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

    @Autowired
    private SnackService snackService;

    @GetMapping
    public ResponseEntity<List<Snack>> getAllSnacks() {
        List<Snack> snacks = snackService.getAllSnacks();
        return ResponseEntity.ok(snacks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Snack> getSnackById(@PathVariable Long id) {
        return snackService.getSnackById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Snack> createSnack(@RequestBody Snack snack) {
        Snack createdSnack = snackService.save(snack);
        return ResponseEntity.status(201).body(createdSnack);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Snack> updateSnack(@PathVariable Long id, @RequestBody Snack snack) {
        Snack updatedSnack = snackService.updateSnack(id, snack);
        return updatedSnack != null ? ResponseEntity.ok(updatedSnack) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSnack(@PathVariable Long id) {
        snackService.deleteSnack(id);
        return ResponseEntity.noContent().build();
    }
}