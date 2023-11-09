import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.ByteBuffer;
import java.util.Random;

public class Block {

    int numBlk;
    int data;
    Hash prevHash;
    long nonce;
    Hash currHash;

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

    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.numBlk = num;
        this.data = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
        this.currHash = calculateHash(nonce);
    }

    public int getNum() {
        return this.numBlk;
    }

    public int getAmount() {
        return this.data;
    }

    public long getNonce() {
        return this.nonce;
    }

    public Hash getPrevHash() {
        return this.prevHash;
    }

    public Hash getHash() {
        return this.currHash;
    }

    public String toString() {
        return "Block " + this.numBlk + " (Amount: " + this.data + ", Nonce: " + this.nonce
                + ", prevHash: " + this.prevHash + ", hash: " + this.currHash + ")";
    }

    public Hash calculateHash(long nonce) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);

        // update block's number
        buffer.putInt(this.numBlk);
        buffer.flip();
        md.update(buffer);

        // Update data contained in the block
        buffer.clear();
        buffer.putInt(this.data);
        buffer.flip();
        md.update(buffer);

        // update prevHash if it is not the first block
        if (this.numBlk != 0) {
            md.update(this.prevHash.getData());
        }

        // update nonce
        buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(nonce);
        buffer.flip();
        md.update(buffer);

        return new Hash(md.digest());
    }

}
