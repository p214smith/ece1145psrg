package hotciv.standard;
import hotciv.framework.*;
public class zetaCivWinningStrategy implements winningStrategy{
    protected int rounds;
    protected int redAttacks;
    protected int blueAttacks;
    public zetaCivWinningStrategy(){
        this.rounds = 0;
        this.redAttacks = 0;
        this.blueAttacks = 0;
    }

    @Override
    public Player getWinner(int age, City[][] cities) {
        return null;
    }
}
