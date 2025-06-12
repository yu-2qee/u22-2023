// 前提として、預金は五万、パスワードは0906としています*/
package bank2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class bank2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int menu = 0;
        int kingaku = 0;
        int yokin = 50000;
        int output = 0;
        String pass = "0906";
        String Possibility = null;
        boolean cardcheck = false;
        boolean passcheck = false;

        while (true) {  //ATMの動作開始
            System.out.println("預け入れ：1");
            System.out.println("引き出し：2");
            System.out.println("");
            System.out.print("選択してください：");

            menu = scanner.nextInt();

            if (menu <= 0 || menu >= 3) {  
                System.out.println("正しい数値が入力されていません。");
                System.out.println("-----------------------------------------------------");
                continue;
            } else {
                System.out.println("-----------------------------------------------------");
                break;
            }
        }

        switch (menu) {
            case 1:  //預け入れ
                cardcheck = cardcheck();
                if (cardcheck) {
                    passcheck = passcheck(pass);
                }

                while (cardcheck && passcheck) {
                    System.out.println("現在の預金は" + yokin + "円です。");
                    System.out.print("お金を投入してください：");
                    kingaku = scanner.nextInt();
                    yokin += kingaku;
                    System.out.println(kingaku + "円預け入れます。");
                    System.out.println("現在の預金は" + yokin + "円です。");
                    System.out.println("-----------------------------------------------------");
                    break;
                }
                break;

            case 2:  //引き出し
                cardcheck = cardcheck();
                if (cardcheck) {
                    passcheck = passcheck(pass);
                }

                while (cardcheck && passcheck) {
                    System.out.println("現在の預金は" + yokin + "円です。");
                    System.out.print("引き出す金額を入力してください：");
                    kingaku = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("引きおろし金額は" + kingaku + "円でよろしいですか？(y/n)：");
                    Possibility = scanner.nextLine();

                    if (Possibility.equals("y")) {
                        if (kingaku > yokin) {
                            System.out.println("預金残高が足りません。");
                            break;
                        }
                        yokin -= kingaku;
                        while (true) {
                            System.out.print("現金をお取り下さい(とった場合1を入力)：");
                            output = scanner.nextInt();
                            if (output == 1) {
                                break;
                            } else {
                                System.out.println("現金が残っています。");
                            }
                        }
                        System.out.println(kingaku + "円引き下ろしして現在の預金は" + yokin + "円です。");
                        System.out.println("-----------------------------------------------------");
                        break;
                    } else if (Possibility.equals("n")) {
                        System.out.println("もう一度金額を入力してください。");
                        System.out.println("-----------------------------------------------------");
                    }
                }
                break;
        }

        while (cardcheck) {
            System.out.print("カードをお取りください(とった場合1を入力)：");
            output = scanner.nextInt();
            if (output == 1) {
                System.out.println("ご利用ありがとうございました。");
                System.out.println("-----------------------------------------------------");
                cardcheck = false;
            } else {
                System.out.println("カードが残っています。");
            }
        }

        exportYokinToFile(yokin);
    }

    public static boolean cardcheck() {
        Scanner scanner = new Scanner(System.in);
        boolean card = false;
        int cardinput;

        while (true) {
            System.out.print("カードを入れてください(入れる場合1を入力)：");
            cardinput = scanner.nextInt();
            if (cardinput != 1) {
                System.out.println("カードが正しく入っていません。");
                System.out.println("-----------------------------------------------------");
                break;
            } else {
                card = true;
                System.out.println("カードが認識されました。");
                System.out.println("-----------------------------------------------------");
                break;
            }
        }
        return card;
    }

    public static boolean passcheck(String pass) {
        Scanner scanner = new Scanner(System.in);
        String inputpass;

        while (true) {
            System.out.print("暗証番号を入力してください：");
            inputpass = scanner.nextLine();
            if (!pass.equals(inputpass)) {
                System.out.println("暗証番号が間違っています。");
                System.out.println("-----------------------------------------------------");
            } else {
                System.out.println("認証に成功しました。");
                System.out.println("-----------------------------------------------------");
                return true;
            }
        }
    }

    public static void exportYokinToFile(int yokin) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("yokin_log.txt"))) {
            pw.println("最終預金額: " + yokin + "円");
            System.out.println("預金額をファイルに保存しました。(yokin_log.txt)");
        } catch (IOException e) {
            System.out.println("ファイル出力に失敗しました: " + e.getMessage());
        }
    }
}
