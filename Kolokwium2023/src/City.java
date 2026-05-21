import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class City extends Polygon{
    public final Point center;
    private String name;
    private boolean port;
    Set<Resource.Type> resources = new HashSet<>();

    public String getName() {
        return name;
    }

    public City(Point center, String name, double wallLength) {
        super(List.of((new Point((center.x - wallLength/2), (center.y)+ wallLength/2)),
        (new Point((center.x + wallLength/2), (center.y)+ wallLength/2)),
        (new Point((center.x + wallLength/2), (center.y)- wallLength/2)),
        (new Point((center.x - wallLength/2), (center.y)- wallLength/2))));
        this.center = center;
        this.name = name;
    }

    public void portCheck(Land land){
        List<Point> wallCorners = this.getPointList();
        for(int i = 0;i<wallCorners.size();i++){
            if(!land.inside(wallCorners.get(i)) && land.inside(this.center)){
                port = true;
        }
        }
    }

    public void addResourcesInRange(List<Resource> resourceList, double range){
        if(this.port == true) {
            for (int i = 0; i < resourceList.size(); i++) {
                if (this.center.distance(resourceList.get(i).place) <= range) {
                    this.resources.add(resourceList.get(i).type);
                }
            }
        } else {
            for (int i = 0; i < resourceList.size(); i++) {
                if (this.center.distance(resourceList.get(i).place) <= range && !resourceList.get(i).type.equals(Resource.Type.Fish)) {
                    this.resources.add(resourceList.get(i).type);
                }
            }
        }
    }

}
