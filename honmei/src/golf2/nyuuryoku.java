package golf2;
public class nyuuryoku {
    public static boolean isValidFormat(String input) {
        return input.matches("^[0-9 ,]+$");
    }

    public static int[] parseScores(String input) {
        try {
            String[] parts = input.split("[ ,]+");
            int[] scores = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                scores[i] = Integer.parseInt(parts[i]);
                if (scores[i] <= 0) {
                    return null;
                }
            }
            return scores;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
