import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import support.MyPoint;

public class Day9 {
    private final String fileName;

    public static void main(String[] args){
        Day9 day9 = new Day9(args[0]);
        System.out.println(day9.doIt());
    }

    public Day9(String filename) {
        this.fileName = filename;
    }

    private int doIt() {
        int result;
        Set<Point> tLocs = new HashSet<>();
        try (InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)));
             BufferedReader reader = new BufferedReader(isr)) {

            MyPoint H = new MyPoint(0,0, "H");
            MyPoint t1 = new MyPoint(0,0, "1");
            MyPoint t2 = new MyPoint(0,0, "2");
            MyPoint t3 = new MyPoint(0,0, "3");
            MyPoint t4 = new MyPoint(0,0, "4");
            MyPoint t5 = new MyPoint(0,0, "5");
            MyPoint t6 = new MyPoint(0,0, "6");
            MyPoint t7 = new MyPoint(0,0, "7");
            MyPoint t8 = new MyPoint(0,0, "8");
            MyPoint T = new MyPoint(0, 0, "9");

            for (String input = reader.readLine(); input != null; input = reader.readLine()) {
                String[] instruction = input.split(" ");

                System.out.printf("======= %s =======\n", input);

                for(int i = 0; i < Integer.parseInt(instruction[1]);  i++) {
                    // Move Head
                    String direction = instruction[0];
                    switch(direction) {
                        case "U" -> H.setLocation(H.getX() , H.getY() + 1);
                        case "D" -> H.setLocation(H.getX(), H.getY() - 1);
                        case "L" -> H.setLocation(H.getX() - 1, H.getY());
                        case "R" -> H.setLocation(H.getX() + 1, H.getY());
                    }
                    moveTail(H, t1);
                    moveTail(t1, t2);
                    moveTail(t2, t3);
                    moveTail(t3, t4);
                    moveTail(t4, t5);
                    moveTail(t5, t6);
                    moveTail(t6, t7);
                    moveTail(t7, t8);
                    moveTail(t8, T);
                    tLocs.add(new Point((int) T.getX(), (int) T.getY()));
                }
                //System.out.println(new support.VisualGrid(Arrays.asList(H,t1, t2, t3, t4, t5, t6, t7, t8, T, new support.MyPoint(0, 0, "s"))));

            }
            result = tLocs.size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    void moveTail(MyPoint n, MyPoint m) {
        double dx = n.getX() - m.getX();
        double dy = n.getY() - m.getY();

        if ((Math.abs(dx) > 1 && dy == 0) || (Math.abs(dy) > 1) && dx == 0 ) { // Cardinal Direction move
            if (dx == -2) {
                m.setLocation((m.getX()-1), m.getY());
            } else if (dx == 2) {
                m.setLocation((m.getX()+1), m.getY());
            } else if (dy == -2) {
                m.setLocation(m.getX(), (m.getY() - 1));
            } else if (dy == 2) {
                m.setLocation(m.getX(), (m.getY() + 1));
            }
        } else {
            if ((dx >= 1 && dy > 1) || ((dx > 1 && dy >= 1))) {  // NE
                m.setLocation(m.getX() + 1, m.getY() + 1);
            } else if ((dx >= 1 && dy < -1) || (dx > 1 && dy <= -1)) {  // SE
                m.setLocation( m.getX() + 1, m.getY() - 1);
            } else if ((dx <= -1 && dy < -1) || (dx < -1 && dy <= -1)) {  // SW
                m.setLocation(m.getX() - 1, m.getY() - 1);
            } else if ((dx < -1 && dy >= 1) || (dx <= -1 && dy > 1)) {  // NW
                m.setLocation(m.getX() - 1, m.getY() + 1);
            }
        }
    }
}