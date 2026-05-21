import java.time.LocalTime;

public class MinuteHand extends ClockHand{
    private double angle;

    @Override
    public void setTime(LocalTime time) {
        double rotation = time.getMinute()*6;
        rotation = rotation + time.getSecond()*0.1;
        this.angle = rotation;
    }

    @Override
    public String toSvg() {
        return "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-70\" stroke=\"black\" stroke-width=\"2\" transform=\"rotate(" + this.angle + ")\" />";
    }
}
