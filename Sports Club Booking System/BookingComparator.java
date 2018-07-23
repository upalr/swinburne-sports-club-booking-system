import java.util.Comparator;

/**
 * class BookingComparator is a opetor used for sort boookings.
 *
 * @author Upal Roy
 * @version .0.01
 */
public class BookingComparator implements Comparator<Booking>

{
    /**
     * Compare date first and then by hours
     *
     * @param  booking1  first paramter of compare method. Represents Booking object
     * @param  booking2  second paramter of compare method. Represents Booking object
     * @return  boolean  Return boolean after compare two object
     */
    public int compare(Booking booking1,Booking booking2)
    {
        //compare date first
        int diff = booking1.getBookingDate().compareTo(booking2.getBookingDate());
        if(diff == 0) //if date is the same compare time
        {
            diff = booking1.getStartTime().compareTo(booking2.getStartTime());
        }
        return diff;     
    }

}
