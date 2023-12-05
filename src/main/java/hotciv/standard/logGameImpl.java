package hotciv.standard;
import hotciv.framework.*;


import java.util.*;
public class logGameImpl implements Game {
    public logGameImpl(FactoryImpl factoryImpl){
        this.game = new GameImpl(factoryImpl);
        this.logging = true;
    }
    protected boolean logging;
    protected Game game;

    public Tile getTileAt(Position p) {
        return this.game.getTileAt(p);
    }
    public Unit getUnitAt(Position p) {
        return this.game.getUnitAt(p);
    }
    public City getCityAt(Position p) {
        return this.game.getCityAt(p);
    }
    public boolean moveUnit(Position from, Position to) {
        if (game.moveUnit(from,to)){
            if(this.logging){
                Player play = game.getPlayerInTurn();
                Unit unit = game.getUnitAt(to);
                String s = play.toString() + " " + unit.getTypeString() + " from " + from.toString() + " to " + to.toString() + ".";
                System.out.println(s);}
            return true;}
        else
            return false;
    }
    public Player getPlayerInTurn() {
        return game.getPlayerInTurn();
    }
    public Player getWinner() {
        return game.getWinner();
    }
    public void changeProductionInCityAt(Position p, String unitType) {
        City unit = game.getCityAt(p);
        if(Objects.nonNull(unit)) {
            if (this.logging) {
                Player play = this.game.getPlayerInTurn();
                String s = play.toString() + " changes production in city at " + p.toString() + " to " + unitType + ".";
                System.out.println(s);
            }
        }
        game.changeProductionInCityAt(p,unitType);
    }
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        City unit = game.getCityAt(p);
        if(Objects.nonNull(unit)) {
            if (this.logging) {
                Player play = this.game.getPlayerInTurn();
                String s = play.toString() + " changes workforce focus in city at " + p.toString() + " to " + balance + ".";
                System.out.println(s);
            }
        }
        game.changeWorkForceFocusInCityAt(p,balance);
    }
    public int getAge() {
        return game.getAge();
    }
    public void performUnitActionAt(Position p) {
        Unit unit = game.getUnitAt(p);
        if(Objects.nonNull(unit)){
            if (this.logging){
                Player play = this.game.getPlayerInTurn();
                String s = play.toString() + " performed unit action on " + unit.getTypeString() + " at position " + p.toString() + ".";
                System.out.println(s);
            }
        }
        game.performUnitActionAt(p);
    }
    public void endOfTurn() {
        Player play =game.getPlayerInTurn();
        if (this.logging){
            String s = play.toString() + " ends turn.";
            System.out.println(s);}
        game.endOfTurn();
    }
    public void toggleLogging(){
        this.logging = !this.logging;
    }
    public void addObserver(GameObserver observer) {}
    public void setTileFocus(Position position) {}
}
