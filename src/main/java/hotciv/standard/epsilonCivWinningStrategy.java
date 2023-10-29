package hotciv.standard;
import hotciv.framework.*;
public class epsilonCivWinningStrategy implements winningStrategy{
    protected int redAttack;
    protected int blueAttack;
    public epsilonCivWinningStrategy(){
        this.blueAttack = 0;
        this.redAttack = 0;
    }
    public void iterateBlueVictory() {
        this.blueAttack += 1;
    }
    public void iterateRedVictory() {
        this.redAttack += 1;
    }
    public void iterateRound() {}

    @Override
    public Player getWinner(int age, City[][] cities) {
        if (this.redAttack >= 3)
            return Player.RED;
        else if (this.blueAttack >= 3)
            return Player.BLUE;
        else return null;
    }
}
