import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.time.Duration;

/**
 * UserInterface class for modeling UserInterface.
 * 
 * @author Upal Roy
 * @version 1.0.0
 */
public class UserInterface
{
    private Club sportsClub;
    private Scanner sc = new Scanner(System.in);

    /**
     * Constructor for objects of class UserInterface
     */
    public UserInterface(Club sportsClub)
    {
        this.sportsClub = sportsClub;
    }

    /**
     * Shows menu and take input  to run applications
     */
    public void run()
    {
        while(true)
            switch (menu() )
            {
                case 1:
                showAvailableCourts();
                break;
                case 2:
                makeBooking();
                break;
                case 3:
                showMemberBookings();
                break;
                case 4:
                showCourtBookings();
                case 5:
                deleteBooking();
                break;
                case 6:
                return;
                default:
                System.out.println ( "Invalid option" );
                break;
            }
    }

    /**
     * Shows menu
     */
    private int menu()
    {
        System.out.println("|-------------------------------------------------|");
        System.out.println("| 1 - Show Available Courts                       |");
        System.out.println("| 2 - Make Booking for Member                     |");
        System.out.println("| 3 - Show Member Bookings                        |");
        System.out.println("| 4 - Show Court Bookings                         |");
        System.out.println("| 5 - Delete Booking                              |");
        System.out.println("| 6 - Exit                                        |" );
        System.out.println("|-------------------------------------------------|");
        System.out.println("Select your option (enter a selection number): ");
        int option = sc.nextInt();
        sc.nextLine();
        return  option;
    }

    /**
     * Shows available courts
     */
    private void showAvailableCourts()
    {
        try
        {
            String sportName = IOUtility.getString("Enter Name of the Sport:").trim();
            Sport sport = sportsClub.getSportByName(sportName);

            if(sport != null){
                LocalDate bookingDate = IOUtility.getLocalDate("Enter date of booking (dd/MM/yyyy):");

                if(bookingDate.isAfter(LocalDate.now()) && bookingDate.isBefore(LocalDate.now().plusDays(7)))
                {
                    LocalTime bookingStartTime = IOUtility.getLocalTime("Enter the start time for your booking(HH:mm):");
                    LocalTime bookingEndTime = IOUtility.getLocalTime("Enter the end time for your booking(HH:mm):");
                    if((bookingStartTime.getHour() >= 8) && bookingEndTime.getHour() <= (22)){
                        if(bookingStartTime.compareTo(bookingStartTime.plusHours(sport.getHighestHoursBookingPerDay())) <= 0){
                            ArrayList<Court> availableCourtList =  sportsClub.findAvailableCourts(sport,bookingDate, bookingStartTime ,bookingEndTime);
                            if(availableCourtList.size() > 0){
                                IOUtility.println("List of available courts:");
                                for(Court court : availableCourtList){
                                    IOUtility.println("Court ID: " + court.toString());
                                }
                            }
                            else
                            {
                                throw new Exception("No available court found.");
                            }
                        }
                        else
                        {
                            throw new Exception("You can only book  " + sport.getHighestHoursBookingPerDay() + " per day for " + sportName);
                        }
                    }
                    else{
                        throw new Exception("Booking from 9 am to 10 pm is acceptable.");
                    }
                }
                else{
                    throw new SevenDaysBookingValidationException("Booking up to seven days is acceptable.");
                }
            }
            else{
                throw new Exception("No such sports exist in the club.");
            }
        }
        catch(SevenDaysBookingValidationException ex){
            IOUtility.println(ex.getMessage());
        }
        catch(Exception ex){
            IOUtility.println(ex.getMessage());
        }
    }

    /**
     * make booking
     */
    private void makeBooking()
    {
        try
        {
            int memberNumber = IOUtility.getInteger("Enter your member number:");
            Member member = sportsClub.getMemberByNumber(memberNumber); 
            long thisBookingDuration = 0;
            long previousTotalBookingDuration = 0;
            if(member != null && member.getNumber() == memberNumber){
                if(sportsClub.getMemberByNumber(memberNumber).getFinancial()){
                    String sportName = IOUtility.getString("Enter sport's name for booking:");
                    Sport sport = sportsClub.getSportByName(sportName);

                    if(member.getSportsPlayed().size() > 0){

                        boolean memberPlayThisSport = false;
                        for(String memberPlaySport  : member.getSportsPlayed())
                        {
                            if(memberPlaySport.equalsIgnoreCase(sportName))
                            {
                                memberPlayThisSport = true;
                            }
                        }

                        if(memberPlayThisSport){
                            LocalDate bookingDate = IOUtility.getLocalDate("Enter date of booking (dd/MM/yyyy):");
                            if(bookingDate.isAfter(LocalDate.now()) && bookingDate.isBefore(LocalDate.now().plusDays(7)))
                            {
                                LocalTime bookingStartTime = IOUtility.getLocalTime("Enter the start time for your booking (HH:mm):");
                                LocalTime bookingEndTime = IOUtility.getLocalTime("Enter the end time for your booking (HH:mm):");
                                if((bookingStartTime.getHour() >= 8) && bookingEndTime.getHour() <= (22)){
                                    //if(Duration.between(bookingStartTime, bookingEndTime).toHours() <= sport.getHighestHoursBookingPerDay()){
                                    IOUtility.println("Select a court from the following available court list:");  
                                    ArrayList<Court> availableCourtList =  sportsClub.findAvailableCourts(sport,bookingDate, bookingStartTime ,bookingEndTime);

                                    if(availableCourtList.size() > 0){

                                        for(Court court : availableCourtList){
                                            System.out.println(court.toString());
                                        }

                                        int courtId = IOUtility.getInteger("Enter Court Number:");

                                        thisBookingDuration = Duration.between(bookingStartTime, bookingEndTime).toMinutes();
                                        previousTotalBookingDuration = sportsClub.findTotalBookingTimeForACourtByAMember(courtId, bookingDate, memberNumber);
                                        long totalDuration = thisBookingDuration + previousTotalBookingDuration;                            

                                        if(totalDuration <= sport.getHighestHoursBookingPerDay()*60){
                                            boolean isvalidCourt = false;
                                            for(Court court : availableCourtList)
                                            {
                                                if(court.getCourtId() == courtId)
                                                {
                                                    isvalidCourt = true;
                                                    break;
                                                }
                                            }

                                            if(isvalidCourt)
                                            {
                                                Court validCourt = sportsClub.getCourtByCourtId(courtId);
                                                Booking newBooking = new Booking(bookingDate, bookingStartTime, bookingEndTime, member, validCourt);
                                                sportsClub.finalizeBooking(newBooking);
                                                IOUtility.println("Booking Successful Completed."); 
                                            }
                                            else
                                            {
                                                throw new Exception("Either your Chosen court is not valid or already booked by another user.");
                                            }
                                        } 
                                        else
                                        {
                                            throw new Exception("You can only book this " + sportName +" court (court ID: "+ courtId +") for " + sport.getHighestHoursBookingPerDay() + " hours per day for");
                                        }
                                    } 
                                    else
                                    {
                                        throw new Exception("No available court found.");
                                    }
                                }
                                else{
                                    throw new Exception("Booking from 9 am to 10 pm is acceptable.");
                                }
                            }
                            else
                            {
                                throw new SevenDaysBookingValidationException("Booking up to seven days is acceptable.");
                            }
                        }
                        else{
                            throw new Exception("Member is not  elected to play " + sportName + ". Try to book for other sports.");
                        }

                    }
                    else
                    {
                        throw new Exception("Member is not  elected to play any sports.");
                    }
                }
                else
                {
                    throw new Exception("Member is not financial.");
                }
            }
            else
            {
                throw new MemberValidationException("No member found with this member id.");
            }
        }
        catch(MemberValidationException ex){
            IOUtility.println(ex.getMessage());
        }
        catch(SevenDaysBookingValidationException ex){
            IOUtility.println(ex.getMessage());
        }
        catch(Exception ex){
            IOUtility.println(ex.getMessage());
        }
    }

    /**
     * Shows Member's booking
     */
    public void showMemberBookings()
    {
        int memberNumber = IOUtility.getInteger("Enter the member ID:");

        if(sportsClub.getMemberByNumber(memberNumber) != null){
            ArrayList<Booking> membersBookingList = sportsClub.findBookingsByMemberNumber(memberNumber);
            if(membersBookingList.size() != 0){
                for(Booking booking : membersBookingList)
                {
                    IOUtility.println(booking.toString());
                }
            }
            else
            {
                IOUtility.println("No booking found for this member.");
            }
        }
        else
        {
            IOUtility.println("Wrong member ID. No such member exist.");
        }
    }

    /**
     * Shows Courts's booking
     */
    private void showCourtBookings()
    {
        int courtId = IOUtility.getInteger("Enter the court ID:");
        if(sportsClub.getCourtByCourtId(courtId).getCourtId() == courtId)
        {
            ArrayList<Booking> courtBookingList = sportsClub.findBookingsByCourtId(courtId);
            if(courtBookingList.size() != 0){
                for(Booking booking : courtBookingList)
                {
                    IOUtility.println(booking.toString());
                }
            }
            else
            {
                IOUtility.println("No booking found for this court.");
            }
        }
        else
        {
            IOUtility.println("Wrong court ID. No such court exist.");
        }
    }

    /**
     * Delete booking
     */

    private void deleteBooking()
    {
        int memberNumber = IOUtility.getInteger("Enter the member number:");
        LocalDate bookingDate = IOUtility.getLocalDate("Enter date of booking:");
        LocalTime bookingStartTime = IOUtility.getLocalTime("Enter the start time of booking:");

        if(sportsClub.deleteBooking(memberNumber, bookingDate, bookingStartTime))
        {
            IOUtility.println("Booking successfully deleted.");
        }
        else
            IOUtility.println("No booking found with the entered information. Enter valid Inforamtion.");
    }

    private void listMemberBooking()
    {
    }

} // end class
