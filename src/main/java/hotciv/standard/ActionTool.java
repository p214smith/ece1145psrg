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
import java.io.File;
import java.util.Objects;
public class ActionTool extends NullTool{
    protected DrawingEditor editor;
    protected Game game;
    public ActionTool(DrawingEditor editor, Game game){
        this.editor = editor;
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Unit unit = new UnitImpl(new Position(1,1),Player.RED,"legion");
        UnitFigure figure = new UnitFigure("legion",new Point(),unit);
        Drawing model = this.editor.drawing();
        Figure unitFigure = model.findFigure(e.getX(),e.getY());
        if(e.isShiftDown() && unitFigure != null){
            if(unitFigure.getClass() == figure.getClass()) {
                Position p = GfxConstants.getPositionFromXY(e.getX(),e.getY());
                game.performUnitActionAt(p);
            }
        }
    }
}
