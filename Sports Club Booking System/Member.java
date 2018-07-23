import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Member class for modeling real Member.
 * 
 * @author Upal Roy
 * @version 1.0.0
 */
public class Member
{
    private String name;
    private int number;
    private ArrayList<String> sportsPlayed;
    private boolean financial;
    
    /**
     * Gets the name of this Member
     * 
     * @return this Member's name
     */
    public String getName(){
       return name;
    }
   
    /**
     * Changes the name of this Member
     * 
     * @param name This Member's new name 
     */
    public void setName(String name) {
       this.name = name;
    }
   
    /**
     * Gets the number of this Member
     * 
     * @return this Member's number
     */
    public int getNumber(){
       return number;
    }
   
    /**
     * Changes the number of this Member
     * 
     * @param number This Member's new number 
     */
    public void setNumber(int number) {
       this.number = number;
    }
    
    /**
     * Gets the financial of this Member
     * 
     * @return this Member's financial
     */
    public boolean getFinancial(){
       return financial;
    }
   
    /**
     * Changes the financial of this Member
     * 
     * @param finincal This Member's new finincial 
     */
    public void setFinancial(boolean financial) {
       this.financial = financial;
    }
    
    /**
     * Gets the sportsPlayed of this Member
     * 
     * @return this Member's sportsPlayed
     */
    public ArrayList<String> getSportsPlayed(){
        return sportsPlayed;
    }
    
    /**
     * Constructor for objects of class Member
     */
    public Member(int number, String name, boolean financial, ArrayList<String> sportsPlayed)
    {
        this.number = number;
        this.name = name;
        this.financial = financial;
        this.sportsPlayed = sportsPlayed;
    }     
    
    /**
     * Constructor for objects of class Member
     */
    public Member(String data)
    {   
        String[] memberInfo = data.split(",");
            
        this.number = Integer.parseInt(memberInfo[0]);
        this.name = memberInfo[1];
        this.financial = Boolean.valueOf(memberInfo[2]);
        
        if(memberInfo.length > 3)
            this.sportsPlayed = new ArrayList<String>(Arrays.asList(memberInfo).subList(3, memberInfo.length));
        else
            this.sportsPlayed = new ArrayList<String>();
    }
    
    /**
     * String representation of Member object.
     *
     * @return this Member's string representation  
     */
    public String toString()
    {
        return "ID: " + this.number +", Name: " + this.name + ", Financial: " + this.financial + ", Sports Played: " + this.sportsPlayed;
    }
    
}