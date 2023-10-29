package hotciv.standard;

import hotciv.framework.winningStrategy;

public class zetaFactory extends alphaFactory{
    @Override
    public winningStrategy getWinningStrategy() {
        return new zetaCivWinningStrategy();
    }
}
