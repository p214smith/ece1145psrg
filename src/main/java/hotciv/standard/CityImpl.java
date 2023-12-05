package hotciv.standard;

import hotciv.framework.*;

import java.util.Objects;

public class CityImpl implements City {

    public CityImpl(){
        this.city_Owner = null;
        this.treasury = 0;
        this.production_Unit = GameConstants.LEGION;
        this.production_level = 6;
        this.population = 1;
        this.workforce_focus = GameConstants.foodFocus;
        this.food_Balance = 0;
        this.food_production = 1;
    }
    protected Player city_Owner;
    protected int food_production;
    protected int food_Balance;
    protected String workforce_focus;
    protected int population;
    protected int treasury;
    protected String production_Unit;
    protected int production_level;
    @Override
    public Player getOwner() {
        return this.city_Owner;
    }
    public int getSize() {
        return this.population;
    }

    @Override
    public int getTreasury() {
        return this.treasury;
    }

    @Override
    public String getProduction() {
        return this.production_Unit;
    }
    public void setCity_Owner(Player p){ this.city_Owner = p;}
    @Override
    public String getWorkforceFocus() {
        return this.workforce_focus;
    }
    public void add_production() {this.treasury += this.production_level;}
    public void take_treasury(int t){this.treasury -= t;}
    public void setProduction_Unit(String s){this.production_Unit = s;}
    public int getUnitCost(){
        if (Objects.equals(this.production_Unit, GameConstants.LEGION))
            return 15;
        else if (Objects.equals(this.production_Unit, GameConstants.ARCHER))
            return 10;
    else if (Objects.equals(this.production_Unit, GameConstants.SETTLER))
            return 30;
        else
            return 60;

    }
    public void decrementPopulation(){
        this.population = this.population - 1;
    }
    public void setWorkforceFocus(String s){
            this.workforce_focus = s;
    }
    public int getFood_Balance(){
        return this.food_Balance;
    }
    public void setProduction_level(int production_level){
        this.production_level = production_level;
    }
    public void setFood_production(int food_production) {
        this.food_production = food_production;
    }

    public void incrementFood_Balance(){
        this.food_Balance += this.food_production;
    }
    public void resetFood_Balance(){
        this.food_Balance = 0;
    }
    public int getFood_production(){return this.food_production;}
    public void incrementPopulation(){this.population = this.population + 1;}
    public int getProduction_level(){return this.production_level;}
}
