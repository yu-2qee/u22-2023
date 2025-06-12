package bank;

import java.util.Scanner;

public class ATM {

    private static final String PASSWORD = "0000";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int menu, amount;

        while (true) {
            kouza account = new kouza(meisai.loadBalance());

            System.out.println("--- ATM メニュー ---");
            System.out.println("1: 預け入れ");
            System.out.println("2: 引き出し");
            System.out.println("3: 残高照会"); // 残高照会メニューを追加
            System.out.print("選択：");
            menu = scanner.nextInt();

            if (menu < 1 || menu > 3) {
                System.out.println("無効な選択です。終了します。");
                break;
            }

            if (!insertCard(scanner)) break;
            if (!authenticate(scanner)) break;

            switch (menu) {
                case 1:
                    System.out.print("預け入れ金額/10億まで：");
                    amount = scanner.nextInt();
                    account.deposit(amount);
                    meisai.saveBalance(account.getBalance());
                    meisai.logTransaction("預け入れ", amount, account.getBalance());
                    System.out.println("預け入れ完了。現在の残高：" + account.getBalance() + "円");
                    break;

                case 2:
                    System.out.print("引き出し金額：");
                    amount = scanner.nextInt();
                    if (!account.withdraw(amount)) {
                        System.out.println("残高不足です。");
                        break;
                    }
                    meisai.saveBalance(account.getBalance());
                    meisai.logTransaction("引き出し", amount, account.getBalance());
                    System.out.println("引き出し完了。現金をお取りください。\n現在の残高：" + account.getBalance() + "円");
                    break;

                case 3:
                    // 残高照会
                    System.out.println("現在の残高：" + account.getBalance() + "円");
                    break;
            }

            ejectCard(scanner);

            System.out.print("もう一度取引しますか？（1: はい）：");
            int again = scanner.nextInt();
            if (again != 1) {
                System.out.println("ご利用ありがとうございました。");
                break;
            }

            System.out.println("--------------------------");
        }

        scanner.close();
    }

    private static boolean insertCard(Scanner scanner) {
        System.out.print("カードを挿入してください（1で挿入）：");
        return scanner.nextInt() == 1;
    }

    private static boolean authenticate(Scanner scanner) {
        scanner.nextLine(); 
        System.out.print("暗証番号を入力：");
        String input = scanner.nextLine();
        if (!PASSWORD.equals(input)) {
            System.out.println("暗証番号が違います。");
            return false;
        }
        return true;
    }

    private static void ejectCard(Scanner scanner) {
        System.out.print("カードを取り出してください（1で取り出し）：");
        if (scanner.nextInt() == 1) {
            System.out.println("ありがとうございました。");
        } else {
            System.out.println("カードが残っています。");
        }
    }
}
