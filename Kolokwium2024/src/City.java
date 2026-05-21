import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class City {
    String capital;
    int timeZone;
    String width;
    String lenght;

    public City(){}



    public City(String capital, int timeZone, String width, String lenght) {
        this.capital = capital;
        this.timeZone = timeZone;
        this.width = width;
        this.lenght = lenght;
    }

    public String getCapital() {
        return capital;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public String getWidth() {
        return width;
    }

    public String getLenght() {
        return lenght;
    }

    private static City parseLine(String line){
        String[] elements = line.split(",");
        City city = new City();
        city.capital = elements[0];
        city.timeZone = Integer.parseInt(elements[1]);
        city.width = elements[2];
        city.lenght = elements[3];
        return city;
    }

    public static Map<String,City> parseFile(String path){
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            Map<String,City> map = new HashMap<>();
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                City city = parseLine(line);
                map.put(city.capital, city);
            }
            return map;
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public LocalTime localMeanTime(LocalTime time){
        this.lenght = this.lenght.trim();
        String[] elements = this.lenght.split(" ");
        double len = 0;
        if(elements[1].equals("W")){
            len = Double.parseDouble(elements[0]) * (-1);
        }
        else {
            len = Double.parseDouble(elements[0]);
        }
        int seconds = (int) (len * 240);
        int secondsZone = this.timeZone * 3600;
        LocalTime value = time.plusSeconds(seconds - secondsZone);
        return value;
    }

    public static int worstTimezoneFit(City city1, City city2){
        LocalTime time = LocalTime.of(12,00,00);
        LocalTime local1  = city1.localMeanTime(time);
        LocalTime local2  = city2.localMeanTime(time);

        long seconds1 = Math.abs((Duration.between(time, local1)).getSeconds());
        long seconds2 = Math.abs((Duration.between(time, local2)).getSeconds());

        return Long.compare(seconds2, seconds1);
    }

    public static void generateAnalogClocksSvg(List<City> cities, AnalogClock analogClock){
        String dirName = analogClock.toString();
        dirName = dirName.replace(":","-");
        File directory = new File(dirName);
        try{
            if(!directory.mkdir() && !directory.exists()){
                throw new IOException("Nie mozna utworzyc katalogu");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        for(int i = 0; i < cities.size();i++){
            analogClock.setCity(cities.get(i));
            analogClock.toSvg( dirName + File.separator + cities.get(i).capital+".svg");
        }
    }


}
