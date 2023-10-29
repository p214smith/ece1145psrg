package hotciv.standard;
import hotciv.framework.*;

import java.util.List;
import java.util.Objects;

public class alphaCivAttackStrategy implements attackStrategy{
    @Override
    public boolean attack(Unit fromTile, Unit toTile, Tile[][] tiles, List<Unit> unitList, City[][] cities,int max) {
        Position to = ((UnitImpl)toTile).getUnitPosition();
        ((UnitImpl)fromTile).setLocation(to);
        unitList.remove(toTile);
        if( Objects.nonNull(cities[to.getRow()][to.getColumn()])
                && cities[to.getRow()][to.getColumn()].getOwner() != fromTile.getOwner()){
            ((CityImpl)cities[to.getRow()][to.getColumn()]).setCity_Owner(fromTile.getOwner());}
        ((UnitImpl)fromTile).decrementMove();
        return true;

    }

}
