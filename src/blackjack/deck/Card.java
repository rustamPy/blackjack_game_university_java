package blackjack.deck;

import java.util.Random;

public class Card implements Comparable {
    private final int value;
    private final int seed;
    public final static String S = "♦♣♥♠";
    public final static String V = "123456789JQKA";

    public Card() {
        Random rand = new Random();
        value = rand.nextInt(V.length());
        seed = rand.nextInt(S.length());
    }

    public Card(int value, int seed) {
        this.value = value;
        this.seed = seed;
    }

    public int getValue() {
        return value;
    }

    public int getSeed() {
        return seed;
    }

    public boolean isAce() {
        return value == V.length() - 1;
    }

    public boolean isFigure() {
        return value >= 9 && value <= 11;
    }

    public int compareTo(Object obj) {
        Card other = (Card) obj;
        if (value > other.getValue())
            return 1;
        else if (value < other.getValue())
            return -1;
        else
            return 0;
    }

    public String toString() {
        return V.charAt(value) + " " + S.charAt(seed);
    }
}
