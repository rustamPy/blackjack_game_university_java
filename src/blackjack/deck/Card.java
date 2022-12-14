package blackjack.deck;

public class Card implements Comparable{
    private final int value;
    private final int seed;
    public final static String S = "♦♣♥♠";
    public final static String V = "23456789TJQKA";


    public Card(int value, int seed) {
        this.value = value;
        this.seed = seed;
    }

    public int getValue() {
        return value;
    }

    public boolean isAce() {
        return value == V.length() - 1;
    }

    public boolean isFigure() {
        return value >= 9 && value <= 11;
    }

    public int compareTo(Object obj) {
        Card other = (Card) obj;
        return Integer.compare(value, other.getValue());
    }

    public String toString() {
        return V.charAt(value) + " " + S.charAt(seed);
    }
}
