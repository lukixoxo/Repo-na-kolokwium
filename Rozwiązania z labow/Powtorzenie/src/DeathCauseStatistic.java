public class DeathCauseStatistic {

    private String code;
    private int[] deathCount = new int[20];

    public DeathCauseStatistic(String code, int[] deathCount) {
        this.code = code;
        this.deathCount = deathCount;
    }

    public String getCode() {
        return code;
    }

    public static DeathCauseStatistic fromCsvLine(String line){
        String[] fragments = line.split(",");
        String cleanCode = fragments[0].trim();
        int[] deathCount = new int[20];
        for(int i = 2;i < fragments.length;i++){
            fragments[i] = fragments[i].replace("-","0");
            deathCount[i-2] = Integer.parseInt(fragments[i]);
        }
        return new DeathCauseStatistic(cleanCode, deathCount);
    }

    public AgeBracketDeaths getDeathBracket(int age){
        int groupIndex = 0;
        if(age < 100) {
            groupIndex = age / 5;
        } else {
            groupIndex = 19;
        }
        int deaths =  deathCount[groupIndex];
        return new AgeBracketDeaths(groupIndex*5,(groupIndex*5)+4, deaths);
    }

    public class AgeBracketDeaths{
        public final int young;
        public final int old;
        public final int deathCount;

        public AgeBracketDeaths(int young, int old, int deathCount) {
            this.young = young;
            this.old = old;
            this.deathCount = deathCount;
        }
    }
}
