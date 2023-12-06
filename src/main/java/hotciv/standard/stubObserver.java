package hotciv.standard;
import hotciv.framework.*;
public class stubObserver implements GameObserver{
    @Override
    public void turnEnds(Player nextPlayer, int age) {
        System.out.println("Turn has ended.\n");
    }

    @Override
    public void tileFocusChangedAt(Position position) {
        System.out.println("Tile Focus Changed at " + position.toString());
    }

    @Override
    public void worldChangedAt(Position pos) {
        System.out.println("World changed at " + pos.toString());
    }
}
