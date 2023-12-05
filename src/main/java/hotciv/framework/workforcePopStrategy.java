package hotciv.framework;

public interface workforcePopStrategy {
    void changeWorkforceFocusInCityAt(Position p,String balance, City c);
    public void changeProductionInCityAt( Position p, String unitType ,City[][] cities);
    public void updatePopulation(City c);
    public void update_Production_Food(City c, int[] values);
}
