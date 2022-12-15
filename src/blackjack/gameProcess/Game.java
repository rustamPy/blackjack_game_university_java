package blackjack.gameProcess;

import blackjack.deck.CardBox;
import blackjack.player.Player;
import utils.Design;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private final CardBox Deck;
    private final Player player;
    private final Player dealer;
    public static final int MAX_CARDS_VALUE = 21;
    public static final int MIN_BET = 2;
    private static final int MILLISECONDS_SLEEP = 1200;
    private boolean active;
    private boolean isTurnPlayer;


    public Game(Player player) {
        Deck = new CardBox();
        Deck.shuffle();
        dealer = new Player(); // dealer.name = "Dealer", dealer.bet = 0, dealer.money = 1000;
        this.player = player; // player.name = "Mark", player.bet = 5, player.money = 1000;
        active = true;
        isTurnPlayer = true;
    }


    private void turnPlayer() {
        if (active) {
            Scanner sc = new Scanner(System.in);
            String sign;

            if (player.countCards() < 2)
                player.addCard(Deck.pop());
            else {
                if (player.cardValue() != 21) {
                    try {
                        System.out.print(Design.YELLOW + "\n Hit (+) or Stand (-)? " + Design.RESET);
                        sign = sc.next();
                        if (sign.equals("+"))
                            player.addCard(Deck.pop());
                        else
                            isTurnPlayer = false;

                        if (player.cardValue() == MAX_CARDS_VALUE) {
                            isTurnPlayer = false;
                            active = false;
                        }
                    } catch (InputMismatchException ignored) {
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

    private void turnDealer() {
        if (dealer.countCards() < 2) {
            dealer.addCard(Deck.pop());
        } else {

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

    private int assessHand() {
        if (dealer.cardValue() > MAX_CARDS_VALUE)
            return 1;
        else if (player.cardValue() > MAX_CARDS_VALUE)
            return -1;
        else if (dealer.cardValue() > player.cardValue() && dealer.cardValue() <= MAX_CARDS_VALUE)
            return -1;
        else if (dealer.cardValue() < player.cardValue() && player.cardValue() <= MAX_CARDS_VALUE)
            return 1;
        else
            return 0;
    }

    private void printHand() {
        System.out.println("\n -> " + dealer.getName() + "\t\t" + dealer.getCards() + " = " + dealer.cardValue());
        System.out.println(" -> " + player.getName() + "\t\t" + player.getCards() + " = " + player.cardValue());
        try {
            Thread.sleep(MILLISECONDS_SLEEP);
        } catch (InterruptedException ignored) {
        }
    }

    private void printResult(int opt) {
        if (opt == -1) {
            System.out.println(
                    Design.RED + "\n\nUnfortunately, you (" + player.getName() + ") lost PLN " + player.getBet()
                            + "\nYour current balance is: PLN " + (player.getMoney() - player.getBet())
                            + Design.RESET);
            player.updateMoney(-player.getBet());
        } else if (opt == 1) {
            System.out.println(Design.BLUE + "\n\nCongratulations! You (" + player.getName() + ") won PLN "
                    + player.getBet() * 2 + "\nYour current balance is: PLN " + (player.getMoney() + player.getBet())
                    + Design.RESET);
            player.updateMoney(player.getBet());
        } else
            System.out.println(Design.YELLOW + "\n\n Draw, money have been refunded to your account!"
                    + "\nYour current balance is: PLN " + player.getMoney()
                    + Design.RESET);

        try {
            Thread.sleep(MILLISECONDS_SLEEP);
        } catch (InterruptedException ignored) {
        }
    }

    public void run() {
        turnDealer();
        while (active) {
            while (isTurnPlayer) {
                //System.out.println(Deck.toString());
                turnPlayer();
                printHand();
            }
            if (active) {
                turnDealer();
                printHand();
            }
        }
        printResult(assessHand());

        player.clearTable();
        dealer.clearTable();
    }

}
