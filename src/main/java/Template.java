import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Template {
    private final String fileName;

    public static void main(String[] args){
        System.out.println(new Template(args[0]).doIt());
    }

    public Template(String filename) {
        this.fileName = filename;
    }

    private int doIt() {
        int result = 0;

        try (InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)));
             BufferedReader reader = new BufferedReader(isr)) {
            for (String input = reader.readLine(); input != null; input = reader.readLine()) {
                // Do stuff by line
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}