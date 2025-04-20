package com.absolutecinema.service;

import com.absolutecinema.entity.LoyaltyAccount;
import com.absolutecinema.repository.LoyaltyAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoyaltyService {

    @Autowired
    private final LoyaltyAccountRepository loyaltyAccountRepository;
    
    public LoyaltyService(LoyaltyAccountRepository loyaltyAccountRepository) {
        this.loyaltyAccountRepository = loyaltyAccountRepository;
    }

    public Optional<LoyaltyAccount> getLoyaltyAccountByUserId(Long userId) {
        return loyaltyAccountRepository.findById(userId);
    }

    public LoyaltyAccount createLoyaltyAccount(LoyaltyAccount loyaltyAccount) {
        return loyaltyAccountRepository.save(loyaltyAccount);
    }

    public LoyaltyAccount updateLoyaltyAccount(LoyaltyAccount loyaltyAccount) {
        return loyaltyAccountRepository.save(loyaltyAccount);
    }

    public void deleteLoyaltyAccount(Long userId) {
        loyaltyAccountRepository.deleteById(userId);
    }

    public void addPoints(Long userId, int points) {
        LoyaltyAccount account = loyaltyAccountRepository.findById(userId).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setPoints(account.getPoints() + points);
        loyaltyAccountRepository.save(account);
    }

    public void redeemPoints(Long userId, int points) {
        LoyaltyAccount account = loyaltyAccountRepository.findById(userId).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getPoints() >= points) {
            account.setPoints(account.getPoints() - points);
            loyaltyAccountRepository.save(account);
        } else {
            throw new RuntimeException("Insufficient points");
        }
    }
    
    public List<LoyaltyAccount> getAllLoyaltyAccounts() {
        return loyaltyAccountRepository.findAll();
    }

}