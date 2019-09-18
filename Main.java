
/**
 * The class with the main method for the VideoRatingAnalyzer project.
 * Used if it is wanted to run this program in a terminal.
 * 
 * @author Preenon Chisty
 * @version 2016-12-25
 */
public class Main
{

    /**
     * Initializes a new Main object's state. 
	 * Nothing to do here...
     */
    public Main()
    {
    }

    public static void main(String[] argument)
    {
        StatisticDisplay videoStatistic = new StatisticDisplay();
        videoStatistic.run();
    } // end of method main(String[] argument)
} // end of class Main
