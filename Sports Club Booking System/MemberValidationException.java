
/**
 * MemberValidationException is for valid member input
 *
 * @author Upal Roy
 * @version 1.0.0
 */
public class MemberValidationException extends Exception
{
    /**
     * Exception for member validation
     */
    public MemberValidationException( )
    {
        super("No member found with this member id.");
    }
    
    /**
     * Exception for member validation
     */
    public MemberValidationException(String message)
    {
        super(message);
    }
}

