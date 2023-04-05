import java.io.IOException;
import java.lang.reflect.Field;

public class Scenario extends City{
    int iteration;
    String scenarioType;
    String scenarioAnswerType;
    String scenarioIntro;
    String scenarioText;
    String scenarioOutComeTextY;
    String scenarioOutComeTextN;
    String scenarioResultY;
    String scenarioResultN;
    
    public Scenario() throws IOException {
        super("empty");
        iteration = 0;

    }

    void parse(String type, String[] yResults, String[] nResults) throws IOException {
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
        FileHandeler.saveToFile("inputLog", mostRecentInput);

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

    String getScenarioOutcomeText(String answer) {
        if(answer.equals("yes")){
            return scenarioOutComeTextY;
        } else {
            if(answer.equals("no")){
                return scenarioOutComeTextN;
            } else {
                return null;
            }
        }
    }

    String[] scenarioResultY() {
        return scenarioResultY.split("~");

    }

    String[] scenarioResultN() {
        return scenarioResultY.split("~");

    }

}
