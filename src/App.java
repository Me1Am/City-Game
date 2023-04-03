import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.reflect.Field;
import java.io.InputStreamReader;
import java.io.FileWriter;

public class App {
    static String mostRecentInput;
    
    static City city;
    static Scenario scenario;
    static BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
        System.out.println("Started " + System.currentTimeMillis());
        city = new City();
        scenario = new Scenario();
        saveToFile("save", debug());
        saveToFile("inputLog", "recreate"); //Clear input log
        System.out.println("Welcome To " + city.cityName 
                            + "!\nPopulation " + city.population
                            + "\nFunds " + city.money);    


        /*Main Loop*/
        while(true) {
            scenario.start();
            parseInput();
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
            saveToFile("inputLog", mostRecentInput);
            System.exit(0);
        } else {
            if(mostRecentInput.equals("debug")){
                System.out.println(debug());
            } else {
                if(mostRecentInput.equals("save")){
                    saveToFile("save", debug());
                }
            }
        }  
        saveToFile("inputLog", mostRecentInput);    //Save input

    }

    static public void saveToFile(String fileName, String input) throws IOException {
        /*Determine Write Factor*/
        boolean append;
        if(fileName.equals("save") || input.equals("recreate")){
            append = false;
        } else {
            append = true;
        }

        /*Write*/
        try {  
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt", append));
            writer.write(input);
            writer.write(System.lineSeparator());
            writer.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    
    }

}

