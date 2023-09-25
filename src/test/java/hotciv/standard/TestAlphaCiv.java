package hotciv.standard;

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
public class TestAlphaCiv {
  private Game game;
  private City city;
  private TileImpl tileimpl;
  private UnitImpl unitimpl;
  private CityImpl cityimpl;

  /** Fixture for alphaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl();
    city = new CityImpl();
    cityimpl = new CityImpl();
  }

  // FRS p. 455 states that 'Red is the first player to take a turn'.
  @Test
  public void shouldBeRedAsStartingPlayer() {
    assertThat(game, is(notNullValue()));
    // TODO: reenable the assert below to get started...
     assertThat(game.getPlayerInTurn(), is(Player.RED));
  }
  @Test
  public void redCityAtRightPosition() {
    assertThat(city, is(notNullValue()));
    city.setCity_Owner(Player.RED);
    assertThat(city.getOwner(), is(Player.RED));
    City test_City = game.getCityAt(new Position(1,1));
    assertThat(test_City.getOwner(), is(Player.RED));
  }
  @Test
  public void checkTerrainTileLocations() {
    assertThat (game, is(notNullValue()));
    for(int i = 0; i < GameConstants.WORLDSIZE; i++) {
      for(int j = 0; j < GameConstants.WORLDSIZE; j++){
        if(i == 0 && j == 1)
          assertThat (game.getTileAt(new Position(i,j)).getTypeString(), is(GameConstants.HILLS));
        else if(i == 1 && j == 0)
          assertThat (game.getTileAt(new Position(i,j)).getTypeString(), is(GameConstants.OCEANS));
        else if(i == 2 && j == 2)
          assertThat (game.getTileAt(new Position(i,j)).getTypeString(), is(GameConstants.MOUNTAINS));
        else
          assertThat(game.getTileAt(new Position(i,j)).getTypeString(), is(GameConstants.PLAINS));
      }
    }
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
  public void citiesReturnPopulationOfOne() {
    assertThat(cityimpl, is(notNullValue()));
    assertThat(cityimpl.getSize(), is(1));
  }
  @Test
  public void gameStartingAgeis4000BC(){
    assertThat(game ,is(notNullValue()));
    assertThat(game.getAge(),is(-4000));
  }
  @Test
  public void checkAllCitiesAreInRightPosition(){
    assertThat(game , is(notNullValue()));
    City test_city;
    test_city = game.getCityAt(new Position(0,0));
    assertThat(test_city, is(nullValue()));
    test_city = game.getCityAt(new Position(1, 1));
    assertThat(test_city.getOwner(), is(Player.RED));
    test_city = game.getCityAt(new Position(4, 1));
    assertThat(test_city.getOwner(), is(Player.BLUE));
  }
  @Test
  public void checkGamePlayers(){
    assertThat(game , is(notNullValue()));
    Player[] players = game.getPlayers();
    int i = players.length;
    assertThat(i,is(2));
    assertThat(players[0],is(Player.RED));
    assertThat(players[1],is(Player.BLUE));
  }
  @Test
  public void checkCitySizeIsOne(){
    assertThat(game, is(notNullValue()));
    City test_city = game.getCityAt(new Position(1,1));
    assertThat(test_city.getSize(),is(1));
    test_city = game.getCityAt(new Position(4,1));
    assertThat(test_city.getSize(),is(1));
    game.endOfTurn();
    game.endOfTurn();
    test_city = game.getCityAt(new Position(1,1));
    assertThat(test_city.getSize(),is(1));
    test_city = game.getCityAt(new Position(4,1));
    assertThat(test_city.getSize(),is(1));
  }
  @Test
  public void addSixProductionAfterEachRound(){
    assertThat(game, is(notNullValue()));
    City test_city = game.getCityAt(new Position(1,1));
    assertThat(test_city.getTreasury(),is(0));
    test_city = game.getCityAt(new Position(4,1));
    assertThat(test_city.getTreasury(),is(0));
    game.endOfTurn();
    game.endOfTurn();
    test_city = game.getCityAt(new Position(1,1));
    assertThat(test_city.getTreasury(),is(6));
    test_city = game.getCityAt(new Position(4,1));
    assertThat(test_city.getTreasury(),is(6));
  }
  @Test
  public void RedWinsAt300BC(){
    assertThat(game, is(notNullValue()));
    int current_age;
    for(int i = 0; i < 10;i++){
      game.endOfTurn();
      game.endOfTurn();
      current_age = game.getAge();
      if (current_age < -3000)
        assertThat(game.getWinner(), is(nullValue()));
      else
        assertThat(game.getWinner(),is(Player.RED));
    }

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
    game.changeProductionInCityAt(new Position(1,1),"nonUnit");
    test_city = game.getCityAt(new Position(1,1));
    assertThat(test_city.getProduction(),is(GameConstants.LEGION));


  }
  @Test
  public void moveUnit(){
    assertThat(game, is(notNullValue()));
    assertThat(game.moveUnit(new Position(2,0),new Position(2,1)),is(true));
    Unit t = game.getUnitAt(new Position(2,1));
    assertThat(t.getMoveCount(),is(0));
  }
  @Test
  public void testAttack(){
    assertThat(game, is(notNullValue()));
    assertThat(game.moveUnit(new Position(4,3),new Position(3,2)),is(true));
    assertThat(game.getUnitAt(new Position(3,2)).getTypeString(),is(GameConstants.SETTLER));
  }
  @Test
  public void playersChangeAfterTurn(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getPlayerInTurn(),is(Player.RED));
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(),is(Player.BLUE));
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(),is(Player.RED));
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(),is(Player.BLUE));
    game.endOfTurn();

  }
  @Test
  public void unit_cant_move_over_mountain(){
    assertThat(game, is(notNullValue()));
    assertFalse(game.moveUnit(new Position(3,2),new Position(2,2)));
  }
  /** REMOVE ME. Not a test of HotCiv, just an example of what
      matchers the hamcrest library has... */
  @Test
  public void shouldDefinitelyBeRemoved() {
    // Matching null and not null values
    // 'is' require an exact match
    String s = null;
    assertThat(s, is(nullValue()));
    s = "Ok";
    assertThat(s, is(notNullValue()));
    assertThat(s, is("Ok"));

    // If you only validate substrings, use containsString
    assertThat("This is a dummy test", containsString("dummy"));

    // Match contents of Lists
    List<String> l = new ArrayList<String>();
    l.add("Bimse");
    l.add("Bumse");
    // Note - ordering is ignored when matching using hasItems
    assertThat(l, hasItems(new String[] {"Bumse","Bimse"}));

    // Matchers may be combined, like is-not
    assertThat(l.get(0), is(not("Bumse")));
  }
}
