import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    Map<Product, Integer> cart = new HashMap<>();

    public void addProduct(Product product, int amount){
        int currentAmount = cart.getOrDefault(product,0);
        cart.put(product,currentAmount + amount);
    }

    public double getPrice(int year, int month){
        double wartosc = 0;
        List<Product> productList = new ArrayList<>(cart.keySet());
        for(int i = 0;i < cart.size();i++){
            Product product = productList.get(i);
            int amount = cart.get(product);
            wartosc += product.getPrice(year,month) * amount;
        }
        return wartosc;
    }

    public double getInflation(int year1, int month1, int year2, int month2){
        double price1 = getPrice(year1,month1);
        double price2 = getPrice(year2,month2);
        int months = (year2 * 12 + month2) - (year1 * 12 + month1);
        return ((price2-price1)/price1*100/months*12);
    }
}
