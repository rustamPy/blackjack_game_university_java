package blackjack.deck;

import java.util.ArrayList;
import java.util.Random;

public class CardBox {
    private final ArrayList<Card> deck;
    public static final int DEFAULT_SHUFFLE_NUM = 5;
    private static final int NUMBER_OF_CARDS = 312;
    //The standard 52-card pack is used, but in most casinos several decks of cards are shuffled together.
    // The six-deck game (312 cards) is the most popular.
    //https://bicyclecards.com/how-to-play/blackjack/


    public CardBox() {
        deck = initDeck();
    }

    private ArrayList<Card> initDeck() {
        ArrayList<Card> vet = new ArrayList<>(NUMBER_OF_CARDS);
        int k = 0;
        while (k < NUMBER_OF_CARDS) { // 0<52
            for (int i = 0; i < Card.S.length(); i++) {
                for (int j = 0; j < Card.V.length(); j++) {
                    vet.add(new Card(j, i));
                    k++;
                }
            }
        }
        return vet;
    }

    public void shuffle() {

        shuffle(DEFAULT_SHUFFLE_NUM);
    }

    public void shuffle(int times) {
        Random rand = new Random();
        int x, y;
        Card tmp;
        for (int t = 0; t < times; t++) {
            for (int i = 0; i < deck.size(); i++) {
                x = rand.nextInt(deck.size());
                y = rand.nextInt(deck.size());
                tmp = deck.get(x);
                deck.set(x, deck.get(y));
                deck.set(y, tmp);
            }
        }
    }

    public Card pop() {
        Card c = deck.get(0);
        deck.remove(0);
        return c;
    }


    public String toString() {
        String s = "\n";
        int i = 1;
        for (Card c : deck) {
            s += c.toString() + " ";
            if (i % Card.V.length() == 0)
                s += "\n";
            i++;
        }
        return s;
    }
}
