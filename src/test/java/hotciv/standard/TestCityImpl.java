package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

public class TestCityImpl {
    private City city;
    @Before
    public void setUp(){
        city = new CityImpl();
    }
    @Test
    public void test_set_city_owner(){
        assertThat(city, is(notNullValue()));
        ((CityImpl)city).setCity_Owner(Player.RED);
        assertThat(city.getOwner(), is(Player.RED));
        ((CityImpl)city).setCity_Owner(Player.BLUE);
        assertThat(city.getOwner(), is(Player.BLUE));
    }
    @Test
    public void get_city_population_size(){
        assertThat(city, is(notNullValue()));
        assertThat(city.getSize(),is(1));
    }
    @Test
    public void test_add_to_treasury(){
        assertThat(city, is(notNullValue()));
        assertThat(city.getTreasury(), is(0));
        ((CityImpl)city).add_production();
        assertThat(city.getTreasury(), is(6));
        ((CityImpl)city).add_production();
        assertThat(city.getTreasury(), is(12));
    }
    @Test
    public void test_remove_from_treasury(){
        assertThat(city, is(notNullValue()));
        assertThat(city.getTreasury(), is(0));
        ((CityImpl)city).add_production();
        assertThat(city.getTreasury(), is(6));
        ((CityImpl)city).add_production();
        assertThat(city.getTreasury(), is(12));
        ((CityImpl)city).take_treasury(10);
        assertThat(city.getTreasury(), is(2));
        ((CityImpl)city).take_treasury(2);
        assertThat(city.getTreasury(), is(0));
    }
    @Test
    public void change_workforce_production_unit(){
        assertThat(city, is(notNullValue()));
        assertThat(city.getProduction(), is(GameConstants.LEGION));
        ((CityImpl)city).setProduction_Unit(GameConstants.ARCHER);
        assertThat(city.getProduction(), is(GameConstants.ARCHER));
        ((CityImpl)city).setProduction_Unit(GameConstants.SETTLER);
        assertThat(city.getProduction(), is(GameConstants.SETTLER));
        ((CityImpl)city).setProduction_Unit(GameConstants.LEGION);
        assertThat(city.getProduction(), is(GameConstants.LEGION));
    }
}
