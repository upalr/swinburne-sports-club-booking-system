import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Basketball class for modeling real Basketball.
 * 
 * @author Upal Roy
 * @version 1.0.0
 */
public class Basketball extends Sport
{
    // instance variables - netHeight represent netHeight of each basketball respectively
    private double netHeight;
    
    /**
     * Gets the netHeight of this Basketball
     * 
     * @return this Basketball's netHeight
     */
    public double getNetHeight(){
       return netHeight;
    }
    
    /**
     * Changes the netHeight of this Basketball
     * 
     * @param netHeight This Basketball's new netHeight 
     */ 
    public void setNetHeight(double netHeight) {
       this.netHeight = netHeight;
    }
    
    /**
     * Constructor for objects of class Basketball
     */
    public Basketball(String name, double usageFee, double insuranceFee, ArrayList<Court> courtList, double netHeight)
    {
        super(name, usageFee, insuranceFee, courtList);
        this.netHeight = netHeight;
        highestHoursBookingPerDay = 3;
    }
    
    /**
     * String representation of Basketball object.
     *
     * @return this Basketball's string representation  
     */
    public String toString()
    {
        return super.toString() + ", Net Height: " + netHeight;
    }

}
