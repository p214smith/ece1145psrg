package hotciv.standard;

import hotciv.framework.*;

import java.util.*;

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
  public GameImpl(worldStrategy world,winningStrategy win,ageStrategy age,actionStrategy action){
    this.game_age = -4000;
    this.cities = new City[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
    this.cities[8][12] = new CityImpl();
    ((CityImpl)cities[8][12]).setCity_Owner(Player.RED);
    cities[4][5] = new CityImpl();
    ((CityImpl)cities[4][5]).setCity_Owner(Player.BLUE);
    this.current_Player = Player.RED;
    this.players = new Player[2];
    this.players[0] = Player.RED;
    this.players[1] = Player.BLUE;
    this.action = action;
    this.age = age;
    this.win = win;
    this.world = world;
    this.unitList = new ArrayList<>();
    this.unitList.add(new UnitImpl(new Position(3,8),Player.RED,GameConstants.ARCHER));
    this.unitList.add(new UnitImpl(new Position(4,4),Player.BLUE,GameConstants.LEGION));
    this.unitList.add(new UnitImpl(new Position(5,5),Player.RED,GameConstants.SETTLER));
    this.tiles = new TileImpl[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
    generateWorld();
  }
  public void generateWorld() {
    HashMap<Position, Tile> worldTerrain = new HashMap<Position,Tile>();
    String[] layout = this.world.getWorld();
    for(int i = 0; i < GameConstants.WORLDSIZE; i++) {
      for(int j = 0; j < GameConstants.WORLDSIZE; j++){
        char terrainType = layout[i].charAt(j);
        if (terrainType == 'p')
          this.tiles[i][j] = new TileImpl(GameConstants.PLAINS);
        if (terrainType == 'o')
          this.tiles[i][j] = new TileImpl(GameConstants.OCEANS);
        if (terrainType == 'm')
          this.tiles[i][j] = new TileImpl(GameConstants.MOUNTAINS);
        if (terrainType == 'h')
          this.tiles[i][j] = new TileImpl(GameConstants.HILLS);
        if (terrainType == 'f')
          this.tiles[i][j] = new TileImpl(GameConstants.FOREST);
      }
    }
  }
  protected actionStrategy action;
  protected ageStrategy age;
  protected worldStrategy world;
  protected winningStrategy win;
  protected int game_age;
  protected Tile[][] tiles;
  protected List<Unit> unitList;
  protected Player[] players;
  protected Player current_Player;
  protected City[][] cities;
  public Tile getTileAt( Position p ) { return this.tiles[p.getRow()][p.getColumn()]; }
  public Unit getUnitAt( Position p ) {
    for (Unit unit : this.unitList) {
      if (((UnitImpl)unit).getUnitPosition().hashCode() == p.hashCode()) return unit;
    }
    return null;}
  public City getCityAt( Position p ) { return this.cities[p.getRow()][p.getColumn()]; }
  public Player getPlayerInTurn() { return this.current_Player; }
  public Player getWinner() {

      return win.getWinner(this.game_age,this.cities);}
  public int getAge() { return this.game_age; }
  public boolean moveUnit( Position from, Position to ) {
    if (Objects.equals(this.tiles[to.getRow()][to.getColumn()].getTypeString(), GameConstants.MOUNTAINS))
      return false;
    if (Objects.equals(this.tiles[to.getRow()][to.getColumn()].getTypeString(), GameConstants.OCEANS))
      return false;
    boolean proper_Move_Distance = Math.abs(from.getColumn() - to.getColumn())<= 1
            && Math.abs(from.getRow() - to.getRow()) <= 1;
    if(!proper_Move_Distance) return false;

    Unit fromTile = null;
    Unit toTile = null;
    for (Unit unit : this.unitList) {
      if (((UnitImpl)unit).getUnitPosition().hashCode() == from.hashCode()){

        fromTile = unit;
      }}
    for (Unit unit1 : this.unitList) {
      if (((UnitImpl)unit1).getUnitPosition().hashCode() == to.hashCode()) toTile = unit1;}
    if (fromTile == null) return false;
    if (this.current_Player != fromTile.getOwner()) return false;
    if( Objects.nonNull(this.cities[to.getRow()][to.getColumn()])
            && this.cities[to.getRow()][to.getColumn()].getOwner() != fromTile.getOwner()){
      ((CityImpl)this.cities[to.getRow()][to.getColumn()]).setCity_Owner(fromTile.getOwner());
      ((UnitImpl)fromTile).decrementMove();
      return true;

    }
    if( Objects.nonNull(this.cities[to.getRow()][to.getColumn()])
            && this.cities[to.getRow()][to.getColumn()].getOwner() == fromTile.getOwner()){
      return false;
    }
    if (toTile == null){
      if (fromTile.getMoveCount() > 0){
        ((UnitImpl)fromTile).setLocation(to);
        ((UnitImpl)fromTile).decrementMove();
        return true;}
    }
    if (toTile.getOwner() != this.current_Player){
      this.unitList.remove(toTile);
      ((UnitImpl)fromTile).setLocation(to);
      ((UnitImpl)fromTile).decrementMove();
      return true;}
    return false;
  }
  public void endOfTurn() {
    if(this.current_Player == Player.RED)
      this.current_Player = Player.BLUE;
    else{
      this.current_Player = Player.RED;
      ageWorld();
      for(int i = 0; i < GameConstants.WORLDSIZE;i++){
        for(int j = 0; j < GameConstants.WORLDSIZE;j++){
          if(Objects.nonNull(this.cities[i][j])){
            ((CityImpl)this.cities[i][j]).add_production();
            create_New_Unit(new Position(i,j),this.cities[i][j].getProduction(),((CityImpl)this.cities[i][j]).getUnitCost());
          }
        }
      }
      for(Unit unit : this.unitList)
        ((UnitImpl)unit).resetMove();
    }
  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {
    if (Objects.equals(unitType, GameConstants.LEGION) || Objects.equals(unitType, GameConstants.ARCHER) || Objects.equals(unitType, GameConstants.SETTLER))
      ((CityImpl)this.cities[p.getRow()][p.getColumn()]).setProduction_Unit(unitType);
  }
  public void performUnitActionAt( Position p ) {
    Unit unit = getUnitAt(p);
    this.action.actionStrategy(unit,this.unitList,this.cities);
  }

  public Player[] getPlayers() {
    return players;
  }
  public void create_New_Unit(Position p,String unitType, int cost){
    if (Objects.nonNull(this.cities[p.getRow()][p.getColumn()])) {
      if (this.cities[p.getRow()][p.getColumn()].getTreasury() >= cost) {
        Position newUnitPosition = FindPositionForNewUnit(p);
        if (Objects.nonNull(newUnitPosition)) {
          unitList.add(new UnitImpl(newUnitPosition, this.cities[p.getRow()][p.getColumn()].getOwner(), unitType));
          ((CityImpl) this.cities[p.getRow()][p.getColumn()]).take_treasury(cost);
        }
      }
    }
  }
  public Position FindPositionForNewUnit(Position P) {
    Position unitLocation = new Position(P.getRow(), P.getColumn());
    Position newLocation;

    for (int i = 0; i < 8; i++) {
      newLocation = getNextPosition(unitLocation, i);

      if (isValidLocation(newLocation, unitLocation)) {
        return newLocation;
      }
    }

    return null;
  }

  private Position getNextPosition(Position current, int direction) {
    int row = current.getRow();
    int col = current.getColumn();

    switch (direction) {
      case 0:
        row--;
        break;
      case 1:
        row--;
        col++;
        break;
      case 2:
        col++;
        break;
      case 3:
        row++;
        col++;
        break;
      case 4:
        row++;
        break;
      case 5:
        row++;
        col--;
        break;
      case 6:
        col--;
        break;
      case 7:
        row--;
        col--;
        break;
    }
    return new Position(row, col);
  }


  private boolean isValidLocation(Position location, Position unitLocation) {
    if (isOccupied(location) || isMountainOrOcean(location)) {
      return false;
    }
    return true;
  }

  public boolean isOccupied(Position unitLocation) {
    for (Unit unit : this.unitList) {
      if (((UnitImpl) unit).getUnitPosition().hashCode() == unitLocation.hashCode())
        return true;
    }
    return false;
  }

  private boolean isMountainOrOcean(Position location) {
    String tileType = this.tiles[location.getRow()][location.getColumn()].getTypeString();
    return Objects.equals(tileType, GameConstants.MOUNTAINS) || Objects.equals(tileType, GameConstants.OCEANS);
  }
  public void ageWorld(){
    this.game_age = age.ageGame(this.game_age);

  }
}
