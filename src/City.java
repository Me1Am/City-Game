import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class City {
    static String cityName;
    static int cityDifficulty;
    static double disasterChance;
    static double crimeRateModifier;
    static int cityDifficultyModifier;
    static double satisfactionStability;
    static double globalDifficultyModifier;

    int power = 100;
    int water = 100;
    int money = 100000;
    int population = 1000;
    int satisfaction = 100;
    double crimeRate = 0.01;


    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public City() throws IOException {
        cityName = setCityName();
        cityDifficulty = setDifficulty();
        
        /*Set Difficulty Dependent Values*/
        double[] modifierValues = setDifficultyModifiers(cityDifficulty - 1);   //Get the modifier and value list
        
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

    static int setDifficulty() throws IOException {
        System.out.println("Select difficulty 1-5:");
        /*Set Difficulty*/
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

    static String setCityName() throws IOException {        
        System.out.println("Input City Name:");
        /*Set and Confirm Name*/
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
        }
        
    }

    static double[] setDifficultyModifiers(int dif) {
        /*Set modifiers*/
        final double[] globalModifierLookup = {1, 0.80, 0.65, 0.40, 0.25};
        final double[] satisfactionStabilityLookup = {1.00, 1.07, 1.15, 1.25, 1.35};
        final double[] crimeRateModifierLookup = {0.01, 0.04, 0.9, 0.27, 0.35};

        /*Set Chances*/
        final double[] disasterLookup = {0.00004, 0.00007, 0.00015, 0.00050, 0.00100};

        /*Set Master Array*/
        final double[] modifiedValues = {globalModifierLookup[dif], 
                                            disasterLookup[dif], 
                                            satisfactionStabilityLookup[dif], 
                                            crimeRateModifierLookup[dif]};
        return modifiedValues;

    }

}
