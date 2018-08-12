package com.shirren.blockchain;

import com.shirren.blockchain.exceptions.InvalidTransactionListException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MerkleTreeFactory {

    /**
     * <p>Take a list of transactions and build a balanced b-tree
     * from it. If the transaction number if even use the last item to
     * re-balance the tree.</p>
     *
     * @param transactions stored in the block
     * @return balanced b-tree
     */
    public MerkleTree build(List<Tx> transactions) {
        if (transactions.isEmpty()) {
            throw new InvalidTransactionListException();
        }
        // We want a transaction list of an even length so we can
        // build a balanced b-tree.
        if (transactions.size() % 2 != 0) {
            transactions.add(transactions.get(transactions.size() - 1));
        }
        List<MerkleTree> nodes = transactions.stream()
                .map(tx -> new MerkleTree(tx.hash()))
                .collect(Collectors.toList());
        return this.fold(nodes).get(0);
    }

    /**
     * <p>We build a Merkle tree using the list of Merkle tree nodes. This
     * achieved by taking 2 tree nodes from the list at a time, then we call
     * MerkleTree.fold which take these 2 entries at a time and creates a single
     * tree node which becomes the parent of these 2 nodes.</p>
     *
     * @param nodes list of nodes to build the Merkle Tree from.
     * @return Folded list of tree nodes.
     */
    private List<MerkleTree> fold(List<MerkleTree> nodes) {
        if (nodes.size() > 1) {
            try {
                List<MerkleTree> foldedList = new ArrayList<>();
                for (int i = 0; i < nodes.size(); i += 2) {
                    MerkleTree left = nodes.get(i);
                    MerkleTree right = nodes.get(i + 1);
                    foldedList.add(MerkleTree.fold(left, right));
                }
                nodes = fold(foldedList);
            } catch (NoSuchAlgorithmException ex) {
                // Log the exception here.
            }
        }
        return nodes;
    }
}
