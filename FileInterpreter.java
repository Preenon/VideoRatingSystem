import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
/**
 * A class to take in strings from its file accessor,
 * and sort their information into videos.
 * 
 * @author Preenon Chisty
 * @version 2017-01-03
 */
public class FileInterpreter
{
    /* non-instance fields: constants */
    private static final String ERROR_FILE_NAME = "invalidLines.text";
    private static final int NUMBER_OF_CATEGORIES = 4;
    private static final int RATING_START_INDEX = 3;
    private static final int STATUS_VALID = 1;
    private static final int STATUS_WRONG_RATING_TYPE = -2;
    private static final int STATUS_WRONG_TOKEN_COUNT = -1;
    
    /* instance fields */
    private FileAccessor accessor;
    private PrintWriter errorFile;
    private ArrayList<Video> video;
    private HashMap<String, Video> videoNameLinker;

    /* constructors */
    /**
     * Initializes a new file interpreter with 
     * its default characteristics.
     */
    public FileInterpreter()
    {
        video = new ArrayList<Video>();
        videoNameLinker = new HashMap<String, Video>();
        accessor = new FileAccessor();
        errorFile = null;
    } // end of constructor FileInterpreter()
    
    /**
     * Sets the error file of this interpreter.
     * 
     * @return true if file could be created, false otherwise.
     */
    public boolean setErrorFile()
    {
        try
        {
            errorFile
            = new PrintWriter(new FileWriter(ERROR_FILE_NAME));
        }
        catch (IOException exception)
        {
            return false;
        } // end of catch (IOException exception)
        return true;
    } // end of method setErrorFile()
    
    /* public interface */
    /**
     * Accesses a file, and synthesizes video objects from it, 
     * using the data found in the file strings.
     * 
     * @param fileName the name of the text file
     * @return true if file could be accessed, false otherwise. 
     */
    public boolean accessAndInterpretFile(String fileName)
    {
        // Sets fileAccessor, and checks to see if it worked.
        // That is to say, could we connect to the file and console?
        boolean couldSetFile = accessor.setFileAccessor(fileName);
        if (!couldSetFile)
        {
            return false;
        } // end of if (!couldSetFile)
        
        /* Analyze every line in the file, creating videos 
           and printing error messages as needed. */
   
        boolean canAnalyzeMoreLines;
        do
        {
            // Are there any more lines to analyze?
            canAnalyzeMoreLines = getAndAnalyzeLine();
        } while (canAnalyzeMoreLines);
        // end of do while (canAnalyzeMoreLines);

        /*
         * We don't need the error file anymore
         * since we're not analyzing lines anymore.
         */
        errorFile.close();
        
        return true;
    } // end of method accessAndInterpretFile(String fileName)
    
    /**
     * Returns an array list that contains video objects.
     * 
     * @return the videos, each with filled arrays of VideoRating.
     */
    public ArrayList<Video> getVideoSet()
    {
        return video;
    } // end of method getVideoSet()
    
    
    /* private implementation */
    /* file-interpreting-methods */
    /**
     * Checks to see if this interpreter does not have 
     * a video with the given creator in its array list.
     * 
     * @param  videoCreator the name of who created the video
     * @return true if video doesn't exist, false otherwise
     */
    private boolean videoDoesNotExist(String videoCreator)
    {
        // Use HashMap, see if it returns null
        Video testVideo = videoNameLinker.get(videoCreator);
        return testVideo == null;
    } // end of method videoDoesNotExist(String videoCreator)
    
    /**
     * Gets and analyzes a line from this interpreter's accessor.
     * 
     * @return true if we can analyze more lines, false otherwise
     */
    private boolean getAndAnalyzeLine()
    {
        // Get line.
        String fileString = accessor.getLineFromFile();
        
        // Could we get a line?
        if (fileString != null)
        {
            analyzeLine(fileString);
            return true;
        } // end of if (fileString != null)
        
        // There are no more lines to analyze.
        return false;
    } // end of method getAndAnalyzeLine()
    
    /**
     * Analyzes a line, printing errror messages as needed.
     */
    private void analyzeLine(String fileString)
    {
        /*
         * Check the validity of the string,
         * and skip over any lines that are not valid.
         * For every error found,
         * print an error message to the error file.
         */
        int statusOfLine = LineChecker.statusOfLine(fileString);
        int numberOfFileLine = accessor.getStringLocationInFile();
        if (statusOfLine == STATUS_VALID)
        {
            sortStringInformation(fileString);
        } // end of if (statusOfLine == STATUS_VALID)
        else if (statusOfLine == STATUS_WRONG_TOKEN_COUNT)
        {
            errorFile.print("Error on line " + numberOfFileLine);
            errorFile.println(": wrong number of tokens.");
        } // end of if (statusOfLine == STATUS_WRONG_TOKEN_COUNT)
        else if (statusOfLine == STATUS_WRONG_RATING_TYPE)
        {
            errorFile.print("Error on line " + numberOfFileLine);
            errorFile.println(": ratings are not integers.");
        } // end of if (statusOfLine == STATUS_WRONG_RATING_TYPE)
    } // end of method analyzeLine(String fileString)
    
    /**
     * Splits a string into its tokens, and assigns 
     * data to new or existing videos based on token information.
     * 
     * @param fileString a given string from the file
     */
    private void sortStringInformation(String fileString)
    {
        // Split fileString into array of tokens without whitespace
        String[] fileStringToken = fileString.trim().split("\\s+");
        
        // Determine the creator of the video.
        String videoCreator = determineVideoCreator(fileStringToken);
        
        // Should we make a new video?
        if (videoDoesNotExist(videoCreator))
        {
            // Create, add, and link new video using podName.
            Video newVideo = new Video(videoCreator);
            video.add(newVideo);
            videoNameLinker.put(videoCreator, newVideo);
        } // end of if (videoDoesNotExist(videoCreator))
        
        // Get chosen video from our HashMap using pod name
        Video videoToAddRating = videoNameLinker.get(videoCreator);
        
        // Get VideoRatingData (int array) from fileString
        int[] ratingData = determineRatingData(fileStringToken);
        
        // Give the video data necessary to form a VideoRating
        videoToAddRating.addVideoRating(ratingData);
    } // end of method sortStringInformation(String fileString)
    
    /* gathering-info-from-token-array methods */
    /**
     * Compiles specified string tokens to 
     * form and return a video creator's name.
     * 
     * @param fileStringToken an array of string tokens
     * @return the name of the creator of the video
     */
    private String determineVideoCreator(String[] fileStringToken)
    {
        /* Name of creator consists of tokens
         * concatenated in sequential order.
         * The first one has an index of 0,
         * and the following have incrementing indexes
         * up until the RATING_START_INDEX
         */
        // Return those tokens, concatenated together.
        String podName = "";
        int nameTokenIndex = 0;
        
        while (nameTokenIndex < RATING_START_INDEX)
        {
            podName = podName + fileStringToken[nameTokenIndex];
            nameTokenIndex = nameTokenIndex + 1;
        } // end of while (nameTokenIndex < RATING_START_INDEX)
        
        return podName;
    } // end of method determineVideoCreator(String[] fileStringToken)
    
    /**
     * Extract the rating data from a string token array, 
     * and build an array with them to return.
     * 
     * @param fileStringToken an array of string tokens
     * @return the ratings, compiled in an integer array
     */
    private int[] determineRatingData(String[] fileStringToken)
    {
        String[] stringRating = new String[NUMBER_OF_CATEGORIES];
        /* We need a ratingStartIndex for where the ratings start
         * showing up in fileStringToken.
         * Since there are 3 tokens making up the name
         * at the start, and their indexes are 0, 1, and 2,
         * then the ratingStartIndex logically is at 3.
         */
        int ratingStartIndex = RATING_START_INDEX;
        
        // Create essentially the array.size() for rating
        // This will help us transfer fileString data to stringRating
        int totalTokenNumber = NUMBER_OF_CATEGORIES + ratingStartIndex;
        
        while (ratingStartIndex < totalTokenNumber)
        {
            // fileStringToken will always be 3 indexes ahead
            // of rating, so that rating will always get a
            // string that can be converted to an integer.
            int stringIndex = ratingStartIndex - 3;
            stringRating[stringIndex] = fileStringToken[ratingStartIndex];
            ratingStartIndex = ratingStartIndex + 1;
        } // end of while (ratingStartIndex < totalTokenNumber)
        
        /* Prepare for conversion into integer array */
        int[] integerRating = new int[NUMBER_OF_CATEGORIES];
        int ratingIndex = 0;
        
        // Take string rating data, convert into integers
        // Fill integer array with integer data.
        while (ratingIndex < NUMBER_OF_CATEGORIES)
        {
            String ratingAsString = stringRating[ratingIndex];
            integerRating[ratingIndex]
            = Integer.parseInt(ratingAsString);
            ratingIndex++;
        } // end of while (ratingIndex < NUMBER_OF_CATEGORIES)
        
        return integerRating;
    } // end of method determineRatingData(String[] fileStringToken)
    
} // end of class FileInterpreter
