package hotciv.standard;
import hotciv.framework.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
public class testEpsilonAttackStrategy {
    private attackStrategy attack;
    private City[][] cities;//stub for cities
    private List<Unit> unitList;//stub for units
    private Tile[][] tiles;//stub for tiles
    @Before
    public void setUp(){
        cities = new City[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
        cities[8][12] = new CityImpl();
        ((CityImpl)cities[8][12]).setCity_Owner(Player.RED);
        tiles = new TileImpl[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
        for(int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for(int j = 0; j < GameConstants.WORLDSIZE; j++){
                tiles[i][j] = new TileImpl(GameConstants.PLAINS);
            }
        }
        tiles[3][3] = new TileImpl(GameConstants.HILLS);
        tiles[9][12] = new TileImpl(GameConstants.FOREST);
        attack = new epsilonCivAttackStrategy();
        unitList = new ArrayList<>();
        unitList.add(new UnitImpl(new Position(8,12),Player.RED,GameConstants.ARCHER));
        unitList.add(new UnitImpl(new Position(9,12),Player.BLUE,GameConstants.LEGION));
        unitList.add(new UnitImpl(new Position(9,11),Player.BLUE,GameConstants.LEGION));
        unitList.add(new UnitImpl(new Position(9,13),Player.BLUE,GameConstants.LEGION));
        unitList.add(new UnitImpl(new Position(3,3),Player.RED,GameConstants.ARCHER));
        unitList.add(new UnitImpl(new Position(4,3),Player.BLUE,GameConstants.LEGION));
    }

    @Test
    public void BlueLegionAttacksRedArcherWins(){
        Unit from = new UnitImpl(new Position(9,12),Player.BLUE,GameConstants.LEGION);
        Unit to =new UnitImpl(new Position(8,12),Player.RED,GameConstants.ARCHER);

        assertTrue(attack.attack(from,to,tiles,unitList,cities,2));
    }
    @Test
    public void BlueLegionAttacksRedArcherLoses(){
        Unit from = new UnitImpl(new Position(4,3),Player.BLUE,GameConstants.LEGION);
        Unit to =new UnitImpl(new Position(3,3),Player.RED,GameConstants.ARCHER);

        assertFalse(attack.attack(from,to,tiles,unitList,cities,2));
    }
    @Test
    public void BlueLegionAttacksRedArcherWithBackupLoses(){
        Unit from = new UnitImpl(new Position(9,12),Player.BLUE,GameConstants.LEGION);
        Unit to =new UnitImpl(new Position(8,12),Player.RED,GameConstants.ARCHER);
        unitList.add(new UnitImpl(new Position(8,11),Player.RED,GameConstants.ARCHER));
        unitList.add(new UnitImpl(new Position(8,13),Player.RED,GameConstants.ARCHER));
        assertFalse(attack.attack(from,to,tiles,unitList,cities,2));
    }

}

