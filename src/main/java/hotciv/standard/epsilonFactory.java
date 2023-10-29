package hotciv.standard;

import hotciv.framework.attackStrategy;
import hotciv.framework.winningStrategy;

public class epsilonFactory extends alphaFactory{
    @Override
    public winningStrategy getWinningStrategy() {
        return new epsilonCivWinningStrategy();
    }
    @Override
    public attackStrategy getAttackStrategy() {
        return new epsilonCivAttackStrategy();
    }
}
