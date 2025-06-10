package golf;

public class score {
    private static final int[] PAR_VALUES = {
        4, 4, 3, 4, 5, 4, 5, 3, 4,
        4, 3, 4, 5, 4, 3, 4, 5, 4
    };

    private int score = 0;
    private int holeCount = 0;

    public void addScores(int[] scores) {
        for (int i = 0; i < scores.length && holeCount < 18; i++) {
            score += scores[i] - PAR_VALUES[holeCount];
            holeCount++;
        }
    }

    public boolean isFinished() {
        return holeCount >= 18;
    }

    public String getStatus() {
        String prefix = score > 0 ? "+" : "";
        return holeCount + "ホール終了して " + prefix + score;
    }
}
