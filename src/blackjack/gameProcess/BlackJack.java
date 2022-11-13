/*
 * Rustam Karimov
 * 17915
 */

package blackjack.gameProcess;

import blackjack.deck.*;
import blackjack.player.*;
import utils.OutputColors;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BlackJack {
    private final CardBox Deck;
    private Player player;
    private final Player dealer;
    public static final int MAX_CARDS_VALUE = 21;
    public static final int MIN_BET = 2;
    private static final int MIN_DEFAULT_CARDS = 104;
    private static final int MILLISECONDS_SLEEP = 1200;
    private boolean active;
    private boolean isTurnPlayer;

    public BlackJack() {
        Deck = new CardBox(MIN_DEFAULT_CARDS);
        Deck.shuffle();
        dealer = new Player(); // dealer.name = "Dealer"
        active = true;
        isTurnPlayer = true;
    }

    public BlackJack(Player player) {
        Deck = new CardBox(MIN_DEFAULT_CARDS);
        Deck.shuffle();
        dealer = new Player(); // dealer.name = "Dealer", dealer.bet = 0, dealer.money = 1000;
        this.player = player; // player.name = "Mark", player.bet = 5, player.money = 1000;
        active = true;
        isTurnPlayer = true;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void turnPlayer() {
        if (active) {
            Scanner sc = new Scanner(System.in);
            String risp;

            if (player.countCards() < 2)
                player.addCard(Deck.pop());
            else {
                if (player.cardValue() != 21) {
                    try {
                        System.out.print(
                                OutputColors.YELLOW + "\n Hit (+) or Stand (-)? " + OutputColors.RESET);
                        risp = sc.next();
                        if (risp.equals("+"))
                            player.addCard(Deck.pop());
                        else
                            isTurnPlayer = false;

                        if (player.cardValue() == MAX_CARDS_VALUE)
                            isTurnPlayer = false;
                    } catch (InputMismatchException e) {
                    }

                    if (player.cardValue() > MAX_CARDS_VALUE) {
                        isTurnPlayer = false;
                        active = false;
                    }
                } else
                    isTurnPlayer = false;
            }
        }
    }

    public void turnDealer() {
        if (dealer.countCards() < 2) {
            dealer.addCard(Deck.pop());
        }

        else {

            if (dealer.cardValue() <= player.cardValue())
                dealer.addCard(Deck.pop());
            else
                active = false;
        }

        if (dealer.countCards() >= 2 && dealer.cardValue() >= player.cardValue()
                || dealer.cardValue() >= MAX_CARDS_VALUE)
            active = false;

    }

    /**
     * Method to check who won the game
     * 1 = player
     * -1 = dealer
     * 0 = tie
     **/

    public int resultGame() {
        if (dealer.cardValue() > MAX_CARDS_VALUE)
            return -1;
        else if (player.cardValue() > MAX_CARDS_VALUE)
            return 1;
        else if (dealer.cardValue() > player.cardValue() && dealer.cardValue() <= MAX_CARDS_VALUE)
            return 1;
        else if (dealer.cardValue() < player.cardValue() && player.cardValue() <= MAX_CARDS_VALUE)
            return -1;
        else
            return 0;
    }

    public void printStatus() {
        System.out.println("\n " + dealer.getName() + "\t\t" + dealer.getCards() + " = " + dealer.cardValue());
        System.out.println(" " + player.getName() + "\t\t" + player.getCards() + " = " + player.cardValue());
        try {
            Thread.sleep(MILLISECONDS_SLEEP);
        } catch (InterruptedException e) {
        }
    }

    private void printResult(int esito) {
        if (esito > 0) {
            System.out.println(
                    OutputColors.RED + "\n\nUnfortunately, you (" + player.getName() + ") lost PLN " + player.getBet()
                            + "\nYour current balance is: PLN " + (player.getMoney() - player.getBet())
                            + OutputColors.RESET);
            player.updateMoney(-player.getBet());
        } else if (esito < 0) {
            System.out.println(OutputColors.BLUE + "\n\nCongratulations! You (" + player.getName() + ") won PLN "
                    + player.getBet() * 2 + "\nYour current balance is: PLN " + (player.getMoney() + player.getBet())
                    + OutputColors.RESET);
            player.updateMoney(player.getBet());
        } else
            System.out.println(OutputColors.YELLOW + "\n\n Draw, money have been refunded to your account!"
                    + "\nYour current balance is: PLN " + player.getMoney()
                    + OutputColors.RESET);

        try {
            Thread.sleep(MILLISECONDS_SLEEP);
        } catch (InterruptedException e) {
        }
    }

    public void run() {
        turnDealer();
        while (active) {
            while (isTurnPlayer) {
                turnPlayer();
                printStatus();
            }
            if (active) {
                turnDealer();
                printStatus();
            }
        }
        printResult(resultGame());

        player.clearTable();
        dealer.clearTable();
    }

}
