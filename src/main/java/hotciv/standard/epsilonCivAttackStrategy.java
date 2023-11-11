package hotciv.standard;
import hotciv.framework.*;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;

public class epsilonCivAttackStrategy implements attackStrategy{
    @Override
    public boolean attack(Unit fromTile, Unit toTile, Tile[][] tiles, List<Unit> unitList, City[][] cities,int max) {
        int attack_Score = fromTile.getAttackingStrength();
        int defending_score = toTile.getDefensiveStrength();
        Position from = ((UnitImpl)fromTile).getUnitPosition();
        Position to = ((UnitImpl)toTile).getUnitPosition();
        Position def;
        Position off;
        for(int i = 0; i < 8;i++){
            off = getNextPosition(from,i);
            def = getNextPosition(to,i);
            for (Unit unit1 : unitList) {
                if(((UnitImpl)unit1).getUnitPosition().hashCode() == off.hashCode() && unit1.getOwner() == fromTile.getOwner())
                    attack_Score = attack_Score + 1;
            }
            for (Unit unit1 : unitList) {
                if(((UnitImpl)unit1).getUnitPosition().hashCode() == def.hashCode() && unit1.getOwner() == toTile.getOwner())
                    defending_score = defending_score + 1;
            }

        }
        if (cities[from.getRow()][from.getColumn()] != null)
            attack_Score = attack_Score * 3;
        if (cities[to.getRow()][to.getColumn()] != null)
            defending_score = defending_score * 3;
        if (Objects.equals(tiles[from.getRow()][from.getColumn()].getTypeString(), GameConstants.FOREST) ||
                Objects.equals(tiles[from.getRow()][from.getColumn()].getTypeString(), GameConstants.HILLS))
            attack_Score = attack_Score * 2;
        if (Objects.equals(tiles[to.getRow()][to.getColumn()].getTypeString(), GameConstants.FOREST) ||
                Objects.equals(tiles[to.getRow()][to.getColumn()].getTypeString(), GameConstants.HILLS))
            defending_score = defending_score * 2;
        attack_Score = attack_Score * ThreadLocalRandom.current().nextInt(1, 2);
        defending_score = defending_score * ThreadLocalRandom.current().nextInt(1,2);
        if (attack_Score >= defending_score){
            ((UnitImpl)fromTile).setLocation(to);
            unitList.remove(toTile);
            if( Objects.nonNull(cities[to.getRow()][to.getColumn()])
                    && cities[to.getRow()][to.getColumn()].getOwner() != fromTile.getOwner()){
                ((CityImpl)cities[to.getRow()][to.getColumn()]).setCity_Owner(fromTile.getOwner());}
            ((UnitImpl)fromTile).decrementMove();
            return true;
        }
        else{
            unitList.remove(fromTile);
            return false;
        }
    }
    private Position getNextPosition(Position current, int direction) {
        int row = current.getRow();
        int col = current.getColumn();

        switch (direction) {
            case 0:
                row--;
                break;
            case 1:
                row--;
                col++;
                break;
            case 2:
                col++;
                break;
            case 3:
                row++;
                col++;
                break;
            case 4:
                row++;
                break;
            case 5:
                row++;
                col--;
                break;
            case 6:
                col--;
                break;
            case 7:
                row--;
                col--;
                break;
        }
        return new Position(row, col);
    }

    @Override
    public boolean move_unit(Position from, Position to, Tile[][] tiles, List<Unit> unitList, City[][] cities, Player current_Player, winningStrategy win) {
        if (to.getColumn() > GameConstants.WORLDSIZE-1 ) return false;
        if (to.getRow() > GameConstants.WORLDSIZE-1 ) return false;
        if (to.getColumn() < 0 ) return false;
        if (to.getRow() < 0) return false;
        if (Objects.equals(tiles[to.getRow()][to.getColumn()].getTypeString(), GameConstants.MOUNTAINS))
            return false;
        if (Objects.equals(tiles[to.getRow()][to.getColumn()].getTypeString(), GameConstants.OCEANS))
            return false;
        boolean proper_Move_Distance = Math.abs(from.getColumn() - to.getColumn())<= 1
                && Math.abs(from.getRow() - to.getRow()) <= 1;
        if(!proper_Move_Distance) return false;

        Unit fromTile = null;
        Unit toTile = null;
        for (Unit unit : unitList) {
            if (((UnitImpl)unit).getUnitPosition().hashCode() == from.hashCode()){

                fromTile = unit;
            }}
        if (fromTile == null) return false;
        if (fromTile.getMoveCount() <= 0)return false;
        for (Unit unit1 : unitList) {
            if (((UnitImpl)unit1).getUnitPosition().hashCode() == to.hashCode()) toTile = unit1;}

        if (current_Player != fromTile.getOwner()) return false;
        if (toTile == null){
            if (fromTile.getMoveCount() > 0){
                ((UnitImpl)fromTile).setLocation(to);
                if( Objects.nonNull(cities[to.getRow()][to.getColumn()])
                        && cities[to.getRow()][to.getColumn()].getOwner() != fromTile.getOwner()){
                    ((CityImpl)cities[to.getRow()][to.getColumn()]).setCity_Owner(fromTile.getOwner());}
                ((UnitImpl)fromTile).decrementMove();
                return true;}
        }
        if (toTile.getOwner() != current_Player){
            if(attack(fromTile,toTile,tiles,unitList,cities,7)){
                if(fromTile.getOwner() == Player.RED)
                    win.iterateRedVictory();
                if(fromTile.getOwner() == Player.BLUE)
                    win.iterateBlueVictory();
                return true;}}


        return false;
    }
    public void end_of_turn(Player current_Player, List<Unit> unitList, City[][] cities,Tile[][] tiles) {

            for(Unit unit : unitList)
                ((UnitImpl)unit).resetMove();

    }
    public Unit getUnitAt(Position p,List<Unit> unitList){
        for (Unit unit : unitList) {
            if (((UnitImpl)unit).getUnitPosition().hashCode() == p.hashCode()) return unit;
        }
        return null;}
    public boolean isOccupied(Position unitLocation,List<Unit> unitList) {
        for (Unit unit : unitList) {
            if (((UnitImpl) unit).getUnitPosition().hashCode() == unitLocation.hashCode())
                return true;
        }
        return false;
    }

    @Override
    public void create_New_Unit(Position p, String unitType, int cost, List<Unit> unitList, City[][] cities, Position newUnitPosition) {

                    unitList.add(new UnitImpl(newUnitPosition, cities[p.getRow()][p.getColumn()].getOwner(), unitType));
                    ((CityImpl) cities[p.getRow()][p.getColumn()]).take_treasury(cost);
        }
}
