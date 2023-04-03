import java.io.BufferedReader;
import java.io.IOException;
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
    double crimeRate;

    double[] modifierValues;    //GlobalModifier(0), DisasterProbability(1), SatisfactionModifier(2), CrimeRateModifier(3)

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

    public City(String empty) {
        cityName = null;
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
        final double[] satisfactionModifierLookup = {0.04, 0.07, 0.15, 0.25, 0.35};
        final double[] crimeRateModifierLookup = {0.01, 0.04, 0.09, 0.27, 0.35};

        /*Set Chances*/
        final double[] disasterLookup = {0.00004, 0.00007, 0.00015, 0.00050, 0.00100};

        /*Set Master Array*/
        final double[] modifiedValues = {globalModifierLookup[dif], 
                                            disasterLookup[dif], 
                                            satisfactionModifierLookup[dif], 
                                            crimeRateModifierLookup[dif]};
        return modifiedValues;

    }

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

}
