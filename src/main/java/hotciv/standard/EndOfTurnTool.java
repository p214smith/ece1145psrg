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
public class EndOfTurnTool extends NullTool{
    protected DrawingEditor editor;
    protected Game game;
    public EndOfTurnTool(DrawingEditor editor,Game game){
        this.editor = editor;
        this.game = game;

    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Drawing model = editor.drawing();
        model.lock();
        Figure figure = model.findFigure(e.getX(),e.getY());
        if (figure != null){
            Rectangle rectangle = figure.displayBox();
            if(rectangle.x == GfxConstants.TURN_SHIELD_X && rectangle.y == GfxConstants.TURN_SHIELD_Y){
                editor.showStatus("End of turn called.");
                game.endOfTurn();
                Player p = game.getWinner();
                if (p != null) {
                    if (Objects.equals(p,Player.RED))
                        editor.showStatus("Red Player Wins!!!");
                    else
                        editor.showStatus("Blue Player Wins!!!");
                }}
        }
        model.unlock();
    }
}
