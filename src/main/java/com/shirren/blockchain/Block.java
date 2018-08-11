package com.shirren.blockchain;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class Block {

    /**
     * A block is always part of a sequential chain. The timestamp
     * and index help us identify where this block sits in that sequence.
     */
    private long timeStamp;
    private int index;

    /**
     * <p>We can store multiple transactions in a block. These transactions once
     * added to the block cannot be altered. A consensus protocol like mining in
     * Bitcoin is used to ensure that the transactions are not tampered with.</p>
     */
    private List<Tx> transactions;

    /**
     * The merkle tree is used to check ti see if the transactions in the
     * block have been tampered. The merkle tree is built when the transactions
     * are added to the block, this is how the immutability of the blocks transactions
     * are preserved.
     */
    private MerkleTree merkleRoot;

    /**
     * We store the hash of the previous block. ???
     */
    private String previousHash;

    private String hash;
    private String nonce = "0000";

    /**
     * <p>When we create a block we need these 3 invariants to be satisfied or the
     * block is invalid.</p>
     *
     * @param index of block in chain.
     * @param timeStamp at which block is created.
     * @param previousHash hash of previous block in chain.
     */
    public Block(int index, long timeStamp, String previousHash) {
        this.index = index;
        this.timeStamp = timeStamp;
        this.previousHash = previousHash;
    }

    /**
     * <p>Return the unique hash of this block.</p>
     *
     * @return hash of this block.
     */
    public String getHash() {
        return this.hash;
    }

    /**
     * <p>Return the hash of the previous block in the chain.</p>
     *
     * @return hash of previous block.
     */
    public String getPreviousHash() {
        return this.previousHash;
    }

    /**
     * <p>We compute a hash for the block using all its properties including the hash
     * of the Merkle tree as well. This hash is used to check for block tampering as
     * blocks are immutable after they are created and added to the chain.</p>
     *
     * @throws NoSuchAlgorithmException
     */
    private void computeHash() throws NoSuchAlgorithmException {
        Gson parser = new Gson();
        String serializedData = parser.toJson(transactions);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String sumValue = timeStamp + index + merkleRoot.toString() + serializedData + nonce + previousHash;
        byte[] hash = digest.digest(sumValue.getBytes(StandardCharsets.UTF_8));
        this.hash = Base64.getEncoder().encodeToString(hash);
    }
}
