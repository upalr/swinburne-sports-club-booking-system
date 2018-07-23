import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Date utility methods for converting dates from string to 
 * 
 * @Upal Roy 
 * @1.0.0 
 */
public class DateUtility
{
    /**
     * Convert string date to local date
     *
     * @param  aDate  first paramter of convertDate method. Represents Local date string
     * @return  LocalDate  Return converted date
     */
    public static LocalDate convertDate(String aDate) 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(aDate, formatter);
        return localDate;
    }
    
    /**
     * Convert string date to local date
     *
     * @param  aDate  first paramter of convertDate method. Represents Local date string
     * @param  format second paramter of convertDate method. Represents the format 
     * @return  LocalDate  Return converted date
     */
    public static LocalDate convertDate(String aDate, String format) 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(aDate, formatter);
        return localDate;
    }
    
    /**
     * Convert local date to string date
     *
     * @param  aDate  first paramter of dateToString method. Represents Local date
     * @return  date  Return converted string date 
     */
    public static String dateToString(LocalDate aDate) 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = formatter.format(aDate);
        return date;
    }
    
    /**
     * Convert string time to local time
     *
     * @param  aTime  first paramter of convertTime method. Represents Local Time string
     * @return  LocalTime  Return converted Time
     */
    public static LocalTime convertTime(String aTime) 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(aTime, formatter);
        return localTime;
    }
    
    
    /**
     * Convert local time to string time
     *
     * @param  aTime  first paramter of timeToString method. Represents Local Time
     * @return  time  Return converted string time 
     */    
    public static String timeToString(LocalTime aTime) 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String time = formatter.format(aTime);
        return time;
    }
        
}
