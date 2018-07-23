import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.time.*;
import java.util.*;

/**
 * For showing the different GUI for booking
 *
 *  @author Upal Roy
 *  @version 1.0.0
 */
public class BookingGUI
{
    private Club sportsClub;
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel messageLabel, availableCourtsLabel; 
    private JTextField memberIdTextField, sportNameTextField, bookingDateTextField, startTimeTextField, endTimeTextField, courtIdTextField;

    public BookingGUI(Club sportsClub)
    {
        this.sportsClub = sportsClub;

        frame = new JFrame("Make Booking"); 
        frame.setSize(800,500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(makeMessagePanel(),BorderLayout.NORTH);
        mainPanel.add(makeForm(), BorderLayout.CENTER);
        mainPanel.add(makeButtonPanel(), BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Make the Form of the GUI.
     *
     * @return the form after building 
     */
    public JPanel makeForm(){
        JPanel formPanel = new JPanel(new GridLayout(5,2));

        JLabel memberIdLabel = new JLabel("Member ID");
        memberIdTextField = new JTextField(10);

        JLabel sportNameLabel = new JLabel("Sport Name");
        sportNameTextField = new JTextField(10);

        JLabel bookingDateLabel = new JLabel("Date of booking (dd/MM/yyyy)");
        bookingDateTextField = new JTextField(10);

        JLabel startTimeLabel = new JLabel("Start time of booking(HH:mm)");
        startTimeTextField = new JTextField(10);

        JLabel endTimeLabel = new JLabel("End time of booking(HH:mm)");
        endTimeTextField = new JTextField(10);

        formPanel.add(memberIdLabel);
        formPanel.add(memberIdTextField);
        formPanel.add(sportNameLabel);
        formPanel.add(sportNameTextField);
        formPanel.add(bookingDateLabel);
        formPanel.add(bookingDateTextField);
        formPanel.add(startTimeLabel);
        formPanel.add(startTimeTextField);
        formPanel.add(endTimeLabel);
        formPanel.add(endTimeTextField);

        return formPanel;
    }
    
    /**
     * Make the Button pannel of the GUI
     *
     * @return the pannel after building it 
     */
    public JPanel makeButtonPanel(){
        JPanel mainButtonPanel = new JPanel(new GridLayout(4,0));

        JPanel availableCourtButtonPanel = new JPanel(new GridLayout(1,2));
        JPanel courtButtonPanel = new JPanel(new FlowLayout());
        JButton showCourtsButton = new JButton("Show available courts");
        courtButtonPanel.add(showCourtsButton);
        availableCourtButtonPanel.add(new JPanel());
        availableCourtButtonPanel.add(courtButtonPanel);
        mainButtonPanel.add(availableCourtButtonPanel);

        JPanel availableCourtPanel = new JPanel(new GridLayout(1,2));
        JLabel courtIdLabel = new JLabel("Court ID");
        courtIdTextField = new JTextField(10);
        availableCourtPanel.add(courtIdLabel);
        availableCourtPanel.add(courtIdTextField);
        mainButtonPanel.add(availableCourtPanel);

        JPanel bookingButtonPanel = new JPanel(new FlowLayout());
        JButton makeBookingButton = new JButton("Make Booking");
        JButton cancelButton = new JButton("Cancel");

        bookingButtonPanel.add(makeBookingButton);
        bookingButtonPanel.add(cancelButton);

        showCourtsButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showAvailableCourts(); 
                }
            });
        makeBookingButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    makeBooking(); 
                }
            });
        cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closeAvailableCourtWindow(); 
                }
            });

        mainButtonPanel.add(bookingButtonPanel);
        return mainButtonPanel;
    }
    
    
    /**
     * Make the message label of the GUI
     *
     * @return the label after building it 
     */
    public JLabel makeMessagePanel(){
        JPanel messagePanel = new JPanel(new FlowLayout());        

        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Serif", Font.BOLD, 20));
        messageLabel.setOpaque(true);
        messageLabel.setBackground(Color.LIGHT_GRAY);
        messageLabel.setPreferredSize(new Dimension(150, 30));

        messagePanel.add(messageLabel);
        return messageLabel;
    }
    
    /**
     * make booking
     */
    private void makeBooking()
    {
        try
        {
            if (!memberIdTextField.getText().equals("") && !sportNameTextField.getText().equals("") && !bookingDateTextField.getText().equals("") && !startTimeTextField.getText().equals("") && !endTimeTextField.getText().equals("") && !courtIdTextField.getText().equals(""))
            {
                int memberNumber = Integer.parseInt(memberIdTextField.getText().trim());
                Member member = sportsClub.getMemberByNumber(memberNumber);
                long thisBookingDuration = 0;
                long previousTotalBookingDuration = 0;
                if(member != null && member.getNumber() == memberNumber){
                    if(sportsClub.getMemberByNumber(memberNumber).getFinancial()){
                        String sportName = sportNameTextField.getText().trim();
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
                                LocalDate bookingDate = DateUtility.convertDate(bookingDateTextField.getText().trim());
                                if(bookingDate.isAfter(LocalDate.now()) && bookingDate.isBefore(LocalDate.now().plusDays(7)))
                                {
                                    LocalTime bookingStartTime = DateUtility.convertTime(startTimeTextField.getText().trim());
                                    LocalTime bookingEndTime = DateUtility.convertTime(endTimeTextField.getText().trim());
                                    if((bookingStartTime.getHour() >= 8) && bookingEndTime.getHour() <= (22)){

                                        //IOUtility.println("Select a court from the following available court list:");  

                                        int courtId =  Integer.parseInt(courtIdTextField.getText().trim());
                                        thisBookingDuration = Duration.between(bookingStartTime, bookingEndTime).toMinutes();
                                        previousTotalBookingDuration = sportsClub.findTotalBookingTimeForACourtByAMember(courtId, bookingDate, memberNumber);
                                        long totalDuration = thisBookingDuration + previousTotalBookingDuration;                            
                                        if(totalDuration <= sport.getHighestHoursBookingPerDay()*60){                   
                                            ArrayList<Court> availableCourtList =  sportsClub.findAvailableCourts(sport,bookingDate, bookingStartTime ,bookingEndTime);
                                            if(availableCourtList.size() > 0){
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
                                                    messageLabel.setText("Booking Successful Completed."); 
                                                }
                                                else
                                                {
                                                    throw new Exception("Either your Chosen court is not valid or already booked by another user.");
                                                }
                                            } 
                                            else
                                            {
                                                throw new Exception("No available court found.");
                                            }
                                        } 
                                        else
                                        {
                                            throw new Exception("You can only book this " + sportName +" court (court ID: "+ courtId +") for " + sport.getHighestHoursBookingPerDay() + " hours per day for");
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
            else
            {
                throw new Exception("Please fill all the fields with corect information");
            }
        }
        catch(DateTimeException ex){
            messageLabel.setText("Please enter the date and time in mentioned format.");
        }
        catch(MemberValidationException ex){
            messageLabel.setText(ex.getMessage());
        }
        catch(SevenDaysBookingValidationException ex){
            messageLabel.setText(ex.getMessage());
        }
        catch(Exception ex){
            messageLabel.setText(ex.getMessage());
        }
    }

    /**
     * Shows available courts
     */
    private void showAvailableCourts()
    {

        try
        {
            if (!sportNameTextField.getText().equals("") && !bookingDateTextField.getText().equals("") && !startTimeTextField.getText().equals("") && !endTimeTextField.getText().equals(""))
            {
                String sportName = sportNameTextField.getText().trim();
                Sport sport = sportsClub.getSportByName(sportName);
                if(sport != null){
                    LocalDate bookingDate = DateUtility.convertDate(bookingDateTextField.getText().trim()); 
                    if(bookingDate.isAfter(LocalDate.now()) && bookingDate.isBefore(LocalDate.now().plusDays(7)))
                    {
                        LocalTime bookingStartTime = DateUtility.convertTime(startTimeTextField.getText().trim());
                        LocalTime bookingEndTime = DateUtility.convertTime(endTimeTextField.getText().trim());
                        if((bookingStartTime.getHour() >= 8) && bookingEndTime.getHour() <= (22)){
                            if(bookingStartTime.compareTo(bookingStartTime.plusHours(sport.getHighestHoursBookingPerDay())) <= 0){
                                ArrayList<Court> availableCourtList =  sportsClub.findAvailableCourts(sport,bookingDate, bookingStartTime ,bookingEndTime);
                                if(availableCourtList.size() > 0){
                                    String[] availableCourtArray = new String[availableCourtList.size()];
                                    for (int i = 0; i < availableCourtList.size(); i++)
                                    {
                                        availableCourtArray[i] = availableCourtList.get(i).toString();
                                    }

                                    String availableCourtString = String.join(", ", availableCourtArray);
                                    messageLabel.setText("List of available courts are: " + availableCourtString);
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
                        else
                        {
                            throw new Exception("Booking from 9 am to 10 pm is acceptable.");
                        }
                    }
                    else
                    {
                        throw new SevenDaysBookingValidationException("Booking up to seven days is acceptable.");
                    }
                }
                else
                {
                    throw new Exception("No such sports exist in the club.");
                }
            }
            else
            {
                throw new Exception("Please fill all the fields with corect information");
            }
        }
        catch(DateTimeException ex){
            messageLabel.setText("Please enter the date and time in mentioned format.");
        }
        catch(SevenDaysBookingValidationException ex){
            messageLabel.setText(ex.getMessage());
        }
        catch(Exception ex){
            messageLabel.setText(ex.getMessage());
        }
    }

    /**
     * For closeing the GUI
     */
    private void closeAvailableCourtWindow(){
        frame.setVisible(false);
        frame.dispose();
    }
}

