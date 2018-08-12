package com.shirren.blockchain;

import com.shirren.blockchain.exceptions.BlockChainTamperedException;
import com.shirren.blockchain.exceptions.InvalidBlockException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>The blockchain manages blocks by accepting transactions. When a predetermined threshold
 * has been reached, then a block is created.</p>
 */
public class BlockChain {

    public static final int BLOCK_SIZE = 10;
    public List<Block> chain = new ArrayList<>();

    /**
     * <p>When we create the chain we add the genesis block to the
     * chain.</p>
     */
    public BlockChain() {
        chain.add(genesisBlock());
    }

    /**
     * <p>We add a block to the tail of the chain iff the entire chain has not been
     * tampered with. We check for tampering by going through each block in the list
     * validating the previous hash stored in each block against the recomputed
     * hash for the prior block in the sequence.</p>
     *
     * @param block we would like to add to the chain.
     * @return the chain to enable a fluent style interface.
     */
    public BlockChain addBlock(Block block) {
        if (!isValid()) {
            throw new BlockChainTamperedException();
        }
        Block lastBlock = this.chain.get(this.chain.size() - 1);
        if (lastBlock.getHash().equals(block.getPreviousHash())) {
            this.chain.add(block);
            return this;
        } else {
            throw new InvalidBlockException();
        }
    }

    /**
     * <p>We can check the validity state of the block chain at any given point in time.</p>
     *
     * @return true if the chain has not been tampered with else false.
     */
    public boolean isValid() {
        String previousHash = null;
        for (Block block : chain) {
            String currentHash = block.getHash();
            if (!currentHash.equals(previousHash)) {
                return false;
            }
            previousHash = currentHash;
        }
        return true;
    }

    /**
     * <p>When we create the chain we add the genesis block.</p>
     *
     * @return genesis block.
     */
    private Block genesisBlock() {
        int count = chain.size();
        String previousHash = "root";
        Block block = new Block(count, System.currentTimeMillis(), previousHash);
        return block;
    }
}
