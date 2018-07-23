import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.*;
import java.util.*;
/**
 * For showing the main GUI of this application
 *
 * @author Upal Roy
 * @version 1.0.0
 */
public class MasterGUI
{
    private Club sportsClub;
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel messageLabel;
    private JScrollPane scrollPane;
    private JTable bookingTable;
    
    public MasterGUI(Club sportsClub){
        this.sportsClub = sportsClub;
    }
    
    /**
     * For building the main GUI
     *
     */
    public void run()
    {
        frame = new JFrame("Sports Club");   
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                exit();
            }
        });
        mainPanel = new JPanel(new BorderLayout());
        
        makeMenuBar(frame);
        makeMessagePanel();

        makeBookingTableWithAllBookings();
        frame.getContentPane().add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    
    }
    
    /**
     * Make the Menu of the GUI.
     */
    private void makeMenuBar(JFrame frame)
    {    
        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(150, 70));
        frame.setJMenuBar(menuBar);
    
        JMenuItem availableCourtsMenuItem = new JMenuItem("Show Available Courts");
        menuBar.add(availableCourtsMenuItem);
        JMenuItem makeBookingMenuItem = new JMenuItem("Make Booking for Member");
        menuBar.add(makeBookingMenuItem);
        JMenuItem showMemberBookingMenuItem = new JMenuItem("Show Member Bookings");
        menuBar.add(showMemberBookingMenuItem);
        JMenuItem showCourtBookingMenuItem = new JMenuItem("Show Court Bookings");
        menuBar.add(showCourtBookingMenuItem);
        JMenuItem deleteBookingMenuItem = new JMenuItem("Delete Booking ");
        menuBar.add(deleteBookingMenuItem); 
        
        availableCourtsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AvailableCourtsGUI(sportsClub);
            }
        });
        
        makeBookingMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BookingGUI(sportsClub);
            }
        });
        
        showMemberBookingMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMemberBookings(); 
            }
        });
        
        showCourtBookingMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCourtBookings(); 
            }
        });
        deleteBookingMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteBooking(); 
            }
        });

    }
    
    /**
     * Make the Message panel of the GUI.
     *
     * @return the form after building 
     */
    
    public void makeMessagePanel(){
         
        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Serif", Font.BOLD, 20));
        messageLabel.setOpaque(true);
        messageLabel.setBackground(Color.LIGHT_GRAY);
        messageLabel.setPreferredSize(new Dimension(150, 30));
        
        mainPanel.add(messageLabel,BorderLayout.NORTH);
    }
    
    /**
     * Make table of the GUI.
     */
    public void makeBookingTable(ArrayList<Booking> bookingList)
    {
        
        if (scrollPane != null){
            mainPanel.remove(scrollPane);;
        }

        try
        {            
            bookingTable = new JTable();
            bookingTable.setModel(new BookingTableModel(bookingList));
            
            if(sportsClub.getAllBookings().isEmpty())
                messageLabel.setText("Booking table is empty. No Booking Found");  
        }
        catch (Exception ex)
        {
            messageLabel.setText("Booking table is empty. No Booking Found");
        }
        scrollPane = new JScrollPane(bookingTable);
        mainPanel.add(scrollPane);
        frame.validate(); 
        frame.repaint();  
    }
    
    /**
     * Shows Member's booking
     */
    private void showMemberBookings(){
        int memberNumber = IOUtility.getIntegerFromDialog("Enter the member ID (Must be a Integer value):");
        if(memberNumber == 0)
            return;
        
        if(sportsClub.getMemberByNumber(memberNumber) != null){
            ArrayList<Booking> membersBookingList = sportsClub.findBookingsByMemberNumber(memberNumber);
            if(membersBookingList.size() != 0){
                makeBookingTable(membersBookingList);
            }   
            else
            {
                messageLabel.setText("No booking found for this member.");
                makeBookingTable(new ArrayList<Booking>());
            }
        }
        else
        {
            messageLabel.setText("Wrong member ID. No such member exist.");
            makeBookingTable(new ArrayList<Booking>());
        }
    }
    
    /**
     * Shows Courts's booking
     */
    private void showCourtBookings(){
           int courtId = IOUtility.getIntegerFromDialog("Enter the court ID (Must be a Integer value):");
           if(courtId == 0)
                return;
            
           if(sportsClub.getCourtByCourtId(courtId) != null)
           {
               ArrayList<Booking> courtBookingList = sportsClub.findBookingsByCourtId(courtId);
               if(courtBookingList.size() != 0){
                   makeBookingTable(courtBookingList);
               }
               else
               {
                    messageLabel.setText("No booking found for this court.");
                    makeBookingTable(new ArrayList<Booking>());
               }
           }
           else
           {
               messageLabel.setText("Wrong court ID. No such court exist.");
               makeBookingTable(new ArrayList<Booking>());
           }
           
    }
    
    /**
     * Delete booking
     */
    private void deleteBooking(){
       // Booking selectedBooking = BookingTableModel.getDataVector().elementAt(bookingTable.getSelectedRow());
       int selectedRowIndex = bookingTable.getSelectedRow();
       if(selectedRowIndex == -1){
           JOptionPane.showMessageDialog(frame,"Please select a Booking from the table to delete");
       }
       else
       {
           int bookingNumber = Integer.parseInt(bookingTable.getValueAt(selectedRowIndex, 0).toString());
           int memberNumber = Integer.parseInt(bookingTable.getValueAt(selectedRowIndex, 4).toString());
           LocalDate bookingDate = DateUtility.convertDate(bookingTable.getValueAt(selectedRowIndex, 1).toString(), "yyyy-MM-dd");
           LocalTime bookingStartTime = DateUtility.convertTime(bookingTable.getValueAt(selectedRowIndex, 2).toString());
           
           int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete booking number: " + bookingNumber, "Delete Confirmation", JOptionPane.YES_NO_OPTION);
           if(option == JOptionPane.YES_OPTION) {
               try
               {
                   if(sportsClub.deleteBooking(memberNumber, bookingDate, bookingStartTime))
                   {
                       messageLabel.setText("Booking successfully deleted.");
                    }
                    else   
                      messageLabel.setText("No booking found with the entered information. Enter valid Inforamtion.");
              }
              catch(Exception ex){
                  messageLabel.setText("Error: Could not delete the booking." + "\n" + ex.getMessage());
              }
              makeBookingTableWithAllBookings();
           }
       }
    }
    
    /**
     * Show all the booking in the table
     */
    private void makeBookingTableWithAllBookings(){
        makeBookingTable(sportsClub.getAllBookings());
    }
    
    /**
     * Call on the exit of the application
     */
    public void exit() 
     {
        try
        {
            sportsClub.saveClub();
            System.exit(0);
        }
        catch (Exception ex)
        {
            messageLabel.setText("Error occured.Cannot save the data properly. "  + ex.getMessage());
        } 
     } 
}
