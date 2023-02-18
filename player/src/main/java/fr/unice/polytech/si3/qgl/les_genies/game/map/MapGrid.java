package fr.unice.polytech.si3.qgl.les_genies.game.map;

import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.Checkpoint;
import fr.unice.polytech.si3.qgl.les_genies.game.pathfinding.NodeA;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.VisibleEntities;

import java.util.ArrayList;
import java.util.List;


public class MapGrid extends InitMap {
    private final List<VisibleEntities> allEntities = new ArrayList<>();
    public MapGrid(int boxSize, Checkpoint[] checkpoints){
        super(boxSize, checkpoints);
    }

    /**
     * Get every reef and call recursive method that change all status
     * @param visibleEntities list of entities
     */
    public void setSpotsOccupied(List<VisibleEntities> visibleEntities) {
        for(VisibleEntities v: visibleEntities){
            int i = (int)Math.round(v.getPosition().getX()/boxSize);
            int j = (int)Math.round(v.getPosition().getY()/boxSize);
            boolean isPlace = recursiveEntityPlaceOnMap(i,j,v);
            if(!isPlace) CheckPlaceOn3by3Grid(i,j,v);
        }
        placeBorder();
    }

    /**
     * Check on a 3*3 grid if he can place the visible entity
     * @param i Coordinate x
     * @param j Coordinate Y
     * @param v Visible entity
     */
    private void CheckPlaceOn3by3Grid(int i, int j, VisibleEntities v){
        boolean isPlace = false;
        for (int k = -1; k<=1;k++){
            for (int l = -1; l<=1;l++){
                isPlace = recursiveEntityPlaceOnMap(i+k,j+l,v);
                if(isPlace) break;
            }if(isPlace) break;
        }
    }

    /**
     * Place a border to avoid being to close of an entity
     */
    private void placeBorder() {
        int id = 100;
        for(BoxMap[] arrayBox : map){
            for(BoxMap boxMap : arrayBox){
                if(boxMap.getStatus() && hasCloseReef(boxMap, id)){
                    boxMap.setOccupied();
                    boxMap.setIndex(id);
                }
            }
        }
    }

    private boolean hasCloseReef(BoxMap boxMap, int id) {
        int length1 = map.length;
        int length2 = map[1].length;

        for(int i = -1; i<2 ; i++){
            for(int j = -1; j<2;  j++){
                int a = boxMap.getI()+i;
                int b = boxMap.getJ()+j;

                if(a>=0 && b>=0 && a<length1 && b<length2){
                    BoxMap box = map[a][b];
                    if(box.getId()!=id && !box.getStatus() && !box.isStream()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Change the status of a box if she as a reef at her position
     * @param i first index of the box
     * @param j second index of the box
     * @param v Visible entity
     */
    boolean recursiveEntityPlaceOnMap(int i, int j, VisibleEntities v){
        int id = getId(v);
        boolean isPlace = false;

        try {
            BoxMap box = map[i][j];
            if(isCornerOrMiddleOnAShape(v,box) && (id != box.getId())){
                if(v.getType().equals(Constants.REEF)) box.setOccupied();
                else if(v.getType().equals(Constants.STREAM)) box.setAsStream(v);
                box.setIndex(id);
                isPlace = true;
                recursiveEntityPlaceOnMap(i+1,j,v);
                recursiveEntityPlaceOnMap(i-1,j,v);
                recursiveEntityPlaceOnMap(i,j+1,v);
                recursiveEntityPlaceOnMap(i,j-1,v);
            }
        }catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            Cockpit.addLog(arrayIndexOutOfBoundsException.getMessage());
        }

        return isPlace;
    }

    /**
     * create a unique id for the entity
     * @param v Visible entity
     * @return the unique id
     */
    public int getId(VisibleEntities v) {
        return (int) (v.getPosition().getX()+v.getPosition().getY()*1000);
    }

    /**
     * Condition to see if the point places on the corner and in the center of the box are in a shape
     * @param ve Visible entity
     * @param box boxMap
     * @return boolean
     */
    boolean isCornerOrMiddleOnAShape(VisibleEntities ve, BoxMap box) {
        Position p;
        p = new Position(box.getCenter().getX() - (double) boxSize / 2, box.getCenter().getY() - (double) boxSize / 2, 0);
        boolean hl = ve.getShape().isOnShape(ve.getPosition(), p);
        p = new Position(box.getCenter().getX() + (double) boxSize / 2, box.getCenter().getY() - (double) boxSize / 2, 0);
        boolean hr = ve.getShape().isOnShape(ve.getPosition(), p);
        p = new Position(box.getCenter().getX() - (double) boxSize / 2, box.getCenter().getY() + (double) boxSize / 2, 0);
        boolean ll = ve.getShape().isOnShape(ve.getPosition(), p);
        p = new Position(box.getCenter().getX() + (double) boxSize / 2, box.getCenter().getY() + (double) boxSize / 2, 0);
        boolean lr = ve.getShape().isOnShape(ve.getPosition(), p);
        return ve.getShape().isOnShape(ve.getPosition(), box.getCenter()) || hl || hr || ll || lr;
    }

    /**
     * Update the map if new entities are seen
     * @param visibleEntities list of visible entity
     * @return true if there is new entity
     */
    public boolean update(List<VisibleEntities> visibleEntities) {
        boolean status = addEntities(visibleEntities);
        if(status) setSpotsOccupied(allEntities);
        return status;
    }

    /**
     * add new entity in the list
     * @param visibleEntities new visible entities
     * @return true if there is new entities
     */
    boolean addEntities(List<VisibleEntities> visibleEntities) {
        int count = 0;
        boolean isOnList;
        for(VisibleEntities v1: visibleEntities){
            isOnList = false;
            for(VisibleEntities v2 : allEntities){
                if(v1.getPosition().getY() == v2.getPosition().getY() && v1.getPosition().getX()==v2.getPosition().getX()){
                    isOnList = true;
                    break;
                }
            }
            if(!isOnList && !v1.getType().equals(Constants.SHIP)){
                count++;
                allEntities.add(v1);
            }
        }

        return count>0;
    }

    public List<VisibleEntities> getAllEntities() {
        return allEntities;
    }

    public BoxMap getBox(int i, int j) {
        return map[i][j];
    }

    public BoxMap getBox(NodeA node) {
        return map[node.getI()][node.getJ()];
    }

}
