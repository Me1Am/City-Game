import java.io.IOException;
import java.lang.reflect.Field;

public class Scenario extends City{
    String scenarioType;
    String scenarioAnswerType;
    String scenarioIntro;
    String scenarioText;
    String scenarioOutComeTextY;
    String scenarioOutComeTextN;
    String scenarioResultY;
    String scenarioResultN;
    int iteration;
    
    public Scenario() throws IOException {
        super("empty");
        iteration = 0;

    }

    static void start() throws IOException, IllegalArgumentException, IllegalAccessException {
        /*First Tutorial Scenario*/
        String[] yResults = {"1000", "money", "-10", "satisfaction"};
        String[] nResults = {"-1000", "money", "10", "satisfaction"};
        
        System.out.println("Hello! This is your city, do what you want. Just make sure everything stays in the green!");
        System.out.println("Lets start easy, just decide if taxes should be risen or not:");
        
        App.parseInput();
        parse("y/n", yResults, nResults);
        System.out.println("Great job! Here's the new stats:");
        System.out.println(App.city.getCityStatsHeaders());

    }

    static void parse(String type, String[] yResults, String[] nResults) throws IOException {
        int yResultLength = yResults.length;    
        int nResultLength = nResults.length;

        /*Result syntax*/
            //Even indexes are the reward
            //Odd indexes are the variable name

        while(true){
            /*Yes-No Option*/
            if(type.equals("y/n")) {
                if(mostRecentInput.equals("yes")){
                    for(int i = 0; i < yResultLength; i += 2){
                        App.city.changeVar(Integer.parseInt(yResults[i]), yResults[i + 1]);
                    }
                    break;
                } else {
                    if(mostRecentInput.equals("no")){
                        for(int i = 0; i < nResultLength; i += 2){
                            App.city.changeVar(Integer.parseInt(nResults[i]), nResults[i + 1]);
                        }
                        break;
                    } else {
                        System.out.println("A 'yes' or 'no' will do");
                        App.getInput();
                    }
                }
            }
        }
        App.city.checkVar();
    
    }

    void loadScenarioData(int scenarioNum) throws IllegalArgumentException, IllegalAccessException, IOException {
        for (Field field : App.scenario.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            if(name.contains("scenario")){            
                String fieldName = name.replace("scenario", "scenario" + scenarioNum); //Set the scenario number
                field.set(App.scenario, FileHandeler.getPropertyData("data/data.properties", fieldName));
            } 
        }

    }

    void loadScenario(String type, String answerType, int scenarioNum) throws IllegalArgumentException, IllegalAccessException, IOException{
        /*Load Scenario Data*/
            scenarioType = FileHandeler.getPropertyData("data/data.properties", type);
            scenarioAnswerType= FileHandeler.getPropertyData("data/data.properties", answerType);

        /*Final Steps*/
        System.out.println("Here's the new stats:");
        System.out.println(App.city.getCityStatsHeaders());

    }

}
