import java.time.format.DateTimeFormatter;

public class DigitalClock extends Clock{
    public enum clockFormat{
        FORMAT_12h,
        FORMAT_24h
    }
    public clockFormat format;

    public DigitalClock(clockFormat format, City city) {
        this.format = format;
        super(city);
    }

    public String toString(){
        if(format == clockFormat.FORMAT_24h){
            return super.toString();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a");
            return clockStatus.format(formatter);
        }
    }
}
