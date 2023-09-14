package hotciv.standard;

import hotciv.framework.*;

public class UnitImpl implements Unit {
    @Override
    public String getTypeString() {
        return null;
    }
    @Override
    public Player getOwner() {
        return null;
    }
    @Override
    public int getMoveCount() {
        return 0;
    }
    @Override
    public int getDefensiveStrength() {
        return 0;
    }
    @Override
    public int getAttackingStrength() {
        return 0;
    }
    public String getUnitLocation(Position p) {
        String[][] unitMap = new String[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
        unitMap[2][0] = GameConstants.ARCHER;
        unitMap[3][2] = GameConstants.LEGION;
        unitMap[4][3] = GameConstants.SETTLER;

        return unitMap[p.getRow()][p.getColumn()];
    }
    public Player getUnitOwner(Position p) {
        //Ignores terrain & just considers unit on tile
        Player[][] colorMap = new Player[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
        colorMap[2][0] = Player.RED;
        colorMap[3][2] = Player.BLUE;
        colorMap[4][3] = Player.RED;

        return colorMap[p.getRow()][p.getColumn()];
    }
}
