import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day7 {
    private final String fileName;
    private MyFile root = new MyFile(true,"/", null);

    public static void main(String[] args){
        System.out.println(new Day7(args[0]).doIt());
    }

    public Day7(String filename) {
        this.fileName = filename;
    }

    private int doIt() {
        int result = 0;


        MyFile currentDirectory = null;

        try (InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)));
             BufferedReader reader = new BufferedReader(isr)) {

            // Create FileSystem
            boolean createFiles = false;
            for (String input = reader.readLine(); input != null; input = reader.readLine()) {
                String[] tokens = input.split(" ");
                if ("$".equals(tokens[0])) {
                    createFiles = false;
                    String command = tokens[1];
                    if ("cd".equals(command)) {
                        MyFile dir = findDirectory(tokens[2], currentDirectory);
                        currentDirectory = dir;
                    } else if ("ls".equals(command)) {
                        createFiles = true;
                    }
                    continue;
                } else if ("dir".equals(tokens[0]) && createFiles) {
                    currentDirectory.getContainedFiles().add(
                            new MyFile(true, tokens[1], currentDirectory));
                } else if (createFiles) {
                    currentDirectory.getContainedFiles().add(
                            new MyFile(false, Long.parseLong(tokens[0]),tokens[1], currentDirectory));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Solve Puzzle
        result = (int) solvePuzzle();

        //printFiles(root, 0);
        return result;
    }

    private long solvePuzzle() {
        List<MyFile> candidates = findCandidates(root);
        candidates.sort(Comparator.comparingLong(MyFile::getSize));
        return (candidates.get(0).getSize());
    }

    private long determineSpaceNeeded() {
        long freeSpace = 70000000 - root.getSize();
        return 30000000-freeSpace;
    }

    private List<MyFile> findCandidates(MyFile file) {
        List<MyFile> candidates = new ArrayList<>();
        if (file.isDirectory()) {
            if (file.getSize() >= determineSpaceNeeded()) {
                candidates.add(file);
            }
            file.getContainedFiles().forEach(f -> candidates.addAll(findCandidates(f)));
        }
        return candidates;
    }


    private MyFile findDirectory(final String name, final MyFile currentDirectory) throws FileNotFoundException {
        if ("/".equals(name)) {
            return root;
        } else if ("..".equals(name)) {
            return currentDirectory.getParent();
        } else {
            Optional<MyFile> dirOptional = currentDirectory.getContainedFiles().stream()
                    .filter(MyFile::isDirectory)
                    .filter(dir -> name.equals(dir.getName()))
                    .findFirst();
            if(dirOptional.isPresent()) {
                return dirOptional.get();
            }
        }
        throw new FileNotFoundException(
                String.format("Unable to find directory. Directory %s is not a child of %s",
                        name, currentDirectory.getName()));

    }

    private void printFiles(MyFile base, int depth) {
        String padding = "   ".repeat(depth);
        System.out.println(padding + base.toString());
        if (base.isDirectory()) {
            base.getContainedFiles().forEach(f -> printFiles(f, depth+1));
        }
    }


}

class MyFile {
    public static final String PADDING = "   ";
    private boolean isDirectory = false;
    private long size = 0;
    private String name = "";
    private MyFile parent = null;
    private List<MyFile> containedFiles = new ArrayList<>();

    public MyFile(boolean isDirectory, long size, String name, MyFile parent) {
        this.isDirectory = isDirectory;
        this.size = size;
        this.name = name;
        this.parent = parent;
    }

    public MyFile(boolean isDirectory, String name, MyFile parent) {
        this.isDirectory = isDirectory;
        this.size = 0;
        this.name = name;
        this.parent = parent;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public long getSize() {
        return (isDirectory) ? determineFileSize(this) : size;
    }

    public String getName() {
        return name;
    }

    public MyFile getParent() {
        return parent;
    }

    public List<MyFile> getContainedFiles() {
        return containedFiles;
    }

    public String toString() {
        return (isDirectory) ?
            String.format("- %s (dir)", name) :
                String.format("- %s (file, size=%d)", name, size);
    }

    private long determineFileSize(MyFile file) {
        long result = 0;
        if (file.isDirectory()) {
            for(MyFile f : file.getContainedFiles()) {
                result += determineFileSize(f);
            }
        } else {
            return file.getSize();
        }
        return result;
    }
}