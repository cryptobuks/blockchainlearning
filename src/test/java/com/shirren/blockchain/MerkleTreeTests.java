package com.shirren.blockchain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MerkleTreeTests {

    @Test
    public void isBalancedWithSingleNodeReturnsTrue() {
        MerkleTree node = new MerkleTree("root");
        assertTrue(node.isBalanced());
    }

    @Test
    public void isBalancedWithSingleChildNodesReturnsFalse() {
        MerkleTree node = new MerkleTree("root", new MerkleTree("l1"), null);
        assertFalse(node.isBalanced());
    }

    @Test
    public void isBalancedWithChildNodesReturnsTrue() {
        MerkleTree node = new MerkleTree("root", new MerkleTree("l1"), new MerkleTree("r1"));
        assertTrue(node.isBalanced());
    }
}
