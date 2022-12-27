package blackjack;

import blackjack.gameProcess.Game;
import blackjack.player.Player;
import blackjack.player.Statistics;
import utils.Design;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        Scanner name_sc = new Scanner(System.in);
        Game bjack;
        Player player = new Player();
        Statistics stats = new Statistics(player);
        int option, bet;
        boolean name_entered = false;
        try {
            do {
                System.out.println(
                        Design.RED
                                + "\n-------------------------------------------" +
                                "\n🃏🃏🎰 Classic and Simple Black Jack 🎰🃏🃏\n" +
                                "-------------------------------------------\n");
                System.out.println(Design.RESET + "(1) - Play");
                System.out.println("(2) - Top-up Balance");
                System.out.println("(3) - View Balance");
                System.out.println("(4) - Help (Tutorial)");
                System.out.println("(5) - Statistics");
                System.out.println("(0) - Exit");
                while (true) {
                    Scanner scan = new Scanner(System.in);
                    System.out.print("Option: ");
                    if (scan.hasNextInt()) {
                        option = scan.nextInt();
                        scan.nextLine();
                        break;
                    } else if (scan.hasNext()) {
                        System.out.println("Error: Option is String not Integer.");
                    } else {
                        System.out.println("Error: Option is not Integer.");
                    }
                }
                switch (option) {
                    case 1:
                        if (!name_entered) {
                            System.out.print("\nEnter your name (Default: 'Unknown player'): ");
                            player.setName(name_sc.nextLine());
                            name_entered = true;
                        }
                        if (player.getMoney() >= Game.MIN_BET) {
                            System.out.println(Design.YELLOW + "\n>>> Balance: PLN " + player.getMoney() + " <<<" + Design.RESET);
                            System.out.println("\n(min: " + Game.MIN_BET + " | all-in: " + player.getMoney() + ")");
                            while (true) {
                                Scanner scan = new Scanner(System.in);
                                System.out.print("Bet: ");
                                if (scan.hasNextInt()) {
                                    bet = scan.nextInt();
                                    scan.nextLine();
                                    break;
                                } else if (scan.hasNext()) {
                                    System.out.println("Error: Bet is String not Integer.");
                                } else {
                                    System.out.println("Error: Bet is not Integer.");
                                }
                            }
                            //Check if bet is set properly (withing the proper range)
                            while (bet > player.getMoney() || bet < Game.MIN_BET) {
                                System.out.print(Design.RED + "Attention!! You can bet only between " + Game.MIN_BET +
                                        " and " + player.getMoney() + ": " + Design.RESET);
                                bet = sc.nextInt();
                            }
                            player.setBet(bet);
                            bjack = new Game(player, stats);
                            bjack.run();
                        } else
                            System.out.println(Design.RED
                                    + "\n You don't have enough money to play. Top-up your account! (option 2)"
                                    + Design.RESET);
                        break;
                    case 2:
                        if (player.getMoney() < Game.MIN_BET) {
                            player.resetMoney();
                            System.out.println(Design.BLUE
                                    + "\nYour balance successfully renewed and now you have PLN " +
                                    player.getDefaultMoney() + Design.RESET);
                        } else
                            System.out.println(Design.RED + "\nAttention!! You have enough money to continue playing"
                                    + Design.RESET);
                        break;
                    case 3:
                        System.out.println(Design.YELLOW + "\n>>> Your balance: PLN " + player.getMoney() + " <<<"
                                + Design.RESET);
                        break;
                    case 4:
                        File file = new File("src/blackjack/Help.txt");
                        Scanner txt_sc = new Scanner(file);
                        while (txt_sc.hasNextLine()) {
                            System.out.println(txt_sc.nextLine());
                            Thread.sleep(400);
                        }
                        break;
                    case 5:
                        if (!name_entered) {
                            System.out.print("\nEnter your name (Default: 'Unknown player'): ");
                            player.setName(name_sc.nextLine());
                            name_entered = true;
                        }
                        Scanner stat_sc = new Scanner(stats.toString());
                        while (stat_sc.hasNextLine()) {
                            System.out.println(stat_sc.nextLine());
                            Thread.sleep(100);
                        }
                    case 0:
                        break;
                    default:
                        System.out.println(Design.RED_BACKGROUND + "\nWrong option: " + option + Design.RESET);
                        break;
                }
            } while (option != 0);
        } catch (InputMismatchException ignored) {
        }
        System.out.println("\nIt's always hard to say goodbye!\nRustam Karimov");
    }
}