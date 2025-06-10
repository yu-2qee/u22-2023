//a,x,b,4, 4, 3, 4, 5, 4, 4, 3, 4, 4, 3, 4, 5, 4, 3, 4, 5, 4 ,4, 4, 3, 4, 5, 4, 4, 3, 4, 4, 3, 4, 5, 4, 3, 4, 5, 4
package golf2;

import java.util.*;

public class golf {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("プレイヤー2人の名前と各18ホールのスコア（合計38項目）をカンマ区切りで入力してください：");

            while (true) {
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("入力がありません。再度入力してください。");
                    continue;
                }

                // Validate input with a regex: Only allow letters, numbers, commas, and spaces
                if (!input.matches("[a-zA-Z0-9 ,]+")) {
                    System.out.println("無効な入力です。英数字、カンマ、半角スペースのみを入力してください。");
                    continue;
                }

                String[] parts = input.split("[ ,]+");

                if (parts.length != 38) {
                    System.out.println("入力は名前2つ+スコア36個、合計38個必要です。再度入力してください。");
                    continue;
                }

                String name1 = parts[0];
                String name2 = parts[1];

                try {
                    int[] allScores = new int[36];
                    for (int i = 0; i < 36; i++) {
                        int score = Integer.parseInt(parts[i + 2]);
                        if (score <= 0) {
                            throw new NumberFormatException();
                        }
                        allScores[i] = score;
                    }

                    int[] scores1 = new int[18];
                    int[] scores2 = new int[18];
                    System.arraycopy(allScores, 0, scores1, 0, 18);
                    System.arraycopy(allScores, 18, scores2, 0, 18);

                    player player1 = new player(name1, scores1);
                    player player2 = new player(name2, scores2);

                    System.out.println(player1.getFormattedScore());
                    System.out.println(player2.getFormattedScore());

                    if (player1.getTotalScore() < player2.getTotalScore()) {
                        System.out.println("勝者は " + player1.getName() + " さんです！");
                    } else if (player1.getTotalScore() > player2.getTotalScore()) {
                        System.out.println("勝者は " + player2.getName() + " さんです！");
                    } else {
                        System.out.println("引き分けです！");
                    }

                    break;
                } catch (NumberFormatException e) {
                    System.out.println("スコアに0以下や無効な数字が含まれています。再度入力してください。");
                }
            }
        }
    }
}
