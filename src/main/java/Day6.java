import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Day6 {
    private final String fileName;

    public static void main(String[] args){
        System.out.println(new Day6(args[0]).doIt());
    }

    public Day6(String filename) {
        this.fileName = filename;
    }

    private int doIt() {
        int result = 0;

        try (InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)));
             BufferedReader reader = new BufferedReader(isr)) {
            for (String input = reader.readLine(); input != null; input = reader.readLine()) {
                for (int i=0; i<input.length(); i++) {
                    String test = input.substring(i, i+14);
                    if (isStartOfPacketMarker(test)) {
                        return i+14;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private boolean isStartOfPacketMarker(String sequence) {
        boolean hasDups = false;
        char[] chars = sequence.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            for(int j=0; j < chars.length; j++){
                if (i==j) continue;
                if (chars[i] == chars[j]) {
                    hasDups = true;
                    break;
                }
            }
        }
        return !hasDups;
    }
}