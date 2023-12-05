package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
public class testEtaCiv {
    private Game game;
    @Before
    public void setUp() {
        game = new GameImpl(new etaFactory());
    }
    @Test
    public void testFoodBalanceUpdate(){
        game.endOfTurn();
        game.endOfTurn();
        City c1 = game.getCityAt(new Position(1,1));
        City c2 = game.getCityAt(new Position(4,1));
        assertEquals(((CityImpl)c1).getFood_Balance(),1);
        assertEquals(((CityImpl)c2).getFood_Balance(),1);
        game.endOfTurn();
        game.endOfTurn();
        assertEquals(((CityImpl)c1).getFood_Balance(),2);
        assertEquals(((CityImpl)c2).getFood_Balance(),2);
        game.endOfTurn();
        game.endOfTurn();
        assertEquals(((CityImpl)c1).getFood_Balance(),3);
        assertEquals(((CityImpl)c2).getFood_Balance(),3);
        game.endOfTurn();
        game.endOfTurn();
        assertEquals(((CityImpl)c1).getFood_Balance(),4);
        assertEquals(((CityImpl)c2).getFood_Balance(),4);
    }
    @Test
    public void testPopulationIncrease(){
        City c1 = game.getCityAt(new Position(1,1));
        City c2 = game.getCityAt(new Position(4,1));
        for (int i = 0; i < 8; i++){
            game.endOfTurn();
            game.endOfTurn();
        }
        assertEquals(c1.getSize(),2);
        assertEquals(c2.getSize(),2);
        assertEquals(c1.getWorkforceFocus(),GameConstants.foodFocus);
        assertEquals(c2.getWorkforceFocus(),GameConstants.foodFocus);
        assertEquals(((CityImpl)c1).getFood_production(),4);
        assertEquals(((CityImpl)c2).getFood_production(),4);

    }
    @Test
    public void testProductionChanges(){
        City c1 = game.getCityAt(new Position(1,1));
        City c2 = game.getCityAt(new Position(4,1));
        for (int i = 0; i < 8; i++){
            game.endOfTurn();
            game.endOfTurn();
        }
        assertEquals(c1.getSize(),2);
        assertEquals(c2.getSize(),2);
        assertEquals(c1.getWorkforceFocus(),GameConstants.foodFocus);
        assertEquals(c2.getWorkforceFocus(),GameConstants.foodFocus);
        assertEquals(((CityImpl)c1).getFood_production(),4);
        assertEquals(((CityImpl)c2).getFood_production(),4);
        assertEquals(((CityImpl)c1).getFood_Balance(),0);
        assertEquals(((CityImpl)c2).getFood_Balance(),0);
        assertEquals(((CityImpl)c1).getProduction_level(),6);
        assertEquals(((CityImpl)c2).getProduction_level(),6);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.productionFocus);
        assertEquals(((CityImpl)c1).getProduction_level(),8);
        assertEquals(((CityImpl)c1).getFood_production(),1);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.foodFocus);
        assertEquals(((CityImpl)c1).getFood_production(),4);
        assertEquals(((CityImpl)c1).getProduction_level(),6);
        for (int i = 0; i < 3; i++){
            game.endOfTurn();
            game.endOfTurn();
        }
        assertEquals(c1.getSize(),3);
        assertEquals(c2.getSize(),3);
        assertEquals(c1.getWorkforceFocus(),GameConstants.foodFocus);
        assertEquals(c2.getWorkforceFocus(),GameConstants.foodFocus);
        assertEquals(((CityImpl)c1).getFood_production(),7);
        assertEquals(((CityImpl)c2).getFood_production(),7);
        assertEquals(((CityImpl)c1).getFood_Balance(),0);
        assertEquals(((CityImpl)c2).getFood_Balance(),0);
        assertEquals(((CityImpl)c1).getProduction_level(),6);
        assertEquals(((CityImpl)c2).getProduction_level(),6);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.productionFocus);
        assertEquals(((CityImpl)c1).getProduction_level(),9);
        assertEquals(((CityImpl)c1).getFood_production(),1);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.foodFocus);
        assertEquals(((CityImpl)c1).getFood_production(),7);
        assertEquals(((CityImpl)c1).getProduction_level(),6);
        for (int i = 0; i < 2; i++){
            game.endOfTurn();
            game.endOfTurn();
        }
        assertEquals(c1.getSize(),4);
        assertEquals(c2.getSize(),4);
        assertEquals(c1.getWorkforceFocus(),GameConstants.foodFocus);
        assertEquals(c2.getWorkforceFocus(),GameConstants.foodFocus);
        assertEquals(((CityImpl)c1).getFood_production(),10);
        assertEquals(((CityImpl)c2).getFood_production(),10);
        assertEquals(((CityImpl)c1).getFood_Balance(),0);
        assertEquals(((CityImpl)c2).getFood_Balance(),0);
        assertEquals(((CityImpl)c1).getProduction_level(),6);
        assertEquals(((CityImpl)c2).getProduction_level(),6);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.productionFocus);
        assertEquals(((CityImpl)c1).getProduction_level(),9);
        assertEquals(((CityImpl)c1).getFood_production(),4);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.foodFocus);
        assertEquals(((CityImpl)c1).getFood_production(),10);
        assertEquals(((CityImpl)c1).getProduction_level(),6);

    }
    @Test
    public void testMaxPopulationIs9(){
        for( int i = 0; i < 40; i++){
            game.endOfTurn();
            game.endOfTurn();
        }
        City c1 = game.getCityAt(new Position(1,1));
        City c2 = game.getCityAt(new Position(4,1));
        assertEquals(c1.getSize(),9);
        assertEquals(c2.getSize(),9);
        for( int i = 0; i < 10; i++){
            game.endOfTurn();
            game.endOfTurn();
        }
        assertEquals(c1.getSize(),9);
        assertEquals(c2.getSize(),9);
    }
}
