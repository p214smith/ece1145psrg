package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

public class TestGameImpl {
    private Game game;
    @Before
    public void setUp() {
        game = new GameImpl(new testFactory());
    }

    @Test
    public void shouldBeRedAsStartingPlayer() {
        assertThat(game, is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.RED));
    }
    @Test
    public void CitiesAtRightPosition() {
        City test_City = game.getCityAt(new Position(8,12));
        assertThat(test_City.getOwner(), is(Player.RED));
        test_City = game.getCityAt(new Position(4,5));
        assertThat(test_City.getOwner(), is(Player.BLUE));
    }
    @Test
    public void checkTerrainTileLocations() {
        assertThat (game, is(notNullValue()));
        assertThat (game.getTileAt(new Position(7,10)).getTypeString(), is(GameConstants.HILLS));
        assertThat (game.getTileAt(new Position(1,11)).getTypeString(), is(GameConstants.FOREST));
        assertThat (game.getTileAt(new Position(7,13)).getTypeString(), is(GameConstants.MOUNTAINS));
        assertThat(game.getTileAt(new Position(9,8)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(13,7)).getTypeString(), is(GameConstants.PLAINS));
    }
    @Test
    public void checkUnitLocations() {
        assertThat (game, is(notNullValue()));
        Unit tester = game.getUnitAt(new Position(3,8));
        assertThat (tester.getTypeString(), is(GameConstants.ARCHER));
        tester = game.getUnitAt(new Position(4,4));
        assertThat (tester.getTypeString(), is(GameConstants.LEGION));
        tester = game.getUnitAt(new Position(5,5));
        assertThat (tester.getTypeString(), is(GameConstants.SETTLER));
    }
    @Test
    public void checkUnitOwner() {
        assertThat (game, is(notNullValue()));
        Unit tester = game.getUnitAt(new Position(3,8));
        assertThat (tester.getOwner(), is(Player.RED));
        tester = game.getUnitAt(new Position(4,4));
        assertThat (tester.getOwner(), is(Player.BLUE));
        tester = game.getUnitAt(new Position(5,5));
        assertThat (tester.getOwner(), is(Player.RED));
    }
    @Test
    public void gameAgesProperly(){
        assertThat (game, is(notNullValue()));
        int age = -4000;
        for(int i = 0; i < (4000-100)/100;i++){
            assertThat(game.getAge(),is(age));
            game.endOfTurn();
            game.endOfTurn();
            age +=100;
        }
        assertThat(game.getAge(),is(age));
        game.endOfTurn();
        game.endOfTurn();
        age +=99;
        assertThat(game.getAge(),is(age));
        game.endOfTurn();
        game.endOfTurn();
        age +=1;
        assertThat(game.getAge(),is(age));
        game.endOfTurn();
        game.endOfTurn();
        age +=1;
        assertThat(game.getAge(),is(age));
        game.endOfTurn();
        game.endOfTurn();
        age +=49;
        for(int i = 0; i < (1750 - 50)/50;i++){
            assertThat(game.getAge(),is(age));
            game.endOfTurn();
            game.endOfTurn();
            age +=50;
        }
        for(int i = 0; i < (1900 - 1750)/25;i++){
            assertThat(game.getAge(),is(age));
            game.endOfTurn();
            game.endOfTurn();
            age +=25;
        }
        for(int i = 0; i < (1970 - 1900)/5;i++){
            assertThat(game.getAge(),is(age));
            game.endOfTurn();
            game.endOfTurn();
            age +=5;
        }
        for(int i = 0; i < 30;i++){
            assertThat(game.getAge(),is(age));
            game.endOfTurn();
            game.endOfTurn();
            age +=1;
        }
    }
    @Test
    public void test_unit_movement(){
        assertThat (game, is(notNullValue()));
        assertTrue(game.moveUnit(new Position(3,8),new Position(3,9)));
        assertThat(game.getUnitAt(new Position(3,9)).getMoveCount(),is(0));
        assertFalse(game.moveUnit(new Position(3,9),new Position(3,8)));
        assertTrue(game.moveUnit(new Position(5,5),new Position(5,4)));
        assertThat(game.getUnitAt(new Position(5,4)).getMoveCount(),is(0));
        game.endOfTurn();
        assertFalse(game.moveUnit(new Position(4,4),new Position(3,4)));
        assertTrue(game.moveUnit(new Position(4,4),new Position(4,5)));

        assertThat(game.getUnitAt(new Position(4,5)).getMoveCount(),is(0));
        game.endOfTurn();
        assertFalse(game.moveUnit(new Position(4,5),new Position(4,4)));
        assertFalse(game.moveUnit(new Position(3,9),new Position(3,10)));
        assertFalse(game.moveUnit(new Position(3,9),new Position(1,9)));
        assertTrue(game.moveUnit(new Position(3,9),new Position(3,8)));
        assertThat(game.getUnitAt(new Position(3,8)).getMoveCount(),is(0));
        assertTrue(game.moveUnit(new Position(5,4),new Position(4,3)));
        assertThat(game.getUnitAt(new Position(4,3)).getMoveCount(),is(0));
        assertThat(game.getUnitAt(new Position(4,3)).getTypeString(),is(GameConstants.SETTLER));
        game.endOfTurn();
        assertTrue(game.moveUnit(new Position(4,5),new Position(4,4)));
    }
    @Test
    public void test_winning_condition(){
        assertThat (game, is(notNullValue()));
        assertThat(game.getWinner(),is(nullValue()));
        assertTrue(game.moveUnit(new Position(5,5),new Position(4,5)));
        assertThat(game.getUnitAt(new Position(4,5)).getMoveCount(),is(0));
        assertThat(game.getCityAt(new Position(4,5)).getOwner(),is(Player.RED));
        game.endOfTurn();
        assertThat(game.getWinner(),is(Player.RED));

    }
    @Test
    public void test_unit_actions(){
        assertThat (game, is(notNullValue()));
        game.performUnitActionAt(new Position(3,8));
        assertThat(game.getUnitAt(new Position(3,8)).getDefensiveStrength(),is(6));
        assertThat(game.getUnitAt(new Position(3,8)).getMoveCount(), is(0));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(3,8)).getDefensiveStrength(),is(6));
        assertThat(game.getUnitAt(new Position(3,8)).getMoveCount(), is(0));
        game.performUnitActionAt(new Position(3,8));
        assertThat(game.getUnitAt(new Position(3,8)).getDefensiveStrength(),is(3));
        game.performUnitActionAt(new Position(5,5));
        assertThat(game.getCityAt(new Position(5,5)).getOwner(),is(Player.RED));
    }
    @Test
    public void test_unit_creation(){
        assertThat (game, is(notNullValue()));
        assertThat(((GameImpl)game).FindPositionForNewUnit(new Position(4,5)),is(new Position(3,6)));
        for(int i = 0;i < 40;i++)
            game.endOfTurn();
        assertThat(game.getUnitAt(new Position(3,6)).getOwner(),is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(4,6)).getOwner(),is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(5,6)).getOwner(),is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(5,5)).getOwner(),is(Player.RED));
        assertThat(game.getUnitAt(new Position(5,4)).getOwner(),is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(4,4)).getOwner(),is(Player.BLUE));
        assertThat(game.getCityAt(new Position(4,5)).getTreasury(),is((20*6)-(4*15)));
        assertThat(game.getUnitAt(new Position(7,12)).getOwner(),is(Player.RED));
        assertThat(game.getUnitAt(new Position(8,13)).getOwner(),is(Player.RED));
        assertThat(game.getUnitAt(new Position(9,13)).getOwner(),is(Player.RED));
        assertThat(game.getUnitAt(new Position(9,12)).getOwner(),is(Player.RED));
        assertThat(game.getUnitAt(new Position(9,11)).getOwner(),is(Player.RED));
        assertThat(game.getUnitAt(new Position(8,11)).getOwner(),is(Player.RED));
        assertThat(game.getUnitAt(new Position(7,11)).getOwner(),is(Player.RED));
        assertThat(game.getCityAt(new Position(8,12)).getTreasury(),is((20*6)-(7*15)));
        game.endOfTurn();
        game.changeProductionInCityAt(new Position(4,5),GameConstants.SETTLER);
        game.moveUnit(new Position(5,6),new Position(5,5));
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(5,6)).getTypeString(),is(GameConstants.SETTLER));

    }
}
