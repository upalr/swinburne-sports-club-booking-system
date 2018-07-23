import java.util.*;
import javax.swing.*;
import java.time.*;
import javax.swing.table.AbstractTableModel;
/**
 * Write a description of class BookingTableModel here.
 *
 * @author Upal Roy
 * @version 1.0.0
 */
public class BookingTableModel extends AbstractTableModel {

    private List<Booking> bookingList = new ArrayList();
    private String[] columnNames = { "Booking ID", "Booking Date", "Start Time",
                "End Time", "Booked By Member ID", "Booked For CourtID"};

    public BookingTableModel(List<Booking> bookingList){
         this.bookingList = bookingList;
    }

    @Override
    public String getColumnName(int columnIndex){
         return columnNames[columnIndex];
    }

    @Override     
    public int getRowCount() {
        return bookingList.size();
    }

    @Override        
    public int getColumnCount() {
        return 6; 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Booking booking = bookingList.get(rowIndex);
        switch (columnIndex) {
            case 0: 
                return  booking.getBookingId();
            case 1:
                return booking.getBookingDate();
            case 2:
                return booking.getStartTime();
            case 3:
                return booking.getEndTime();
            case 4:
                return booking.getMember().getNumber();
            case 5:
                return booking.getCourt().getCourtId(); 
           }
           return null;
   }

   @Override
   public Class<?> getColumnClass(int columnIndex){
          switch (columnIndex){
             case 0:
               return Integer.class;
             case 1:
               return LocalDate.class;
             case 2:
               return LocalTime.class;
             case 3:
               return LocalTime.class;
             case 4:
               return Integer.class;
             case 5:
               return Integer.class;
             }
             return null;
      }
 }
