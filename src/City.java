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
        final double[] satisfactionModifierLookup = {1.00, 1.07, 1.15, 1.25, 1.35};
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

    void changeVar(String type, String variableName) {
        int modifier;
        if(type.equals("+")){
            modifier = 1;
        } else {
            modifier = 0;
        }
        //If 'modifier' is 1 then it increases, if not it decerases
        if(variableName.equals("power")){
            power *= (modifier + globalDifficultyModifier);
        } else {
            if(variableName.equals("water")){
                water *= (modifier + globalDifficultyModifier);
            } else {
                if(variableName.equals("money")){
                    money *= (modifier + globalDifficultyModifier);
                } else {
                    if(variableName.equals("population")){
                        population *= (modifier + globalDifficultyModifier);
                    } else {
                        if(variableName.equals("satisfaction")){
                            satisfaction *= (modifier + modifierValues[2]);
                        } else {
                            if(variableName.equals("crimerate")){
                                crimeRate *= (modifier + modifierValues[3]);
                            }
                        }   
                    }   
                }   
            }   
        }
    }

}
