package fr.unice.polytech.si3.qgl.les_genies.game.tools;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * class that represent the Coordinates of stuff in general on the ship
 * no negative coordinates on the ship
 */
public class Coordinates {
    @JsonIgnore
    private int x;
    @JsonIgnore
    private int y;

    public Coordinates(int x,int y){
        this.x = x;
        this.y = y;
    }

    /**
     * return the value of y, the abscisse
     */
    public int getCoordinateY(){
        return this.y;
    }
    /**
     * return the value of x, the ordinate
     */
    public int getCoordinateX(){
        return this.x;
    }

    public void setCoordinateX(int x) {
        this.x = x;
    }

    public void setCoordinateY(int y) {
        this.y = y;
    }

    /**
     * return the value of the coordinate of the thing
     */
    public int[] getCoordinates(){
        return new int[]{this.x, this.y};
    }

    /**
     * change x or y in function of the shift
     * @param xDistance moving distance x
     * @param yDistance moving distance y
     */
    public void translation(int xDistance, int yDistance){
        this.x += xDistance;
        this.y += yDistance;
    }

    public int distance(Coordinates coordinates){
        return Math.abs(this.y-coordinates.getCoordinateY())+Math.abs(this.x-coordinates.getCoordinateX());
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
