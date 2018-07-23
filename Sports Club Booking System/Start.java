import java.util.*;

/**
 * Mange to start the application
 *
 * @author Upal Roy
 * @version 1.0.0
 */

public class Start
{
    public static void main(String[] args)
    {
        try
        {
            Club sportsClub = new Club("Sports Club");

            IOUtility.println("Select one of the below interfaces for running the application: ");
            IOUtility.println("1. Console Application");
            IOUtility.println("2. GUI");
            IOUtility.println("3. Exit" );
            int option = IOUtility.getInteger("Select your option (enter a selection number):");


            switch(option)
            {
                case 1: 
                showConsoleInterface(sportsClub);
                break;
                case 2: 
                showGraphicalInterface(sportsClub);
                break;
                case 3:
                System.exit(0);
                break;
                default:
                System.out.println("No such option found");
                break;

            }

            sportsClub.saveClub();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }    
    }

    public static void showConsoleInterface(Club sportsClub)
    {
        UserInterface consoleApp = new UserInterface(sportsClub);
        consoleApp.run();
    }

    public static void showGraphicalInterface(Club sportsClub)
    { 
        MasterGUI gui = new MasterGUI(sportsClub);
        gui.run();
    }
}