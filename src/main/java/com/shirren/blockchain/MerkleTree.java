package com.shirren.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * <p>The Merkle Tree type serves as an abstraction for a Merkle tree which needs
 * to be a balanced b-tree.</p>
 */
public final class MerkleTree {

    private String value;
    private MerkleTree left;
    private MerkleTree right;

    /**
     * <p>Each node captures some state which is used in the merkle tree
     * to validate the transaction lists in the Block.</p>
     *
     * @param state or hash value of the node.
     */
    public MerkleTree(String state) {
        this.value = state;
    }

    /**
     * <p>A tree can be created with a hash and two child nodes.</p>
     *
     * @param state or hash value of the node.
     * @param left branch.
     * @param right branch.
     */
    public MerkleTree(String state, MerkleTree left, MerkleTree right) {
        this(state);
        this.left = left;
        this.right = right;
    }

    /**
     * <p>Get the underlying hash stored in the merkle node.</p>
     *
     * @return underlying hash value.
     */
    public String getValue() { return this.value; }

    /**
     * <p>Return the left node.</p>
     *
     * @return left node.
     */
    public MerkleTree getLeft() { return this.left; }

    /**
     * <p>Return the right node.</p>
     *
     * @return right node.
     */
    public MerkleTree getRight() { return this.right; }

    /**
     * <p>Take two nodes and fold them by creating a new parent for the
     * two nodes and computing a hash by adding the hash of each node
     * and then hashing this value.</p>
     *
     * @return MerkleTree which folds two Merkle trees.
     * @throws NoSuchAlgorithmException if an invalid hashing algorithm is used.
     */
    public static MerkleTree fold(MerkleTree left, MerkleTree right) throws NoSuchAlgorithmException {
        String valueToHash = left.getValue() + right.getValue();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(valueToHash.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);
        return new MerkleTree(encoded, left, right);
    }

    /**
     * <p>Merkle Tree is a Btree, and for it to be balanced as each leaf node
     * it should either have no children or two for it to be balanced.</p>
     *
     * @return true if balanced else false.
     */
    public boolean isBalanced() {
        if (getHeight(this) == -1) {
            return false;
        }
        return true;
    }

    /**
     * <p>Returns the height of each branch of tree. A tree with a single node
     * has a height of 1, a balanced tree with a depth of 1 has a height of 2.
     * All imbalanced trees are of height -1.</p>
     *
     * @param node node
     * @return height of tree.
     */
    public int getHeight(MerkleTree node) {
        if (node == null) {
            return 0;
        }
        int left = getHeight(node.left);
        int right = getHeight(node.right);

        if (left == -1 || right == -1) {
            return -1;
        }
        if (Math.abs(left - right) > 0) {
            return -1;
        }

        return Math.max(left, right) + 1;
    }

    /**
     * <p>This is provided a convenience for visualising a Merkle tree node and
     * for also creating the hash of the block.</p>
     *
     * @return the unique hash of the node.
     */
    @Override
    public String toString() {
        return this.getValue();
    }
}
