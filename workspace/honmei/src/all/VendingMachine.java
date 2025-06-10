package all;


import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private Map<String, Drink> drinks = new HashMap<>();
    private Inventory inventory = new Inventory();
    private MoneyHandler moneyHandler = new MoneyHandler();

    public void registerDrink(String name, int price, int quantity) {
        drinks.put(name, new Drink(name, price));
        inventory.addDrink(name, quantity);
    }

    public void insertMoney(int amount) {
        moneyHandler.insert(amount);
        System.out.println("現在の投入金額: " + moneyHandler.getInsertedMoney() + "円");
    }

    public void showDrinks() {
        System.out.println("【商品一覧】");
        for (String name : drinks.keySet()) {
            Drink d = drinks.get(name);
            int stock = inventory.getStock(name);
            System.out.println("- " + name + " (" + d.getPrice() + "円) 在庫: " + stock + (stock == 0 ? " [売切]" : ""));
        }
    }

    public void buy(String name) {
        if (!drinks.containsKey(name)) {
            System.out.println("その商品は存在しません。");
            return;
        }
        if (!inventory.isAvailable(name)) {
            System.out.println("売り切れです。");
            return;
        }

        Drink d = drinks.get(name);
        if (!moneyHandler.canAfford(d.getPrice())) {
            System.out.println("お金が足りません。");
            return;
        }

        moneyHandler.buy(d.getPrice());
        inventory.reduceStock(name);
        System.out.println(name + " を購入しました。お釣り: " + moneyHandler.getInsertedMoney() + "円");
    }

    public void refund() {
        int refund = moneyHandler.refund();
        System.out.println("返金額: " + refund + "円");
    }

    public void showSales() {
        System.out.println("売上金額: " + moneyHandler.getSales() + "円");
    }

    public void showInsertedAmount() {
        System.out.println("投入金額: " + moneyHandler.getInsertedMoney() + "円");
    }
public void exportStatusToFile(String fileName) {
    try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
        pw.println("=== 自動販売機の状態 ===");

        pw.println("\n【商品一覧】");
        for (String name : drinks.keySet()) {
            Drink d = drinks.get(name);
            int stock = inventory.getStock(name);
            pw.println("- " + name + " (" + d.getPrice() + "円): " + stock + "本" + (stock == 0 ? " [売切]" : ""));
        }

        pw.println("\n【売上】");
        pw.println("売上金額: " + moneyHandler.getSales() + "円");

        pw.println("\n【投入金額】");
        pw.println("現在の投入金額: " + moneyHandler.getInsertedMoney() + "円");

        System.out.println("ファイル出力が完了しました: " + fileName);
    } catch (IOException e) {
        System.out.println("ファイル出力エラー: " + e.getMessage());
    }
}
}
    