import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Day2 {
    private final String fileName;

    public static void main(String[] args){
        System.out.println(new Day2(args[0]).calculateScore());
    }

    public Day2(String filename) {
        this.fileName = filename;
    }

    private int calculateScore() {
        int score = 0;

        try (InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)));
             BufferedReader reader = new BufferedReader(isr)) {
            for (String input = reader.readLine(); input != null; input = reader.readLine()) {
                String[] roundArr = input.split(" ");
                score += new Round(roundArr[1], roundArr[0]).score();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return score;
    }
}
class Round {
    private final Shape yours;
    private final Shape opponent;

    public Round(String outcome, String opp) {
        this.opponent = getShape(opp);
        this.yours = determineYourBasedOnOutcome(this.opponent, outcome);
    }

    private Shape determineYourBasedOnOutcome(Shape opp, String outcome) {
        return switch (outcome) {
            case "Z" -> switch (opp) {
                case ROCK -> Shape.PAPER;
                case PAPER -> Shape.SCISSORS;
                case SCISSORS -> Shape.ROCK;
                default -> Shape.NONE;
            };
            case "X" -> switch (opp) {
                case ROCK -> Shape.SCISSORS;
                case PAPER -> Shape.ROCK;
                case SCISSORS -> Shape.PAPER;
                default -> Shape.NONE;
            };
            default -> opp;
        };
    }

    public int score() {
        return yours.getScoreValue() + outcomeScore();
    }

    private int outcomeScore() {
        if (yours == opponent) {
            return 3;
        } else {
            return switch (yours) {
                case ROCK -> (opponent == Shape.PAPER) ? 0 : 6;
                case PAPER -> (opponent == Shape.SCISSORS) ? 0 : 6;
                case SCISSORS -> (opponent == Shape.ROCK) ? 0 : 6;
                default -> 9999999;
            };
        }
    }

    private Shape getShape(String shape) {
        return switch (shape) {
            case "A" -> Shape.ROCK;
            case "B" -> Shape.PAPER;
            case "C" -> Shape.SCISSORS;
            default -> Shape.NONE;
        };
    }
}

enum Shape {
    NONE(0),
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    private final int scoreValue;

    Shape(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public int getScoreValue() {
        return scoreValue;
    }

}
