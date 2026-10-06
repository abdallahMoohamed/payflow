package com.abdallah.payflow.wallet.factory;

import com.abdallah.payflow.user.entity.User;
import com.abdallah.payflow.wallet.entity.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WalletFactory {
    public Wallet create(User user) {
        return create(user, "USD");
    }

    public Wallet create(User user, String currency) {
        // Validate input parameters
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency cannot be empty");
        }
        // Create a new Wallet entity and set its properties
        Wallet newWallet = new Wallet();
        newWallet.setUser(user);
        newWallet.setBalance(BigDecimal.ZERO);
        newWallet.setCurrency(currency.toUpperCase());
        return newWallet;
    }
}
