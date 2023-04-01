import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.reflect.Field;
import java.io.InputStreamReader;
import java.io.FileWriter;

public class App {
    static String mostRecentInput;

    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
        System.out.println("Started " + System.currentTimeMillis());
        City city = new City();
        saveToFile("save", debug(city));
        saveToFile("inputLog", "recreate");
        System.out.println("City Name: " + city.cityName);    

        /*Main Loop*/
        while(true) {
            getInput();
            parseInput(city);

        }

    }

    static String debug(Object city) throws IllegalArgumentException, IllegalAccessException {
        String outPut = "";
        for (Field field : city.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(city);
            outPut += name + ": " + value + "\n";
        }    
        return outPut;

    }

    static void getInput() throws IOException{
        BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
        mostRecentInput = keyboardReader.readLine();

    }

    static void parseInput(Object city) throws IOException, IllegalArgumentException, IllegalAccessException {
        if(mostRecentInput.equals("debug")){
            System.out.println(debug(city));
        }
        if(mostRecentInput.equals("exit")){
            System.exit(0);
            saveToFile("inputLog", mostRecentInput);
        }
        if(mostRecentInput.equals("save")){
            saveToFile("save", debug(city));
        }
        saveToFile("inputLog", mostRecentInput);

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

