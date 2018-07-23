import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Badminton extends Sport
{
    // instance variables - isRacketsProvided represent Rackets Provided of each badminton respectively
    private boolean isRacketsProvided;
    
    /**
     * Gets the isRacketsProvided of this Badminton
     * 
     * @return this Badminton's isRacketsProvided
     */
    public boolean getIsRacketsProvided(){
       return isRacketsProvided;
    }
   
    /**
     * Changes the isRacketsProvided of this Badminton
     * 
     * @param isRacketsProvided This Badminton's new isRacketsProvided 
     */
    public void setIsracketsProvided(boolean isRacketsProvided) {
       this.isRacketsProvided = isRacketsProvided;
    }
    
    /**
     * Constructor for objects of class Badminton
     */
    public Badminton(String name, double usageFee, double insuranceFee, ArrayList<Court> courtList, boolean isRacketsProvided)
    {
        super(name, usageFee, insuranceFee, courtList);
        this.isRacketsProvided = isRacketsProvided;
        highestHoursBookingPerDay = 2;
    }
    
    /**
     * String representation of Badminton object.
     *
     * @return this Badminton's string representation  
     */
    public String toString()
    {
        return super.toString() + ", Racket Provided: " + isRacketsProvided;
    }

}
