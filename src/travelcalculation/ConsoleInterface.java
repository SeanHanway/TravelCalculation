package travelcalculation;

import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Seán Hanway
 */

public class ConsoleInterface {
    
    private String point1, point2;
    private String region = Locale.getDefault().getCountry();

    private final Scanner scan = new Scanner(System.in);
    
    /** A console interface designed to allow the user to use the calculateDistance method of the TravelCalculation class.
     *  The user can enter their desired origin and destination as well as change regions through the console.
     *  Program ends when the user enters an appropriate end message such as 'exit' for example.
     */
    public void runTravelCalculationsInterface(){
        System.out.println("You can exit the program at any time by typing any appropriate close phrase such as 'end' or 'exit'.");
        try {
            while (true) {
                System.out.print("Please input your origin: ");
                point1 = scan.next();
                exitCondition(point1);
                System.out.print("Please input your destination: ");
                point2 = scan.next();
                exitCondition(point2);
                getRegionMessage(scan);
                TravelCalculation travel = new TravelCalculation(region);
                travel.calculateDistance(point1, point2);
            }
        } catch(CancelledException e){
            //Exists for the purpose of ending the loop, nothing needs be done with the exception.
        }
    }

    /** Gives the user the opportunity to change their desired search region.
     *
     * @param scan Uses the calling method's scanner object to save duplicate objects being created when just one is sufficient.
     * @return Returns a boolean value which indicates whether or not the region has been changed.
     * @throws CancelledException If a user enters a String which satisfies the exitCondition method then a CancelledException will be thrown to the calling method.
     */
    private boolean getRegionMessage(Scanner scan) throws CancelledException{
        String response;
        System.out.println("Your current region is set to " + region.toUpperCase() + ". \nDo you wish to change it? Y/N");
        response = scan.next();
        exitCondition(response);

        if (response.equalsIgnoreCase("Y")) {
            System.out.println("Please enter a new region using ISO standard region code. Example: IE, GB, FR");
            region = scan.next();
            exitCondition(region);
            return true;
        }
        return false;
    }
    
    /** Tests to see if a given string matches common phrases a user might enter to exit the program, namely 'exit', 'break', 'close', 'quit', 'stop', 'end'
     * If any of these phrases is entered a CancelledException is thrown.
     *
     * @param compare Enter a String you wish to test.
     * @throws CancelledException Used to end loops in calling methods.
     */
    private static void exitCondition(String compare) throws CancelledException{
        if (compare.equalsIgnoreCase("exit") || compare.equalsIgnoreCase("break") || compare.equalsIgnoreCase("end") ||
            compare.equalsIgnoreCase("close") || compare.equalsIgnoreCase("quit") || compare.equalsIgnoreCase("stop")){
            throw new CancelledException();
        }
    }
}

class CancelledException extends Exception{

}
