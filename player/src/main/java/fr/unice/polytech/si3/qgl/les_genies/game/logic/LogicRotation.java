package fr.unice.polytech.si3.qgl.les_genies.game.logic;

import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.Checkpoint;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Vector;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;
import static java.lang.Math.PI;

public class LogicRotation {

    Ship ship;
    private double angleRudder;
    private final OarConfig oarConfig;
    private double angle;
    private double bestAngle;

    public LogicRotation(Ship ship){
        this.ship = ship;
        angleRudder = 0.0;
        oarConfig = new OarConfig(0,0);
    }

    public void computeRotationData(Checkpoint checkpoint){
        angle = angleToCheckpoint(checkpoint);

        if (angle >= -Math.PI/4 && angle <= Math.PI/4){
            // We can make the right angle using only the rudder
            angleRudder = angle;
        } else {
            // We need to use the oars to turn
            computeOar(angle);
            angleRudder = angleAjustement(angle, bestAngle);

        }
    }

    double angleAjustement(double angle, double bestAngle) {
        double angleAdjustment = angle - bestAngle;

        if (angleAdjustment > 0){
            return Math.min(angleAdjustment, PI / 4);
        } else if(angleAdjustment < 0) {
            return Math.max(angleAdjustment, -PI / 4);
        } else {
            return 0;
        }
    }

    /**
     * Compute the angle between the ship direction and
     * the given checkpoint
     *
     * @param checkpoint The checkpoint to be aligned with
     * @return A double value, it's the value to turn to be
     * aligned with the given checkpoint
     */
    public double angleToCheckpoint(Checkpoint checkpoint) {
        double shipOrientation = ship.getPosition().getOrientation();

        // The vector between the positions of the ship and the checkpoint
        Vector vectorShipCheckpoint = new Vector(ship.getPosition(), checkpoint.getPosition());

        // Return the difference of angle (the angle to turn)
        return correctAngle(vectorShipCheckpoint.getAngle() - shipOrientation);
    }

    /**
     * Verify if the angle is bigger than PI or smaller than -PI and correct it
     * @param angle in radian
     * @return the corrected angle in radian
     */
    double correctAngle(double angle){
        if(angle<-PI) return PI + angle+PI;
        else if(angle>PI) return -PI+ angle-PI;
        return angle;
    }

    /**
     * Compute all the angles possible with the number of oars
     * and find the closer one to turn
     *
     * @param angleToTurn The angle to turn
     * @return A tab of int with 2 value : the first one is the number
     * of oars to activate on the left side of the ship and the second
     * is the number of oars to activate on the right side of the ship
     */
    private void computeOar(double angleToTurn) {
        int leftOar = 0;
        int rightOar = 0;
        int totalOar;

        // Count the number of oars each side of the ship
        for (Entity entity : ship.getEntities()) {
            if (Objects.equals(entity.getType(), "oar")) {
                if (entity.getY() == 0) {
                    leftOar++;
                } else if(entity.getY()==ship.getDeck().getWidth()-1){
                    rightOar++;
                }
            }
        }
        totalOar = leftOar + rightOar;

        // If the angle to turn is superior to PI/2 then all the oars on right side must be activated
        if (angleToTurn > Math.PI / 2 && angleToTurn < Math.PI) {
            oarConfig.setOarRightSide(rightOar);
        }
        // If the angle to turn is inferior to -PI/2 then all the oars on left side must be activated
        else if (angleToTurn < -Math.PI / 2 && angleToTurn > -Math.PI) {
            oarConfig.setOarLeftSide(leftOar);
        } else {
            double[] angles = possibleAngles(totalOar); // Get all the angles possible with the number of oars
            bestAngle = bestAngle(angleToTurn, angles); // Find the best of them compared to the angle to turn
            findBestOarCombination(bestAngle, leftOar, rightOar); //Find the best combination of oars to be activated
        }
    }

    /**
     * Find the best combination of oars to be activated
     * to turn the ship correctly
     *
     * @param bestAngle The best angle we can turn to be aligned with the checkpoint
     * @param leftOar   The number of oar on the left side of the ship
     * @param rightOar  The number of oar on the right side of the ship
     * @return A tab of int with 2 value : the first one is the number
     * of oars to activate on the left side of the ship and the second
     * is the number of oars to activate on the right side of the ship
     */
    private void findBestOarCombination(double bestAngle, int leftOar, int rightOar) {
        // Creating a format to round on the fifteenth decimal of the angle
        DecimalFormat df = new DecimalFormat("#.###############");
        df.setRoundingMode(RoundingMode.CEILING);

        double oarPower = (Math.PI) / (leftOar + rightOar); //The power of a unique oar when activated

        int[] bestCombination = {0, 0}; //The initial combination

        boolean found = false;

        for (int i = 0; i < leftOar + 1 && !found; i++) {
            for (int j = 0; j < rightOar + 1; j++) {

                /*
                The angle that the ship will turn with i oars activated
                on the left and j oars activated on the right
                 */
                double power = (i * (-oarPower) + (j * oarPower));

                /*
                If the power calculated is the same that the angle we want to turn
                then it's the good combination of oars
                 */
                if (Objects.equals(df.format(power), df.format(bestAngle))) {
                    bestCombination[0] = i;
                    bestCombination[1] = j;
                    oarConfig.setOarEachSide(bestCombination[0],bestCombination[1]);
                    found = true;
                }
            }
        }
    }

    /**
     * Find the best angle we can turn compared to
     * the actual angle we want to turn
     *
     * @param angleToTurn The angle we want to turn
     * @param angles      The angles we can turn in a SORTED array
     * @return The best angle we can turn
     */
    double bestAngle(double angleToTurn, double[] angles) {
        double best = 5; // Random "high" value that will be use for the first comparison
        int bestIndex = 0; // Initial best index

        // Find the index of the best angle in the possible angles
        for (int i = 0; i < angles.length; i++) {
            double minus = angleToTurn - angles[i];
            if (Math.abs(minus) < best) {
                best = minus;
                bestIndex = i;
            }
        }
        best = angles[bestIndex];
        return best;
    }

    /**
     * Compute all the possible angles the ship can turn
     * depending on the oars it has
     *
     * @param totalOar The number of oars on the ship
     * @return A tab containing all the angle the ship can turn
     */
    public double[] possibleAngles(int totalOar) {

        double[] angles = new double[totalOar + 1];

        if (totalOar == 0) {
            angles[0] = 0.0;
            return angles;
        }

        for (int i = 0; i < totalOar + 1; i++) {
            angles[i] = -Math.PI / 2 + i * ((Math.PI) / totalOar);
        }

        return angles;
    }

    /**
     * Convert a radian value into a degree value
     *
     * @param rad The radian to be converted
     * @return The degree value of the initial radian value
     */
    public double radToDeg(double rad) {
        return (rad * 180) / Math.PI;
    }

    public double getAngleRudder() {
        return angleRudder;
    }

    public double getAngle() {
        return angle;
    }

    public OarConfig getOarConfig() {
        return oarConfig;
    }
}
