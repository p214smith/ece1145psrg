package hotciv.standard;

import hotciv.framework.ageStrategy;
import hotciv.framework.winningStrategy;

public class betaFactory extends alphaFactory{
    @Override
    public ageStrategy getAgeStrategy() {
        return new betaCivAgeStrategy();
    }
    @Override
    public winningStrategy getWinningStrategy() {
        return new betaCivWinningStrategy();
    }
}
