package golf2;

public class player {
    private final String name;
    private final int[] score;  
    private int totalScore = 0;

    private static final int[] PAR_VALUES = {
        4, 4, 3, 4, 5, 4, 5, 3, 4,
        4, 3, 4, 5, 4, 3, 4, 5, 4
    };

    public player(String name, int[] score) {
        this.name = name;
        this.score = score;
        calculateScore();
        
    }

    private void calculateScore() {
        for (int i = 0; i < 18; i++) {
            totalScore += score[i] - PAR_VALUES[i];
        }
    }

    public String getName() {
        return name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getFormattedScore() {
        return name + ": " + (totalScore > 0 ? "+" : "") + totalScore;
    }
}
