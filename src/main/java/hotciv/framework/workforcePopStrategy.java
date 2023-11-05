package hotciv.framework;

public interface workforcePopStrategy {
    void changeWorkforceFocusInCityAt(Position p,String balance);
    public void changeProductionInCityAt( Position p, String unitType ,City[][] cities);
}
