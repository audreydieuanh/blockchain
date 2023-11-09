import java.security.NoSuchAlgorithmException;

/**
 * Nested node static class
 */
class Node {
    Block block;
    Node next;

    Node(Block block) {
        this.block = block;
        this.next = null;
    }

    Block getBlock() {
        return this.block;
    }
}

/**
 * A singly-linked structure represents blocks linked together in linear order.
 *
 * @author Audrey Trinh, Jinny Eo
 */
public class BlockChain {
    // +--------+------------------------------------------------------
    // | Fields |
    // +--------+
    /**
     * Pointer to the first block in the chain
     */
    Node first;

    /**
     * Pointer to the last block in the chain
     */
    Node last;

    /**
     * Alexis's balance
     */
    int alexis;

    /**
     * Blake's balance
     */
    int blake;

    // +-------------+--------------------------------------------------
    // | Constructor |
    // +-------------+

    /**
     * Creates a new blockchain that possess
     * a single block that starts with the given initial amount
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        // create a new node with num as 0, amount as the initial amount and no prevHash
        Node newNode = new Node(new Block(0, initial, null));
        // assign first pointer and last pointer of chain to new node
        this.first = newNode;
        this.last = newNode;
    } // constructor

    /**
     * Mines a new candidate block with the given amount
     * to be added to the end of the chain
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        // the new candidate block should be after the last block
        return new Block(this.last.getBlock().getNum() + 1, amount,
                this.last.getBlock().getHash());
    } // mine

    /**
     * Mines a new candidate block with the given amount and nonce
     * to be added to the end of the chain.
     * If no valid block can be mined using given amount and nonce,
     * throw an Exception
     */
    public Block mine(int amount, long nonce) throws Exception {
        Block mineBlock = new Block(this.last.getBlock().getNum() + 1, amount,
                this.last.getBlock().getHash(), nonce);
        // if the mined block is invalid
        if (!mineBlock.getHash().isValid()) {
            // throw an exception
            throw new Exception("The hash is invalid with this nonce value. Please try another nonce value");
        }
        return mineBlock;
    } // mine

    /**
     * Return the size of the blockchain
     */
    public int getSize() {
        return last.getBlock().getNum() + 1;
    } // getSize

    /**
     * Add given block (blk) to the blockchain
     */
    public void append(Block blk) throws IllegalArgumentException {
        // create a new node with the given block
        Node newNode = new Node(blk);
        // if the blockchain is empty
        if (this.first == null) {
            this.first = newNode;
            this.last = newNode;
        } else {
            // add the new node to the end
            this.last.next = newNode;
            this.last = this.last.next;
        }
        this.last.next = null;
        // if the chain is invalid due to the block
        if (!isValidBlockChain()) {
            // throw an exception
            throw new IllegalArgumentException("Invalid transaction");
        }
    } // append

    /**
     * Removes the last block from the chain
     */
    public boolean removeLast() {
        // if the chain only contains a single block
        if (this.last == this.first) {
            // do nothing and return false
            return false;
        }

        // find the second-to-last block in the chain
        Node secondLast = this.first;
        while (secondLast.next.next != null) {
            secondLast = secondLast.next;
        }
        // delete the last block in the chain
        secondLast.next = null;
        this.last = secondLast;
        return true;
    } // removeLast

    /**
     * Returns the hash of the last block in the chain.
     */
    public Hash getHash() {
        return this.last.getBlock().getHash();
    } // getHash

    /**
     * Walks the blockchain and ensures that its blocks are consistent and valid
     */
    public boolean isValidBlockChain() {
        Node currentNode = this.first.next;
        // alexis initial balance is the amount in the first block
        this.alexis = this.first.getBlock().getAmount();
        // blake initial balance is zero
        this.blake = 0;
        // loop through the chain
        while (currentNode != null) {
            // update alexis' balance
            this.alexis += currentNode.getBlock().getAmount();
            // update blake's balance
            this.blake += currentNode.getBlock().getAmount() * (-1);
            // chain is invalid if alexis' or blake's balance is smaller than zero
            if (this.alexis < 0 || this.blake < 0) {
                return false;
            }
            currentNode = currentNode.next;
        } // while

        return (this.alexis >= 0 && this.blake >= 0);
    } // isValidBlockChain

    /**
     * Prints Alexis’s and Blake’s respective balances
     */
    public void printBalances() {
        // calling isValidBlockChain to loop through the chain and update
        // alexis' and blake's balance
        isValidBlockChain();
        System.out.printf("Alexis: %d, Blake: %d%n",
                this.alexis, this.blake);
    } // printBalances

    /**
     * Returns a string representation of the BlockChain
     */
    public String toString() {
        Node currentNode = this.first;
        StringBuilder str = new StringBuilder();
        // loop through the chain
        while (currentNode != null) {
            // add the string representation of each block
            // to the string representation of the chain
            str.append(currentNode.getBlock().toString());
            str.append("\n");
            currentNode = currentNode.next;
        } // while
        return str.toString();
    } // toString
}
