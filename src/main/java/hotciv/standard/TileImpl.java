package hotciv.standard;

import hotciv.framework.*;

public class TileImpl implements Tile {
     @Override
     public String getTypeString(Position p) {
         String[][] terrain = new String[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];
         terrain[1][0] = GameConstants.OCEANS;
         terrain[0][1] = GameConstants.HILLS;
         terrain[2][2] = GameConstants.MOUNTAINS;

         return terrain[p.getRow()][p.getColumn()];
     }
}
