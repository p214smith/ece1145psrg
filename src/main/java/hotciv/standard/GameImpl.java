package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Objects;

/** Skeleton implementation of HotCiv.
 
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

public class GameImpl implements Game {
  public GameImpl(){
    this.game_age = -4000;
    this.cities = new CityImpl[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
    cities[1][1] = new CityImpl();
    cities[1][1].setCity_Owner(Player.RED);
    cities[4][1] = new CityImpl();
    cities[4][1].setCity_Owner(Player.BLUE);
    this.current_Player = Player.RED;
    this.players = new Player[2];
    this.players[0] = Player.RED;
    this.players[1] = Player.BLUE;
  }
  protected int game_age;
  protected Player[] players;
  protected Player current_Player;
  protected City[][] cities;
  public Tile getTileAt( Position p ) { return null; }
  public Unit getUnitAt( Position p ) { return null; }
  public City getCityAt( Position p ) { return this.cities[p.getRow()][p.getColumn()]; }
  public Player getPlayerInTurn() { return this.current_Player; }
  public Player getWinner() { if (this.game_age == -3000)
    return Player.RED;
   else
    return null;}
  public int getAge() { return this.game_age; }
  public boolean moveUnit( Position from, Position to ) {
    return false;
  }
  public void endOfTurn() {
    if(this.current_Player == Player.RED)
      this.current_Player = Player.BLUE;
    else{
      this.current_Player = Player.RED;
      this.game_age += 100;
      this.cities[1][1].add_production();
      this.cities[4][1].add_production();
    }
  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {
    if (Objects.equals(unitType, GameConstants.LEGION) || Objects.equals(unitType, GameConstants.ARCHER) || Objects.equals(unitType, GameConstants.SETTLER))
      this.cities[p.getRow()][p.getColumn()].setProduction_Unit(unitType);
  }
  public void performUnitActionAt( Position p ) {}

  public Player[] getPlayers() {
    return players;
  }
}
