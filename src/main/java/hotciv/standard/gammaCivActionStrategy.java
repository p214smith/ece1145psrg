package hotciv.standard;
import hotciv.framework.*;

import java.util.List;
import java.util.Objects;

public class gammaCivActionStrategy implements actionStrategy{
    @Override
    public void actionStrategy(Unit unit, List<Unit> unitList, City[][] cities) {
        if(Objects.nonNull(unit)){
            if(Objects.equals(unit.getTypeString(),GameConstants.ARCHER)){
                if(unit.getDefensiveStrength() == 3){
                    ((UnitImpl)unit).decrementMove();
                    ((UnitImpl)unit).setDefense(6);}
                else{
                    ((UnitImpl)unit).setDefense(3);
                }
            }
            else if(Objects.equals(unit.getTypeString(),GameConstants.SETTLER)){
                Position new_City_Position = ((UnitImpl)unit).getUnitPosition();
                cities[new_City_Position.getRow()][new_City_Position.getColumn()] = new CityImpl();
                ((CityImpl)cities[new_City_Position.getRow()][new_City_Position.getColumn()]).setCity_Owner(unit.getOwner());
                unitList.remove(unit);
            }
        }
    }
}
