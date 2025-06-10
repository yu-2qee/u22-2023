package all;

import java.util.Arrays;
import java.util.List;

public class MoneyHandler {
    private static final List<Integer> VALID_COINS = Arrays.asList(10, 50, 100, 500);
    private static final List<Integer> VALID_BILLS = Arrays.asList(1000);
    private static final int MAX_AMOUNT = 1990;

    private int insertedMoney = 0;
    private int sales = 0;

    public boolean insert(int amount) {
        if (!VALID_COINS.contains(amount) && !VALID_BILLS.contains(amount)) {
            System.out.println("使用できない硬貨または紙幣です。");
            return false;
        }
        if (insertedMoney + amount > MAX_AMOUNT) {
            System.out.println("投入可能な金額の上限を超えています。");
            return false;
        }
        insertedMoney += amount;
        return true;
    }

    public boolean canAfford(int price) {
        return insertedMoney >= price;
    }

    public int getInsertedMoney() {
        return insertedMoney;
    }

    public int buy(int price) {
        if (!canAfford(price)) return -1;
        insertedMoney -= price;
        sales += price;
        return price;
    }

    public int refund() {
        int refund = insertedMoney;
        insertedMoney = 0;
        return refund;
    }

    public int getSales() {
        return sales;
    }
}
