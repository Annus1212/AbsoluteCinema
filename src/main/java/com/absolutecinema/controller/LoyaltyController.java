package com.absolutecinema.controller;

import com.absolutecinema.entity.LoyaltyAccount;
import com.absolutecinema.service.LoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loyalty")
public class LoyaltyController {

    @Autowired
    private LoyaltyService loyaltyService;

    @GetMapping("/{userId}")
    public ResponseEntity<LoyaltyAccount> getLoyaltyAccount(@PathVariable Long userId) {
        LoyaltyAccount account = loyaltyService.getLoyaltyAccountByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Loyalty account not found for user ID: " + userId));
        return ResponseEntity.ok(account);
    }

    @PostMapping("/addPoints")
    public ResponseEntity<LoyaltyAccount> addPoints(@RequestParam Long userId, @RequestParam int points) {
        loyaltyService.addPoints(userId, points);
        LoyaltyAccount updatedAccount = loyaltyService.getLoyaltyAccountByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Loyalty account not found for user ID: " + userId + " after adding points"));
        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LoyaltyAccount>> getAllLoyaltyAccounts() {
        List<LoyaltyAccount> accounts = loyaltyService.getAllLoyaltyAccounts();
        return ResponseEntity.ok(accounts);
    }
}