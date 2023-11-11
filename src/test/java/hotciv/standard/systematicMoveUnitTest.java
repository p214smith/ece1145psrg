package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class systematicMoveUnitTest {
    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new alphaFactory());
    }

    @Test
    public void a1() {
        for (int i = 0; i < 20; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
        assertFalse(game.moveUnit(new Position(2, 0), new Position(2, 1)));
        Unit unit = game.getUnitAt(new Position(2, 0));
        assertThat(unit.getMoveCount(), is(1));
    }

    @Test
    public void a2() {
        for (int i = 0; i < 20; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
        assertTrue(game.moveUnit(new Position(2, 0), new Position(3, 0)));
        Unit unit = game.getUnitAt(new Position(3, 0));
        assertThat(unit.getMoveCount(), is(0));
    }

    @Test
    public void a3() {

        game.endOfTurn();

        assertFalse(game.moveUnit(new Position(3, 2), new Position(2, 2)));
        Unit unit = game.getUnitAt(new Position(3, 2));
        assertThat(unit.getMoveCount(), is(1));
    }

    @Test
    public void a4() {

        game.endOfTurn();

        assertTrue(game.moveUnit(new Position(3, 2), new Position(3, 3)));
        Unit unit = game.getUnitAt(new Position(3, 3));
        assertThat(unit.getMoveCount(), is(0));
    }

    @Test
    public void a5() {

        assertFalse(game.moveUnit(new Position(2, 0), new Position(1, 0)));
        Unit unit = game.getUnitAt(new Position(2, 0));
        assertThat(unit.getMoveCount(), is(1));
    }

    @Test
    public void a6() {

        assertTrue(game.moveUnit(new Position(2, 0), new Position(3, 0)));
        Unit unit = game.getUnitAt(new Position(3, 0));
        assertThat(unit.getMoveCount(), is(0));
    }

    @Test
    public void a7() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertFalse(game.moveUnit(new Position(3, 2), new Position(3, 3)));
        Unit unit = game.getUnitAt(new Position(3, 2));
        assertThat(unit.getOwner(), is(Player.BLUE));
        assertThat(unit.getMoveCount(), is(1));
    }

    @Test
    public void a8() {
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
        assertTrue(game.moveUnit(new Position(3, 2), new Position(3, 3)));
        Unit unit = game.getUnitAt(new Position(3, 3));
        assertThat(unit.getOwner(), is(Player.BLUE));
        assertThat(unit.getMoveCount(), is(0));
    }

    @Test
    public void b1() {

        assertFalse(game.moveUnit(new Position(4,3), new Position(4,5)));
        Unit unit = game.getUnitAt(new Position(4,3));
        assertThat(unit.getMoveCount(), is(1));
    }

    @Test
    public void b2() {

        assertTrue(game.moveUnit(new Position(4,3), new Position(4,4)));
        Unit unit = game.getUnitAt(new Position(4,4));
        assertThat(unit.getMoveCount(), is(0));
    }
    @Test
    public void b3() {

        assertFalse(game.moveUnit(new Position(4,3), new Position(6,3)));
        Unit unit = game.getUnitAt(new Position(4,3));
        assertThat(unit.getMoveCount(), is(1));
    }

    @Test
    public void b4() {

        assertTrue(game.moveUnit(new Position(4,3), new Position(5,3)));
        Unit unit = game.getUnitAt(new Position(5,3));
        assertThat(unit.getMoveCount(), is(0));
    }
    @Test
    public void b5() {

        assertFalse(game.moveUnit(new Position(4,3), new Position(6,4)));
        Unit unit = game.getUnitAt(new Position(4,3));
        assertThat(unit.getMoveCount(), is(1));
    }
    @Test
    public void b6() {

        assertFalse(game.moveUnit(new Position(4,3), new Position(5,5)));
        Unit unit = game.getUnitAt(new Position(4,3));
        assertThat(unit.getMoveCount(), is(1));
    }
    @Test
    public void b7() {

        assertFalse(game.moveUnit(new Position(4,3), new Position(6,5)));
        Unit unit = game.getUnitAt(new Position(4,3));
        assertThat(unit.getMoveCount(), is(1));
    }
    @Test
    public void b8() {

        assertTrue(game.moveUnit(new Position(4,3), new Position(5,4)));
        Unit unit = game.getUnitAt(new Position(5,4));
        assertThat(unit.getMoveCount(), is(0));
    }
    @Test
    public void b9() {

        assertFalse(game.moveUnit(new Position(2, 0), new Position(2,-1)));
        Unit unit = game.getUnitAt(new Position(2, 0));
        assertThat(unit.getMoveCount(), is(1));
    }
    @Test
    public void b10() {
        for (int i = 0; i < 20; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
        assertFalse(game.moveUnit(new Position(0, 1), new Position(-1, 1)));
        Unit unit = game.getUnitAt(new Position(0,1));
        assertThat(unit.getMoveCount(), is(1));
    }
    @Test
    public void b11(){
        assertTrue(game.moveUnit(new Position(4,3),new Position(4,4)));
        game.endOfTurn();
        game.endOfTurn();
        for (int i = 4; i <= 14; i++){
            assertTrue(game.moveUnit(new Position(i,i),new Position(i+1,i+1)));
            game.endOfTurn();
            game.endOfTurn();
        }
        assertFalse(game.moveUnit(new Position(15,15),new Position(15,16)));
        Unit unit = game.getUnitAt(new Position(15,15));
        assertThat(unit.getMoveCount(),is(1));
    }
    @Test
    public void b12(){
        assertTrue(game.moveUnit(new Position(4,3),new Position(4,4)));
        game.endOfTurn();
        game.endOfTurn();
        for (int i = 4; i <= 14; i++){
            assertTrue(game.moveUnit(new Position(i,i),new Position(i+1,i+1)));
            game.endOfTurn();
            game.endOfTurn();
        }
        assertFalse(game.moveUnit(new Position(15,15),new Position(16,15)));
        Unit unit = game.getUnitAt(new Position(15,15));
        assertThat(unit.getMoveCount(),is(1));
    }
    @Test
    public void b13() {

        assertTrue(game.moveUnit(new Position(4,3), new Position(5,2)));
        Unit unit = game.getUnitAt(new Position(5,2));
        assertThat(unit.getMoveCount(), is(0));
    }
}
