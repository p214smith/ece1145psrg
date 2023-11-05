package hotciv.standard;
import  hotciv.framework.*;

import java.util.List;

public class thetaCivWorldImpl implements worldStrategy{
    public String[] getWorld() {
        String[] layout = {
                "phpppppppppppppp",
                "oppppppppppppppp",
                "ppmppppppppppppp",
                "pppppppppppppppp",
                "fppppppppppppppp",
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

        unitList.add(new ufoUnitImpl(new Position(2, 0), Player.RED, GameConstants.ARCHER));
        unitList.add(new ufoUnitImpl(new Position(3, 2), Player.BLUE, GameConstants.LEGION));
        unitList.add(new ufoUnitImpl(new Position(4, 3), Player.RED, GameConstants.SETTLER));
        cities[1][1] = new CityImpl();
        ((CityImpl) cities[1][1]).setCity_Owner(Player.RED);
        cities[4][1] = new CityImpl();
        ((CityImpl) cities[4][1]).setCity_Owner(Player.BLUE);
    }
}
