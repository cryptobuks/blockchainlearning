package com.shirren.blockchain;

public interface Tx {

    /**
     * <p>Compute a has for the transaction and return it. This hash changes
     * if the transaction itself changes.</p>
     *
     * @return hash for the transaction.
     */
    String hash();
}
