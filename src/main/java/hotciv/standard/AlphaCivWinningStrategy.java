package hotciv.standard;
import hotciv.framework.*;
public class AlphaCivWinningStrategy implements winningStrategy{
    public Player getWinner(int age,City[][] cities){
        if (age == -3000)
            return Player.RED;
        else
            return null;
    }

    public void iterateBlueVictory() {}
    public void iterateRound() {}
    public void iterateRedVictory() {}
}
