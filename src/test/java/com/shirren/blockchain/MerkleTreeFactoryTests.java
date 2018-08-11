package com.shirren.blockchain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MerkleTreeFactoryTests {

    private MerkleTreeFactory factory = new MerkleTreeFactory();

    @Test
    public void buildMerkleTreeFromEmptyTransactionsShouldThrowException() {
        assertThrows(InvalidTransactionListException.class, () -> {
            factory.build(new ArrayList<>());
        });
    }

    @Test
    public void buildMerkleTreeFromSingleTransactionShouldReturnMerkleTreeWithHashedValue() {
        List<Tx> transactions = new ArrayList<>();
        transactions.add(new Transaction("test"));
        MerkleTree tree = factory.build(transactions);
        assertNotEquals("test", tree.getValue());
    }

    @Test
    public void buildMerkleTreeFromSingleTransactionShouldReturnMerkleTreeWithThreeNodes() {
        List<Tx> transactions = new ArrayList<>();
        transactions.add(new Transaction("test"));
        MerkleTree tree = factory.build(transactions);
        assertNotNull(tree.getLeft());
        assertNotNull(tree.getRight());
        assertNotEquals(tree.getLeft(), tree.getRight());
    }

    @Test
    public void buildMerkleTreeFromTwoTransactionsShouldReturnMerkleTreeWithThreeNodes() {
        List<Tx> transactions = new ArrayList<>();
        transactions.add(new Transaction("1"));
        transactions.add(new Transaction("2"));
        MerkleTree tree = factory.build(transactions);
        assertEquals(2, tree.getHeight(tree));
        assertTrue(tree.isBalanced());
    }

    @Test
    public void buildMerkleTreeFromThreeTransactionsShouldReturnMerkleTreeWithSevenNodes() {
        List<Tx> transactions = new ArrayList<>();
        transactions.add(new Transaction("1"));
        transactions.add(new Transaction("2"));
        transactions.add(new Transaction("3"));
        MerkleTree tree = factory.build(transactions);
        assertEquals(3, tree.getHeight(tree));
        assertTrue(tree.isBalanced());
    }
}
