import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;

public class Day3 {
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String fileName;

    public static void main(String[] args){
        System.out.println(new Day3(args[0]).calPriorities());
    }

    public Day3(String filename) {
        this.fileName = filename;
    }

    private int calPriorities() {
        int score = 0;

        try (InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)));
             BufferedReader reader = new BufferedReader(isr)) {
            for (String input = reader.readLine(); input != null; input = reader.readLine()) {
                String first = input;
                String second = (reader.readLine());
                String third = (input = reader.readLine());

                char item = findDuplicateItem(first, second, third);
                int priority = findPriority(item);
                score += priority;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return score;
    }

    private int findPriority(char item) {
        return ALPHABET.indexOf(item) + 1;
    }


    private char findDuplicateItem(String input1, String input2, String input3) {
        char match = 0;
        for (int i=0; i < input1.length(); i++){
            char candidate = input1.charAt(i);
            for (int j=0; j < input2.length(); j++) {
                if (candidate == input2.charAt(j)) {
                    for (int k=0; k < input3.length(); k++) {
                        if (candidate == input3.charAt(k)) {
                            match = candidate;
                            break;
                        }
                    }
                }
            }
        }
        //System.out.printf("%s %s - %s%n", first, second, match);
        return match;
    }
}