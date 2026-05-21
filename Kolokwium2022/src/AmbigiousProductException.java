import java.util.List;

public class AmbigiousProductException extends RuntimeException {
    public AmbigiousProductException(List<String> list) {
            for(int i = 0; i < list.size();i++){
                System.out.println(list.get(0));
            }
    }
}
