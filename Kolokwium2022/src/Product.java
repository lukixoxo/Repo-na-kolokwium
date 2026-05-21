import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class Product {
    private String name;
    private static List<Product> productList = new ArrayList<>();

    public static void printAllProducts() {
        System.out.println("--- LISTA PRODUKTÓW ---");
        for (Product p : productList) {
            System.out.println(p.getName()); // Używamy getName(), żeby zobaczyć tekst, a nie adres w pamięci
        }
        System.out.println("Łączna liczba wczytanych produktów: " + productList.size());
    }

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void clearProducts(){
        productList.clear();
    }

    public static void addProducts(Function<Path, ? extends Product> toCsv, Path path){
        productList.add(toCsv.apply(path));
    }

    public static Product getProducts(String namePrefix) {
        int counter = 0;
        List<String> nameList = new ArrayList<>();
        Product product = null;
        for(int i =0; i< productList.size();i++){
            if(productList.get(i).getName().startsWith(namePrefix)) {
                counter++;
                nameList.add(productList.get(i).getName());
                product = productList.get(i);
            }
        }
        if(counter == 0)
            throw new IndexOutOfBoundsException(namePrefix +" nie wskazuje na zaden obiekt");
        if(counter > 1)
            throw  new AmbigiousProductException(nameList);

        return product;
    }



    public abstract double getPrice(int year, int month);

}
