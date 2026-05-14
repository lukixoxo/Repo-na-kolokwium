import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DeathCauseStatisticList {
    ArrayList<DeathCauseStatistic> list = new ArrayList<>();

public void repopulate(String path){
    list.clear();

    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        String line;
        br.readLine();
        br.readLine();
        while ((line = br.readLine()) != null) {
            list.add(DeathCauseStatistic.fromCsvLine(line));
        }
    } catch (IOException e){
        System.out.println("Błąd odczytu "+ e.getMessage());
    }
}
public List<DeathCauseStatistic> mostDeadlyDiseases (int age, int number){
    List<DeathCauseStatistic> result = new ArrayList<>(list);

    result.sort(new Comparator<DeathCauseStatistic>() {
        @Override
        public int compare(DeathCauseStatistic o1, DeathCauseStatistic o2) {
            int zgony1 = o1.getDeathBracket(age).deathCount;
            int zgony2 = o2.getDeathBracket(age).deathCount;

            return Integer.compare(zgony2,zgony1);
        }
    });
    if(number > result.size()){
        number = result.size();
    }
    return result.subList(0,number);

}

}
//alusia