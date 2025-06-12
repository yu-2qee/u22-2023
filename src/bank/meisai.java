package bank;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class meisai {

    private static final String balance_file = "balance.txt";
    private static final String logmesse = "yokin_log.txt";

    public static int loadBalance() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(balance_file ), "UTF-8"))) {
            String line = br.readLine();
            if (line != null) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("残高読み込みに失敗。初期値50000円を使用します。");
        }
        return 50000;
    }

    public static void saveBalance(int balance) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(balance_file ), "UTF-8"))) {
            pw.println(balance);
        } catch (IOException e) {
            System.out.println("残高保存に失敗: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void logTransaction(String action, int amount, int balance) {
        String timeStamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String log = String.format(
            "--- 取引日時: %s ---%n" +
            "取引種別 : %s%n" +
            "取引金額 : %d 円%n" +
            "取引後残高 : %d 円%n" +
            "取引結果 : 成功%n" +
            "-------------------------------%n",
            timeStamp, action, amount, balance
        );

        try {
//            System.out.println("カレントディレクトリ: " + new File(".").getAbsolutePath());
            try (PrintWriter pw = new PrintWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(logmesse, true)))) {
                pw.print(log);
            }
        } catch (IOException e) {
            System.out.println("ログ保存に失敗: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
