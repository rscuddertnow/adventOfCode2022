import org.junit.jupiter.api.Test;
import support.MyPoint;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day9Test {
    Day9 day9 = new Day9("foo");

    @Test
    public void testMoveTail() {
        MyPoint H  = new MyPoint(0, 0, "H");
        MyPoint T = new MyPoint(0, 0, "T");

        day9.moveTail(H, T);
        assertTrue(T.getX() == 0 && T.getY() == 0);

        H.setLocation(2, 0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == 1 && T.getY() == 0);

        H.setLocation(0,-2);
        T.setLocation(0,0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == 0 && T.getY() == -1);

        H.setLocation(-2,0);
        T.setLocation(0,0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == -1 && T.getY() == 0);

        H.setLocation(0,2);
        T.setLocation(0,0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == 0 && T.getY() == 1);

        // Diagonal
        H.setLocation(1,2);
        T.setLocation(0,0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == 1 && T.getY() == 1);

        H.setLocation(2,1);
        T.setLocation(0,0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == 1 && T.getY() == 1);

        H.setLocation(2,-1);
        T.setLocation(0,0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == 1 && T.getY() == -1);

        H.setLocation(1,-2);
        T.setLocation(0,0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == 1 && T.getY() == -1);

        H.setLocation(-1,-2);
        T.setLocation(0,0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == -1 && T.getY() == -1);

        H.setLocation(-2,-1);
        T.setLocation(0,0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == -1 && T.getY() == -1);

        H.setLocation(-2,1);
        T.setLocation(0,0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == -1 && T.getY() == 1);

        H.setLocation(-1,2);
        T.setLocation(0,0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == -1 && T.getY() == 1);

        // No Moves
        H.setLocation(1,1);
        T.setLocation(0, 0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == 0 && T.getY() == 0, String.format("Tail was %s", T));

        H.setLocation(1,-1);
        T.setLocation(0, 0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == 0 && T.getY() == 0, String.format("Tail was %s", T));

        H.setLocation(-1,-1);
        T.setLocation(0, 0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == 0 && T.getY() == 0, String.format("Tail was %s", T));

        H.setLocation(-1,1);
        T.setLocation(0, 0);
        day9.moveTail(H, T);
        assertTrue(T.getX() == 0 && T.getY() == 0, String.format("Tail was %s", T));
    }
    
}
