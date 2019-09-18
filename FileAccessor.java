import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * A class that actually connects to the file and reads it.
 * 
 * @author Preenon Chisty
 * @version 2016-12-25
 */
public class FileAccessor
{
    /* instance fields */
    private int currentStringNumber;
    private BufferedReader fileReader;
    private ArrayList<String> fileString;
    
    /* constructors */
    /**
     * Initializes a new file accessor 
     * with the default characteristics.
     */
    public FileAccessor()
    {
        /* 
         * For allowing access to element zero of fileString
         * when incrementing currentStringNumber
         */
        currentStringNumber = -1;
        fileReader = null;
        fileString = new ArrayList<String>();
    } // end of constructor FileAccessor()
    
    /* public interface */
    /**
     * Prepares this file accessor to 
     * obtain data from the file with the given file name
     * 
     * @param fileName the path of the wanted file
     * @return true if we could establish both file and console
     */
    public boolean setFileAccessor(String fileName)
    {
        // Can we connect to the file?
        boolean connectedToFile = connectToFile(fileName);
        
        if (connectedToFile)
        {
            // Can we establish a console to read from the file?
            boolean establishedReader = getAllDataFromFile();
            
            if (establishedReader)
            {
                return true;
            } // end of if (establishedReader)
            
            // We couldn't establish it.
            return false;
        } // end of if (connectedToFile)
        
        // We couldn't connect to a file.
        return false;
    } // end of method setFileAccessor(String fileName)
    
    /**
     * Returns the next line from this file 
     * accessor's array list of file strings
     * 
     * @return the next line, null if there are no more lines.
     */
    public String getLineFromFile()
    {
        /* Use currentStringNumber to keep track of
         * which String to return next
         */
        currentStringNumber = currentStringNumber + 1;
        if (currentStringNumber < fileString.size())
        {
            return fileString.get(currentStringNumber);
        }
        else
        {
            // There are no more strings to return.
            return null;
        } // end of if (currentStringNumber < fileString.size())
    } // end of method getLineFromFile()
    
    /**
     * Returns the number of the file line of
     * the string most recently returned.
     * 
     * @return an integer specifying a file line.
     */
    public int getStringLocationInFile()
    {
        /*
         * Increment by 1 to make up for offset 
         * caused by index starting at zero.
         */
        return currentStringNumber + 1;
    } // end of method getStringLocationInFile()
    
    /* private implementation */
    /**
     * Sets up the buffered reader for a file.
     * 
     * @param fileName the path of the given file
     * @return true if we could connect to the file.
     */
    private boolean connectToFile(String fileName)
    {
        // Can we get the file?
        try
        {
            fileReader = new BufferedReader(new FileReader(fileName));
        }
        catch (IOException exception)
        {
            return false;
        } // end of catch (IOException exception)
        return true;
    } // end of method connectToFile(String fileName)
    
    /**
     * Populates this accessor's array list of strings 
     * with the strings from the file.
     * 
     * @return true if the console was established
     */
    private boolean getAllDataFromFile()
    {
        // Can we establish the console?
        try
        {
            /* Add to the ArrayList of strings, while 
             * there are still strings in the file */
            String currentString = fileReader.readLine();
            while (currentString != null)
            {
                fileString.add(currentString);
                currentString = fileReader.readLine();
            } // end of while (currentString != null)
            
            // We don't need the file anymore, close it.
            fileReader.close();
        }
        catch (IOException exception)
        {
            return false;
        } // end of catch (IOException exception)
        return true;
    } // end of method getAllDataFromFile()
    
} // end of class FileAccessor
