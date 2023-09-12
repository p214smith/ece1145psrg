package hotciv.standard;

import hotciv.framework.*;

public class CityImpl implements City {
    @Override
    public Player getOwner() {
        return Player.RED;
    }
    public int getSize() {
        return 0;
    }

    @Override
    public int getTreasury() {
        return 0;
    }

    @Override
    public String getProduction() {
        return null;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }
}
