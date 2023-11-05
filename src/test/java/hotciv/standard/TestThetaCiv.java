package hotciv.standard;

import org.junit.Before;
import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/** Skeleton class for AlphaCiv test cases

 Updated Oct 2015 for using Hamcrest matchers

 This source code is from the book
 "Flexible, Reliable Software:
 Using Patterns and Agile Development"
 published 2010 by CRC Press.
 Author:
 Henrik B Christensen
 Department of Computer Science
 Aarhus University

 Please visit http://www.baerbak.com/ for further information.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */
public class TestThetaCiv {
    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new thetaFactory());

    }
    @Test
    public void checkTerrainTileLocations() {
        assertThat (game, is(notNullValue()));
        assertThat (game.getTileAt(new Position(0,1)).getTypeString(), is(GameConstants.HILLS));
        assertThat (game.getTileAt(new Position(2,2)).getTypeString(), is(GameConstants.MOUNTAINS));
        assertThat(game.getTileAt(new Position(1,0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(13,7)).getTypeString(), is(GameConstants.PLAINS));
    }
    @Test
    public void checkUnitLocations() {
        assertThat (game, is(notNullValue()));
        Unit tester = game.getUnitAt(new Position(2,0));
        assertThat (tester.getTypeString(), is(GameConstants.ARCHER));
        tester = game.getUnitAt(new Position(3,2));
        assertThat (tester.getTypeString(), is(GameConstants.LEGION));
        tester = game.getUnitAt(new Position(4,3));
        assertThat (tester.getTypeString(), is(GameConstants.SETTLER));
    }
    @Test
    public void checkUnitOwner() {
        assertThat (game, is(notNullValue()));
        Unit tester = game.getUnitAt(new Position(2,0));
        assertThat (tester.getOwner(), is(Player.RED));
        tester = game.getUnitAt(new Position(3,2));
        assertThat (tester.getOwner(), is(Player.BLUE));
        tester = game.getUnitAt(new Position(4,3));
        assertThat (tester.getOwner(), is(Player.RED));
    }
    @Test
    public void canChangeProductionUnit(){
        assertThat(game, is(notNullValue()));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        City test_city = game.getCityAt(new Position(1,1));
        assertThat(test_city.getProduction(),is(GameConstants.ARCHER));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.SETTLER);
        test_city = game.getCityAt(new Position(1,1));
        assertThat(test_city.getProduction(),is(GameConstants.SETTLER));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.LEGION);
        test_city = game.getCityAt(new Position(1,1));
        assertThat(test_city.getProduction(),is(GameConstants.LEGION));
        game.changeProductionInCityAt(new Position(1,1),"ufo");
        test_city = game.getCityAt(new Position(1,1));
        assertThat(test_city.getProduction(),is("ufo"));
        game.changeProductionInCityAt(new Position(1,1),"nonUnit");
        test_city = game.getCityAt(new Position(1,1));
        assertThat(test_city.getProduction(),is("ufo"));
        assertThat(((CityImpl)test_city).getUnitCost(),is(60));

    }
    @Test
    public void create_Ufo_Unit(){
        game.changeProductionInCityAt(new Position(1,1),"ufo");
        City test_city = game.getCityAt(new Position(1,1));
        assertThat(test_city.getProduction(),is("ufo"));
        for(int i = 0; i < 10 ; i++) {
            game.endOfTurn();
            game.endOfTurn();

        }
        Unit testUnit = game.getUnitAt(new Position(0,1));
        assertThat(testUnit.getTypeString(),is("ufo"));
    }
    @Test
    public void move_Ufo_Unit(){
        game.changeProductionInCityAt(new Position(1,1),"ufo");
        City test_city = game.getCityAt(new Position(1,1));
        assertThat(test_city.getProduction(),is("ufo"));
        for(int i = 0; i < 10 ; i++) {
            game.endOfTurn();
            game.endOfTurn();

        }
        Unit testUnit = game.getUnitAt(new Position(0,1));
        assertThat(testUnit.getTypeString(),is("ufo"));
        //moving over ocean
        assertTrue(game.moveUnit(new Position(0,1),new Position(1,0)));
        assertFalse(game.moveUnit(new Position(0,1),new Position(3,3)));
        assertTrue(game.moveUnit(new Position(1,0),new Position(1,1)));
        assertThat(((ufoUnitImpl)game.getUnitAt(new Position(1,1))).getMoveCount(),
                is(0));
        assertFalse(game.moveUnit(new Position(1,1),new Position(2,2)));
        game.endOfTurn();
        game.endOfTurn();
        testUnit = game.getUnitAt(new Position(1,1));
        assertThat(testUnit.getTypeString(),is("ufo"));
        assertTrue(game.moveUnit(new Position(1,1),new Position(2,2)));
        assertTrue(game.moveUnit(new Position(2,2),new Position(3,2)));
        testUnit = game.getUnitAt(new Position(3,2));
        assertThat(testUnit.getTypeString(),is("ufo"));
        assertFalse(game.moveUnit(new Position(3,2),new Position(4,1)));
        game.endOfTurn();
        game.endOfTurn();
        assertTrue(game.moveUnit(new Position(3,2),new Position(4,1)));
        City blue = game.getCityAt(new Position(4,1));
        assertThat(blue.getSize(),is(1));
        game.performUnitActionAt(new Position(4,1));
        blue = game.getCityAt(new Position(4,1));
        assertNull(blue);
        assertTrue(game.moveUnit(new Position(4,1),new Position(4,0)));
        assertThat(game.getTileAt(new Position(4,0)).getTypeString(),is(GameConstants.FOREST));
        game.performUnitActionAt(new Position(4,0));
        assertThat(game.getTileAt(new Position(4,0)).getTypeString(),is(GameConstants.PLAINS));
    }
}
