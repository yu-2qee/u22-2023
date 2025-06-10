package mondai;

import java.util.Scanner;

public class golf{
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] parValues = {4, 4, 3, 4, 5, 4, 5, 3, 4, 4, 3, 4, 5, 4, 3, 4, 5, 4};
            int score = 0;
            int holeCount = 0;
            
            while (true) {
                System.out.println("スコアを入力してください");
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.println("入力がありません。再度度入力してください�?");
                    continue;
                }
                
                if (!input.matches("[0-9, ]*")) {
                    System.out.println("無効な数字字が入力されました。数字、カンマ、半角スペースのみを入力してください。");
                    continue;
                }
                
                String[] parts = input.split("[,\\s]+");
                int[] scores = new int[parts.length];
                
                boolean invalidInput = false;
                for (int i = 0; i < parts.length; i++) {
                    try {
                        scores[i] = Integer.parseInt(parts[i]);
                        if (scores[i] <= 0) {
                            invalidInput = true;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        invalidInput = true;
                        break;
                    }
                }
                
                if (invalidInput) {
                    System.out.println("0以下の数値が含まれています。再度度入力してください�?");
                    continue;
                }
                
                for (int i = 0; i < scores.length && i < 18; i++) {
                    score += scores[i] - parValues[i];
                    holeCount++;
                }
                
                System.out.println(holeCount + "ホ―ル終了して" + (score > 0 ? "+" : "") +score);
                
                if (holeCount >= 18) {
                    break;
                }
            }
        }
    }
}
