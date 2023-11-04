package hotciv.standard;
import hotciv.framework.*;

import java.util.List;

public class deltaCivWorldImpl implements worldStrategy{
    public String[] getWorld(){
        String[] layout = {
                "oooppmpppppooooo",
                "oophhppppfffppoo",
                "opppppmpppoooppo",
                "oppmmmppppoopppp",
                "ooopfppphhppppoo",
                "opfppfppppphhppo",
                "ooopppoooooooooo",
                "opppppoppphppmoo",
                "opppppopphpppfoo",
                "pfffppppopffpppp",
                "ppppppppoooppppp",
                "oppmmmppppoooooo",
                "ooppppppffppppoo",
                "oooopppppppppooo",
                "ooppphhppooooooo",
                "ooooopppppppppoo"
        };
        return layout;
    }

    @Override
    public void addWorldElements(City[][] cities, List<Unit> unitList) {
        cities[8][12] = new CityImpl();
        ((CityImpl)cities[8][12]).setCity_Owner(Player.RED);
        cities[4][5] = new CityImpl();
        ((CityImpl)cities[4][5]).setCity_Owner(Player.BLUE);
        unitList.add(new UnitImpl(new Position(3,8),Player.RED,GameConstants.ARCHER));
        unitList.add(new UnitImpl(new Position(4,4),Player.BLUE,GameConstants.LEGION));
        unitList.add(new UnitImpl(new Position(5,5),Player.RED,GameConstants.SETTLER));
    }
}
