import java.time.*;
import java.util.*;

/**
 * Booking class for modeling Booking.
 * 
 * @author Upal Roy
 * @version 1.0.0
 */
public class Booking
{
    public int bookingId;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Member bookedBy;
    private Court bookedFor;
    private static int i = 1;
    
    /**
     * Gets the bookingId of this Booking
     * 
     * @return this Booking's bookingId
     */
    public int getBookingId() {
       return bookingId;
    }
    
    /**
     * Changes the bookingId of this Booking
     * 
     * @param bookingId This Booking's new bookingId 
     */
    public void setBookigId(int bookingId) {
       this.bookingId = bookingId;
    }
    
    /**
     * Gets the bookingDate of this Booking
     * 
     * @return this Booking's bookingDate
     */
    public LocalDate getBookingDate() {
       return bookingDate;
    }
    
    /**
     * Changes the bookingDate of this Booking
     * 
     * @param bookingDate This Booking's new bookingDate 
     */
    public void setBookingDate(LocalDate bookingDate) {
       this.bookingDate = bookingDate;
    }
    
    /**
     * Gets the startTime of this Booking
     * 
     * @return this Booking's startTime
     */
    public LocalTime getStartTime() {
       return startTime;
    }
    
    /**
     * Changes the startTime of this Booking
     * 
     * @param startTime This Booking's new startTime 
     */
    public void setStartTime(LocalTime startTime) {
       this.startTime = startTime;
    }
    
    /**
     * Gets the endTime of this Booking
     * 
     * @return this Booking's endTime
     */
    public LocalTime getEndTime() {
       return endTime;
    }
    
    /**
     * Changes the endTime of this Booking
     * 
     * @param endTime This Booking's new endTime 
     */
    public void setEndTime(LocalTime endTime) {
       this.endTime = endTime;
    }
    
    /**
     * Gets the bookedBy of this Booking
     * 
     * @return this Booking's bookedBy
     */
    public Member getMember() {
       return bookedBy;
    }
    
    /**
     * Changes the bookedBy of this Booking
     * 
     * @param bookedBy This Booking's new bookedBy 
     */
    public void setMember(Member bookedBy) {
       this.bookedBy = bookedBy;
    }
    
    /**
     * Gets the bookedFor of this Booking
     * 
     * @return this Booking's bookedFor
     */
    public Court getCourt() {
       return bookedFor;
    }
    
    /**
     * Changes the bookFor of this Booking
     * 
     * @param bookFor This Booking's new bookFor 
     */
    public void setCourt(Court bookedFor) {
       this.bookedFor = bookedFor;
    }
    
    /**
     * Constructor for objects of class Booking
     */
    public Booking(int bookingId, 
                   LocalDate bookingDate, 
                   LocalTime startTime,
                   LocalTime endTime,
                   Member bookedBy,
                   Court bookedFor)
    {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookedBy = bookedBy;
        this.bookedFor = bookedFor;
        i++;
    }
    
    /**
     * Constructor for objects of class Booking
     */
    public Booking(LocalDate bookingDate, 
                   LocalTime startTime,
                   LocalTime endTime,
                   Member bookedBy,
                   Court bookedFor)
    {
        this.bookingId = i++;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookedBy = bookedBy;
        this.bookedFor = bookedFor;
    }
    
    /**
     * Save Club's sports data as Strings
     * params dataList data for save in file 
     */
    public void saveBooking(ArrayList<String> dataList){
        dataList.add(bookingId + "," + DateUtility.dateToString(bookingDate)  + "," + DateUtility.timeToString(startTime)  + "," + DateUtility.timeToString(endTime) + "," + bookedBy.getNumber() + "," + bookedFor.getCourtId());
    }
    
    /**
     * String representation of Booking object.
     *
     * @return this Booking's string representation  
     */
    public String toString()
    {
        return "Booking ID: " + bookingId + ", Booking Date: " + bookingDate + ", Start Time: " + startTime + ", End Time: " + endTime + ", Booked By Member ID: " + bookedBy.getNumber() + ", Booked For CourtID: " + bookedFor.getCourtId(); 
    }
}
