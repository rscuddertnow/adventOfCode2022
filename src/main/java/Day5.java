import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day5 {
    private static final Pattern MOVE_PATTERN = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");

    private final String fileName;

    public static void main(String[] args){
        Day5 day5 = new Day5(args[0]);
        System.out.println(day5.doIt());
    }

    public Day5(String filename) {
        this.fileName = filename;
    }

    private String doIt() {
        List<Stack<String>> stacks = null;

        try (InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)));
             BufferedReader reader = new BufferedReader(isr)) {

            boolean readingStacks = true;
            List<String> stackLines = new ArrayList<>();
            for (String input = reader.readLine(); input != null; input = reader.readLine()) {
                if (readingStacks) {
                    if ("".equals(input.trim())) {
                        readingStacks = false;
                        stacks = createStacks(stackLines);
                        continue;
                    }
                    stackLines.add(input);
                    continue;
                }

                moveCreates(input, stacks);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stacks.stream().map(Stack::peek)
                .collect(Collectors.joining(""));
    }

    private List<Stack<String>> createStacks(List<String> stackLines) {
        List<Stack<String>> stacks = new ArrayList<>();
        List<Integer> stackIndexes = new ArrayList<>();
        for (int i=stackLines.size()-1; i>=0; i--) {
           String line = stackLines.get(i);

           if (line.matches("( +[0-9])+")) {
               stackIndexes = getStackIndexes(line);
               stackIndexes.forEach(index -> stacks.add(new Stack<>()));
               continue;
           }

           for(int j=0; j < stacks.size(); j++) {
               Stack<String> current;
               int index = 0;
               try {
                   current = stacks.get(j);
                   index = stackIndexes.get(j);
                   char createLetter = line.charAt(index);
                   if (createLetter != ' ') {
                    current.add(String.valueOf(createLetter));
                   }
               } catch(IndexOutOfBoundsException ioobe) {
                   System.out.println("No value for line at index: " + index);
               }
           }
        }
        return stacks;
    }

    private List<Integer> getStackIndexes(String line) {
        List<Integer> result = new ArrayList<>();
        String[] stackNumbers = line.split(" +");
        for (int i=1; i<stackNumbers.length; i++) {
            result.add(line.indexOf(stackNumbers[i]));
        }
        return result;
    }

    private void moveCreates(String input, List<Stack<String>> stacks) {
        Matcher m = MOVE_PATTERN.matcher(input);
        if (m.matches()) {
            int numMoves = Integer.parseInt(m.group(1));
            int fromStack = Integer.parseInt(m.group(2));
            int toStack = Integer.parseInt(m.group(3));

            moveCreates(numMoves, fromStack, toStack, stacks);
        }
    }

    public void moveCreates(int numMoves, int fromStack, int toStack, List<Stack<String>> stacks) {
        System.out.println("Moving create...");
        Stack<String> from = stacks.get(fromStack-1);
        Stack<String> to = stacks.get(toStack-1);

        Stack<String> temp = new Stack<>();

        for (int i=0; i<numMoves; i++) {
            String itemToMove = from.pop();
            temp.push(itemToMove);
        }

        for(int i = 0; i< numMoves; i++) {
            to.push(temp.pop());
        }
    }
}