package hotciv.standard;
import hotciv.framework.*;

import java.util.Objects;

public class thetaCivWorkStrategy implements workforcePopStrategy{
    @Override
    public void changeProductionInCityAt(Position p, String unitType, City[][] cities) {
        if (Objects.equals(unitType, GameConstants.LEGION) || Objects.equals(unitType, GameConstants.ARCHER)
                || Objects.equals(unitType, GameConstants.SETTLER) || Objects.equals(unitType, "ufo"))
            ((CityImpl)cities[p.getRow()][p.getColumn()]).setProduction_Unit(unitType);
    }
    public void changeWorkforceFocusInCityAt(Position p, String balance,City c) {

    }
    public void updatePopulation(City c){

    }
    public void update_Production_Food(City c, int[] values){((CityImpl)c).setProduction_level(6);}
}
