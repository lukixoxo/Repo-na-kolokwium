import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular{
Map<String , String> codeFinder = new HashMap<>();

    public ICDCodeTabularOptimizedForTime(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = br.readLine()) != null){
                String[] elements = line.split(" ", 2);
                codeFinder.put(elements[0], elements[1]);
            }
        } catch (IOException e){
            System.out.println("Błąd odczytu "+e.getMessage());
        }
    }


    @Override
    public String getDescription(String code) {
        String description = codeFinder.get(code);
        if(description == null){
            throw new IndexOutOfBoundsException();
        }
        return description;
    }
}