package hotciv.standard;
import hotciv.framework.*;

import java.util.List;
import java.util.Objects;

public class thetaCivAttackStrategy implements attackStrategy{
    public boolean attack(Unit fromTile, Unit toTile, Tile[][] tiles, List<Unit> unitList, City[][] cities, int max) {
        Position to = ((ufoUnitImpl)toTile).getUnitPosition();
        ((ufoUnitImpl)fromTile).setLocation(to);
        unitList.remove(toTile);
        if( Objects.nonNull(cities[to.getRow()][to.getColumn()])
                && cities[to.getRow()][to.getColumn()].getOwner() != fromTile.getOwner()){
            ((CityImpl)cities[to.getRow()][to.getColumn()]).setCity_Owner(fromTile.getOwner());}
        ((ufoUnitImpl)fromTile).decrementMove();
        return true;
    }

    @Override
    public boolean move_unit(Position from, Position to, Tile[][] tiles, List<Unit> unitList, City[][] cities,Player current_Player,winningStrategy win) {

        boolean proper_Move_Distance = Math.abs(from.getColumn() - to.getColumn())<= 1
                && Math.abs(from.getRow() - to.getRow()) <= 1;
        if(!proper_Move_Distance) return false;

        Unit fromTile = null;
        Unit toTile = null;
        for (Unit unit : unitList) {
            if (((ufoUnitImpl)unit).getUnitPosition().hashCode() == from.hashCode()){

                fromTile = unit;
            }}
        for (Unit unit1 : unitList) {
            if (((ufoUnitImpl)unit1).getUnitPosition().hashCode() == to.hashCode()) toTile = unit1;}
        if (fromTile == null) return false;
        if (fromTile.getMoveCount() <= 0) return false;
        if (current_Player != fromTile.getOwner()) return false;
        if (!Objects.equals(fromTile.getTypeString(), "ufo")){
            if (Objects.equals(tiles[to.getRow()][to.getColumn()].getTypeString(), GameConstants.MOUNTAINS))
                return false;
            if (Objects.equals(tiles[to.getRow()][to.getColumn()].getTypeString(), GameConstants.OCEANS))
                return false;
        }
        if (toTile == null) {
            if (fromTile.getMoveCount() > 0) {
                ((ufoUnitImpl) fromTile).setLocation(to);
                if (Objects.nonNull(cities[to.getRow()][to.getColumn()])
                        && cities[to.getRow()][to.getColumn()].getOwner() != fromTile.getOwner() && !Objects.equals(fromTile.getTypeString(), "ufo")) {
                    ((CityImpl) cities[to.getRow()][to.getColumn()]).setCity_Owner(fromTile.getOwner());
                    ((ufoUnitImpl) fromTile).decrementMove();
                    return true;
                } else {
                    ((ufoUnitImpl) fromTile).setLocation(to);
                    ((ufoUnitImpl) fromTile).decrementMove();
                    return true;
                }

            }
        }
            if (toTile.getOwner() != current_Player) {
                if (attack(fromTile, toTile, tiles, unitList, cities, 7)) {
                    if (fromTile.getOwner() == Player.RED)
                        win.iterateRedVictory();
                    if (fromTile.getOwner() == Player.BLUE)
                        win.iterateBlueVictory();
                    return true;
                }
            }


        return false;
    }

    @Override
    public void end_of_turn(Player current_Player, List<Unit> unitList, City[][] cities,Tile[][] tiles) {
        for(Unit unit : unitList)
                ((ufoUnitImpl)unit).resetMove();


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
    public void create_New_Unit(Position p, String unitType, int cost, List<Unit> unitList, City[][] cities, Position newUnitPosition) {
        unitList.add(new ufoUnitImpl(newUnitPosition, cities[p.getRow()][p.getColumn()].getOwner(),unitType));
        ((CityImpl) cities[p.getRow()][p.getColumn()]).take_treasury(cost);}

    public Unit getUnitAt(Position p,List<Unit> unitList){
        for (Unit unit : unitList) {
            if (((ufoUnitImpl)unit).getUnitPosition().hashCode() == p.hashCode()) return unit;
        }
        return null;}
    public boolean isOccupied(Position unitLocation,List<Unit> unitList) {
        for (Unit unit : unitList) {
            if (((ufoUnitImpl) unit).getUnitPosition().hashCode() == unitLocation.hashCode())
                return true;
        }
        return false;
    }


}

