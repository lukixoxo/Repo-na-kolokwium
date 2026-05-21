public class Resource {
    public enum Type{
        Coal,Wood,Fish
    }
    public final Point place;
    public final Type type;

    public Resource(Point place, Type type) {
        this.place = place;
        this.type = type;
    }


}
