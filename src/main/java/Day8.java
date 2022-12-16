import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Day8 {
    private final String fileName;
    private List<String[]> treeGridRows = new ArrayList<>();

    public static void main(String[] args){
        System.out.println(new Day8(args[0]).doIt());
    }

    public Day8(String filename) {
        this.fileName = filename;
    }

    private int doIt() {
        int result = 0;

        try (InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)));
             BufferedReader reader = new BufferedReader(isr)) {
            for (String input = reader.readLine(); input != null; input = reader.readLine()) {
                // Read Grid
                String[] cols = new String[input.length()];
                for(int i=0; i < input.length(); i++) {
                    cols[i] = input.substring(i, i+1);
                }
                treeGridRows.add(cols);
            }
            result = findVisibleTrees();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private int findVisibleTrees() {
        int result = 0;
        int numOfRows = treeGridRows.size();
        for (int i = 0; i < numOfRows; i++) {
            String[] treeRow = treeGridRows.get(i);
            int numOfCols = treeRow.length;
            for (int j = 0; j < numOfCols; j++) {
                if (isVisible(i,j,numOfRows,numOfCols)) result++;
            }
        }
        return result;
    }

    private boolean isVisible(int row, int col, int maxRows, int maxCols) {
        if (row == 0 || row == maxRows-1 
                || col == 0 || col == maxCols-1) {
            return true;
        } else {
            int originalValue = Integer.parseInt((treeGridRows.get(row))[col]);
            boolean up =  isVisibleVertically(row - 1, col, originalValue, maxRows, -1);
            boolean down = isVisibleVertically(row + 1, col, originalValue, maxRows, 1);
            boolean left = isVisibleHorizontally(row, col - 1, originalValue, maxCols, -1);
            boolean right = isVisibleHorizontally(row, col + 1, originalValue, maxCols, 1);

            return up || down || left || right;
        }
    }

    private boolean isVisibleVertically(final int row, final int col, final int originalValue, final int maxRows, int crawlStep) {
        int currentValue = Integer.parseInt((treeGridRows.get(row))[col]);

        if (row == 0 || row == maxRows-1) {
            return (currentValue < originalValue);
        }

        return ((currentValue < originalValue))
                && isVisibleVertically((row + crawlStep), col, originalValue, maxRows, crawlStep);
    }

    private boolean isVisibleHorizontally(final int row, final int col, final int originalValue, final int maxCols, int crawlStep) {
        int currentValue = Integer.parseInt((treeGridRows.get(row))[col]);
        if (col == 0 || col == maxCols-1) {
            return (currentValue < originalValue);
        }

        return ((currentValue < originalValue))
                && isVisibleHorizontally(row, (col + crawlStep), originalValue, maxCols, crawlStep);
    }
}