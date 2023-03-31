import java.io.IOException;
import java.lang.reflect.Field;

public class App {

    public static void main(String[] args) throws IOException{
        System.out.println("Started " + System.currentTimeMillis());
        City city = new City();
        System.out.println("City Name: " + city.cityName);    
        for (Field field : city.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(city);
            System.out.printf("%s: %s%n", name, value);
        }    
    
    }

    static void startUp() {


    }

}

