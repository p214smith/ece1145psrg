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
        game = new GameImpl();
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

    }
}
