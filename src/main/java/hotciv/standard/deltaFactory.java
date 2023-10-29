package hotciv.standard;

import hotciv.framework.worldStrategy;

public class deltaFactory extends alphaFactory{
    @Override
    public worldStrategy getWorldStrategy() {
        return new deltaCivWorldImpl();
    }
}
