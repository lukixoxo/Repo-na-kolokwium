import java.util.ArrayList;
import java.util.List;

public class Land extends Polygon{
    private List<City> cityList = new ArrayList<>();

    public Land(List<Point> pointList) {
        super(pointList);
    }

    public void addCity(City city){
        if(this.inside(city.center) == true){
            cityList.add(city);
            city.portCheck(this);
        } else {
            throw new RuntimeException(city.getName()+" nie znajduje sie na lądzie");
        }
    }



}
