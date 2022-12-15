package blackjack.player;

public class Statistics {

    public int gamewon;
    public int gamelost;
    private int gamedraw;
    private final Player player;

    public Statistics(Player player) {
        this.player = player;
        gamewon = 0;
        gamelost = 0;
        gamedraw = 0;
    }

    public void plusWin() {
        this.gamewon++;
    }

    private int getWin() {
        return gamewon;
    }

    public void plusLoss() {
        this.gamelost++;
    }

    private int getLoss() {
        return gamelost;
    }

    public void plusDraw() {
        this.gamedraw++;
    }

    private int getDraw() {
        return gamedraw;
    }

    private int getTotal() {
        return getDraw() + getLoss() + getWin();
    }

    public String toString() {

        return  "\n ---------------------------\n" +
                "|\tPlayer name: " + player.getName() + "\t\t|" +
                "\n ---------------------------" +
                "\n|\tTotal games: " + (getTotal()) + "\t\t\t|" +
                "\n ---------------------------" +
                "\n|\tSuccessful games: " + getWin() + "\t\t|" +
                "\n ---------------------------" +
                "\n|\tLost games: " + getLoss() + "\t\t\t|" +
                "\n ---------------------------" +
                "\n|\tTied games: " + getDraw() + "\t\t\t|" +
                "\n ---------------------------" +
                "\n|\tW/L: " + getWin() + "/" + getLoss() + "\t\t\t\t|" +
                "\n ---------------------------";
    }

}


