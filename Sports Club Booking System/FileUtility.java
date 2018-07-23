import java.util.*;
import java.io.*;

public class FileUtility
{   
    /**
     * Constructor for objects of class FileUtility
     */
    public FileUtility()
    {
    }
    
    
    /** readFromFile(String fileName) reads object data from the file specified to an ArrayList of Strings.
     * 
     * @param   fileName  The file name(may include tha path) from which object data is read
     * @return  ArrayList<String>  An array list of Strings which consists of object data that is read from the file
     * 
     */
    public static ArrayList<String> readFromFile(String fileName) throws IOException, FileNotFoundException
    {
        ArrayList<String> linesList = new ArrayList<String>();
        BufferedReader bufferReader = new BufferedReader(new FileReader(fileName));
        
        String currentLine;
        while ((currentLine = bufferReader.readLine()) != null) {
            if (!currentLine.startsWith("//"))
                linesList.add(currentLine);
        }
        bufferReader.close();
        return linesList;
    }
    
    
    /** writeToFile(String fileName, ArrayList<String> data) writes the list of Strings in data ArrayList 
     * to the file specified.
     * 
     * @param   fileName  The file name(may include tha path) to which data is written
     * @param   data  An array list of Strings which consists of object data to be saved
     * 
     */
    public static void writeToFile(String fileName, ArrayList<String> linesList) throws IOException
    {
            PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
            for(String currentLine : linesList)
            {
                printWriter.println(currentLine);
            }
            printWriter.close();
    }
}
