import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

class FileHandeler {
    static void saveToFile(String fileName, String input) throws IOException {
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

    static String getFileData() throws IOException {
        String path = "data/data.txt";
        FileInputStream fis = new FileInputStream(path);
        byte[] bytesArray = new byte[(int)path.length()];
        fis.read(bytesArray);
        return new String(bytesArray);
    }

}