import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AnalogClock extends Clock{

    private final List<ClockHand> hands = new ArrayList<>();

    public AnalogClock(City city) {
        super(city);
        hands.add(new SecondHand());
        hands.add(new MinuteHand());
        hands.add(new HourHand());
    }

    public void toSvg(String path ) {

        City currentCity = this.getCity();
        LocalTime time = currentCity.localMeanTime(this.clockStatus);
            hands.get(0).setTime(time);
            hands.get(1).setTime(time);
            hands.get(2).setTime(time);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
            bw.write("<svg width=\"200\" height=\"200\" viewBox=\"-100 -100 200 200\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "  <!-- Tarcza zegara -->\n" +
                    "  <circle cx=\"0\" cy=\"0\" r=\"90\" fill=\"none\" stroke=\"black\" stroke-width=\"2\" />\n" +
                    "  <g text-anchor=\"middle\">\n" +
                    "    <text x=\"0\" y=\"-80\" dy=\"6\">12</text>\n" +
                    "    <text x=\"80\" y=\"0\" dy=\"4\">3</text>\n" +
                    "    <text x=\"0\" y=\"80\" dy=\"6\">6</text>\n" +
                    "    <text x=\"-80\" y=\"0\" dy=\"4\">9</text>\n" +
                    "  </g>\n" +
                    hands.get(0).toSvg() +
                    hands.get(1).toSvg() +
                    hands.get(2).toSvg() +

                    "</svg>");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }



}
