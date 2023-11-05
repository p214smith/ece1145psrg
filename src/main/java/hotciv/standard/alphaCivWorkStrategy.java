package hotciv.standard;
import hotciv.framework.*;

import java.util.Objects;

public class alphaCivWorkStrategy implements workforcePopStrategy {
    public void changeWorkforceFocusInCityAt(Position p, String balance) {
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType, City[][] cities) {
        if (Objects.equals(unitType, GameConstants.LEGION) || Objects.equals(unitType, GameConstants.ARCHER) || Objects.equals(unitType, GameConstants.SETTLER))
            ((CityImpl)cities[p.getRow()][p.getColumn()]).setProduction_Unit(unitType);
    }
}
