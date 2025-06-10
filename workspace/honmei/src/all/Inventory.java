package all;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<String, Integer> stock = new HashMap<>();

    public void addDrink(String name, int quantity) {
        stock.put(name, stock.getOrDefault(name, 0) + quantity);
    }

    public boolean isAvailable(String name) {
        return stock.getOrDefault(name, 0) > 0;
    }

    public void reduceStock(String name) {
        if (isAvailable(name)) {
            stock.put(name, stock.get(name) - 1);
        }
    }

    public int getStock(String name) {
        return stock.getOrDefault(name, 0);
    }

    public Map<String, Integer> getAllStock() {
        return stock;
    }
}
