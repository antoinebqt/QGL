package fr.unice.polytech.si3.qgl.les_genies.game.map;

import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.Checkpoint;
import fr.unice.polytech.si3.qgl.les_genies.game.pathfinding.Size;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;

public class InitMap {
    BoxMap[][] map;
    int nbBox;
    static int boxSize;

    public InitMap(int boxSize, Checkpoint[] checkpoints) {
        Size s = getSizeMap(checkpoints);
        nbBox = Math.max(s.getX()/boxSize, s.getY()/boxSize) + 3;
        InitMap.boxSize = boxSize;
        initMap();
    }

    /**
     * initialise the map with default BoxMap on each box
     */
    private void initMap() {
        map = new BoxMap[nbBox][nbBox];
        for (int i = 0; i < map.length ; i++){
            for(int j = 0; j < map[i].length ; j++){
                map[i][j] = new BoxMap(boxSize,i,j);
            }
        }
    }

    /**
     * Calcul the size off the map using positions of checkpoints.
     * @param checkpoints list of checkpoints (WARNING their positions needs to be in positive)
     * @return an object size
     */
    public Size getSizeMap(Checkpoint[] checkpoints) {
        double x = 0;
        double y = 0;

        for(Checkpoint c:checkpoints){
            x = Math.max(Math.abs(c.getPosition().getX()),x);
            y = Math.max(Math.abs(c.getPosition().getY()),y);
        }

        int sizeBorder = 10000;
        return new Size((int)x+ sizeBorder,(int)y+ sizeBorder);
    }



    /**
     * get the box place at a given position
     * @param position Position object(WARNING need to be in the map)
     * @return boxMap
     */
    public BoxMap getBox(Position position) {
        int i = (int)Math.round(position.getX()/boxSize);
        int j = (int)Math.round(position.getY()/boxSize);
        if(i > map.length-1){
            i = map.length-1;
        }
        if(j > map[i].length-1){
            j = map[i].length-1;
        }
        return map[i][j];
    }

    public BoxMap[][] getMap() {
        return map;
    }

    public int getNbBox() {
        return nbBox;
    }

    public static int getBoxSize() {
        return boxSize;
    }
}
