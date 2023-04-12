import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class App {
    static String mostRecentInput;
    
    static City city;
    static Scenario scenario;
    static BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
        /*Setup and Initilize City*/
        System.out.println("Started " + System.currentTimeMillis());
        city = new City();  //Run setup
        clearCMD(); //Clear terminal
        FileHandeler.saveProperties("data/save", city.debugArrayList()); //Save city
        FileHandeler.saveToFile("inputLog", "recreate"); //Clear input log
        System.out.println("Welcome To " + city.cityName + "!\nPopulation " + city.population + "\nFunds " + city.money + "\n");   //Introduce user to their city  
        
        /*Run Tutorial Scenarios*/
        runScenario(1);
        getInput();
        parseInput();
        runScenario(2);
        getInput();
        parseInput();

        /*Main Loop*/
        while(true) {
            runScenario(3);
            getInput();
            parseInput();            
        }

    }

    /*Load and Run A Scenario*/
    static void runScenario(int scenarioNum) throws IOException, IllegalArgumentException, IllegalAccessException {
        scenario = new Scenario();
        scenario.loadScenarioData(scenarioNum);    //Load the scenario values from the data file
        
        System.out.println(scenario.scenarioIntro + "\n" + scenario.scenarioText);  //Print scenario text
        getInput(); //Get user's answer
        scenario.parse(scenario.scenarioAnswerType, scenario.scenarioResultY(), scenario.scenarioResultN());    //Computate the user's input
        System.out.println(scenario.getScenarioOutcomeText(mostRecentInput));   //Print the outcom

        scenario.iteration += 1;    //Increase iteration
        city.checkVar();    //Check and adjust variables if necessary
        System.out.println(city.getCityStatsHeaders()); //Print new changes

    }
    
    /*Pause and Get User Input*/
    static void getInput() throws IOException{
        mostRecentInput = keyboardReader.readLine();
        FileHandeler.saveToFile("inputLog", mostRecentInput);    //Save input

    }
    
    /*Parse/Computate the User's Input*/
    static void parseInput() throws IOException, IllegalArgumentException, IllegalAccessException {      
        if(mostRecentInput.equals("exit")){
            FileHandeler.saveToFile("inputLog", mostRecentInput);
            System.exit(0);
        } else {
            if(mostRecentInput.equals("debug city")){
                System.out.println(city.debug());
            } else {
                if(mostRecentInput.equals("save")){
                    FileHandeler.saveProperties("data/save", city.debugArrayList());
                }
            }
        }
        
        clearCMD();

    }
    
    /*Clear Terminal*/
    static void clearCMD() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

}

