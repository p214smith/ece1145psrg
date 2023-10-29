package hotciv.standard;
import hotciv.framework.*;

import java.util.Objects;

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
        if( this.rounds < 20){
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
        else{
            if (this.redAttacks >= 3)
                return Player.RED;
            else if (this.blueAttacks >= 3)
                return Player.BLUE;
            else return null;
        }
    }

    @Override
    public void iterateBlueVictory() {
        if(this.rounds >= 20)
            this.blueAttacks++;
    }

    public void iterateRedVictory() {
        if (this.rounds >= 20)
            this.redAttacks++;
    }

    @Override
    public void iterateRound() {
        this.rounds++;
    }
}
