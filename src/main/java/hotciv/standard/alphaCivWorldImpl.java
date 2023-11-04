package hotciv.standard;
import hotciv.framework.*;

import java.util.ArrayList;
import java.util.List;

public class alphaCivWorldImpl implements worldStrategy{
    public String[] getWorld() {
        String[] layout = {
                "phpppppppppppppp",
                "oppppppppppppppp",
                "ppmppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp",
                "pppppppppppppppp"
        };
        return layout;
    }

    @Override
    public void addWorldElements(City[][] cities, List<Unit> unitList) {

        unitList.add(new UnitImpl(new Position(2, 0), Player.RED, GameConstants.ARCHER));
        unitList.add(new UnitImpl(new Position(3, 2), Player.BLUE, GameConstants.LEGION));
        unitList.add(new UnitImpl(new Position(4, 3), Player.RED, GameConstants.SETTLER));
        cities[1][1] = new CityImpl();
        ((CityImpl) cities[1][1]).setCity_Owner(Player.RED);
        cities[4][1] = new CityImpl();
        ((CityImpl) cities[4][1]).setCity_Owner(Player.BLUE);
    }
}
