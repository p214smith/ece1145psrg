package hotciv.standard;
import hotciv.framework.*;
import hotciv.stub.StubGame1;
import hotciv.view.CityFigure;
import hotciv.view.GfxConstants;
import hotciv.view.MapView;
import hotciv.view.UnitFigure;
import minidraw.framework.*;
import minidraw.standard.*;
import minidraw.standard.handlers.DragTracker;
import minidraw.standard.handlers.SelectAreaTracker;
import minidraw.standard.handlers.StandardRubberBandSelectionStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class unitMoveTool extends AbstractTool implements Tool{
    protected Unit unit;
    protected Tool fChild;
    protected Tool cachedNullTool;
    protected Position from;
    protected Figure draggedFigure;
    RubberBandSelectionStrategy selectionStrategy;
    Game game;
    protected UnitFigure figure;
    public unitMoveTool(DrawingEditor editor,Game game){
        super(editor);
        fChild = cachedNullTool = new NullTool();
        draggedFigure = null;
        this.selectionStrategy = new StandardRubberBandSelectionStrategy();
        this.game = game;
    }
    public void mouseDown(MouseEvent e, int x, int y){
        Drawing model = editor().drawing();

        model.lock();

        draggedFigure = model.findFigure(e.getX(), e.getY());
        Unit unit = new UnitImpl(new Position(1,1),Player.RED,"legion");
        UnitFigure f = new UnitFigure("legion",new Point(),unit);
        if (draggedFigure.getClass() == f.getClass()){
            this.from = GfxConstants.getPositionFromXY(e.getX(),e.getY());
            fChild = createDragTracker(draggedFigure);
        }
         else {
        if (!e.isShiftDown()) {
            model.clearSelection();
        }
        fChild = createAreaTracker();
    }
    }
    public void mouseDrag(MouseEvent e, int x, int y) {
        fChild.mouseDrag(e, x, y);
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y) {
        fChild.mouseMove(e, x, y);
    }
    public void mouseUp(MouseEvent e, int x, int y) {
        editor().drawing().unlock();
        Position to = GfxConstants.getPositionFromXY(e.getX(),e.getY());
        game.moveUnit(this.from,to);

        fChild = cachedNullTool;
        draggedFigure = null;
    }
    protected Tool createDragTracker(Figure f) {
        return new DragTracker(editor(), f);
    }
    protected Tool createAreaTracker() {
        return new SelectAreaTracker(editor(), selectionStrategy);
    }
}
