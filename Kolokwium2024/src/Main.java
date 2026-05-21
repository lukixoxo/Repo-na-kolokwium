import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Map<String, City> cities = City.parseFile("strefy.csv");
        City lublin = cities.get("Lublin");
        AnalogClock clock = new AnalogClock( lublin);
        clock.setCurrentTime();
        LocalTime time2;
        time2 = lublin.localMeanTime(clock.clockStatus);
        System.out.println(time2);

        Map<String, City> miasta = City.parseFile("strefy.csv");
        List<City> lista = new ArrayList<>(miasta.values());
        lista.sort(new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return City.worstTimezoneFit(o1, o2);
            }
        });
        AnalogClock zegar = new AnalogClock(lublin);
        zegar.setTime(23,43,32);
        List<City> citiesList = new ArrayList<>(miasta.values());
        City.generateAnalogClocksSvg(citiesList, zegar);


    }
}