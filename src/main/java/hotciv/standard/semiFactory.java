package hotciv.standard;
import hotciv.framework.*;
public class semiFactory implements FactoryImpl {
    public worldStrategy getWorldStrategy() {
        return new deltaCivWorldImpl();
    }
    public winningStrategy getWinningStrategy() {return new epsilonCivWinningStrategy();
    }
    public attackStrategy getAttackStrategy() {
        return new epsilonCivAttackStrategy();
    }
    public ageStrategy getAgeStrategy() {
        return new betaCivAgeStrategy();
    }
    public actionStrategy getActionStrategy() {
        return new gammaCivActionStrategy();
    }
    public workforcePopStrategy getWorkforceStrategy() {
        return new etaCivWorkStrategy();
    }
}
