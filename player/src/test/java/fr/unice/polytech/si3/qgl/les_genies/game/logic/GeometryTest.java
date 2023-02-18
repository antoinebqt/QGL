package fr.unice.polytech.si3.qgl.les_genies.game.logic;

import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.print.attribute.standard.PresentationDirection;

import static org.junit.jupiter.api.Assertions.*;

class GeometryTest {
    Geometry geo;

    @BeforeEach
    void setUp() {
        geo = new Geometry();
    }


    @Test
    void marginPointOfACircle() {
        Position p1 = new Position(1000, 0, 0);
        Position p2 = new Position(0, 1000, 0);
        double radius = 37.5;
        Position p = geo.marginPointOfACircle(p1, radius, p2);

        assertEquals(973.4834957055044, p.getX());
        assertEquals(26.516504294495576, p.getY());
    }

    @Test
    void marginPointOfACircleSameX() {
        Position p1 = new Position(500, 200, 0);
        Position p2 = new Position(500, 1000, 0);
        double radius = 50;
        Position p = geo.marginPointOfACircle(p1, radius, p2);

        assertEquals(500, p.getX());
        assertEquals(250, p.getY());
    }

    @Test
    void marginPointOfACircleSameX2() {
        Position p1 = new Position(500, 200, 0);
        Position p2 = new Position(500, 0, 0);
        double radius = 50;
        Position p = geo.marginPointOfACircle(p1, radius, p2);

        assertEquals(500, p.getX());
        assertEquals(150, p.getY());
    }

    @Test
    void equationLineTest() {
        Position p1 = new Position(0, 0, 0);
        Position p2 = new Position(3, 3, 0);
        double[] res = geo.equationLine(p1, p2);

        assertEquals(1, res[0]);
        assertEquals(0, res[1]);
    }

    @Test
    void equationLineTestInv() {
        Position p1 = new Position(0, 7, 0);
        Position p2 = new Position(4, 2, 0);
        double[] res = geo.equationLine(p1, p2);

        assertEquals(-1.25, res[0]);
        assertEquals(7, res[1]);
    }

    @Test
    void equationCircleSubLineTest() {
        Position p1 = new Position(1000, 0, 0);
        double[] eqLine = new double[] {-1, 1000};
        double radius = 37.5;
        double[] res = geo.equationCircleSubLine(p1, radius, eqLine);

        assertEquals(2, res[0]);
        assertEquals(-4000, res[1]);
        assertEquals(1998593.75, res[2]);
    }

    @Test
    void equationCircleSubLineTestNeg() {
        Position p1 = new Position(-10, -5, 0);
        double[] eqLine = new double[] {0.75, 2.5};
        double radius = 3;
        double[] res = geo.equationCircleSubLine(p1, radius, eqLine);

        assertEquals(1.5625, res[0]);
        assertEquals(31.25, res[1]);
        assertEquals(147.25, res[2]);
    }

    @Test
    void determinantTestPos() {
        double det = geo.determinant(2, 18, 4);
        assertEquals(292, det);
    }

    @Test
    void determinantTestNeg() {
        double det = geo.determinant(1, 2, 3);
        assertEquals(-8, det);
    }

    @Test
    void determinantTestNul() {
        double det = geo.determinant(3, 6, 3);
        assertEquals(0, det);
    }

    @Test
    void xQuadraticSubTest() {
        double x = geo.xQuadraticSub(2, 5, 16);
        assertEquals(-2.25, x);
    }

    @Test
    void xQuadraticAddTest() {
        double x = geo.xQuadraticAdd(2, 5, 16);
        assertEquals(-0.25, x);
    }

    @Test
    void closestDoubleTestPos() {
        double res = geo.closestDouble(5, -10, 10);
        assertEquals(10, res);
    }

    @Test
    void closestDoubleTestNeg() {
        double res = geo.closestDouble(-5, -10, 10);
        assertEquals(-10, res);
    }

    @Test
    void closestDoubleTestEqu() {
        double res = geo.closestDouble(0, -10, 10);
        assertEquals(10, res);
    }
}