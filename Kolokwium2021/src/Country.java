import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Country {
    private final String name;
    private static String casesFile;
    private static String deathsFile;

    public String getName() {
        return name;
    }

    public Country(String name) {
        this.name = name;
    }


    public static void setFiles (String casesFile, String deathsFile) throws FileNotFoundException{
        Country.casesFile = casesFile;
        Country.deathsFile = deathsFile;

        File f1 = new File(casesFile);
        File f2 = new File(deathsFile);
        if(!f1.exists() || !f1.canRead()){
            throw new FileNotFoundException(casesFile);
        }
        if(!f2.exists() || !f2.canRead()){
            throw new FileNotFoundException(deathsFile);
        }
    }
    public static Country fromCsv(String countryName) throws CountryNotFoundException{
        String lineCases;
        String lineDeaths;
        Country country = null;
        try(BufferedReader brCases = new BufferedReader(new FileReader(casesFile))){
            try(BufferedReader brDeaths = new BufferedReader(new FileReader(deathsFile))){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
            lineCases = brCases.readLine();
            lineDeaths = brDeaths.readLine();
            CountryColumns columns = getCountryColumns(lineCases, countryName);
            if(columns.columnCount == 1){
                country = new CountryWithoutProvinces(countryName);
                brCases.readLine();
                brDeaths.readLine();

                while((lineCases = brCases.readLine()) != null){
                    lineDeaths = brDeaths.readLine();
                    String[] partsCases = lineCases.split(";");
                    String[] partsDeaths = lineDeaths.split(";");
                    LocalDate date = LocalDate.parse(partsCases[0], formatter);
                    int cases = Integer.parseInt(partsCases[columns.firstColumnIndex]);
                    int deaths = Integer.parseInt(partsDeaths[columns.firstColumnIndex]);
                    ((CountryWithoutProvinces) country).addDailyStatistic(date, cases, deaths);
                }
            } else {
                lineCases = brCases.readLine();
                lineDeaths = brDeaths.readLine();
                String[] allProvinces = lineCases.split(";");
                Country[] provinces = new Country[columns.columnCount];
                for(int i = 0;i < provinces.length; i++){
                    provinces[i] = new CountryWithoutProvinces(allProvinces[columns.firstColumnIndex + i]);
                }
                while((lineCases = brCases.readLine()) != null ){
                    lineDeaths = brDeaths.readLine();
                    String[] parts = lineCases.split(";");
                    String[] partsDeaths = lineDeaths.split(";");
                    LocalDate date = LocalDate.parse(parts[0], formatter);
                    int cases = 0;
                    for(int i = 0;i < provinces.length;i++){
                        cases =  Integer.parseInt(parts[columns.firstColumnIndex + i]) ;
                        int deaths = Integer.parseInt(partsDeaths[columns.firstColumnIndex + i]);
                        ((CountryWithoutProvinces)provinces[i]).addDailyStatistic(date,cases,deaths);
                    }

                }
                country = new CountryWithProvinces(countryName,provinces);
            }
        }} catch (IOException e){
            System.out.println("Błąd odczytu "+ e.getMessage());
        }
        return country;
        }

    private static class CountryColumns{
        public final int firstColumnIndex;
        public final int columnCount;

        public CountryColumns(int firstColumnIndex, int columnCount) {
            this.firstColumnIndex = firstColumnIndex;
            this.columnCount = columnCount;
        }
    }

    private static CountryColumns getCountryColumns(String firstLine, String country) throws CountryNotFoundException{
        String columns[] = firstLine.split(";");
        int counter = 0;
        int index = -1;
        for (int i = 0;i < columns.length; i ++){
            if(columns[i].equals(country)){
                counter++;
                index = i;
            }
        }

        if(index == -1){
            throw (new CountryNotFoundException(country));
        }

        index = index-(counter-1);
        return  new CountryColumns(index,counter);
    }

    public static Country[] fromCsv(String[] countryNames){
        ArrayList<Country> foundCountries = new ArrayList<>();
        for(int i =0; i<countryNames.length;i++){
            try{
                Country wynik = fromCsv(countryNames[i]);
                foundCountries.add(wynik);
            } catch (CountryNotFoundException e){
                System.out.println(e.getMessage());
            }
        }
        return foundCountries.toArray(new Country[0]);
    }

    public int deathsInRange(LocalDate start, LocalDate end){
        int deathsTillStart = 0;
        while (!start.isAfter(end)){
            deathsTillStart += getDeaths(start);
            start = start.plusDays(1);
        }
        return deathsTillStart;
    }

    public static void sortByDeaths(List<Country> countryList, LocalDate startDate, LocalDate endDate){
        countryList.sort(new Comparator<Country>() {
            @Override

            public int compare(Country o1, Country o2) {
                int deaths1 = o1.deathsInRange(startDate,endDate);
                int deaths2 = o2.deathsInRange(startDate,endDate);

                return Integer.compare(deaths2,deaths1);
            }
        });

    }

    public void saveToDataFile(String path){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yy");
            List<LocalDate> dateList = getDates();
            for(int i = 0; i < dateList.size();i++){
                bw.write(dateList.get(i).format(formatter) + "\t" + getConfirmedCases(dateList.get(i)) + "\t" + getDeaths(dateList.get(i)));
                bw.newLine();
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public abstract List<LocalDate> getDates();
    public abstract int getConfirmedCases(LocalDate date);
    public abstract int getDeaths(LocalDate date);
    }




