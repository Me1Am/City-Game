import java.io.IOException;
import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.io.InputStreamReader;

public class App {
    static String mostRecentInput;
    
    static City city;
    static Scenario scenario;
    static BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
        System.out.println("Started " + System.currentTimeMillis());
        city = new City();
        scenario = new Scenario();
        FileHandeler.saveToFile("save", debug());
        FileHandeler.saveToFile("inputLog", "recreate"); //Clear input log
        System.out.println("Welcome To " + city.cityName 
                            + "!\nPopulation " + city.population
                            + "\nFunds " + city.money);    


        /*Main Loop*/
        while(true) {
            scenario.start();
            parseInput();
            fileHandeler.getFileData();
        }

    }

    static String debug() throws IllegalArgumentException, IllegalAccessException {
        String output = "";
        for (Field field : city.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(city);
            output += name + ": " + value + "\n";
        } 
        return output;

    }

    static void getInput() throws IOException{
        mostRecentInput = keyboardReader.readLine();

    }

    static void parseInput() throws IOException, IllegalArgumentException, IllegalAccessException {
        getInput();
        
        if(mostRecentInput.equals("exit")){
            FileHandeler.saveToFile("inputLog", mostRecentInput);
            System.exit(0);
        } else {
            if(mostRecentInput.equals("debug")){
                System.out.println(debug());
            } else {
                if(mostRecentInput.equals("save")){
                    FileHandeler.saveToFile("save", debug());
                }
            }
        }  
        FileHandeler.saveToFile("inputLog", mostRecentInput);    //Save input

    }

}

