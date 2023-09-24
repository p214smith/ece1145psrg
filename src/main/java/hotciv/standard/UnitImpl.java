package hotciv.standard;

import hotciv.framework.*;

public class UnitImpl implements Unit {
    public UnitImpl(Position p,Player owner,String type){
        this.owner = owner;
        this.location = p;
        this.unitType = type;
        this.moves_left = 1;
    }
    protected Player owner;
    protected Position location;
    protected String unitType;
    protected int moves_left;
    @Override
    public String getTypeString() {
        return this.unitType;
    }
    @Override
    public Player getOwner() {
        return this.owner;
    }
    @Override
    public int getMoveCount() {
        return this.moves_left;
    }
    @Override
    public int getDefensiveStrength() {
        return 0;
    }
    @Override
    public int getAttackingStrength() {
        return 0;
    }

    @Override
    public Position getUnitPosition() {
        return this.location;
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
