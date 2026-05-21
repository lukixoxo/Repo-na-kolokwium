import java.time.LocalTime;

public class HourHand extends ClockHand{

    private double angle;

    @Override
    public void setTime(LocalTime time) {
        double rotation = ((time.getHour())%12)*30;
        rotation = rotation + time.getMinute()*0.5;
        rotation = rotation + time.getSecond()*0.00833333333;
        this.angle = rotation;
    }

    @Override
    public String toSvg() {
        return "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-50\" stroke=\"black\" stroke-width=\"3\" transform=\"rotate(" + this.angle + ")\" />";
    }
}
