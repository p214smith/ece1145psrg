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
  private Tile tile;
  private Unit unit;

  /** Fixture for alphaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl();
    city = new CityImpl();
    tile = new TileImpl();
    unit = new UnitImpl();
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
    assertThat(city.getOwner(), is(Player.RED));
  }
  @Test
  public void checkTerrainTileLocations() {
    assertThat (tile, is(notNullValue()));
    assertThat (tile.getTypeString(new Position(1,0)), is(GameConstants.OCEANS));
    assertThat (tile.getTypeString(new Position(0,1)), is(GameConstants.HILLS));
    assertThat (tile.getTypeString(new Position(2,2)), is(GameConstants.MOUNTAINS));
  }
  @Test
  public void checkUnitLocations() {
    assertThat (unit, is(notNullValue()));
    assertThat (unit.getTypeString(new Position(2,0)), is(GameConstants.ARCHER));
    assertThat (unit.getTypeString(new Position(3,2)), is(GameConstants.LEGION));
    assertThat (unit.getTypeString(new Position(4,3)), is(GameConstants.SETTLER));
  }
  @Test
  public void checkUnitOwner() {
    assertThat (unit, is(notNullValue()));
    assertThat (unit.getOwner(new Position(2,0)), is(Player.RED));
    assertThat (unit.getOwner(new Position(3,2)), is(Player.BLUE));
    assertThat (unit.getOwner(new Position(4,3)), is(Player.RED));
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
