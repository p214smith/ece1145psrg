package hotciv.standard;
import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
public class testZetaCivWinningStrategy {
    private Game game;
    @Before
    public void setUp() {
        game = new GameImpl(new testFactoryZeta());

    }
    @Test
    public void playerWinsWithin20rounds(){
        assertThat (game, is(notNullValue()));
        assertThat(game.getWinner(),is(nullValue()));
        game.endOfTurn();
        game.endOfTurn();
        assertTrue(game.moveUnit(new Position(5,5),new Position(4,5)));
        assertThat(game.getUnitAt(new Position(4,5)).getMoveCount(),is(0));
        assertThat(game.getCityAt(new Position(4,5)).getOwner(),is(Player.RED));
        game.endOfTurn();
        assertThat(game.getWinner(),is(Player.RED));
    }
    @Test
    public void testRedTeamWinsWith3attacks(){
        for (int i = 0; i < 40; i++)
            game.endOfTurn();
        assertTrue(game.moveUnit(new Position(5,5),new Position(5,6)));
        assertNull(game.getWinner());
        game.endOfTurn();
        game.endOfTurn();
        assertTrue(game.moveUnit(new Position(5,6),new Position(4,6)));
        assertNull(game.getWinner());
        game.endOfTurn();
        game.endOfTurn();
        assertTrue(game.moveUnit(new Position(4,6),new Position(3,6)));
        assertThat(game.getWinner(),is(Player.RED));
    }
    @Test
    public void testBlueTeamWinsWith3attacks(){
        assertTrue(game.moveUnit(new Position(5,5),new Position(6,5)));
        game.endOfTurn();
        game.endOfTurn();
        assertTrue(game.moveUnit(new Position(6,5),new Position(7,5)));
        game.performUnitActionAt(new Position(7,5));
        for (int i = 0; i < 40; i++)
            game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(5,4)).getOwner(),is(Player.BLUE));
        assertTrue(game.moveUnit(new Position(5,4),new Position(6,4)));
        assertNull(game.getWinner());
        game.endOfTurn();
        game.endOfTurn();
        assertTrue(game.moveUnit(new Position(6,4),new Position(7,4)));
        assertNull(game.getWinner());
        game.endOfTurn();
        game.endOfTurn();
        assertTrue(game.moveUnit(new Position(7,4),new Position(8,4)));
        assertThat(game.getWinner(),is(Player.BLUE));
    }
}
