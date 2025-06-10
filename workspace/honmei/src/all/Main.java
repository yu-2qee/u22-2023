package all;

public class Main {
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();


        vm.registerDrink("コーラ", 120, 5);
        vm.registerDrink("お茶", 110, 3);
        vm.registerDrink("水", 100, 4);
        vm.registerDrink("コーヒー", 130, 2);
        vm.registerDrink("スポーツドリンク", 150, 1);

        vm.showDrinks();
        vm.insertMoney(1000);
        vm.insertMoney(100);
        vm.buy("コーヒー");
        vm.showDrinks();
        vm.refund();
        vm.showSales();
    }
}
