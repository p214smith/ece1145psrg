package hotciv.standard;

import hotciv.framework.*;

import java.util.Objects;

public class UnitImpl implements Unit {
    public UnitImpl(Position p,Player owner,String type){
        this.owner = owner;
        this.location = p;
        this.unitType = type;
        this.moves_left = 1;
        if (Objects.equals(type,GameConstants.SETTLER)){
            this.defense = 3;
            this.attack = 0;
        }
        else if (Objects.equals(type,GameConstants.ARCHER)){
            this.defense = 3;
            this.attack = 2;
        }
        else{
            this.defense = 2;
            this.attack = 4;

        }
    }
    protected Player owner;
    protected Position location;
    protected int defense;
    protected int attack;

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
        return this.defense;
    }
    @Override
    public int getAttackingStrength() {
        return this.attack;
    }


    public Position getUnitPosition() {
        return this.location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }

    public void decrementMove(){
        this.moves_left = this.moves_left - 1;
    }
    public void resetMove(){
        if(Objects.equals(this.unitType,GameConstants.ARCHER) && this.defense > 3)
            this.moves_left = 0;
        else
            this.moves_left = 1;
    }
    public Player getUnitOwner(Position p) {
        //Ignores terrain & just considers unit on tile
        Player[][] colorMap = new Player[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
        colorMap[2][0] = Player.RED;
        colorMap[3][2] = Player.BLUE;
        colorMap[4][3] = Player.RED;

        return colorMap[p.getRow()][p.getColumn()];
    }
    public void setDefense(int j){
        this.defense = j;
    }
}
