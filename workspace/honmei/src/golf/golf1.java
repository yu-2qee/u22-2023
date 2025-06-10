package golf;

import java.util.Scanner;

public class golf1 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            score scoreManager = new score();  
                        
            while (!scoreManager.isFinished()) {
                System.out.println("スコアを入力してください：");

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("入力がありません。再度入力してください。");
                    continue;
                }

                if (!nyuuryoku.isValidFormat(input)) {
                    System.out.println("無効な入力です。数字、カンマ、半角スペースのみを入力してください。");
                    continue;
                }

                int[] scores = nyuuryoku.parseScores(input);
                if (scores == null) {
                    System.out.println("0以下の数値が含まれています。再度入力してください。");
                    continue;
                }

                scoreManager.addScores(scores);
                System.out.println(scoreManager.getStatus());
            }

            System.out.println("全ホール終了しました！");
        }
    }
}
