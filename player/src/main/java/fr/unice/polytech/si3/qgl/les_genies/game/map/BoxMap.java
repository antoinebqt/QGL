package fr.unice.polytech.si3.qgl.les_genies.game.map;

import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Stream;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.VisibleEntities;

public class BoxMap {
    private boolean isFree;
    private final Position pos;
    private final int i;
    private final int j;
    private int index;
    private BoxStream boxStream = null;

    public BoxMap(int size, int i, int j) {
        isFree = true;
        pos = new Position(i*size+size/2.0,j*size+size/2.0,0);
        this.i = i;
        this.j = j;
        this.index = 0;
    }

    public void setOccupied(){
        isFree = false;
    }

    /**
     * @return true if is free : no reef
     */
    public boolean getStatus(){
        return isFree;
    }

    public Position getCenter() {
        return pos;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public String toString() {
        return "{" +
                "isFree=" + isFree +
                ", pos=" + pos +
                ", i=" + i +
                ", j=" + j +
                '}';
    }

    public void setIndex(int id) {
        this.index = id;
    }

    public int getId() {
        return index;
    }

    public void setAsStream(VisibleEntities v) {
        boxStream = new BoxStream((Stream) v);
    }

    public boolean isStream(){
        return boxStream != null;
    }

    public BoxStream getStreamInfos(){
        return boxStream;
    }
}
