package com.shirren.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * <p>A simple representation for a transaction for the purpose of the
 * exercise.</p>
 */
public class Transaction implements Tx {

    private String value;

    /**
     * <p>Transaction holds a simple value.</p>
     *
     * @param value
     */
    public Transaction(String value) {
        this.value = value;
    }

    /**
     * <p>Compute a has for the transaction and return it. This hash changes
     * if the transaction itself changes.</p>
     *
     * @return hash for the transaction.
     */
    @Override
    public String hash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(this.value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException ex) {
            return this.value;
        }
    }
}
