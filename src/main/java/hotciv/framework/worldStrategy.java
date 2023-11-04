package hotciv.framework;

import java.util.List;

public interface worldStrategy {
    public String[] getWorld();
    public void addWorldElements(City[][] cities, List<Unit> unitList);
}
