package hotciv.standard;

import hotciv.framework.*;

import java.util.Objects;

public class CityImpl implements City {

    public CityImpl(){
        this.city_Owner = null;
        this.treasury = 0;
        this.production_Unit = GameConstants.LEGION;
        this.production_level = 6;
    }
    protected Player city_Owner;

    protected int treasury;
    protected String production_Unit;
    protected int production_level;
    @Override
    public Player getOwner() {
        return city_Owner;
    }
    public int getSize() {
        return 1;
    }

    @Override
    public int getTreasury() {
        return treasury;
    }

    @Override
    public String getProduction() {
        return production_Unit;
    }
    public void setCity_Owner(Player p){ this.city_Owner = p;}
    @Override
    public String getWorkforceFocus() {
        return null;
    }
    public void add_production() {this.treasury += this.production_level;}
    public void take_treasury(int t){this.treasury -= t;}
    public void setProduction_Unit(String s){this.production_Unit = s;}
    public int getUnitCost(){
        if (Objects.equals(this.production_Unit, GameConstants.LEGION))
            return 15;
        if (Objects.equals(this.production_Unit, GameConstants.ARCHER))
            return 10;
        else
            return 30;

    }
}
