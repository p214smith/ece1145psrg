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
  public GameImpl(FactoryImpl gameFactory) {
    this.game_age = -4000;
    this.cities = new City[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
    this.current_Player = Player.RED;
    this.players = new Player[2];
    this.players[0] = Player.RED;
    this.players[1] = Player.BLUE;
    this.attack = gameFactory.getAttackStrategy();
    this.action = gameFactory.getActionStrategy();
    this.age = gameFactory.getAgeStrategy();
    this.win = gameFactory.getWinningStrategy();
    this.world = gameFactory.getWorldStrategy();
    this.work = gameFactory.getWorkforceStrategy();
    this.unitList = new ArrayList<>();
    this.world.addWorldElements(this.cities, this.unitList);
    this.tiles = new TileImpl[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
    generateWorld();
    for(int i = 0; i < GameConstants.WORLDSIZE;i++){
      for(int j = 0; j < GameConstants.WORLDSIZE;j++){
        if (Objects.nonNull(getCityAt(new Position(i,j)))){
          int[] t = getTilesAroundCity(new Position(i,j));
          work.update_Production_Food(getCityAt(new Position(i,j)),t);
        }
      }
    }

  }

  public void generateWorld() {

    String[] layout = this.world.getWorld();
    for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
      for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
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

  protected attackStrategy attack;
  protected actionStrategy action;
  protected ageStrategy age;
  protected worldStrategy world;
  protected winningStrategy win;
  protected workforcePopStrategy work;
  protected int game_age;
  protected Tile[][] tiles;
  protected List<Unit> unitList;
  protected Player[] players;
  protected Player current_Player;
  protected City[][] cities;
  protected GameObserver observer;

  public Tile getTileAt(Position p) {
    return this.tiles[p.getRow()][p.getColumn()];
  }

  public Unit getUnitAt(Position p) {
    return this.attack.getUnitAt(p, this.unitList);
  }

  public City getCityAt(Position p) {
    return this.cities[p.getRow()][p.getColumn()];
  }

  public Player getPlayerInTurn() {
    return this.current_Player;
  }

  public Player getWinner() {

    return win.getWinner(this.game_age, this.cities);
  }

  public int getAge() {
    return this.game_age;
  }

  public boolean moveUnit(Position from, Position to) {
    boolean move =attack.move_unit(from, to, this.tiles, this.unitList, this.cities, this.current_Player, this.win);
    if (move){
      observer.worldChangedAt(from);
      observer.worldChangedAt(to);
    }
    return move;
  }

  public void endOfTurn() {
    if (this.current_Player == Player.RED)
      this.current_Player = Player.BLUE;
    else {
      this.current_Player = Player.RED;
      for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
        for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
          if (Objects.nonNull(cities[i][j])) {
            ((CityImpl) cities[i][j]).incrementFood_Balance();
            ((CityImpl) cities[i][j]).add_production();
            this.work.updatePopulation(getCityAt(new Position(i,j)));
            this.work.update_Production_Food(getCityAt(new Position(i,j)),getTilesAroundCity(new Position(i,j)));
            create_New_Unit(new Position(i, j), cities[i][j].getProduction(), ((CityImpl) cities[i][j]).getUnitCost());
          }
        }
      }
      this.attack.end_of_turn(this.current_Player, this.unitList, this.cities, this.tiles);
      ageWorld();
      win.iterateRound();
      observer.turnEnds(this.current_Player,this.game_age);
    }
  }

  public void changeWorkForceFocusInCityAt(Position p, String balance) {
    if (Objects.nonNull(getCityAt(p))){
      this.work.changeWorkforceFocusInCityAt(p, balance, getCityAt(p));
      int [] t = getTilesAroundCity(p);
      this.work.update_Production_Food(getCityAt(p),t);
  }}

  public void changeProductionInCityAt(Position p, String unitType) {
    if (Objects.nonNull(getCityAt(p))){
      if (getCityAt(p).getOwner() == this.current_Player) {
          this.work.changeProductionInCityAt(p, unitType, this.cities);
      }
    }
  }

  public void performUnitActionAt(Position p) {
    Unit unit = getUnitAt(p);
    this.action.actionStrategy(unit, this.unitList, this.cities, this.tiles);
  }
  public void addObserver(GameObserver observer) {this.observer = observer;}
  public void setTileFocus(Position position) {this.observer.tileFocusChangedAt(position);}

  public Player[] getPlayers() {
    return players;
  }


  public void create_New_Unit(Position p, String unitType, int cost) {
    if (Objects.nonNull(this.cities[p.getRow()][p.getColumn()])) {
      if (this.cities[p.getRow()][p.getColumn()].getTreasury() >= cost) {
        Position newUnitPosition = FindPositionForNewUnit(p);
        if (Objects.nonNull(newUnitPosition)) {
          this.attack.create_New_Unit(p, unitType, cost, this.unitList, cities, newUnitPosition);
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
    return this.attack.isOccupied(unitLocation, this.unitList);
  }

  private boolean isMountainOrOcean(Position location) {
    String tileType = this.tiles[location.getRow()][location.getColumn()].getTypeString();
    return Objects.equals(tileType, GameConstants.MOUNTAINS) || Objects.equals(tileType, GameConstants.OCEANS);
  }

  public void ageWorld() {
    this.game_age = age.ageGame(this.game_age);

  }

  public int[] getTilesAroundCity(Position p) {
    int[] tile_types = new int[5];
    Position newLocation;
    int plains = 0;
    int oceans = 0;
    int forest = 0;
    int hills = 0;
    int mountain = 0;
    for (int i = 0; i < 8; i++) {
      newLocation = getNextPosition(p, i);
      if (Objects.equals(getTileAt(newLocation).getTypeString(), GameConstants.PLAINS))
        plains += 1;
      else if (Objects.equals(getTileAt(newLocation).getTypeString(), GameConstants.OCEANS))
        oceans += 1;
      else if (Objects.equals(getTileAt(newLocation).getTypeString(), GameConstants.FOREST))
        forest += 1;
      else if (Objects.equals(getTileAt(newLocation).getTypeString(), GameConstants.HILLS))
        hills += 1;
      else if (Objects.equals(getTileAt(newLocation).getTypeString(), GameConstants.MOUNTAINS))
        mountain += 1;
    }
    tile_types[0] = plains;
    tile_types[1] = oceans;
    tile_types[2] = forest;
    tile_types[3] = hills;
    tile_types[4] = mountain;
    return tile_types;
  }
}
