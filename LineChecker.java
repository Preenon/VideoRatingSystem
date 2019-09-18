import java.lang.NumberFormatException;
/**
 * A class to determine if a given
 * string can be examined for data.
 * 
 * @author Preenon Chisty
 * @version 2017-01-03
 */
public class LineChecker
{
    /* non-instance fields: constants */
    private static final int REQUIRED_TOKEN_COUNT = 7;
    private static final int STATUS_TO_BE_IGNORED = 2;
    private static final int STATUS_VALID = 1;
    private static final int STATUS_WRONG_RATING_TYPE = -2;
    private static final int STATUS_WRONG_TOKEN_COUNT = -1;
    private static final String STRING_TO_IGNORE = "Block";
    
    /* constructors */
    /**
     * Constructs a new LineChecker.
     * 
     * Nothing to do here...
     */
    public LineChecker()
    {
    } // end of constructor LineChecker()

    /**
     * Returns the status of the given string.
     * 
     * @param fileString a string from the file.
     * @return the status, in the form of an integer. <br>
     * Possible statuses: <br>
     * - Is valid; can be analyzed for data. <br>
     * - Does not contain the required number of tokens. <br>
     * - Ratings at the end of the string
     *  are not integers. <br>
     *  - Is part of the string set to be ignored.
     */
    public static int statusOfLine(String fileString)
    {
        /*
         * For a string to have a valid
         * status, as of this date, it must:
         * -Be composed of 7 string tokens
         * -Have the last 4 tokens be integers
         */
        
        // Right off the bat, can we ignore this string?
        if (fileString.startsWith(STRING_TO_IGNORE))
        {
            return STATUS_TO_BE_IGNORED;
        } // end of if (fileString.startsWith(STRING_TO_IGNORE))
        
        // Split fileString into array of tokens without whitespace
        String[] fileToken = fileString.trim().split("\\s+");
        
        // Does our string contain the right number of tokens?
        boolean validCount = fileToken.length == REQUIRED_TOKEN_COUNT;
        if (validCount)
        {
            // Are our ratings in this string integers?
            if (validRating(fileToken))
            {
                return STATUS_VALID;
            }
            else
            {
                return STATUS_WRONG_RATING_TYPE;
            } // end of if (validRating(fileToken)
        }
        else
        {
            return STATUS_WRONG_TOKEN_COUNT;
        } // end of if (validCount)

    } // end of method statusOfLine(String fileString)
    
    /**
     * Checks if every rating in the
     * given array is an integer.
     * 
     * @param fileToken the array that contains the ratings.
     * @return true if every rating 
     * is an integer, false otherwise.
     */
    public static boolean validRating(String[] fileToken)
    {
        /*
         * As of this date, ratings start
         * at the index of 3.
         * So, for each rating in the array...
         */
        int arraySize = fileToken.length;
        for (int rating = 3; rating < arraySize; rating++)
        {
            try
            {
                // Try converting the rating to an int.
                String tokenToTest = fileToken[rating];
                int testInteger 
                = Integer.parseInt(tokenToTest);
            }
            catch (NumberFormatException ratingTypeError)
            {
                // At least one rating was not an int.
                return false;
            }
        }
        // We made it through the ratings without any errors!
        return true;
    } // end of method validRating(String[] fileToken))
} // end of class LineChecker
