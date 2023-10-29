package hotciv.framework;

import java.util.List;

public interface attackStrategy {
    boolean attack(Unit fromTile, Unit toTile, Tile[][] tiles, List<Unit> unitList,City[][] cities, int max);
}
