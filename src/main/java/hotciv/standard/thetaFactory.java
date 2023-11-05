package hotciv.standard;
import hotciv.framework.*;
public class thetaFactory implements Factory{
    public actionStrategy getActionStrategy() {
        return new thetaCivActionStrategy();
    }

    @Override
    public ageStrategy getAgeStrategy() {
        return new alphaCivAgeStrategy();
    }

    @Override
    public attackStrategy getAttackStrategy() {
        return new thetaCivAttackStrategy();
    }

    @Override
    public winningStrategy getWinningStrategy() {
        return new AlphaCivWinningStrategy();
    }

    @Override
    public worldStrategy getWorldStrategy() {
        return new thetaCivWorldImpl();
    }

    public workforcePopStrategy getWorkforceStrategy() { return new thetaCivWorkStrategy(); }
}
