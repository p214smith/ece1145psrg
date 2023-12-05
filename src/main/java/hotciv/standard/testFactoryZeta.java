package hotciv.standard;

import hotciv.framework.*;

public class testFactoryZeta implements FactoryImpl {
        @Override
        public actionStrategy getActionStrategy() {
            return new gammaCivActionStrategy();
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
            return new zetaCivWinningStrategy();
        }

        @Override
        public worldStrategy getWorldStrategy() {
            return new deltaCivWorldImpl();
        }

    @Override
    public workforcePopStrategy getWorkforceStrategy() {
        return new alphaCivWorkStrategy();
    }
}
