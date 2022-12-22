package support;

import java.awt.Point;

public class MyPoint extends Point {
    private final String label;

    public MyPoint(int x, int y, String label) {
        super(x, y);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
