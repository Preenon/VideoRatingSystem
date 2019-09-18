import java.util.ArrayList;
/**
 * A representation of a video that knows an array 
 * of its video ratings, and some extra metadata, like: <br>
 * -The name of its creator(s) <br>
 * -The total score for each category (stored in an integer array) <br>
 * -The total score overall <br>
 * -The average score for each category (stored in an integer array) <br>
 * -The average score overall <br>
 * 
 * @author Preenon Chisty
 * @version 2016-12-28
 */
public class Video
{
    /* constants */
    private static final int CATEGORIES = 4;
    
    /* instance fields */
    private int[] averageOfScore;
    private int averageOverallScore;
    private ArrayList<int[]> rating;
    private int[] sumOfScore;
    private int totalOverallScore;
    private String videoCreator;
    
    /* constructors */
    /**
     * Initializes a new video's state 
     * with the given name of its creator(s)
     * 
     * @param nameOfCreator the creator of this video
     */
    public Video(String nameOfCreator)
    {
        videoCreator = nameOfCreator;
        rating = new ArrayList<int[]>();
        averageOfScore = new int[CATEGORIES];
        sumOfScore = new int[CATEGORIES];
        totalOverallScore = 0;
        averageOverallScore = 0;
    } // end of constructor Video(String nameOfCreator)
    
    /* String methods */
    /**
     * Returns a string representing the state of this video.
     * 
     * @return the state, in string format, made up of multiple lines.
     */
    public String returnDetails()
    {
        // Create the indexes for each category
        int content = 0;
        int layout = 1;
        int technical = 2;
        int creativity = 3;
        
        String detailString
        = "Made by: " + videoCreator + "\n"
        + "Total content score: " + sumOfScore[content] + "\n"
        + "Total layout score: " + sumOfScore[layout] + "\n"
        + "Total technical score: " + sumOfScore[technical] + "\n"
        + "Total creativity score: " + sumOfScore[creativity] + "\n"
        + "Total overall score: " + totalOverallScore + "\n" + "\n"
        + "Average content score: " + averageOfScore[content] + "\n"
        + "Average layout score: " + averageOfScore[layout] + "\n"
        + "Average technical score: " + averageOfScore[technical] + "\n"
        + "Average creativity score: " + averageOfScore[creativity] + "\n"
        + "Average overall score: " + averageOverallScore + "\n" + "\n";
        return detailString;
    } // end of method returnDetails()
    
    /**
     * Returns the name of the creator(s) of this video.
     * 
     * @return a string representing the creator name.
     */
    public String getVideoCreator()
    {
        return videoCreator;
    } // end of getVideoCreator()
   
    /* video rating methods */
    /**
     * Returns a primitive array version of 
     * this video's array list of ratings.
     * 
     * @return a two-dimensional integer array
     */
    public int[][] getRating()
    {
        // Get the necessary parameters to create the array matrix.
        int numberOfRatings = rating.size();
        int[][] ratingArray = new int[numberOfRatings][CATEGORIES];
        
        // Compile every rating array into the array array.
        int arrayIndex = 0;
        while (arrayIndex < numberOfRatings)
        {
            ratingArray[arrayIndex] = rating.get(arrayIndex);
            arrayIndex = arrayIndex + 1;
        } // end of while (arrayIndex < numberOfRatings)
        
        return ratingArray;

    } // end of method getVideoRating()
    
    /**
     * Adds a video rating in the form
     * of an integer array to this video's
     * array list of ratings
     * 
     * @param ratingDatum the integer array representing a video rating
     */
    public void addVideoRating(int[] ratingDatum)
    {
        rating.add(ratingDatum);
    } // end of method addVideoRating(int[] ratingDatum)
    
    /* accessors and mutators for total scores */
    
    /**
     * Returns this video's array of sums
     * for each category in a rating.
     * 
     * @return an array of integer sums.
     */
    public int[] getSumScores()
    {
        return sumOfScore;
    } // end of method getSumScores()
    
    /**
     * Transfers the data from the given
     * sum array to this video's sum array.
     * 
     * @param sum an array of integer sums.
     */
    public void setSumScores(int[] sum)
    {
        for (int category = 0; category < CATEGORIES; category++)
        {
            sumOfScore[category] = sum[category];
        } // end of for (int category = 0; category < CATEGORIES; category++)
    } // end of method setSumScores(int[] sum)
   
    /**
     * Returns this video's total overall score.
     * 
     * @return the total overall score, as an integer.
     */
    public int getTotalOverallScore()
    {
        return totalOverallScore;
    } // end of method getTotalOverallScore()
    
    /**
     * Sets the sum of the sums of all video rating scores
     * to the score parameter passed to this method.
     * 
     * @param overallScore the new total overall score
     */
    public void setTotalOverallScore(int overallScore)
    {
        totalOverallScore = overallScore;
    } // end of method setTotalOverallScore(int overallScore)
    
    /* accessors and mutators for average scores */
    /**
     * Returns this video's array of averages
     * for each category in a rating.
     * 
     * @return an array of integer averages.
     */
    public int[] getAverageScores()
    {
        return averageOfScore;
    } // end of method getAverageScores()
    
    /**
     * Transfers the data from the given
     * average array to this video's average array.
     * 
     * @param average an array of integer averages
     */
    public void setAverageScores(int[] average)
    {
        for (int category = 0; category < CATEGORIES; category++)
        {
            averageOfScore[category] = average[category];
        } // end of for (int category = 0; category < CATEGORIES; category++)
    } // end of method setAverageScores(int[] sum)
    
    /**
     * Returns this video's average overall score.
     * 
     * @return the average overall score, as an integer
     */
    public int getAverageOverallScore()
    {
        return averageOverallScore;
    } // end of method getAverageOverallScore()
    
    /**
     * Sets the average of the sums of all video rating scores
     * to the score parameter passed to this method.
     * 
     * @param overallScore the new average overall score
     */
    public void setAverageOverallScore(int overallScore)
    {
        averageOverallScore = overallScore;
    } // end of method setAverageOverallScore(int overallScore)
       
} // end of class Video
