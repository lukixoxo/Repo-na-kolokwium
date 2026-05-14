import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, CountryNotFoundException {
        Country.setFiles("confirmed_cases.csv", "deaths.csv");
        Country[] countries = Country.fromCsv(new String[]{"Poland","Australia","Albania"});
        List<Country> countryList = Arrays.asList(countries);
        LocalDate start = LocalDate.of(2020, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 28);
        Country.sortByDeaths(countryList, start, end);
        for(int i = 0;i<countryList.size();i++){
            System.out.println("Nazwa: "+countryList.get(i).getName()+"   Liczba śmierci: "+countryList.get(i).deathsInRange(start,end));
        }
        Country country = countryList.get(0);
        String fileName = "dane.txt";
        country.saveToDataFile(fileName);

    }
}