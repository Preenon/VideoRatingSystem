import java.util.ArrayList;
/**
 * A class to extract extra information
 * about each video, like total scores,
 * and add it to their states.
 * 
 * @author Preenon Chisty
 * @version 2016-12-28
 */
public class StatisticExtractor
{
    /* constants */
    private static final int CATEGORIES = 4;
    
    /* constructors */
    /**
     * Initializes a new extractor's state.
     * 
     * Nothing to do here...
     */
    public StatisticExtractor()
    {
    } // end of constructor StatisticExtractor()

    /* main method */
    /**
     * Calculates extra data based on each video
     * and assigns these data to them.
     * 
     * @param video an array list of video objects.
     */
    public void extractStatistics(ArrayList<Video> video)
    {
        /* For each video... */
        for (Video currentVideo : video)
        {
            // Get a representation of all of its VideoRatings
            int[][] rating = currentVideo.getRating();
            
            /* Use this 2D array in methods to calculate metadata
             * Assign that metadata to local variables here
             */
            
            /* Calculate sums */
            int[] sum = sumScores(rating);
            int overallSum = calculateOveralllSum(sum);
            
            /* Calculate averages */
            int[] average = averageScores(sum, rating);
            
            /* Overall average is average of overall sum,
             * not sum of all averages of this video!
             */
            int overallAverage = 0;
            if (rating.length > 0)
            {
                overallAverage = overallSum / rating.length;
            } // end of if (rating.length > 0)
            
            /* Assign sums to current video */
            currentVideo.setSumScores(sum);
            currentVideo.setTotalOverallScore(overallSum);
            
            /* Assign averages to current video */
            currentVideo.setAverageScores(sum);
            currentVideo.setAverageOverallScore(overallAverage);
        } // end of for (Video currentVideo : video)
    } // end of extractStatistics(ArrayList<Video> video)
    
    /* sum methods */
    /**
     * Sums the scores of the rating array,
     * and returns an array compiled of the sums.
     * 
     * @param rating an array of integer arrays.
     * @return an array of sums for each category.
     */
    private int[] sumScores(int[][] rating)
    {
        int[] sum = new int[CATEGORIES];
        
        // For each score category in the 2D array...
        for (int category = 0; category < CATEGORIES; category++)
        {
            int sumOfScore = 0;
            int currentArray = 0;
            int lengthOfArray = rating.length;
            
            // Accumulate the scores for that category...
            while (currentArray < lengthOfArray)
            {
                sumOfScore = sumOfScore
                + rating[currentArray][category];
                
                currentArray = currentArray + 1;
            } // end of while (currentArrayIndex < lengthOfArray)
            
            // Assign the score to the corresponding element.
            sum[category] = sumOfScore;
        } // end of for (int category = 0; category < CATEGORIES; category++)
        
        return sum;
    } // end of method sumScores(int[][] rating)
    
    /**
     * Calculates and returns the
     * overall sum of the sums of each category.
     * 
     * @param sum an array of sums for each category.
     * @return the overall sum.
     */
    private int calculateOveralllSum(int[] sum)
    {
        int totalSum = 0;
        for (int category = 0; category < CATEGORIES; category++)
        {
            totalSum = totalSum + sum[category];
        } // end of for (int category = 0; category < CATEGORIES; category++)
        return totalSum;
    } // end of method calculateOveralllSum(int[] sum)
    
    /* average methods */
    /**
     * Averages the scores of the rating array,
     * and returns an array compiled of the averages.
     * 
     * @param sum an array of sums for each category.
     * @param rating an array of integer arrays.
     * @return an array of averages for each category.
     */
    private int[] averageScores(int[] sum, int[][] rating)
    {
        int[] average = new int[CATEGORIES];
        
        // For each score category in the 2D array...
        for (int category = 0; category < CATEGORIES; category++)
        {
            /* Divide not by number of categories,
             * but number of ratings, which is 
             * rating.length
             */
            // And make the check for division by zero.
            if (rating.length > 0)
            {
                average[category] = sum[category] / rating.length;
            }
            else
            {
                average[category] = 0;
            } // end of if (rating.length > 0)
            
        } // end of for (int category = 0; category < CATEGORIES; category++)
        
        return average;
    } // end of method averageScores(int[] sum, int[][] rating)
    
} // end of class StatisticExtractor
