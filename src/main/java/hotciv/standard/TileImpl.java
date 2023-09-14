package hotciv.standard;

import hotciv.framework.*;

public class TileImpl implements Tile {
     @Override
     public String getTypeString() {
         return null;
     }
     public String getTileTerrain(Position p){
         //Need to create constructor or set method for world creation (goes for all arrays)
         String[][] terrain = new String[GameConstants.WORLDSIZE][GameConstants.WORLDSIZE];

         for(int i = 0; i < GameConstants.WORLDSIZE; i++) {
             for(int j = 0; j < GameConstants.WORLDSIZE; j++){
                 if(i == 0 && j == 1)
                     terrain[i][j] = GameConstants.HILLS;
                 else if(i == 1 && j == 0)
                     terrain[i][j] = GameConstants.OCEANS;
                 else if(i == 2 && j == 2)
                     terrain[i][j] = GameConstants.MOUNTAINS;
                 else
                     terrain[i][j] = GameConstants.PLAINS;
             }
         }
         return terrain[p.getRow()][p.getColumn()];
     }
}
