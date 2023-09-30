package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;


public class TestUnitImpl {
    private Unit archer;
    private Unit legion;
    private Unit settler;
    @Before
    public void setUp(){
        archer = new UnitImpl(new Position(1,1),Player.RED,GameConstants.ARCHER);
        legion = new UnitImpl(new Position(2,2),Player.BLUE,GameConstants.LEGION);
        settler = new UnitImpl(new Position(3,3),Player.RED,GameConstants.SETTLER);
    }

    @Test
    public void test_proper_stats(){
        assertThat(archer.getAttackingStrength(),is(2));
        assertThat(archer.getDefensiveStrength(),is(3));
        assertThat(settler.getAttackingStrength(),is(0));
        assertThat(settler.getDefensiveStrength(),is(3));
        assertThat(legion.getAttackingStrength(),is(4));
        assertThat(legion.getDefensiveStrength(),is(2));
    }
    @Test
    public void check_location_changes(){
        assertThat(((UnitImpl)archer).getUnitPosition(),is(new Position(1,1)));
        ((UnitImpl)archer).setLocation(new Position(1,2));
        assertThat(((UnitImpl)archer).getUnitPosition(),is(new Position(1,2)));
        assertThat(((UnitImpl)legion).getUnitPosition(),is(new Position(2,2)));
        ((UnitImpl)legion).setLocation(new Position(2,3));
        assertThat(((UnitImpl)legion).getUnitPosition(),is(new Position(2,3)));
        assertThat(((UnitImpl)settler).getUnitPosition(),is(new Position(3,3)));
        ((UnitImpl)settler).setLocation(new Position(3,4));
        assertThat(((UnitImpl)settler).getUnitPosition(),is(new Position(3,4)));
    }
    @Test
    public void check_unit_type(){
        assertThat(archer.getTypeString(),is(GameConstants.ARCHER));
        assertThat(legion.getTypeString(),is(GameConstants.LEGION));
        assertThat(settler.getTypeString(),is(GameConstants.SETTLER));
    }
    @Test
    public void check_unit_owner(){
        assertThat(archer.getOwner(),is(Player.RED));
        assertThat(legion.getOwner(),is(Player.BLUE));
        assertThat(settler.getOwner(),is(Player.RED));
    }
    @Test
    public void check_unit_moves(){
        assertThat(archer.getMoveCount(),is(1));
        ((UnitImpl)archer).decrementMove();
        assertThat(archer.getMoveCount(),is(0));
        ((UnitImpl)archer).resetMove();
        assertThat(archer.getMoveCount(),is(1));
        ((UnitImpl)archer).setDefense(6);
        assertThat(archer.getDefensiveStrength(),is(6));
        ((UnitImpl)archer).decrementMove();
        ((UnitImpl)archer).resetMove();
        assertThat(archer.getMoveCount(),is(0));
        assertThat(legion.getMoveCount(),is(1));
        ((UnitImpl)legion).decrementMove();
        assertThat(legion.getMoveCount(),is(0));
        ((UnitImpl)legion).resetMove();
        assertThat(legion.getMoveCount(),is(1));
        assertThat(settler.getMoveCount(),is(1));
        ((UnitImpl)settler).decrementMove();
        assertThat(settler.getMoveCount(),is(0));
        ((UnitImpl)settler).resetMove();
        assertThat(settler.getMoveCount(),is(1));


    }
}
