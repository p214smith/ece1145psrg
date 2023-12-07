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
public class setFocusTool extends NullTool{
    protected Game game;
    private DrawingEditor editor;
    public setFocusTool(DrawingEditor editor,Game game){
        this.game = game;
        this.editor = editor;
    }
    public void mouseDown(MouseEvent e, int x, int y) {
        int e_x = e.getX();
        int e_y = e.getY();
        int x_min = GfxConstants.MAP_OFFSET_X;
        int x_max = x_min + GameConstants.WORLDSIZE * GfxConstants.TILESIZE;
        int y_min = GfxConstants.MAP_OFFSET_Y;
        int y_max = y_min + GameConstants.WORLDSIZE * GfxConstants.TILESIZE;
        if(e_x > x_min && e_x < x_max && e_y > y_min && e_y < y_max ){
            Position p = GfxConstants.getPositionFromXY(e_x,e_y);
            game.setTileFocus(p);
        }
    }
}
