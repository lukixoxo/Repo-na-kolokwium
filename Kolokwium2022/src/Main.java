import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path gazPath = Path.of("C:\\Users\\lukas\\IdeaProjects\\Kolokwium2022\\data\\nonfood\\gaz_ziemny.csv");
        Path burakiPath = Path.of("C:\\Users\\lukas\\IdeaProjects\\Kolokwium2022\\data\\food\\buraki.csv");
        Product gaz = NonFoodProduct.fromCsv(gazPath);
        FoodProduct buraki = FoodProduct.fromCsv(burakiPath);
        double price = buraki.getPrice(2010,4,"LUBELSKIE");
        System.out.println(price);
        double avgprice = buraki.getPrice(2010,4);
        System.out.println("srednia: "+avgprice);
        Path jajaPath = Path.of("C:\\Users\\lukas\\IdeaProjects\\Kolokwium2022\\data\\food\\jaja.csv");
        Product.addProducts(FoodProduct::fromCsv,jajaPath);
        Product.addProducts(NonFoodProduct::fromCsv,gazPath);
        Product.addProducts(FoodProduct::fromCsv,burakiPath);
        Path jablkaPath = Path.of("data\\food\\jablka.csv");
        Product.addProducts(FoodProduct::fromCsv,jablkaPath);

        Product.printAllProducts();

        Product x = Product.getProducts("Bu");
        System.out.println(x.getName());

    }
}