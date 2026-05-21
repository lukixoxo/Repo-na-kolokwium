import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

public abstract class Clock {
LocalTime clockStatus;
private City city;


    public City getCity() {
        return city;
    }

    public Clock() {
    }

    public Clock(City city) {
        this.city = city;
    }

    public void setCurrentTime(){
        this.clockStatus = LocalTime.now();
    }
    public void setTime(int hour,int minute,int second){
        if(hour > 23 || hour < 0){
            throw new IllegalArgumentException("Podano błędną godzinę");
        }
        if(minute > 59 || minute < 0){
            throw new IllegalArgumentException("Podano błędną minutę");
        }
        if(second > 59 || second < 0){
            throw new IllegalArgumentException("Podano błędną sekundę");
        }
        clockStatus = LocalTime.of(hour,minute,second);
    }
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return clockStatus.format(formatter);
    }

    public void setCity(City newCity){
        int diff = newCity.timeZone - city.timeZone;
        this.clockStatus = this.clockStatus.plusHours(diff);
        this.city = newCity;
    }

}
