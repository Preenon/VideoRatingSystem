import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
/**
 * A class to display the statistics
 * of each video developed from a text file.
 * 
 * @author Preenon Chisty
 * @version 2017-01-03
 */
public class StatisticDisplay
{
    /* non-instance fields: constants */
    private static final String ERROR_FILE_NAME = "invalidLines.text";
    private static final String OUTPUT_FILE_NAME = "videoResults.text";
    
    /* instance fields */
    private BufferedReader console;
    private StatisticExtractor extractor;
    private FileInterpreter interpreter;
    private ArrayList<Video> video;
    
    /* constructors */
    /**
     * Initializes a new statistic display.
     */
    public StatisticDisplay()
    {
        console = new BufferedReader(new InputStreamReader(System.in));
        extractor = new StatisticExtractor();
        interpreter = new FileInterpreter();
        video = null;
    } // end of constructor StatisticDisplayIO

    /**
     * Runs the VideoRatingAnalyzer project.
     */
    public void run()
    {
        // Establish the error file, before anything else.
        boolean errorFileSet = interpreter.setErrorFile();
        if (!errorFileSet)
        {
            printFileCreationError();
            return;
        } // end of if (!errorFileSet)
        
        // Explain the program and get the file name.
        instructAndPromptUser();
        
        // Establish the console to get the file name.
        try
        {
            String fileName = console.readLine();
            System.out.println();
            
            /* Interpret the file:
             * Get every line from the file, and
             * organize the information into videos
             * complete with their video ratings
             * Check if we could access it.
             */
            boolean couldAccessFile
            = interpreter.accessAndInterpretFile(fileName);
            
            if (couldAccessFile)
            {
                getAndAnalyzeVideos();
            }
            else
            {
                // Let user know of file access issue.
                printFileAccessError(fileName);
            } // end of (couldInterpretFile)
            
        }
        // Console couldn't be established
        catch (IOException exception)
        {
            printConsoleCreationError();
        } // end of catch (IOException exception)
    } // end of method run()
    
    /**
     * Creates an array list of videos,
     * and extracts and prints their details
     * to a text file.
     */
    private void getAndAnalyzeVideos()
    {
        // Get the video set from the interpreter.
        video = interpreter.getVideoSet();
        
        /*
         * Does the set contain any videos?
         * I.E: Was the file empty or not?
         */
        if (video.size() == 0)
        {
            printNoVideosError();
            return;
        } // end of if video.size() == 0)
        
        // Extract extra stats from the videos.
        extractor.extractStatistics(video);
        
        // Print details for each video to the file
        try
        {
            PrintWriter outputFile =
            new PrintWriter(new FileWriter(OUTPUT_FILE_NAME));
            
            for (Video currentVideo : video)
            {
                String videoDetails = currentVideo.returnDetails();
                outputFile.println(videoDetails);
            }
            
            outputFile.close();
            
            // Tell the user where to look for the results.
            System.out.println("Thank you. Please check "
            + "the file by the path of "
            + "\"" + OUTPUT_FILE_NAME + "\"");
            System.out.print("for the video results.");
            
        }
        // Output file couldn't be established
        catch (IOException exception)
        {
            printFileCreationError();
        } // end of catch (IOException exception)
    } // end of method getAndAnalyzeVideos()
    
    /* introduction methods */
    /**
     * Explains the program to the user, and
     * prompts for a file name.
     */
    private void instructAndPromptUser()
    {
        introduce();
        notifyOfTemplate();
        explainErrorsFoundByInterpreter();
        System.out.print("Please enter a file name: ");
    } // end of method instructAndPromptUser()
    
    /**
     * Explains the purpose of the program.
     */
    private void introduce()
    {
        System.out.print("This is an analyzer ");
        System.out.print("for files that contain data ");
        System.out.println("on ratings of videos. \n");
    } // end of method introduce()
    
    /**
     * Tells the user of what template
     * the data in the given file should be in.
     */
    private void notifyOfTemplate()
    {
        String template
        = "name name name rating rating rating rating";
        
        System.out.print("To be able to be analyzed, ");
        System.out.println("the data in the given file");
        System.out.print("should be put in following ");
        System.out.println("template for each file line: \n");
        System.out.println("\"" + template + "\"" + "\n");
        System.out.println("where \"rating\" is an integer. \n");
        System.out.print("Essentially, each line in the file ");
        System.out.println("should contain only");
        System.out.print("7 tokens, and have ");
        System.out.print("the last 4 tokens be integers, ");
        System.out.println("for now. \n");
        
    } // end of method notifyOfTemplate()
    
    /**
     * Notifies the user of the error file.
     */
    private void explainErrorsFoundByInterpreter()
    {
        System.out.println("This program will skip invalid lines.");
        System.out.print("To determine them, the console will ");
        System.out.println("print out the line number");
        System.out.print("of every invalid line, ");
        System.out.println("along with a diagnostic like:");
        System.out.print("\"wrong number of tokens\", or ");
        System.out.println("\"ratings are not integers\".");
        System.out.println("Check the file by the path of "
        + "\"" + ERROR_FILE_NAME + "\"");
        System.out.println("for the invalid lines. \n");
    } // end of method explainErrorsFoundByInterpreter()
    
    /* error message printers */
    /**
     * Prints a message detailing that
     * the given file could not be accessed.
     * 
     * @param fileName the path of the failed file.
     */
    private void printFileAccessError(String fileName)
    {
        System.out.print("Error: Could not access file ");
        System.out.println("of path \"" + fileName + "\".");
        System.out.print("Please check that ");
        System.out.println("you entered the correct file name.");
        System.out.println("Program has been terminated.");
    } // end of method printConnectionError(String fileName)
    
    /**
     * Prints a message detailing that
     * either the output file or the error file
     * could not be established.
     */
    private void printFileCreationError()
    {
        /*
         * If this method was called, then
         * either the output file or the error file 
         * couldn't be established.
         */
        System.out.print("Error: Could not establish output ");
        System.out.println("or error file.");
        System.out.print("If you saw an introduction, then ");
        System.out.println("the output file couldn't be set,");
        System.out.println("and vice-versa.");
        System.out.print("You may have too little space ");
        System.out.println("on your drive. Try freeing up");
        System.out.println("some space by deleting some files and apps.");
        System.out.println("Consult a technician for more advice.");
        System.out.println("Program has been terminated.");
    } // end of method printFileCreationError()
    
    /**
     * Prints a message detailing that
     * there were no data to be found in the file.
     */
    private void printNoVideosError()
    {
        System.out.println("Error: Could not find any video ratings.");
        System.out.print("Please put in some data according ");
        System.out.println("to the template mentioned above.");
        System.out.println("Program has been terminated.");
    } // end of method printNoVideosError()
    
    /**
     * Prints a message detailing that
     * the console to take in input
     * couldn't be established.
     */
    private void printConsoleCreationError()
    {
        System.out.println("Error: Could not read from keyboard.");
        System.out.print("This is a serious error. ");
        System.out.println("Please consult a technician for help.");
        System.out.println("Program has been terminated.");
    } // end of method printConsoleCreationError()
} // end of class StatisticDisplay
