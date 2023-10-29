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
}
