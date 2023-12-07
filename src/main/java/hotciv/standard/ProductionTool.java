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
    protected int unit_counter;
    protected int production_counter;
    protected Position pos;
    public ProductionTool(DrawingEditor editor, Game game){
        this.pos = null;
        this.game = game;
        this.editor = editor;
        this.unit_counter = 0;
        this.production_counter = 0;
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
                if (rectangle.contains(GfxConstants.REFRESH_BUTTON_X,GfxConstants.REFRESH_BUTTON_Y)){
                    editor.showStatus("Refresh called");
                    model.requestUpdate();
                }
                if(pos != null){
                    if (rectangle.contains(GfxConstants.CITY_PRODUCTION_X,GfxConstants.CITY_PRODUCTION_Y)){
                        switch (unit_counter){
                            case 0:{
                                game.changeProductionInCityAt(pos,GameConstants.LEGION);
                                unit_counter++;
                                break;
                            }
                            case 1:{
                                game.changeProductionInCityAt(pos,GameConstants.ARCHER);
                                unit_counter++;
                                break;
                            }
                            case 2:{
                                game.changeProductionInCityAt(pos,GameConstants.SETTLER);
                                unit_counter++;
                                break;
                            }
                            case 3:{
                                game.changeProductionInCityAt(pos,"ufo");
                                unit_counter = 0;
                                break;
                            }
                        }
                    }
                    if (rectangle.contains(GfxConstants.WORKFORCEFOCUS_X,GfxConstants.WORKFORCEFOCUS_Y)){
                        switch (production_counter){
                            case 0:{
                                game.changeWorkForceFocusInCityAt(pos,GameConstants.foodFocus);
                                production_counter++;
                                break;
                            }
                            case 1:{
                                game.changeWorkForceFocusInCityAt(pos,GameConstants.productionFocus);
                                production_counter = 0;
                                break;
                            }
                    }
                    }
                }
            }
        }
    }

    public void setPos(Position p){this.pos = p;}
}
