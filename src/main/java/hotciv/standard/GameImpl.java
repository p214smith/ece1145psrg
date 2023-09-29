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
  public GameImpl(){
    this.game_age = -4000;
    this.cities = new City[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
    this.cities[1][1] = new CityImpl();
    ((CityImpl)cities[1][1]).setCity_Owner(Player.RED);
    cities[4][1] = new CityImpl();
    ((CityImpl)cities[4][1]).setCity_Owner(Player.BLUE);
    this.current_Player = Player.RED;
    this.players = new Player[2];
    this.players[0] = Player.RED;
    this.players[1] = Player.BLUE;
    this.unitList = new ArrayList<>();
    this.unitList.add(new UnitImpl(new Position(2,0),Player.RED,GameConstants.ARCHER));
    this.unitList.add(new UnitImpl(new Position(3,2),Player.BLUE,GameConstants.LEGION));
    this.unitList.add(new UnitImpl(new Position(4,3),Player.RED,GameConstants.SETTLER));
    this.tiles = new TileImpl[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
    generateWorld();
  }
  public void generateWorld() {
    HashMap<Position, Tile> worldTerrain = new HashMap<Position,Tile>();
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
  public Player getWinner() { if( this.game_age == -3000)
    return Player.RED;
  else
    return null;}
  public int getAge() { return this.game_age; }
  public boolean moveUnit( Position from, Position to ) {
    if (Objects.equals(this.tiles[to.getRow()][to.getColumn()].getTypeString(), GameConstants.MOUNTAINS))
        return false;
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
      this.game_age += 100;
      ((CityImpl)this.cities[1][1]).add_production();
      ((CityImpl)this.cities[4][1]).add_production();
      if (Objects.equals(this.cities[1][1].getProduction(),GameConstants.LEGION) && this.cities[1][1].getTreasury() >= 15){
        Position newUnitPosition = FindPositionForNewUnit(new Position(1,1));
        if(Objects.nonNull(newUnitPosition)){
          unitList.add(new UnitImpl(newUnitPosition,this.cities[1][1].getOwner(),GameConstants.LEGION));
          ((CityImpl)this.cities[1][1]).take_treasury(15);
        }
      }
      if (Objects.equals(this.cities[4][1].getProduction(),GameConstants.LEGION) && this.cities[4][1].getTreasury() >= 15){
        Position newUnitPosition = FindPositionForNewUnit(new Position(4,1));
        if(Objects.nonNull(newUnitPosition)){
          unitList.add(new UnitImpl(newUnitPosition,this.cities[4][1].getOwner(),GameConstants.LEGION));
          ((CityImpl)this.cities[4][1]).take_treasury(15);
        }
      }
      if (Objects.equals(this.cities[1][1].getProduction(),GameConstants.ARCHER) && this.cities[1][1].getTreasury() >= 10){
        Position newUnitPosition = FindPositionForNewUnit(new Position(1,1));
        if(Objects.nonNull(newUnitPosition)){
          unitList.add(new UnitImpl(newUnitPosition,this.cities[1][1].getOwner(),GameConstants.ARCHER));
          ((CityImpl)this.cities[1][1]).take_treasury(10);
        }
      }
      if (Objects.equals(this.cities[4][1].getProduction(),GameConstants.ARCHER) && this.cities[4][1].getTreasury() >= 10){
        Position newUnitPosition = FindPositionForNewUnit(new Position(4,1));
        if(Objects.nonNull(newUnitPosition)){
          unitList.add(new UnitImpl(newUnitPosition,this.cities[4][1].getOwner(),GameConstants.ARCHER));
          ((CityImpl)this.cities[4][1]).take_treasury(10);
        }
      }
      if (Objects.equals(this.cities[1][1].getProduction(),GameConstants.SETTLER) && this.cities[1][1].getTreasury() >= 30){
        Position newUnitPosition = FindPositionForNewUnit(new Position(1,1));
        if(Objects.nonNull(newUnitPosition)){
          unitList.add(new UnitImpl(newUnitPosition,this.cities[1][1].getOwner(),GameConstants.SETTLER));
          ((CityImpl)this.cities[1][1]).take_treasury(30);
        }
      }
      if (Objects.equals(this.cities[4][1].getProduction(),GameConstants.LEGION) && this.cities[4][1].getTreasury() >= 30){
        Position newUnitPosition = FindPositionForNewUnit(new Position(4,1));
        if(Objects.nonNull(newUnitPosition)){
          unitList.add(new UnitImpl(newUnitPosition,this.cities[4][1].getOwner(),GameConstants.SETTLER));
          ((CityImpl)this.cities[4][1]).take_treasury(30);
        }
      }
    }
  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {
    if (Objects.equals(unitType, GameConstants.LEGION) || Objects.equals(unitType, GameConstants.ARCHER) || Objects.equals(unitType, GameConstants.SETTLER))
      ((CityImpl)this.cities[p.getRow()][p.getColumn()]).setProduction_Unit(unitType);
  }
  public void performUnitActionAt( Position p ) {}

  public Player[] getPlayers() {
    return players;
  }
  public Position FindPositionForNewUnit(Position P){
    Position unitLocation = new Position(P.getRow()-1,P.getColumn());
    Position newLocation = new Position(P.getRow()-1,P.getColumn());
    for (Unit unit : this.unitList) {
      if (((UnitImpl)unit).getUnitPosition().hashCode() == unitLocation.hashCode()) {
        newLocation = new Position(P.getRow()-1,P.getColumn()+1);
        break;}}
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.MOUNTAINS))
      newLocation = new Position(P.getRow()-1,P.getColumn()+1);
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.OCEANS))
      newLocation = new Position(P.getRow()-1,P.getColumn()+1);
    if (unitLocation == newLocation)return unitLocation;
    unitLocation = new Position(P.getRow()-1,P.getColumn()+1);
    for (Unit unit : this.unitList) {
      if (((UnitImpl)unit).getUnitPosition().hashCode() == unitLocation.hashCode()) {
        newLocation = new Position(P.getRow(),P.getColumn()+1);
        break;}}
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.MOUNTAINS))
      newLocation = new Position(P.getRow(),P.getColumn()+1);
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.OCEANS))
      newLocation = new Position(P.getRow(),P.getColumn()+1);
    if (unitLocation == newLocation)return unitLocation;
    unitLocation = new Position(P.getRow(),P.getColumn()+1);
    for (Unit unit : this.unitList) {
      if (((UnitImpl)unit).getUnitPosition().hashCode() == unitLocation.hashCode()) {
        newLocation = new Position(P.getRow()+1,P.getColumn()+1);
        break;}}
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.MOUNTAINS))
      newLocation = new Position(P.getRow()+1,P.getColumn()+1);
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.OCEANS))
      newLocation = new Position(P.getRow()+1,P.getColumn()+1);
    if (unitLocation == newLocation)return unitLocation;
    unitLocation = new Position(P.getRow()+1,P.getColumn()+1);
    for (Unit unit : this.unitList) {
      if (((UnitImpl)unit).getUnitPosition().hashCode() == unitLocation.hashCode()) {
        newLocation = new Position(P.getRow()+1,P.getColumn());
        break;}}
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.MOUNTAINS))
      newLocation = new Position(P.getRow()+1,P.getColumn());
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.OCEANS))
      newLocation = new Position(P.getRow()+1,P.getColumn());
    if (unitLocation == newLocation)return unitLocation;
    unitLocation = new Position(P.getRow()+1,P.getColumn());
    for (Unit unit : this.unitList) {
      if (((UnitImpl)unit).getUnitPosition().hashCode() == unitLocation.hashCode()) {
        newLocation = new Position(P.getRow()+1,P.getColumn()-1);
        break;}}
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.MOUNTAINS))
      newLocation = new Position(P.getRow()+1,P.getColumn()-1);
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.OCEANS))
      newLocation = new Position(P.getRow()+1,P.getColumn()-1);
    if (unitLocation == newLocation)return unitLocation;
    unitLocation = new Position(P.getRow()+1,P.getColumn()-1);
    for (Unit unit : this.unitList) {
      if (((UnitImpl)unit).getUnitPosition().hashCode() == unitLocation.hashCode()) {
        newLocation = new Position(P.getRow(),P.getColumn()-1);
        break;}}
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.MOUNTAINS))
      newLocation = new Position(P.getRow(),P.getColumn()-1);
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.OCEANS))
      newLocation = new Position(P.getRow(),P.getColumn()-1);
    if (unitLocation == newLocation)return unitLocation;
    unitLocation = new Position(P.getRow(),P.getColumn()-1);
    for (Unit unit : this.unitList) {
      if (((UnitImpl)unit).getUnitPosition().hashCode() == unitLocation.hashCode()) {
        newLocation = new Position(P.getRow()+1,P.getColumn()-1);
        break;}}
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.MOUNTAINS))
      newLocation = new Position(P.getRow()+1,P.getColumn()-1);
    if (Objects.equals(this.tiles[unitLocation.getRow()][unitLocation.getColumn()].getTypeString() , GameConstants.OCEANS))
      newLocation = new Position(P.getRow()+1,P.getColumn()-1);
    if (unitLocation == newLocation)return unitLocation;
    return null;}
}
