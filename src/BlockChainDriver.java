import java.util.Scanner;

public class BlockChainDriver {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Invalid number of arguments. Please enter 1 number");
        }

        Scanner object = new Scanner(System.in);

        int initialAmount = Integer.parseInt(args[0]);

        BlockChain blockchain = new BlockChain(initialAmount);
        System.out.print(blockchain.toString());

        while (true) {
            System.out.println("");
            System.out.print("Command? ");
            String input = object.nextLine();
            switch (input) {
                case "mine": {
                    System.out.print("Amount transferred? ");
                    String argument = object.nextLine();
                    int transAmount = Integer.parseInt(argument);
                    Block block = blockchain.mine(transAmount);
                    System.out.printf("amount = %d, nonce = %d%n", transAmount, block.getNonce());
                    break;
                }
                case "append": {
                    System.out.print("Amount transferred? ");
                    String arg1 = object.nextLine();
                    int transAmount = Integer.parseInt(arg1);
                    System.out.print("Nonce? ");
                    String arg2 = object.nextLine();
                    long nonce = Long.parseLong(arg2);
                    try {
                        Block block = blockchain.mine(transAmount, nonce);
                        blockchain.append(block);
                    } catch (Exception e){
                        System.err.println("The hash is invalid with this nonce value. Please try another nonce value");
                    }
                    break;
                }
                case "remove":
                    blockchain.removeLast();
                    break;
                case "check":
                    if (blockchain.isValidBlockChain()) {
                        System.out.println("Chain is valid!");
                    } else {
                        System.out.println("Chain is invalid.");
                    }
                    break;
                case "report":
                    blockchain.printBalances();
                    break;
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
                case "quit":
                    break;
                default:
                    System.err.print("Invalid command.");
                    break;
            }
            System.out.println("");
            System.out.print(blockchain.toString());
        }
    }
}
