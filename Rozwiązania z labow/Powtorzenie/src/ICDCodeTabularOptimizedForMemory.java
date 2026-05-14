import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular{
    String path;

    public ICDCodeTabularOptimizedForMemory(String path) {
        this.path = path;
    }

    @Override
    public String getDescription(String code){
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while (((line = br.readLine()) != null)){
                String[] elements = line.split(" ",2);
                if(elements[0].equals(code)){
                    return elements[1];
                }
            }
        } catch (IOException e){
            System.out.println("Błąd odczytu  "+e.getMessage());
        }
        throw new IndexOutOfBoundsException();
    }
}
