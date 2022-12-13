import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Day4 {
    private final String fileName;

    public static void main(String[] args){
        Day4 day4 = new Day4(args[0]);
        System.out.println("Result:" + day4.doIt());
    }

    public Day4(String filename) {
        this.fileName = filename;
    }

    private int doIt() {
        int result = 0;
        Map<Integer, Integer> histogram = new HashMap<>();
        try (InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)));
             BufferedReader reader = new BufferedReader(isr)) {
            for (String input = reader.readLine(); input != null; input = reader.readLine()) {
                String[] ranges = input.split(",");
                Set<Integer> firstElf = setFromRange(ranges[0]);
                Set<Integer> secondElf = setFromRange(ranges[1]);

                if (containsAny(firstElf, secondElf) || containsAny(secondElf, firstElf)) {
                    result++;
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private boolean containsAny(Set<Integer> s1, Set<Integer> s2) {
        for(Integer test : s1) {
            if (s2.contains(test)){
                return true;
            }
        }
        return false;
    }

    private Set<Integer> setFromRange(String range) {
        String[] rangeValues = range.split("-");
        Set<Integer> result = new HashSet<>();
        for (int i=Integer.parseInt(rangeValues[0]); i<=Integer.parseInt(rangeValues[1]); i++) {
            result.add(i);
        }
        return result;
    }

    public void testSetFromRange() {
        String[] data = {"2-8", "3-7", "6-6", "4-5"};
        Arrays.stream(data).forEach(input -> System.out.println(input + ": " + setFromRange(input)));
    }
}