package fr.unice.polytech.si3.qgl.les_genies.game.entities;

import com.fasterxml.jackson.annotation.*;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Coordinates;
import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import java.util.List;

public class Entity {

    protected String type;

    @JsonIgnore
    protected Sailor sailor;

    @JsonIgnore
    protected Coordinates coordinates;

    @JsonIgnore
    protected boolean active;

    @JsonIgnore
    protected boolean focus;

    @JsonIgnore
    protected double angleEntity;

    /**
     * Constructor to make test, we can create an entity with coordinate
     * don't affect the Json stuff
     * @param x coordinate x
     * @param y coordinate y
     */
    public Entity(int x, int y){
        this.coordinates = new Coordinates(x,y);
    }

    public String getType() {
        return type;
    }

    @JsonGetter
    public int getX() {
        return this.coordinates.getCoordinateX();
    }

    @JsonGetter
    public int getY() {
        return this.coordinates.getCoordinateY();
    }

    @JsonSetter("x")
    public void setX(int x) {
        coordinates.setCoordinateX(x);
    }

    @JsonSetter("y")
    public void setY(int y) {
        coordinates.setCoordinateY(y);
    }

    public boolean isActive() {
        return active;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocusedBy(Sailor sailor){
        this.focus = true;
        this.sailor = sailor;
    }

    public void disFocusing(){
        this.focus = false;
        this.sailor = null;
    }

    /**
     * The entity is now use by a sailor
     * @param sailor a sailor
     */
    public void getUsedBy(Sailor sailor){
        this.sailor = sailor;
        this.active = true;
    }

    /**
     * Entity is now inactive
     */
    public void liberation(){
        this.sailor = null;
        this.active = false;
    }

    public boolean isLoaded() {
        return false;
    }

    public double getAngleEntity() {
        return angleEntity;
    }

    public boolean isOpenned() {
        return false;
    }

    @JsonSetter("type")
    public void setType(String type){
        this.type = type;
    }

    /**
     * @param sailor a sailor
     * @return the distance between this entity and the sailor
     */
    public int distanceFromSailor(Sailor sailor){
        return this.coordinates.distance(sailor.getCoordinates());
    }

    /**
     * method that return the closest sailor from this entity
     * witch is not already link to an entity
     * if list is empty, then this return null. cz there no sailor
     * @param sailorList a list of present sailor that can move
     * @return a sailor
     */
    public Sailor muchCloserSailor(List<Sailor> sailorList){
        int min = 100000;
        Sailor sal = null;
        if(!sailorList.isEmpty()) {
            for(Sailor s: sailorList){
                if(!s.isOnEntity()) {
                    int nat = distanceFromSailor(s);
                    if (nat < min) {
                        min = nat;
                        sal = s;
                    }
                }
            }
        }
        return sal;
    }

    public Sailor getSailor(){
        return sailor;
    }
}
