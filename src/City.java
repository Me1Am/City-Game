import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.io.InputStreamReader;

public class City extends App{
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    int cityDifficulty;
    int cityDifficultyModifier;
    double disasterChance;
    double crimeRateModifier;
    double satisfactionStability;
    double globalDifficultyModifier;
    String cityName;

    int power = 100;
    int water = 100;
    int money = 100000;
    int population = 1000;
    int satisfaction = 90;
    float crimeRate;

    float[] modifierValues;    //GlobalModifier(0), DisasterProbability(1), SatisfactionModifier(2), CrimeRateModifier(3)

    /*Constructor*/
    public City() throws IOException {
         /*Set Variables*/

        cityName = setCityName();
        cityDifficulty = setDifficulty();
        
        /*Set Difficulty Dependent Values*/
        modifierValues = setDifficultyModifiers(cityDifficulty - 1);   //Get the modifier and value list
        
        /*Global Difficulty Modifier*/
        globalDifficultyModifier = modifierValues[0];   //Set global modifier
        
        money *= globalDifficultyModifier;
        population *= globalDifficultyModifier;

        /*Other*/
        disasterChance = modifierValues[1];
        satisfactionStability = modifierValues[2];
        crimeRate = modifierValues[3];
        crimeRateModifier = modifierValues[3];
        
    }
    
    /*Empty Constructor*/
    public City(String empty) {
        cityName = null;
    }

    /*Return Inputted Difficulty*/
    private static int setDifficulty() throws IOException {
        System.out.println("Select difficulty 1-5:");
        
        //Set Difficulty
        while(true) {   //Repeat until valid integer is intered
            try {
                int difficulty = Integer.parseInt(reader.readLine());
                if(difficulty <= 5 && difficulty > 0){
                    return difficulty;
                } else {
                    System.out.println("Enter a number between 1 and 5!");
                }
            } catch(NumberFormatException e) {
                System.out.println("Enter and integer");

            }
        }
    
    }

    /*Return Inputted Name for City*/
    private static String setCityName() throws IOException {        
        System.out.println("Input City Name:");
        
        //Set and Confirm Name
        while(true) {   //Repeat until name is confirmed
            String initial = reader.readLine();
            if(initial.length() <= 64) {
                System.out.println("Confirm: " + initial);
                String fin = reader.readLine();
                if(initial.equals(fin)){    //Check if both inputs are the same
                    return initial;
                }
            } else {
                System.out.println("Name too long! 64 characters max!");
            }
            System.out.println("Enter Again:");
        }
        
    }

    /*Return Float Array of The Modifiers and Chances*/
    //Based on difficulty
    private static float[] setDifficultyModifiers(int dif) {
        /*Set modifiers*/
        final float[] globalModifierLookup = {1f, 0.80f, 0.65f, 0.40f, 0.25f};
        final float[] satisfactionModifierLookup = {0.00004f, 0.07f, 0.15f, 0.25f, 0.35f};
        final float[] crimeRateModifierLookup = {0.01f, 0.04f, 0.09f, 0.27f, 0.35f};

        /*Set Chances*/
        final float[] disasterLookup = {0.00004f, 0.00007f, 0.00015f, 0.00050f, 0.00100f};

        /*Set Master Array*/
        final float[] modifiedValues = {globalModifierLookup[dif], 
                                            disasterLookup[dif], 
                                            satisfactionModifierLookup[dif], 
                                            crimeRateModifierLookup[dif]};
        return modifiedValues;

    }

    /*Change City Values*/
    //Applies results from scenarios
    void changeVar(int reward, String variableName) {
        if(variableName.equals("power")){
            power += (reward * globalDifficultyModifier);
        } else {
            if(variableName.equals("water")){
                water += (reward * globalDifficultyModifier);
            } else {
                if(variableName.equals("money")){
                    money += (reward * globalDifficultyModifier);
                } else {
                    if(variableName.equals("population")){
                        population += (reward * globalDifficultyModifier);
                    } else {
                        if(variableName.equals("satisfaction")){
                            satisfaction += (reward * modifierValues[2]);
                        } else {
                            if(variableName.equals("crimerate")){
                                crimeRate += (reward * modifierValues[3]);
                            }
                        }   
                    }   
                }   
            }   
        }
    }

    /*Check Variables for Validity*/
    //Sets and keeps limit for variables
    void checkVar() {
        /*Make Sure Variables Don't Excede Limit*/
        if(power > 100){
            power = 100;
        } else {
            if(power < 0){
                power = 0;
            }
        }
        if(water > 100){
            water = 100;
        } else {
            if(water < 0){
                water = 0;
            }
        }
        if(satisfaction > 100){
            satisfaction = 100;
        } else {
            if(satisfaction < 0){
                satisfaction = 0;
            }
        }
        if(population < 0){
            population = 0;
        }
        if(crimeRate < 0){
            crimeRate = 0;
        }

    }

    /*Return a String of the City Object's Values*/
    //Primarly for printing values to terminal
    String debug() throws IllegalArgumentException, IllegalAccessException {
        String output = "";
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(this);
            output += name + ": " + value + "\n";
        } 
        return output;

    }

    /*Return an Array of the City object's Values*/
    //Primarly to save to file
    ArrayList<String> debugArrayList() throws IllegalArgumentException, IllegalAccessException {
        ArrayList<String> output = new ArrayList<String>();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(this);
            output.add(name + "=" + value);
        } 
        return output;

    }

    /*Return User's City Stats*/
    //Returns only the values that the user should see
    String getCityStatsHeaders() throws IllegalArgumentException, IllegalAccessException {
        String output = "";
        for (Field field : city.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            if(name.equals("power") || name.equals("water") || name.equals("money") || name.equals("population") || name.equals("satisfaction") || name.equals("crimeRate")){
                Object value = field.get(city);
                output += name + ": " + value + "\n";
            }
        } 
        return output;

    }

}
