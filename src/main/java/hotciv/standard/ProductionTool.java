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
public class ProductionTool extends NullTool{
    protected DrawingEditor editor;
    protected Game game;
    protected Position pos;
    public ProductionTool(DrawingEditor editor, Game game){
        this.pos = null;
        this.game = game;
        this.editor = editor;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        int x_min = GfxConstants.MAP_OFFSET_X;
        int x_max = x_min + GameConstants.WORLDSIZE * GfxConstants.TILESIZE;
        if (e.getX() > x_max){
            Drawing model = editor.drawing();
            model.lock();
            Figure figure = model.findFigure(e.getX(),e.getY());
            if (figure != null) {
                Rectangle rectangle = figure.displayBox();
            }
        }
    }

    public void setPos(Position p){this.pos = p;}
}
