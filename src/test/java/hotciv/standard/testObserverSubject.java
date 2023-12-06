package hotciv.standard;
import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
public class testObserverSubject {
    private Game game;
    @Before
    public void setUp() {
        game = new GameImpl(new testFactory());
        game.addObserver(new stubObserver());
    }
    @Test
    public void testMoveUnit(){
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

}
