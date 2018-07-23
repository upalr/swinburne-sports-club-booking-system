import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Sport class for modeling real Sport.
 * 
 * @author Upal Roy
 * @version 1.0.0
 */
public class Sport
{
    // instance variables - name, usageFee, insuranceFee and courtList and highestHoursBookingPerDay represent name, usage fee, insurance fee and courtList and highest hours booking per day of each sport respectively
    private String name;
    private double usageFee;
    private double insuranceFee;
    protected ArrayList<Court> courtList;
    protected int highestHoursBookingPerDay;
    
    /**
     * Gets the name of this Sport
     * 
     * @return this Sport's name
     */
    public String getName(){
       return name;
    }
    
    /**
     * Changes the name of this Sport
     * 
     * @param name This Sport's new name 
     */
    public void setName(String name) {
       this.name = name;
    }
    
    /**
     * Gets the usage fee of this Sport
     * 
     * @return this Sport's usageFee
     */
    public double getUsageFee(){
       return usageFee;
    }
   
    /**
     * Changes the usage fee of this Sport
     * 
     * @param usageFee This Sport's new usage fee 
     */
    public void setUsageFee(double usageFee) {
       this.usageFee= usageFee;
    }
    
    /**
     * Gets the insurance fee of this Sport
     * 
     * @return this Sport's insuranceFee
     */
    public double getInsuranceFee(){
       return insuranceFee;
    }
    
    /**
     * Changes the insurance fee of this Sport
     * 
     * @param insuranceFee This Sport's new insurance fee 
     */
    public void setInsuranceFee(double insuranceFee) {
       this.insuranceFee = insuranceFee;
    }
    
    /**
     * Gets the highestHoursBookingPerDay of this Sport
     * 
     * @return this Sport's highestHoursBookingPerDay
     */
    public int getHighestHoursBookingPerDay(){
       return highestHoursBookingPerDay;
    }
    
    /**
     * Changes the highestHoursBookingPerDay of this Sport
     * 
     * @param highestHoursBookingPerDay This Sport's new highestHoursBookingPerDay 
     */
    public int setHighestHoursBookingPerDay(int highestHoursBookingPerDay){
       return this.highestHoursBookingPerDay = this.highestHoursBookingPerDay;
    }
   
    /**
     * Gets the courtList of this Sport
     * 
     * @return this Sport's courtList
     */
    
   public ArrayList<Court> getCourts()
   {
       return courtList;
   }
   
   /**
    * Constructor for objects of class Sport
    */
    public Sport(String name, double usageFee, double insuranceFee, ArrayList<Court> courtList)
    {        
        this.name = name;
        this.usageFee = usageFee;
        this.insuranceFee = insuranceFee;
        this.courtList = courtList;
    }
    
    /**
     * String representation of sport object.
     *
     * @return this Sport's string representation  
     */
    public String toString()
    {
        return "Name: " + name + ", Usage Fee: " + usageFee + ", InsuranceFee: " + insuranceFee + ", Court Numbers: " + courtList;
    }
}