/*
 * Rustam Karimov
 * 17915
 */

package blackjack;

import java.io.*;

import blackjack.gameProcess.Game;
import blackjack.player.Player;
import utils.OutputColors;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        Game bjack;
        Player player = new Player();
        int cond, bet = 0;
        boolean name_entered = false;
        try {
            do {
                System.out.println(
                        OutputColors.RED
                                + "\n-------------------\n🃏🃏🎰 Black Jack 🎰🃏🃏\n-------------------\n");
                System.out.println(OutputColors.RESET + "(1) - Play");
                System.out.println("(2) - Top-up Balance");
                System.out.println("(3) - View Balance");
                System.out.println("(4) - Help (Tutorial)");
                System.out.println("(0) - Exit");
                System.out.print("\nOption: ");
                cond = sc.nextInt();
                switch (cond) {
                    case 1:
                        if (!name_entered) {
                            System.out.print("\nEnter your name: ");
                            player.setName(sc.next());
                            name_entered = true;
                        }
                        if (player.getMoney() >= Game.MIN_BET) {
                            System.out
                                    .println(OutputColors.YELLOW + "\n>>> Balance: PLN " + player.getMoney() + " <<<"
                                            + OutputColors.RESET);

                            System.out
                                    .println("\n(min: " + Game.MIN_BET + " | all-in: " + player.getMoney() + ")");
                            System.out.print("Your bet: ");
                            bet = sc.nextInt();
                            while (bet > player.getMoney() || bet < Game.MIN_BET) {
                                System.out.print(OutputColors.RED + "Attention!! You can bet only between "
                                        + Game.MIN_BET + " and "
                                        + player.getMoney() + ": "
                                        + OutputColors.RESET);
                                bet = sc.nextInt();
                            }

                            player.setBet(bet);

                            bjack = new Game(player);
                            bjack.run();
                        } else
                            System.out.println(OutputColors.RED
                                    + "\n You don't have enough money to play. Top-up your account! (option 2)"
                                    + OutputColors.RESET);
                        break;

                    case 2:
                        if (player.getMoney() < Game.MIN_BET) {
                            player.resetMoney();
                            System.out.println(OutputColors.BLUE
                                    + "\nYour balance successfully renewed and now you have PLN "
                                    + player.getDefaultMoney() + "."
                                    + OutputColors.RESET);
                        } else
                            System.out.println(OutputColors.RED
                                    + "\nAttention!! You have enough money to continue playing"
                                    + OutputColors.RESET);
                        break;

                    case 3:
                        System.out
                                .println(OutputColors.YELLOW + "\n>>> Your balance: PLN " + player.getMoney() + " <<<"
                                        + OutputColors.RESET);
                        break;
                    case 4:
                        File file = new File(
                                "src\\blackjack\\Help.txt");
                        Scanner txt_sc = new Scanner(file);
                        while (txt_sc.hasNextLine()) {
                            System.out.println(txt_sc.nextLine());
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println(
                                OutputColors.RED_BACKGROUND + "\nWrong option: " + cond
                                        + OutputColors.RESET);
                        break;

                }
            } while (cond != 0);
        } catch (InputMismatchException e) {
        }
        System.out.println("\nIt's always hard to say goodbye!\nRustam Karimov");
    }
}
