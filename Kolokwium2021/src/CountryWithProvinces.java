import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CountryWithProvinces extends Country{
    private Country[] provinces;

    public CountryWithProvinces(String name, Country[] provinces) {
        super(name);
        this.provinces = provinces;
    }

    @Override
    public List<LocalDate> getDates() {
        return provinces[0].getDates();
    }

    @Override
    public int getConfirmedCases(LocalDate date) {
        int sum = 0;
        for(int i = 0; i < provinces.length; i++){  
            sum += provinces[i].getConfirmedCases(date);
            }
        return sum;
    }

    @Override
    public int getDeaths(LocalDate date) {
        int sum = 0;
        for(int i = 0; i < provinces.length; i++){
            sum += provinces[i].getDeaths(date);
        }
        return sum;
    }
    }



