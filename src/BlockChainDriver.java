import java.util.Scanner;

/**
 * @author Jinny Eo, Audrey Trinh
 * <p>
 * Employs BlockChain to carry out different user commands to manipulate a blockchain.
 */

public class BlockChainDriver {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Invalid number of arguments. Please enter 1 number");
            return;
        }

        Scanner object = new Scanner(System.in);
        // the initialAmount is set by the first argument in the command line
        int initialAmount = Integer.parseInt(args[0]);
        // intialize blockchain with the initialAmount
        BlockChain blockchain = new BlockChain(initialAmount);
        // print first block after blockchain is initialized
        System.out.print(blockchain.toString());

        while (true) {
            System.out.println("");
            // ask user for command
            System.out.print("Command? ");
            String command = object.nextLine();
            // if user command is quit
            if (command.equals("quit")) {
                break;
            }
            switch (command) {
                // if user command is mine
                case "mine": {
                    // prompts amount/data
                    System.out.print("Amount transferred? ");
                    String amountStr = object.nextLine();
                    if (!isNumeric(amountStr)) {
                        // check if amount is integer
                        System.out.println("Invalid amount. Should be an integer");
                        continue;
                    }
                    int transAmount = Integer.parseInt(amountStr);
                    // call the mine method to create a block with the amount
                    Block block = blockchain.mine(transAmount);
                    // print amount and nonce that is calculated through getNonce()
                    System.out.printf("amount = %d, nonce = %d%n", transAmount, block.getNonce());
                    break;
                }
                // if user command is append
                case "append": {
                    // prompts amount
                    System.out.print("Amount transferred? ");
                    String amountStr = object.nextLine();
                    if (!isNumeric(amountStr)) {
                        // check if amount is integer
                        System.out.println("Invalid amount. Should be an integer");
                        continue;
                    }
                    int transAmount = Integer.parseInt(amountStr);
                    // prompts nonce
                    System.out.print("Nonce? ");
                    String nonceStr = object.nextLine();
                    if (!isNumeric(nonceStr)) {
                        // check if nonce is integer/long
                        System.out.println("Invalid nonce. Should be a long integer with no space before");
                        continue;
                    }
                    long nonce = Long.parseLong(nonceStr);
                    try {
                        Block block = blockchain.mine(transAmount, nonce);
                        // if valid block cannot be mined using given amount and nonce
                        if (block == null) {
                            continue;
                        }
                        blockchain.append(block);
                    } catch (IllegalArgumentException e) { // if the transaction is valid
                        System.err.println("This transaction is invalid. Cannot append this block to the chain");
                        blockchain.removeLast();
                    }
                    break;
                }
                // if user command is remove
                case "remove":
                    blockchain.removeLast();
                    break;
                // if user command is check
                case "check":
                    if (blockchain.isValidBlockChain()) {
                        System.out.println("Chain is valid!");
                    } else {
                        System.out.println("Chain is invalid.");
                    }
                    break;
                // if user command is report
                case "report":
                    blockchain.printBalances();
                    break;
                // if user command is help
                case "help":
                    System.out.println("""
                            Valid commands:
                                mine: discovers the nonce for a given transaction
                                append: appends a new block onto the end of the chain
                                remove: removes the last block from the end of the chain
                                check: checks that the block chain is valid
                                report: reports the balances of Alexis and Blake
                                help: prints this list of commands
                                quit: quits the program""");
                    break;
                // if user enter anything else
                default:
                    System.err.print("Invalid command.");
                    break;
            }
            System.out.println("");
            System.out.print(blockchain.toString());
        } // while
    } // main

    /**
     * Returns true if str is a numeric/integer string
     */
    public static boolean isNumeric(String str) {
        if (str.length() == 0) {
            return false;
        }

        // if str length is 1 and it is not a number
        if (str.length() == 1 && !Character.isDigit(str.charAt(0))) {
            return false;
        }

        // if the first character is not a negative sign or a number
        if (str.charAt(0) != '-' && !Character.isDigit(str.charAt(0))) {
            return false;
        }

        // loop through the string to check each character
        for (int i = 1; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    } // isNumeric
}
