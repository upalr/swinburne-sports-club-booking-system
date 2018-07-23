import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.time.*;
import java.util.*;

/**
 *For showing the available GUI of this application
 *
 * @author Upal Roy
 *  \@version 1.0.0
 */
public class AvailableCourtsGUI
{
    private Club sportsClub;
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel messageLabel;
    private JTextField sportNameTextField, bookingDateTextField, startTimeTextField, endTimeTextField;

    public AvailableCourtsGUI(Club sportsClub){
        this.sportsClub = sportsClub;

        frame = new JFrame("Available Courts"); 
        frame.setSize(600,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(makeMessagePanel(),BorderLayout.NORTH);
        mainPanel.add(makeForm());

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
        JPanel formPanel = new JPanel(new GridLayout(6,2));

        JLabel sportNameLabel = new JLabel("Sport Name");
        sportNameTextField = new JTextField(10);

        JLabel bookingDateLabel = new JLabel("Date of booking (dd/MM/yyyy)");
        bookingDateTextField = new JTextField(10);

        JLabel startTimeLabel = new JLabel("Start time of booking(HH:mm)");
        startTimeTextField = new JTextField(10);

        JLabel endTimeLabel = new JLabel("End time of booking(HH:mm)");
        endTimeTextField = new JTextField(10);

        JButton showCourtsButton = new JButton("Show available courts");
        JButton CancelButton = new JButton("Cancel");

        formPanel.add(sportNameLabel);
        formPanel.add(sportNameTextField);
        formPanel.add(bookingDateLabel);
        formPanel.add(bookingDateTextField);
        formPanel.add(startTimeLabel);
        formPanel.add(startTimeTextField);
        formPanel.add(endTimeLabel);
        formPanel.add(endTimeTextField);
        formPanel.add(new JPanel());
        formPanel.add(makeButtonPanel());

        return formPanel;
    }

    /**
     * Make the Button pannel of the GUI
     *
     * @return the pannel after building it 
     */
    public JPanel makeButtonPanel(){
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton showCourtsButton = new JButton("Show available courts");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(showCourtsButton);
        buttonPanel.add(cancelButton);

        showCourtsButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showAvailableCourts(); 
                }
            });
        cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closeAvailableCourtWindow(); 
                }
            });

        return buttonPanel;
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
