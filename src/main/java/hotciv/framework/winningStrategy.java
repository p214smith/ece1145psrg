package hotciv.framework;

public interface winningStrategy {
    public Player getWinner(int age,City[][] cities);
    public void iterateRound();
    public void iterateRedVictory();
    public void iterateBlueVictory();
}
