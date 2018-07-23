import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.*;

/**
 * This class is a utility class which defines methods to read different forms of user input.
 * 
 * @author Upal Roy
 * @version 1.0
 */

public class IOUtility
{
    private static Scanner in = new Scanner(System.in);
    
    /** getString(String prompt) prompts the user for an input, 
     * reads it as a String object and returns it.
     * 
     * @param   prompt  A String which promts for user input
     * @return  String  An object which holds the user input
     */
    public static String getString(String prompt)
    {
        System.out.print(prompt + " ");
        return in.nextLine();
    }
    
    /** getDouble(String prompt) prompts the user for an input 
     * and parses the input to Double object.
     * 
     * @param   prompt  A String which promts for user input
     * @return  Double  An object which holds the user input
     */
    public static Double getDouble(String prompt)
    {
        Double d = 0.00;
        while(true)
        {
            try
            {
                System.out.print(prompt + " ");
                d = Double.parseDouble(in.nextLine());
                break;
            }
            catch(Exception e)
            {
                  System.out.println("Not a valid Double");
            }
        }
        return d;  
    }
    
    /** getInteger(String prompt) prompts the user for an input 
     * and parses the input to an Integer object.
     * 
     * @param   prompt  A String which promts for user input
     * @return  Integer An object which holds the user input
     */
     public static Integer getInteger(String prompt)
    {
        Integer i = 0;
        while(true)
        {
            try
            {
                System.out.print(prompt + " ");
                i = Integer.parseInt(in.nextLine());
                break;
            }
            catch(Exception e)
            {
                System.out.println("Not a valid Integer");
            }
        }
        return i;  
    }
    
    
    /** getIntegerFromDialog(String prompt) prompts the user for an input dialog 
     * and parses the input to an Integer object.
     * 
     * @param   prompt  A String which promts for user input
     * @return  Integer An object which holds the user input
     */
     public static Integer getIntegerFromDialog(String prompt){
        Integer i = 0;
        while(true)
        {
            try
            {
                i = Integer.parseInt(JOptionPane.showInputDialog(prompt + " "));
                break;
            }
            catch(Exception e)
            {
                //System.out.println("Not a valid Integer");
                break;
            }
        }
        return i;
     }
        
        
    /** getLocalDate(String prompt) prompts the user for an input 
     * and parses the input to an LocalDate object.
     * 
     * @param   prompt  A String which promts for user input
     * @return  LocalDate An object which holds the user input
     */
     public static LocalDate getLocalDate(String prompt)
    {
        LocalDate date;
        while(true)
        {
            try
            {
                System.out.print(prompt + " ");
                date = DateUtility.convertDate((in.nextLine()));
                break;
            }
            catch(Exception e)
            {
                System.out.println("Not a valid Date");
            }
        }
        return date;  
    }
    
    /** getLocalTime(String prompt) prompts the user for an input 
     * and parses the input to an LocalTime object.
     * 
     * @param   prompt  A String which promts for user input
     * @return  LocalTime An object which holds the user input
     */
     public static LocalTime getLocalTime(String prompt)
    {
        LocalTime time;
        while(true)
        {
            try
            {
                System.out.print(prompt + " ");
                time = DateUtility.convertTime((in.nextLine()));
                break;
            }
            catch(Exception e)
            {
                System.out.println("Not a valid Time");
            }
        }
        return time;  
    }
    
     /** println(String toPrint) prints the argument passed to the method. 
      * 
     * @param   toPrint A String that should be printed to the stdout
     */
    public static void println(String toPrint)
    {
        System.out.println(toPrint);
    }
   
    /** getInteger() promts the user for an integer input and 
     * parses the input to an Integer object.
     * 
     * @return Integer  An object which holds the user input
     */
    public static Integer getInteger()
    {
        Integer i = 0;
        while(true)
        {
            try
            {
                System.out.print("Please enter an integer");
                i = Integer.parseInt(in.nextLine());
                break;
            }
            catch(Exception e)
            {
                System.out.println("Not a valid Integer");
            }
        }
        return i;  
    }

}// end of class