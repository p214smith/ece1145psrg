package hotciv.standard;

import hotciv.framework.actionStrategy;

public class gammaFactory extends alphaFactory{
    @Override
    public actionStrategy getActionStrategy() {
        return new gammaCivActionStrategy();
    }
}
