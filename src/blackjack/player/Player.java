package blackjack.player;

import blackjack.deck.*;
import blackjack.gameProcess.*;

import java.util.ArrayList;

public class Player {

    private String name;
    public ArrayList<Card> cards;
    private int money;
    private int bet;
    private static final int DEFAULT_MONEY = 1000;

    public Player() {
        name = "Dealer";
        cards = new ArrayList<>();
        money = DEFAULT_MONEY;
        bet = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() == 0)
            this.name = "Unknown player";
        else
            this.name = name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getMoney() {
        return money;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void resetMoney() {
        this.money = DEFAULT_MONEY;
    }

    public void updateMoney(int money) {
        this.money += money;
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public int cardValue() {
        ArrayList<Card> card_list = ord(cards);
        int v = 0;
        for (Card c : card_list) {
            if (!c.isAce()) {
                if (c.isFigure())
                    v += 10;
                else
                    v += c.getValue() + 2;
            } else {
                if (v + 11 > Game.MAX_CARDS_VALUE)
                    v += 1;
                else
                    v += 11;
            }
        }
        return v;
    }

    private ArrayList<Card> ord(ArrayList<Card> hand) {
        ArrayList<Card> card_list = new ArrayList<>(hand);
        card_list.sort(Comparable::compareTo);
        return card_list;
    }

    public int countCards() {
        return cards.size();
    }

    public void clearTable() {
        cards.clear();
    }

    public int getDefaultMoney() {
        return DEFAULT_MONEY;
    }

    public String toString() {
        return "\n Name: \t\t" + name + "\n Cards: \t\t" + cards.toString() + " = " + cardValue() + "\n Money: \tPLN"
                + money;
    }
}
