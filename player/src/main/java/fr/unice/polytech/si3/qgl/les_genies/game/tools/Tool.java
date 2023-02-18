package fr.unice.polytech.si3.qgl.les_genies.game.tools;

import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Wind;
import java.awt.*;

public class Tool {

    Position shipPosition;

    public Tool(Position shipPosition) {
        this.shipPosition = shipPosition;
    }
    public Tool() {}

    /**
     * return the distance made by the boat and the new orientation in a Position object
     * @param rightOar number of oar use at the right of the boat
     * @param leftOar number of oar use at the left of the boat
     * @param totalOar number of oar on the boat
     * @return a Position that is corresponding to the move
     */
    Position distanceOar(int rightOar, int leftOar, int totalOar){
        double vitesse = oarSpeed(rightOar,leftOar,totalOar);
        double rotation = oarAngle(rightOar,leftOar,totalOar);

        return calculPosition(vitesse, rotation);
    }

    /**
     * return the distance made by the boat and the new orientation in a Position object
     * @param rightOar number of oar use at the right of the boat
     * @param leftOar number of oar use at the left of the boat
     * @param totalOar number of oar on the boat
     * @param angleRudder the angle to turn with the rudder in radian
     * @return a Position that is corresponding to the move
     */
    Position distanceOarAndRudder(int rightOar, int leftOar, int totalOar, double angleRudder){
        double vitesse = oarSpeed(rightOar,leftOar,totalOar);
        double rotation = oarAngle(rightOar,leftOar,totalOar) + angleRudder;

        return calculPosition(vitesse, rotation);
    }

    /**
     * return the distance made by the boat and the new orientation in a Position object
     * @param rightOar number of oar use at the right of the boat
     * @param leftOar number of oar use at the left of the boat
     * @param totalOar number of oar on the boat
     * @param angleRudder the angle to turn with the rudder in radian
     * @param sailOpen number of sail open
     * @param nbSail number of sail on the ship
     * @param wind object wind that has the strength and the orientation of the wind
     * @return a Position that is corresponding to the move
     */
    public Position distanceOarAndRudderAndSail(int rightOar, int leftOar, int totalOar, double angleRudder, int sailOpen, int nbSail, Wind wind){
        double vitesse = oarSpeed(rightOar,leftOar,totalOar) + sailSpeed(sailOpen,nbSail,wind,shipPosition.getOrientation());
        double rotation = oarAngle(rightOar,leftOar,totalOar) + angleRudder;

        return calculPosition(vitesse, rotation);
    }

    /**
     * Calcul the relative position with the speed and rotation asked
     * @param vitesse speed of the boat
     * @param rotation angle to turn
     * @return a Position that is corresponding to the move
     */
    Position calculPosition(double vitesse, double rotation){
        Position newPosition = new Position(shipPosition.getX(),shipPosition.getY(),shipPosition.getOrientation());
        double angle = shipPosition.getOrientation();

        int cut = 10000;
        for(int i = 0; i < cut; i++){
            newPosition.setOrientation((newPosition.getOrientation()+rotation/ cut));
            newPosition.setX(newPosition.getX()+(vitesse/ cut)*Math.cos(angle));
            newPosition.setY(newPosition.getY()+(vitesse/ cut)*Math.sin(angle));
            angle+=rotation/ cut;
        }
        return newPosition;
    }

    /**
     * give the angle between 2 orientation
     * @param a first orientation
     * @param b second orientation
     * @return an angle in rad
     */
    double angleBetween(double a, double b){
        return a-b;
    }

    /**
     * Calcul the angle made by Oar
     * @param rightOar number of oar used at the right of the boat
     * @param leftOar number of oar used at the left of the boat
     * @param totalOar number of oar on the boat
     * @return the angle made
     */
    double oarAngle(int rightOar, int leftOar, int totalOar){
        return Math.PI*(rightOar-leftOar)/totalOar;
    }

    /**
     * Calcul the speed made by Oar
     * @param rightOar number of oar used at the right of the boat
     * @param leftOar number of oar used at the left of the boat
     * @param totalOar number of oar on the boat
     * @return the speed made
     */
    double oarSpeed(int rightOar, int leftOar, int totalOar){
        return 165.0 * (rightOar+leftOar)/totalOar;
    }

    /**
     * Calcul the speed made by sail
     * @param sailOpen number of sail open
     * @param nbSail number of sail on the boat
     * @param wind Object wind
     * @param shipOrientation orientation of the ship
     * @return the speed made
     */
    double sailSpeed(int sailOpen, int nbSail, Wind wind, double shipOrientation){
        if(nbSail==0) return 0;
        if(wind == null) {
            return 0.0;
        }
        return (double)sailOpen/nbSail*wind.getStrength()*Math.cos(angleBetween(shipOrientation,wind.getOrientation()));
    }

    private Polygon rotatePoints(Position position, Shape shape, int[] xs, int[] ys, int size){
        double temp;
        double angle = position.getOrientation() + shape.getOrientation();

        for (int i = 0; i<size;i++){
            temp = xs[i]*Math.cos(angle) - ys[i]*Math.sin(angle);
            ys[i] = (int)(xs[i]*Math.sin(angle) + ys[i]*Math.cos(angle) + position.getY());
            xs[i] = (int)(temp + position.getX());
        }
        return new Polygon(xs,ys, size);
    }


    private Polygon calculRectangle(Shape shape, Position position){
        int size = 4;
        int[] xs = new int[size];
        int[] ys = new int[size];

        xs[0] = (int)shape.getHeight()/2;
        ys[0] = (int)shape.getWidth()/2;

        xs[1] = (int)-shape.getHeight()/2;
        ys[1] = (int)shape.getWidth()/2;

        xs[2] = (int)-shape.getHeight()/2;
        ys[2] = (int)-shape.getWidth()/2;

        xs[3] = (int)shape.getHeight()/2;
        ys[3] = (int)-shape.getWidth()/2;

        return rotatePoints(position, shape, xs, ys, size);
    }

    private Polygon calculPolygone(Shape shape, Position position){
        int size = shape.getVertices().length;

        int[] xs = new int[size];
        int[] ys = new int[size];

        for(int i = 0 ; i < size ; i++){
            xs[i] = (int) shape.getVertices()[i].getX();
            ys[i] = (int) shape.getVertices()[i].getY();
        }

        return rotatePoints(position, shape, xs, ys, size);
    }

    public Polygon createRectangle(Shape shape, Position position) {
        if(shape.getType().equals(Constants.RECTANGLE)) return calculRectangle(shape, position);
        return null;
    }

    public Polygon createPolygone(Shape shape, Position position){
        if(shape.getType().equals(Constants.POLYGONE)) return calculPolygone(shape, position);
        return null;
    }
}
