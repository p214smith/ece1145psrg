package hotciv.stub;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

/** Test stub for game for visual testing of
 * minidraw based graphics.
 *
 * SWEA support code.
 *
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

public class StubGame2 implements Game {

  // === Unit handling ===
  private Position pos_archer_red;
  private Position pos_legion_blue;
  private Position pos_settler_red;
  private Position pos_ufo_red;
  private City city;
  private Position pos_city;
  private Unit red_archer;
  private Unit red_settler;
  public Unit getUnitAt(Position p) {
    if ( p.equals(pos_archer_red) ) {
      return red_archer;
    }
    if ( p.equals(pos_settler_red) ) {
      return red_settler;
    }
    if ( p.equals(pos_legion_blue) ) {
      return new StubUnit( GameConstants.LEGION, Player.BLUE );
    }
    if ( p.equals(pos_ufo_red) ) {
      return new StubUnit( ThetaConstants.UFO, Player.RED );
    }
    return null;
  }

  // Stub only allows moving red archer
  public boolean moveUnit( Position from, Position to ) { 
    System.out.println( "-- StubGame2 / moveUnit called: "+from+"->"+to );
    if ( from.equals(pos_archer_red) ) {
      pos_archer_red = to;
    }
    boolean proper_Move_Distance = Math.abs(from.getColumn() - to.getColumn())<= 1
            && Math.abs(from.getRow() - to.getRow()) <= 1;
    if(!proper_Move_Distance) return false;
    // notify our observer(s) about the changes on the tiles
    gameObserver.worldChangedAt(from);
    gameObserver.worldChangedAt(to);
    return true; 
  }

  // === Turn handling ===
  private Player inTurn;
  public void endOfTurn() {
    System.out.println( "-- StubGame2 / endOfTurn called." );
    inTurn = (getPlayerInTurn() == Player.RED ?
              Player.BLUE : 
              Player.RED );
    // no age increments
    gameObserver.turnEnds(inTurn, -4000);
  }
  public Player getPlayerInTurn() { return inTurn; }
  

  // === Observer handling ===
  protected GameObserver gameObserver;
  // observer list is only a single one...
  public void addObserver(GameObserver observer) {
    gameObserver = observer;
  } 

  public StubGame2() { 
    defineWorld(1); 
    // AlphaCiv configuration
    pos_archer_red = new Position( 2, 0);
    pos_legion_blue = new Position( 3, 2);
    pos_settler_red = new Position( 4, 3);
    pos_ufo_red = new Position( 6, 4);
    pos_city = new Position(3,3);
    city = new CityStub();
    // the only one I need to store for this stub
    red_archer = new StubUnit( GameConstants.ARCHER, Player.RED );   
    red_settler = new StubUnit(GameConstants.SETTLER,Player.RED);
    inTurn = Player.RED;
  }

  // A simple implementation to draw the map of DeltaCiv
  protected Map<Position,Tile> world; 
  public Tile getTileAt( Position p ) { return world.get(p); }


  /** define the world.
   * @param worldType 1 gives one layout while all other
   * values provide a second world layout.
   */
  protected void defineWorld(int worldType) {
    world = new HashMap<Position,Tile>();
    for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
      for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
        Position p = new Position(r,c);
        world.put( p, new StubTile(GameConstants.PLAINS));
      }
    }
  }

  // TODO: Add more stub behaviour to test MiniDraw updating
  public City getCityAt( Position p ) {
    if (p.equals(pos_city))
      return city;
    else if (p.equals(pos_settler_red) && red_settler == null)
      return city;
    else
      return null; }
  public Player getWinner() { return null; }
  public int getAge() { return 0; }  
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
  public void performUnitActionAt( Position p ) {
    if (p.hashCode() == pos_settler_red.hashCode()){
      red_settler = null;
      gameObserver.worldChangedAt(p);
    }
  }

  public void setTileFocus(Position position) {
    gameObserver.tileFocusChangedAt(position);
    System.out.println("-- StubGame2 / setTileFocus called.");
    System.out.println(" *** IMPLEMENTATION PENDING ***");
  }

}

class StubUnit implements  Unit {
  private String type;
  private Player owner;
  public StubUnit(String type, Player owner) {
    this.type = type;
    this.owner = owner;
  }
  public String getTypeString() { return type; }
  public Player getOwner() { return owner; }
  public int getMoveCount() { return 1; }
  public int getDefensiveStrength() { return 0; }
  public int getAttackingStrength() { return 0; }
}
class CityStub implements City {
  boolean redOwns = true;
  // a testing method just to make some
  // state changes
  public void  makeAChange() {
    redOwns = ! redOwns;
  }
  public Player getOwner() {
    return (redOwns ? Player.RED : Player.BLUE);
  }
  public int getSize() {
    return (redOwns ? 4 : 9);
  }
  public int getTreasury() {
    return 0;
  }
  public String getProduction() {
    return GameConstants.LEGION;
  }
  public String getWorkforceFocus() {
    return GameConstants.foodFocus;
  }
}
