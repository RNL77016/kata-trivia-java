package trivia;

public class Player {
    private final String name;
    private int position = 1;
    private int coins = 0;
    private boolean inPenaltyBox = false;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoin() {
        this.coins++;
    }

    public void advanceBy(int roll, int boardSize) {
        this.position += roll;
        if (this.position > boardSize) {
            this.position -= boardSize;
        }
    }

    public boolean hasWon(int winningCoins) {
        return this.coins == winningCoins;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    @Override
    public String toString() {
        return name;
    }
}
