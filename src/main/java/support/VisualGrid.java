package support;

import java.util.List;
import java.util.Optional;

public class VisualGrid {
    int width = 27;
    int height = 21;

    int xOffset = width/2;
    int yOffset = height/2;

    final List<MyPoint> points;

    public VisualGrid(List<MyPoint> points) {
        this.points = points;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=height; i > 0; i--) {
            for (int j=0; j < width; j++) {
                int x = j - xOffset;
                int y = i - yOffset;
                Optional<MyPoint> option = points.stream().filter(p -> ((p.getX() == (double)x) && p.getY() == (double)y))
                        .findAny();
                if (option.isPresent()) {
                    sb.append(option.get().getLabel());
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}