import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Court class for modeling Court.
 * 
 * @author Upal Roy
 * @version 1.0.0
 */
public class Court
{
    private int courtId;

    /**
     * Gets the courtId of this Court
     * 
     * @return this Court's courtId
     */
    public int getCourtId(){
       return courtId;
    }
   
    /**
     * Changes the courtId of this Sport
     * 
     * @param courtId This Sport's new courtId 
     */
    public void setCourtId(int courtId) {
       this.courtId = courtId;
    }
    
    /**
     * Constructor for objects of class Court
     */
    public Court(int courtId)
    {
        this.courtId = courtId;

    }
    
    /**
     * String representation of Court object.
     *
     * @return this Court's string representation  
     */
    public String toString()
    {
        return "" + courtId;
    }  

}
