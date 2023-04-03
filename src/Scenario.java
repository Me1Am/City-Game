import java.io.IOException;

public class Scenario extends City{
    int iteration;
    public Scenario() throws IOException {
        super("empty");
        iteration = 0;
    }

    public void start() throws IOException, IllegalArgumentException, IllegalAccessException {
        String[] yResults = {"10", "money", "-10", "satisfaction"};
        String[] nResults = {"-1000", "money", "10", "satisfaction"};
        
        System.out.println("Hello! This is your city, do what you want. Just make sure everything stays in the green!");
        System.out.println("Lets start easy, just decide if taxes should be risen or not:");
        
        App.parseInput();
        parse("y/n", yResults, nResults);

    }

    void parse(String type, String[] yResults, String[] nResults) throws IOException {
        int yResultLength = yResults.length;    
        int nResultLength = nResults.length;

        /*Result syntax*/
            //Even indexes are the reward
            //Odd indexes are the variable name

        while(true){
            /*Yes-No Option*/
            if(type.equals("y/n")) {
                if(mostRecentInput.equals("yes")){
                    //Apply each consequence of the choice
                    for(int i = 0; i < yResultLength; i += 2){
                        App.city.changeVar(Integer.parseInt(yResults[i]), yResults[i + 1]);
                    }
                    break;
                } else {
                    if(mostRecentInput.equals("no")){
                        for(int i = 0; i < nResultLength; i += 2){
                            App.city.changeVar(Integer.parseInt(nResults[i]), nResults[i + 1]);
                        }
                        break;
                    } else {
                        System.out.println("A 'yes' or 'no' will do");
                        App.getInput();
                    }
                }
            }
        }
        App.city.checkVar();
    
    }

}
