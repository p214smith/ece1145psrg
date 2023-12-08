package hotciv.standard;
import hotciv.standard.*;
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

import java.awt.*;
import java.awt.event.MouseEvent;

public class CompTool extends SelectionTool{
    protected Unit unit;
    protected Tool fChild;
    protected Tool cachedNullTool;
    protected Position from;
    protected Figure draggedFigure;
    protected RubberBandSelectionStrategy selectionStrategy;
    protected Game game;
    protected ActionTool action;
    protected EndOfTurnTool end;
    protected ProductionTool prod;
    protected unitMoveTool move;
    protected setFocusTool focus;
    public CompTool(DrawingEditor editor,Game game) {
        super(editor);
        fChild = cachedNullTool = new NullTool();
        draggedFigure = null;
        this.selectionStrategy = new StandardRubberBandSelectionStrategy();
        this.game = game;
        this.action = new ActionTool(editor,game);
        this.end = new EndOfTurnTool(editor,game);
        this.move = new unitMoveTool(editor,game);
        this.focus = new setFocusTool(editor,game);
        this.prod = new ProductionTool(editor,game);
        this.from = null;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Drawing model = editor().drawing();
        Position p = GfxConstants.getPositionFromXY(e.getX(),e.getY());
        model.lock();
        draggedFigure = model.findFigure(e.getX(), e.getY());
        Unit unit = new UnitImpl(new Position(1,1),Player.RED,"legion");
        UnitFigure f = new UnitFigure("legion",new Point(),unit);

        int e_x = e.getX();
        int e_y = e.getY();
        int x_min = GfxConstants.MAP_OFFSET_X;
        int x_max = x_min + GameConstants.WORLDSIZE * GfxConstants.TILESIZE;
        int y_min = GfxConstants.MAP_OFFSET_Y;
        int y_max = y_min + GameConstants.WORLDSIZE * GfxConstants.TILESIZE;
        if(e_x > x_min && e_x < x_max && e_y > y_min && e_y < y_max ){
            this.from = p;
            if(e.isShiftDown())
                this.action.mouseDown(e,x,y);

            else if (draggedFigure != null ) {
                if(draggedFigure.getClass() == f.getClass()){
                this.move.mouseDown(e, x, y);
                this.fChild = this.move;}
            }
            if (e.isAltDown())
                this.focus.mouseDown(e,x,y);

        }
        else if (e_y < 200)
            this.end.mouseDown(e,x,y);
        else{
            this.prod.setPos(from);
            this.prod.mouseDown(e,x,y);
        }
    }
    public void mouseDrag(MouseEvent e, int x, int y) {
        fChild.mouseDrag(e, x, y);
    }
    public void mouseMove(MouseEvent e, int x, int y) {
        fChild.mouseMove(e, x, y);
    }
    public void mouseUp(MouseEvent e, int x, int y) {
        fChild.mouseUp(e,x,y);
        fChild = cachedNullTool;
        editor().drawing().unlock();
        draggedFigure = null;
    }
    protected Tool createDragTracker(Figure f) {
        return new DragTracker(editor(), f);
    }
    protected Tool createAreaTracker() {
        return new SelectAreaTracker(editor(), selectionStrategy);}
}
