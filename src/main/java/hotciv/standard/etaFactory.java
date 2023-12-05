package hotciv.standard;

import hotciv.framework.*;

public class etaFactory implements Factory {
    @Override
    public actionStrategy getActionStrategy() {
        return new alphaCivActionStrategy();
    }

    @Override
    public ageStrategy getAgeStrategy() {
        return new alphaCivAgeStrategy();
    }

    @Override
    public attackStrategy getAttackStrategy() {
        return new alphaCivAttackStrategy();
    }

    @Override
    public winningStrategy getWinningStrategy() {
        return new AlphaCivWinningStrategy();
    }

    @Override
    public worldStrategy getWorldStrategy() {
        return new alphaCivWorldImpl();
    }

    public workforcePopStrategy getWorkforceStrategy() { return new etaCivWorkStrategy(); }
}