import java.util.*;
import java.time.*;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Club class for modeling real Club.
 * 
 * @author Upal Roy
 * @version 1.0.0
 */
public class Club
{
    // instance variables - name, memberList, sportList and bookingList represent name, member list, sport list and booking list of each club respectively
    private String name;
    private ArrayList<Member> memberList;
    private ArrayList<Sport> sportList;
    private ArrayList<Booking> bookingList;
    
    /**
     * Gets the name of this Club
     * 
     * @return this Club's name
     */
    public String getName() {
       return name;
    }
    
    /**
     * Changes the name of this Club
     * 
     * @param name This Club's new name 
     */
    public void setName(String name) {
       this.name = name;
    }
   
    /**
     * Constructor for objects of class Club
     */
    public Club(String name)
    {
        this.name = name;
        try
        {
            memberList = new ArrayList<Member>();
            sportList = new ArrayList<Sport>(); 
            bookingList = new ArrayList<Booking>();
            
            getMembersFromFile();
            getSportsFromFile();
            getBookingsFromFile();
        }
        catch(Exception ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
    
    /**
     * Find  member from member's list 
     *
     * @param  number  first paramter of getMemberByNumber method. Represents member's number
     * @return  member  Return the member if matches with the number. Otherwise return null
     */
    public Member getMemberByNumber(int number){
        for(Member member : memberList){
            if(member.getNumber() == number)
                return member;
        }
        return null;
    }
    
    /**
     * Find  sport from sport's list 
     *
     * @param  name  first paramter of getSportByName method. Represents sport's name
     * @return  sport  Return the sport if matches with the name. Otherwise return null
     */
    public Sport getSportByName(String name){
        for(Sport sport : sportList){
            if(sport.getName().equalsIgnoreCase(name))
                return sport;
        }
        return null;
    }
    
    /**
     * Find  court from court's list 
     *
     * @param  courtId  first paramter of getCourtByCourtId method. Represents courts's Id
     * @return  court  Return the court if matches with the courtId. Otherwise return null
     */
    public Court getCourtByCourtId(int courtId){
        for(Sport sport : sportList){
            for(Court court : sport.courtList){
                if(court.getCourtId() == courtId)
                    return court;
            }       
        }
        return null;
    }
    
    /**
     * Return the whole booking list
     *
     * @return  BookingList  Return all the bookings 
     */
    public ArrayList<Booking> getAllBookings(){
        return bookingList;
    }
    
    /**
     * Find  Booking from booking's list 
     *
     * @param  memberNumber  first paramter of getBookingByMemberNumberAndDate method. Represents member's number
     * @param  bookingDate  second paramter of getBookingByMemberNumberAndDate method. Represents booking date
     * @param  bookingStartTime  third paramter of getBookingByMemberNumberAndDate method. Represents booking's start time
     * @return  Booking  Return the booking if matches with the parameters. Otherwise return null
     */
    public Booking getBookingByMemberNumberAndDate(int memberNumber, LocalDate bookingDate, LocalTime bookingStartTime){
        for(Booking booking : bookingList){
            if(booking.getMember().getNumber() == memberNumber && booking.getBookingDate().compareTo(bookingDate) == 0 && booking.getStartTime().compareTo(bookingStartTime) == 0)
                return booking;
        }
        return null;
    }
    
    /**
     * Read member's data from file and build member objects.
     */
    public void getMembersFromFile()throws IOException, FileNotFoundException
    {
        ArrayList<String> linesList = FileUtility.readFromFile("./../Members.txt");
    
        for(String currentLine : linesList)
        {
            memberList.add(new Member(currentLine));
        }
    }
    
    /**
     * Read Sport's data from file and build sport objects.
     */
    public void getSportsFromFile()throws IOException, FileNotFoundException
    {
        ArrayList<String> linesList = FileUtility.readFromFile("./../Sports.txt");
    
        for(String currentLine : linesList)
        {
            String[] sportInfo = currentLine.split(",");
            ArrayList<Court> courtNumbersForSport = new ArrayList<Court>(); 
            
            if(sportInfo.length > 3){
                for(int i = 3 ; i < sportInfo.length; i++)
                {
                    courtNumbersForSport.add(new Court(Integer.parseInt(sportInfo[i])));
                }
            }
            
            if(sportInfo[0].equals("Badminton"))
                sportList.add(new Badminton(sportInfo[0], Double.parseDouble(sportInfo[1]), Double.parseDouble(sportInfo[2]),courtNumbersForSport, true));   
            else if(sportInfo[0].equals("Basketball"))
                sportList.add(new Basketball(sportInfo[0], Double.parseDouble(sportInfo[1]), Double.parseDouble(sportInfo[2]),courtNumbersForSport, 9.00));
        }
    }
    
    /**
     * Read member data from file and build member objects.
     */
    public void getBookingsFromFile()throws IOException, FileNotFoundException
    {
        ArrayList<String> linesList = FileUtility.readFromFile("./../ClubBookingData.txt");
    
        for(String currentLine : linesList)
        {
            String[] bookingInfo = currentLine.split(",");
            
            Member bookedBy = getMemberByNumber(Integer.parseInt(bookingInfo[4])); 
            Court bookedFor = getCourtByCourtId(Integer.parseInt(bookingInfo[5]));
            bookingList.add(new Booking(Integer.parseInt(bookingInfo[0]),DateUtility.convertDate(bookingInfo[1]), DateUtility.convertTime(bookingInfo[2]), DateUtility.convertTime(bookingInfo[3]),bookedBy, bookedFor));
        }
    }
    
    /**
     * Find  available courts 
     *
     * @param  sport  first paramter of findAvailableCourts method. Represents member's number
     * @param  bookingDate  second paramter of findAvailableCourts method. Represents booking date
     * @param  bookingStartTime  third paramter of findAvailableCourts method. Represents booking's start time
     * @param  bookingEndTime  fourth paramter of findAvailableCourts method. Represents booking's end time
     * @return  BookingList  Return the bookinglist after filtering
     */
    public ArrayList<Court> findAvailableCourts(Sport sport, LocalDate bookingDate, LocalTime bookingStartTime, LocalTime bookingEndTime){
        ArrayList<Court> availableCourtList = new ArrayList<Court>();
        
        for(Court court : sport.getCourts()){
            if(checkAvailability(court.getCourtId(), bookingDate, bookingStartTime, bookingEndTime) == true) // check not
                 availableCourtList.add(court);
        } 
        return availableCourtList;        
    }
    
    /**
     * Check availibily of courts
     *
     * @param  courtId  first paramter of checkAvailability method. Represents court's id
     * @param  bookingDate  second paramter of checkAvailability method. Represents booking date
     * @param  bookingStartTime  third paramter of checkAvailability method. Represents booking's start time
     * @param  bookingEndTime  fourth paramter of checkAvailability method. Represents booking's end time
     * @return  boolean  Return true is the court is available otherwise return false
     */
    public boolean checkAvailability(int courtId, LocalDate bookingDate, LocalTime bookingStartTime, LocalTime bookingEndTime){
        ArrayList<Booking> filteredBookingList = findBookingsByCourtIdAndDate(courtId, bookingDate);
        boolean available = true;
        if(bookingList.size() > 0){
            for(Booking booking : filteredBookingList){

                if((bookingStartTime.isBefore(booking.getStartTime()) && 
                   bookingEndTime.isBefore(booking.getStartTime())) || 
                   (bookingStartTime.isAfter(booking.getEndTime()) && 
                   bookingEndTime.isAfter(booking.getEndTime())))
                    available = true;
                else{
                    available = false;
                    break;
                }
            }
        }
        return available;
    }
    
    /**
     * Find booking for a court
     *
     * @param  memberNumber  first paramter of findBookingsByMemberNumber method. Represents member's number
     * @return  BookingList  Return filtered booking list
     */
    public ArrayList<Booking> findBookingsByMemberNumber(int memberNumber){
        ArrayList<Booking> membersBookingList = new ArrayList<Booking>();
        
        for(Booking booking : bookingList){
            if(booking.getMember().getNumber() == memberNumber){
                membersBookingList.add(booking);
            }
        } 
        return membersBookingList;
    }
    
    /**
     * Find booking for a court
     *
     * @param  courtId  first paramter of findBookingsByCourtId method. Represents court's Id
     * @return  BookingList  Return filtered booking list
     */
    public ArrayList<Booking> findBookingsByCourtId(int courtId){
        ArrayList<Booking> courtBookingList = new ArrayList<Booking>();
        for(Booking booking : bookingList){
            if(booking.getCourt().getCourtId() == courtId){
                courtBookingList.add(booking);
            }
        }
        
        return courtBookingList;
    }
    
    /**
     * Find booking for a court
     *
     * @param  courtId  first paramter of findBookingsByCourtIdAndDate method. Represents court's id
     * @param  bookingDate  second paramter of findBookingsByCourtIdAndDate method. Represents booking date
     * @return  BookingList  Return filtered booking list
     */
    public ArrayList<Booking> findBookingsByCourtIdAndDate(int courtId, LocalDate bookingDate){
        ArrayList<Booking> filteredBookingList = new ArrayList<Booking>();
        for(Booking booking : bookingList){           
           if(booking.getBookingDate().compareTo(bookingDate) == 0 && booking.getCourt().getCourtId() == courtId)
                filteredBookingList.add(booking);
        }

        return filteredBookingList;
    }
            
    /**
     * Calculate total booking time for a court by a member
     *
     * @param  courtId  first paramter of findBookingsByCourtIdAndDate method. Represents court's id
     * @param  bookingDate  second paramter of findBookingsByCourtIdAndDate method. Represents booking date
     * @param  memberNumber  first paramter of findBookingsByMemberNumber method. Represents member's number
     * @return  BookingList  Return filtered booking list
     */
    public long findTotalBookingTimeForACourtByAMember(int courtId, LocalDate bookingDate, int memberNumber){
        long totalDuration= 0; 
        for(Booking booking : bookingList){           
           if(booking.getBookingDate().compareTo(bookingDate) == 0 && booking.getCourt().getCourtId() == courtId && booking.getMember().getNumber() == memberNumber)                 
                //filteredBookingList.add(booking);
                 totalDuration += Duration.between(booking.getStartTime(),booking.getEndTime()).toMinutes();
        }

        return totalDuration;
    }
    
    /**
     * Finalize the booking
     *
     * @param  newBooking  first paramter of finalizeBooking method. Represents booking
     */
    public void finalizeBooking(Booking newBooking){
        bookingList.add(newBooking);
        sortingBookings();
    }
    
    /**
     * Delete booking
     *
     * @param  memberNumber  first paramter of deleteBooking method. Represents member's number
     * @param  bookingDate  second paramter of deleteBooking method. Represents booking date
     * @param  bookingStartTime  third paramter of deleteBooking method. Represents booking's start time
     * @return  boolean  Return true is delete is successful otherwise return false
     */
    public boolean deleteBooking(int memberNumber, LocalDate bookingDate, LocalTime bookingStartTime){
         Booking booking = getBookingByMemberNumberAndDate(memberNumber, bookingDate, bookingStartTime);
        if(booking != null){
            bookingList.remove(booking);
            return true;
        }
        return false;
    }
    
    /**
     * Save Club's sports data as Strings
     */
    public void saveClub() throws IOException
    {
        ArrayList<String> dataList = new ArrayList<String>();
    
        for(Booking booking : bookingList)
        {
            booking.saveBooking(dataList);
        }
       
        FileUtility.writeToFile("./../ClubBookingData.txt",dataList);
    }
    
    /**
     * Sort booking list
     */
    public void sortingBookings()
    {
        Collections.sort(bookingList, new BookingComparator());
    }
    
    /**
     * String representation of club object.
     *
     * @return this club's string representation  
     */
    public String toString()
    {
        return "Club Name: " + this.name;
    }
    
}