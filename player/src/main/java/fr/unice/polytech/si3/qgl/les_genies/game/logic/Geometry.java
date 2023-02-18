package fr.unice.polytech.si3.qgl.les_genies.game.logic;

import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import java.util.Arrays;

public class Geometry {

    /**
     * Return the position of a point of which is at the intersection of the margin of the circle with radius = radiusP1
     * and with center = p1 and the point p2.
     *
     * @param p1 Position
     * @param radiusP1 double
     * @param p2 Position
     * @return Position
     */
    public Position marginPointOfACircle(Position p1, double radiusP1, Position p2) {
        double[] eqLine = equationLine(p1, p2);
        if (Arrays.equals(eqLine, new double[2])) {
            double y = closestDouble(p2.getY(), p1.getY() + radiusP1, p1.getY() - radiusP1);
            return new Position(p2.getX(), y, 0);
        }

        // shape of the equation : ax² + bx + c = 0
        double[] eqCircle = equationCircleSubLine(p1, radiusP1, eqLine);

        double deter = determinant(eqCircle[0], eqCircle[1], eqCircle[2]);

        double xMin = xQuadraticSub(eqCircle[0], eqCircle[1], deter);
        double xMax = xQuadraticAdd(eqCircle[0], eqCircle[1], deter);
        double x = closestDouble(p2.getX(), xMin, xMax);
        double y = eqLine[0] * x + eqLine[1];

        return new Position(x, y, 0);
    }


    /**
     * Return the val a and b of a line equation made by the given 2 positions.
     *
     * @param p1 Position
     * @param p2 Position
     * @return double[2]
     */
    public double[] equationLine(Position p1, Position p2) {
        if (p1.getX() == p2.getX()) return new double[2];
        double a = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
        double b = p1.getY() - a * p1.getX();

        return new double[]{a, b};
    }

    /**
     * Calculate the equation of a circle and replace the value of Y by the value of the equation of a line.
     * circle equation : (x-a)²+(y-b)² = r²
     * equation include line equation : (x-a)²+(eqLine-b)² = r²
     * final equation : ax² + bx + c = 0
     *
     * @param p1 Position
     * @param radiusP1 double
     * @param equLine double[2]
     * @return double[3]
     */
    public double[] equationCircleSubLine(Position p1, double radiusP1, double[] equLine) {
        double a = 1 + Math.pow(equLine[0], 2);
        double b = 2 * (-p1.getX()) + 2 * equLine[0] * (equLine[1] - p1.getY());
        double c = Math.pow(p1.getX(), 2) + Math.pow(equLine[1] - p1.getY(), 2) - Math.pow(radiusP1,2);

        return new double[]{a, b, c};
    }

    /**
     * Return the determinant of an equation
     * f = b²-4ac
     *
     * @param a double
     * @param b double
     * @param c double
     * @return determinant double
     */
    double determinant(double a, double b, double c) {
        return b*b-4*a*c;
    }

    /**
     * Return max x value
     * f = (-b+sqrt(determinant))/(2a)
     *
     * @param a double
     * @param b double
     * @param determinant double
     * @return x double
     */
    double xQuadraticAdd(double a, double b, double determinant) {
        return (-b + Math.sqrt(determinant)) / (2 * a);
    }

    /**
     * Return min x value
     * f = (-b-sqrt(determinant))/(2a)
     *
     * @param a double
     * @param b double
     * @param determinant double
     * @return x double
     */
    double xQuadraticSub(double a, double b, double determinant) {
        return (-b - Math.sqrt(determinant)) / (2 * a);
    }

    /**
     * Return the value x the closest from the goal.
     * If  x1 and x2 have the same distance compare to the goal, so x2 is return
     *
     * @param goal double
     * @param x1 double
     * @param x2 double
     * @return double
     */
    double closestDouble(double goal, double x1, double x2) {
        if (Math.abs(goal - x1) < Math.abs(goal - x2))
            return x1;
        else return x2;
    }
}
