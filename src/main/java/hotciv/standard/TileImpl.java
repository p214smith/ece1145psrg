package hotciv.standard;

import hotciv.framework.*;

public class TileImpl implements Tile {
    public TileImpl(String t){
        this.type = t;
    }
    protected String type;

     @Override
     public String getTypeString() {
         return this.type;
     }

}
