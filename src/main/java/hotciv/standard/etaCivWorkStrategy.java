package hotciv.standard;
import hotciv.framework.*;

import java.util.Objects;
public class etaCivWorkStrategy implements workforcePopStrategy {
    public void changeWorkforceFocusInCityAt(Position p, String balance,City c) {
        ((CityImpl)c).setWorkforceFocus(balance);

    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType, City[][] cities) {
        if (Objects.equals(unitType, GameConstants.LEGION) || Objects.equals(unitType, GameConstants.ARCHER) || Objects.equals(unitType, GameConstants.SETTLER))
            ((CityImpl)cities[p.getRow()][p.getColumn()]).setProduction_Unit(unitType);
    }
    public void updatePopulation(City c){
        int food = ((CityImpl)c).getFood_Balance();
        int size = c.getSize();
        if ((food >= 5 + (size * 3)) && (size < 9)){
            ((CityImpl)c).resetFood_Balance();
            ((CityImpl)c).incrementPopulation();}
    }
    public void update_Production_Food(City c, int[] values){
        int size = c.getSize();
        int food = 1;
        int production = 6;
        while (size > 1){
            if (Objects.equals(c.getWorkforceFocus(), GameConstants.foodFocus)){
                if (values[0] > 0){
                    food += 3;
                    values[0] -=1;
                }
                else if (values[1] > 0){
                    food += 1;
                    values[1] -= 1;
                }
                else if (values[2] > 0){
                    production +=3;
                    values[2] -=1;
                }
                else if (values[3] > 0){
                    production += 2;
                    values[3] -= 1;
                } else if (values[4] > 0) {
                    production += 1;
                    values[4] -=1;
                }
            }
            else{
                if (values[2] > 0){
                    production += 3;
                    values[2] -=1;
                }
                else if (values[3] > 0){
                    production += 2;
                    values[3] -= 1;
                }
                else if (values[4] > 0){
                    production +=1;
                    values[4] -=1;
                }
                else if (values[0] > 0){
                    food += 3;
                    values[0] -= 1;
                } else if (values[1] > 0) {
                    food += 1;
                    values[1] -=1;
                }
            }
            size -= 1;
        }
        ((CityImpl)c).setFood_production(food);
        ((CityImpl)c).setProduction_level(production);
    }

}
