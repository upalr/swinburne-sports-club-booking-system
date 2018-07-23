
/**
 * SevenDaysBookingValidationException is for valid days booking
 *
 * @author Upal Roy
 * @version 1.0.0
 */
public class SevenDaysBookingValidationException extends Exception
{
    /**
     * Exception for seven daysbooking validation
     */
    public SevenDaysBookingValidationException( )
    {
        super("Booking up to seven days is acceptable.");
    }
    
    /**
     * Exceptionfor seven daysbooking validation
     */
    public SevenDaysBookingValidationException(String message)
    {
        super(message);
    }
}
