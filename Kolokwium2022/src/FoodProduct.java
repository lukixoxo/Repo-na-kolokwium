import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodProduct extends Product{
     String name;
     String[] provinces;
     Map<String, Double[]> regionPrices;

    public FoodProduct(String name, String[] provinces, Map<String, Double[]> regionPrices) {
        super(name);
        this.provinces = provinces;
        this.regionPrices = regionPrices;
    }

    public static FoodProduct fromCsv(Path path){
         Map<String, Double[]> regionPrices = new HashMap<>();
         List<String > provinces = new ArrayList<>();
         String name;
         try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))){
             String line = br.readLine();
             name = line;
             br.readLine();
             int index = 0;
             while ((line = br.readLine()) != null){
                 List<Double> prices = new ArrayList<>();
                 String[] elements = line.split(";");
                 for(int i = 1;i < elements.length;i++){
                     prices.add(Double.parseDouble(elements[i].replace(',', '.')));
                 }
                 Double[] doubleArray = prices.toArray(new Double[0]);
                 provinces.add(elements[0]);
                 regionPrices.put(elements[0], doubleArray);
             }
             String[] provincesArray = provinces.toArray(new String[0]);
             return new FoodProduct(name,provincesArray , regionPrices);

         } catch (Exception e) {
             throw new RuntimeException(e);
         }
     }


    @Override
    public double getPrice(int year, int month) {
        double sum = 0;
        for(int i =0 ; i< provinces.length;i++){
            sum += getPrice(year, month, provinces[i]);
        }
        return (sum/ provinces.length);
    }

    public double getPrice(int year, int month, String province) {
        if(regionPrices.get(province) == null || year < 2010 || (year >= 2022 && month > 3)){
            throw new IndexOutOfBoundsException();
        }
        Double[] price = regionPrices.get(province);
        return price[(year-2010)*12 + month - 1];
    }
}
