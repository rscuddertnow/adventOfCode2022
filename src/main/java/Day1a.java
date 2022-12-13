import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Day1a {
    private final String fileName;

    public static void main(String[] args){
       System.out.println(new Day1a(args[0]).getLargestCalories());
    }

    public Day1a(String filename) {
       this.fileName = filename;
    }

    private int getLargestCalories() {
        List<Integer> counts = new ArrayList<>();

        try (InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)));
             BufferedReader reader = new BufferedReader(isr)) {
            String input = null;
            int calCounter = 0;
            for (input = reader.readLine(); input != null; input = reader.readLine()) {
                if ("".equals(input.trim())) {
                    counts.add(calCounter);
                    calCounter = 0;
                    continue;
                }
                calCounter += Integer.parseInt(input);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        counts.sort(Comparator.reverseOrder());
        return counts.get(0) + counts.get(1) + counts.get(2);
    }
}
