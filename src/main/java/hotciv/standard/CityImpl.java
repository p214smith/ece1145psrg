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
    public Player getOwner(Position p) {
        Player[][] cityMap = new Player[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
        cityMap[1][1] = Player.RED;

        return cityMap[p.getRow()][p.getColumn()];
    }
}
