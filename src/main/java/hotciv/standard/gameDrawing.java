package hotciv.standard;
import hotciv.framework.*;
import hotciv.view.CityFigure;
import hotciv.view.GfxConstants;
import hotciv.view.TextFigure;
import hotciv.view.UnitFigure;
import minidraw.framework.*;
import minidraw.standard.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class gameDrawing implements Drawing,GameObserver{

    protected Drawing delegate;
    /** store all moveable figures visible in this drawing = units */
    protected Map<Unit, UnitFigure> unitFigureMap;
    protected Map<City, CityFigure> cityFigureMap;

    /** the Game instance that this CivDrawing is going to render units
     * from */
    protected Game game;
    protected TextFigure movesFigure;
    protected ImageFigure produceFigure;
    protected TextFigure ageFigure;
    protected ImageFigure workforceFigure;
    protected ImageFigure cityShieldFigure;
    protected ImageFigure unitShieldFigure;
    public gameDrawing( DrawingEditor editor, Game game ) {
        super();
        this.delegate = new StandardDrawing();
        this.game = game;
        this.unitFigureMap = new HashMap<>();
        this.cityFigureMap = new HashMap<>();



        // register this unit drawing as listener to any game state
        // changes...
        game.addObserver(this);
        // ... and build up the set of figures associated with
        // units in the game.
        defineUnitMap();
        // and the set of 'icons' in the status panel
        defineIcons();
        defineCityMap();
    }

    /** The CivDrawing should not allow client side
     * units to add and manipulate figures; only figures
     * that renders game objects are relevant, and these
     * should be handled by observer events from the game
     * instance. Thus this method is 'killed'.
     */
    public Figure add(Figure arg0) {
        throw new RuntimeException("Should not be used...");
    }


    /** erase the old list of units, and build a completely new
     * one from scratch by iterating over the game world and add
     * Figure instances for each unit in the world.
     */
    protected void defineUnitMap() {
        // ensure no units of the old list are accidental in
        // the selection!
        clearSelection();

        // remove all unit figures in this drawing
        removeAllUnitFigures();

        // iterate world, and create a unit figure for
        // each unit in the game world, as well as
        // create an association between the unit and
        // the unitFigure in 'unitFigureMap'.
        Position p;
        for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
                p = new Position(r,c);
                Unit unit = game.getUnitAt(p);
                if ( unit != null ) {
                    String type = unit.getTypeString();
                    // convert the unit's Position to (x,y) coordinates
                    Point point = new Point( GfxConstants.getXFromColumn(p.getColumn()),
                            GfxConstants.getYFromRow(p.getRow()) );
                    UnitFigure unitFigure =
                            new UnitFigure( type, point, unit );
                    unitFigure.addFigureChangeListener(this);
                    unitFigureMap.put(unit, unitFigure);

                    // also insert in delegate list as it is
                    // this list that is iterated by the
                    // graphics rendering algorithms
                    delegate.add(unitFigure);
                }
            }
        }
    }
    protected void defineCityMap(){
        // ensure no units of the old list are accidental in
        // the selection!
        clearSelection();

        // remove all unit figures in this drawing
        removeAllCityFigures();

        // iterate world, and create a unit figure for
        // each unit in the game world, as well as
        // create an association between the unit and
        // the unitFigure in 'unitFigureMap'.
        Position p;
        for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
                p = new Position(r,c);
                City unit = game.getCityAt(p);
                if ( unit != null ) {
                    // convert the unit's Position to (x,y) coordinates
                    Point point = new Point( GfxConstants.getXFromColumn(p.getColumn()),
                            GfxConstants.getYFromRow(p.getRow()) );
                    CityFigure unitFigure =
                            new CityFigure( unit,point );
                    unitFigure.addFigureChangeListener(this);
                    cityFigureMap.put(unit, unitFigure);

                    // also insert in delegate list as it is
                    // this list that is iterated by the
                    // graphics rendering algorithms
                    delegate.add(unitFigure);
                }
            }
        }
    }
    /** remove all unit figures in this
     * drawing, and reset the map (unit->unitfigure).
     * It is important to actually remove the figures
     * as it forces a graphical redraw of the screen
     * where the unit figure was.
     */
    protected void removeAllUnitFigures() {
        for (Unit u : unitFigureMap.keySet()) {
            UnitFigure uf = unitFigureMap.get(u);
            delegate.remove(uf);
        }
        unitFigureMap.clear();
    }
    protected void removeAllCityFigures(){
        for (City c : cityFigureMap.keySet()){
            CityFigure cf = cityFigureMap.get(c);
            delegate.remove(cf);
        }
        cityFigureMap.clear();
    }
    protected ImageFigure turnShieldIcon;
    protected ImageFigure refreshFigure;
    protected void defineIcons() {
        delegate.remove(this.turnShieldIcon);
        delegate.remove(this.movesFigure);
        delegate.remove(this.produceFigure);
        delegate.remove(this.workforceFigure);
        delegate.remove(this.cityShieldFigure);
        delegate.remove(this.unitShieldFigure);
        delegate.remove(this.refreshFigure);
        String playername = "red";
        if ( game.getPlayerInTurn() == Player.BLUE)
            playername = "blue";
        this.turnShieldIcon =
                new ImageFigure( playername + "shield",
                        new Point( GfxConstants.TURN_SHIELD_X,
                                GfxConstants.TURN_SHIELD_Y ) );
        delegate.remove(this.ageFigure);
        int age = game.getAge();
        String s;
        if (age < 0)
            s = -age + " BC";
        else
            s = age + " AD";
        this.ageFigure = new TextFigure(s,
                new Point(GfxConstants.AGE_TEXT_X,
                        GfxConstants.AGE_TEXT_Y) );

        this.movesFigure = new TextFigure("",
                new Point(GfxConstants.UNIT_COUNT_X,
                        GfxConstants.UNIT_COUNT_Y) );
        this.produceFigure = new ImageFigure(GfxConstants.NOTHING,
                new Point(GfxConstants.CITY_PRODUCTION_X,
                        GfxConstants.CITY_PRODUCTION_Y) );
        this.workforceFigure = new ImageFigure(GfxConstants.NOTHING,
                new Point(GfxConstants.WORKFORCEFOCUS_X,GfxConstants.WORKFORCEFOCUS_Y));
        this.cityShieldFigure = new ImageFigure(GfxConstants.NOTHING,
                new Point(GfxConstants.CITY_SHIELD_X,GfxConstants.CITY_SHIELD_Y));
        this.unitShieldFigure = new ImageFigure(GfxConstants.NOTHING,
                new Point(GfxConstants.UNIT_SHIELD_X,GfxConstants.UNIT_SHIELD_Y));
        this.refreshFigure = new ImageFigure(GfxConstants.REFRESH_BUTTON,
                new Point(GfxConstants.REFRESH_BUTTON_X,GfxConstants.REFRESH_BUTTON_Y));
        // insert in delegate figure list to ensure graphical
        // rendering.
        delegate.add(this.turnShieldIcon);
        delegate.add(this.ageFigure);
        delegate.add(this.movesFigure);
        delegate.add(this.produceFigure);
        delegate.add(this.workforceFigure);
        delegate.add(this.cityShieldFigure);
        delegate.add(this.unitShieldFigure);
        delegate.add(this.refreshFigure);
    }

    // === Observer Methods ===

    public void worldChangedAt(Position pos) {
        // TODO: Remove system.out debugging output
        System.out.println( "CivDrawing: world changes at "+pos);
        // this is a really brute-force algorithm: destroy
        // all known units and build up the entire set again

        defineCityMap();
        defineUnitMap();
        City c = game.getCityAt(pos);
        if (c != null){
            this.workforceFigure.set(c.getWorkforceFocus(),new Point(GfxConstants.WORKFORCEFOCUS_X,GfxConstants.WORKFORCEFOCUS_Y));
            this.produceFigure.set(c.getProduction(),new Point(GfxConstants.CITY_PRODUCTION_X,GfxConstants.CITY_PRODUCTION_Y));}

    }

    public void turnEnds(Player nextPlayer, int age) {

        System.out.println( "CivDrawing: turnEnds at "+age+", next is "+nextPlayer );
        String playername = "red";
        if ( nextPlayer == Player.BLUE ) { playername = "blue"; }
        turnShieldIcon.set( playername+"shield",
                new Point( GfxConstants.TURN_SHIELD_X,
                        GfxConstants.TURN_SHIELD_Y ) );
        String s;
        if (age < 0)
            s = -age + " BC";
        else
            s = age + " AD";
        this.ageFigure.setText(s);
        requestUpdate();
    }

    public void tileFocusChangedAt(Position position) {
        City c = game.getCityAt(position);
        Unit u = game.getUnitAt(position);
        if (c != null){
            this.workforceFigure.set(c.getWorkforceFocus(),new Point(GfxConstants.WORKFORCEFOCUS_X,GfxConstants.WORKFORCEFOCUS_Y));
            this.produceFigure.set(c.getProduction(),new Point(GfxConstants.CITY_PRODUCTION_X,GfxConstants.CITY_PRODUCTION_Y));
            String playername = "red";
            if ( c.getOwner() == Player.BLUE)
                playername = "blue";
            this.cityShieldFigure.set(playername + "shield",new Point(GfxConstants.CITY_SHIELD_X,GfxConstants.CITY_SHIELD_Y));
        }
        else{
            this.workforceFigure.set(GfxConstants.NOTHING,new Point(GfxConstants.WORKFORCEFOCUS_X,GfxConstants.WORKFORCEFOCUS_Y));
            this.produceFigure.set(GfxConstants.NOTHING,new Point(GfxConstants.CITY_PRODUCTION_X,GfxConstants.CITY_PRODUCTION_Y));
            this.cityShieldFigure.set(GfxConstants.NOTHING,new Point(GfxConstants.CITY_SHIELD_X,GfxConstants.CITY_SHIELD_Y));
        }
        if(u != null){
            String playername = "red";
            if ( u.getOwner() == Player.BLUE)
                playername = "blue";
            this.unitShieldFigure.set(playername + "shield",new Point(GfxConstants.UNIT_SHIELD_X,GfxConstants.UNIT_SHIELD_Y));
            this.movesFigure.setText(String.valueOf(u.getMoveCount()));
        }
        else{
            this.unitShieldFigure.set(GfxConstants.NOTHING,new Point(GfxConstants.UNIT_SHIELD_X,GfxConstants.UNIT_SHIELD_Y));
            this.movesFigure.setText("");
        }
    }

    @Override
    public void requestUpdate() {
        // A request has been issued to repaint
        // everything. We simply rebuild the
        // entire Drawing.

        defineIcons();
        defineCityMap();
        defineUnitMap();
    }

    @Override
    public void addToSelection(Figure arg0) {
        delegate.addToSelection(arg0);
    }

    @Override
    public void clearSelection() {
        delegate.clearSelection();
    }

    @Override
    public void removeFromSelection(Figure arg0) {
        delegate.removeFromSelection(arg0);
    }

    @Override
    public List<Figure> selection() {
        return delegate.selection();
    }

    @Override
    public void toggleSelection(Figure arg0) {
        delegate.toggleSelection(arg0);
    }

    @Override
    public void figureChanged(FigureChangeEvent arg0) {
        delegate.figureChanged(arg0);
    }

    @Override
    public void figureInvalidated(FigureChangeEvent arg0) {
        delegate.figureInvalidated(arg0);
    }

    @Override
    public void figureRemoved(FigureChangeEvent arg0) {
        delegate.figureRemoved(arg0);
    }

    @Override
    public void figureRequestRemove(FigureChangeEvent arg0) {
        delegate.figureRequestRemove(arg0);
    }

    @Override
    public void figureRequestUpdate(FigureChangeEvent arg0) {
        delegate.figureRequestUpdate(arg0);
    }

    @Override
    public void addDrawingChangeListener(DrawingChangeListener arg0) {
        delegate.addDrawingChangeListener(arg0);
    }

    @Override
    public void removeDrawingChangeListener(DrawingChangeListener arg0) {
        delegate.removeDrawingChangeListener(arg0);
    }

    @Override
    public Figure findFigure(int arg0, int arg1) {
        return delegate.findFigure(arg0, arg1);
    }

    @Override
    public Iterator<Figure> iterator() {
        return delegate.iterator();
    }

    @Override
    public void lock() {
        delegate.lock();
    }

    @Override
    public Figure remove(Figure arg0) {
        return delegate.remove(arg0);
    }

    @Override
    public void unlock() {
        delegate.unlock();
    }
}
