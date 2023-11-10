import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.ByteBuffer;
import java.util.Random;

/**
 * @author Jinny Eo, Audrey Trinh
 * <p>
 * Employs Hash to formulate a singular block to be used in the blockchain.
 */

public class Block {
    // +--------+------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * number of the block in the blockchain
     */
    int numBlk;

    /**
     * amount transferred between the two parties
     */
    int data;

    /**
     * hash of previous block in the chain
     */
    Hash prevHash;

    /**
     * nonce of the block
     */
    long nonce;

    /**
     * hash of the block
     */
    Hash currHash;

    // +-------------+--------------------------------------------------
    // | Constructor |
    // +-------------+

    /**
     * Creates a new block from the specified parameters,
     * performing the mining operation to discover the nonce and
     * hash for this block given these parameters
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.numBlk = num;
        this.data = amount;
        this.prevHash = prevHash;

        // loop for nonce mining
        while (true) {
            // randomly generate a nonce value
            Random rand = new Random();
            long tempNonce = rand.nextLong();
            // calculate Hash with that nonce value
            Hash tempHash = calculateHash(tempNonce);
            // check if the calculated Hash is valid
            if (tempHash.isValid()) {
                this.currHash = tempHash;
                this.nonce = tempNonce;
                break;
            }
        }
    }

    /**
     * Creates a new block from the specified parameters, using the
     * provided nonce and additional parameters to generate hash for the block
     */

    public Block(int num, int amount, Hash prevHash, long nonce) throws Exception {
        this.numBlk = num;
        this.data = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
        Hash tempHash = calculateHash(nonce);
        // throw exception if the hash generated is invalid
        if (!tempHash.isValid()) {
            throw new Exception("Invalid hash");
        } else {
            this.currHash = tempHash;
        }
    }

    // +---------+------------------------------------------------------
    // | Methods |
    // +---------+

    /**
     * returns the number of the block
     */
    public int getNum() {
        return this.numBlk;
    }

    /**
     * returns the amount transferred that is recorded in this block
     */
    public int getAmount() {
        return this.data;
    }

    /**
     * returns the nonce of this block
     */
    public long getNonce() {
        return this.nonce;
    }

    /**
     * returns the hash of the previous block in the blockchain
     */
    public Hash getPrevHash() {
        return this.prevHash;
    }

    /**
     * returns the hash of this block
     */
    public Hash getHash() {
        return this.currHash;
    }

    /**
     * returns a string representation of the block
     */
    public String toString() {
        return "Block " + this.numBlk + " (Amount: " + this.data + ", Nonce: " + this.nonce
                + ", prevHash: " + this.prevHash + ", hash: " + this.currHash + ")";
    }

    /**
     * calculate hash using this block's numblk, data and
     * the passed in nonce
     */
    public Hash calculateHash(long nonce) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        // creates a ByteBuffer of Integer size in bytes
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);

        // update block's number
        buffer.putInt(this.numBlk);
        buffer.flip();
        md.update(buffer);

        // Update data contained in the block
        buffer.clear();
        // puts the given value into the ByteBuffer
        buffer.putInt(this.data);
        buffer.flip();
        md.update(buffer);

        // update prevHash if it is not the first block
        if (this.numBlk != 0) {
            md.update(this.prevHash.getData());
        }

        // update nonce
        buffer = ByteBuffer.allocate(Long.BYTES);
        // puts the given value into the ByteBuffer
        buffer.putLong(nonce);
        buffer.flip();
        md.update(buffer);

        return new Hash(md.digest());
    }

}
