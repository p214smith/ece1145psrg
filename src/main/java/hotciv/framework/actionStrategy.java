package hotciv.framework;

import java.util.List;

public interface actionStrategy {
    public void actionStrategy(Unit unit, List<Unit> unitList,City[][] cities,Tile[][] tiles);
}
