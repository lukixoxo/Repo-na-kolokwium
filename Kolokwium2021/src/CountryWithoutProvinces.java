import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CountryWithoutProvinces extends Country{
    ArrayList<DateCasesDeaths> dateStats = new ArrayList<>();

    public CountryWithoutProvinces(String name) {
        super(name);
    }

    @Override
    public List<LocalDate> getDates() {
        ArrayList<LocalDate> dates = new ArrayList<>();
        for(int i = 0; i < dateStats.size(); i++){
            dates.add(dateStats.get(i).date);
        }
        return dates;
    }

    @Override
    public int getConfirmedCases(LocalDate date) {
        for(int i = 0;i < dateStats.size();i++){
            if(date.equals(dateStats.get(i).date )){
                return dateStats.get(i).cases;
            }
        }
        return 0;
    }

    @Override
    public int getDeaths(LocalDate date) {
        for(int i =0 ;i<dateStats.size();i++){
            if(date.equals(dateStats.get(i).date)){
                return dateStats.get(i).deaths;
            }
        }

        return 0;
    }

    private static class DateCasesDeaths{
        LocalDate date;
        int cases;
        int deaths;

        public DateCasesDeaths(LocalDate date, int cases, int deaths) {
            this.date = date;
            this.cases = cases;
            this.deaths = deaths;
        }
    }

    public void addDailyStatistic(LocalDate date, int cases, int deaths){
        DateCasesDeaths stats = new DateCasesDeaths(date,cases,deaths);
        this.dateStats.add(stats);
    }



}
