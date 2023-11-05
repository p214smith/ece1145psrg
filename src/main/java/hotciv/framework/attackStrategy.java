package hotciv.framework;

import java.util.List;

public interface attackStrategy {
    boolean attack(Unit fromTile, Unit toTile, Tile[][] tiles, List<Unit> unitList,City[][] cities, int max);
    boolean move_unit(Position from, Position to , Tile[][] tiles, List<Unit> unitList,City[][] cities,Player current_Player,winningStrategy win);
    void end_of_turn(Player current_Player, List<Unit> unitList,City[][] cities,Tile[][] tiles);
    void create_New_Unit(Position p,String unitType, int cost,List<Unit> unitList,City[][] cities, Position newUnitPosition);
    Unit getUnitAt(Position p,List<Unit> unitList);
    boolean isOccupied(Position unitLocation,List<Unit> unitList);
}
