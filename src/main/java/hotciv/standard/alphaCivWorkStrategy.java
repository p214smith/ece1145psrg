package hotciv.standard;
import hotciv.framework.*;

import java.util.Objects;

public class alphaCivWorkStrategy implements workforcePopStrategy {
    public void changeWorkforceFocusInCityAt(Position p, String balance, City c) {
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType, City[][] cities) {
        if (Objects.equals(unitType, GameConstants.LEGION) || Objects.equals(unitType, GameConstants.ARCHER) || Objects.equals(unitType, GameConstants.SETTLER))
            ((CityImpl)cities[p.getRow()][p.getColumn()]).setProduction_Unit(unitType);
    }
    public void updatePopulation(City c){

    }
    public void update_Production_Food(City c, int[] values){((CityImpl)c).setProduction_level(6);}
}
