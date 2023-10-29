package hotciv.standard;
import hotciv.framework.*;

import java.util.Objects;

public class betaCivWinningStrategy implements winningStrategy{
    public Player getWinner(int age,City[][] cities){
        int red_cities = 0;
        int total_cities = 0;
        for( int i = 0; i < GameConstants.WORLDSIZE;i++){
            for(int j = 0; j < GameConstants.WORLDSIZE;j++){
                if ( Objects.nonNull(cities[i][j])){
                    total_cities += 1;
                    if(cities[i][j].getOwner() == Player.RED)
                        red_cities += 1;
                }
            }
        }
        if(total_cities == red_cities)
            return Player.RED;
        else if (red_cities == 0)
            return Player.BLUE;
        else
            return null;
    }
    public void iterateBlueVictory() {}
    public void iterateRedVictory() {}
    public void iterateRound() {}
}
