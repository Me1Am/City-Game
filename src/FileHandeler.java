import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

class FileHandeler {
    static File configFile;
    static FileReader reader;
    static FileWriter writer;
    static Properties properties;

    static void saveToFile(String fileName, String input) throws IOException {
        /*Determine Write Factor*/
        boolean append;
        String fileType;
        if(fileName.equals("save") || input.equals("recreate")){
            append = false;
            if(fileName.equals("save")){
                fileType = ".properties";
            } else {
                fileType = ".txt";
            }
        } else {
            append = true;
            fileType = ".txt";
        }

        /*Write*/
        try {  
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + fileType, append));
            writer.write(input);
            writer.write(System.lineSeparator());
            writer.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    
    }

    static void saveProperties(String name, ArrayList<String> data) {
        configFile = new File(name + ".properties");
        /*Set Data Helper Variables*/
        String[] dataNameValue;
        String dataName = "";
        String dataValue = "";
        
        try {
            properties = new Properties();
            writer = new FileWriter(configFile);

            for (String string : data) {
                //Get data name and value from each string in the ArrayList, seperated by "="
                dataNameValue = string.split("=");
                dataName = dataNameValue[0];
                dataValue = dataNameValue[1];
                //Save values
                properties.setProperty(dataName, dataValue);
            }
            
            properties.store(writer, "save");
            writer.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        
    }

    static String getPropertyData(String path, String item) throws IOException {
        configFile = new File(path);
        try {
            FileReader reader = new FileReader(configFile);
            Properties properties = new Properties();

            properties.load(reader);    //Load the file reader object
            String data = properties.getProperty(item); //Get data

            reader.close();
            return data;
        } catch (FileNotFoundException ex) {
            return "FileNotFoundException";
        } catch (IOException ex) {
            return "IOExeption";
        }
    }

}