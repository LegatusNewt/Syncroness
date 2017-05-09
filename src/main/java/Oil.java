/**
 * Literally a int / int touple of number of months and number of miles
 * Created by Kenneth C. LaMarca on 5/6/2017.
 */
public class Oil
{
    public int miles;
    public int months;
    private boolean isElectric;
    public static final int DEFAULT_MILES = 3000;
    public static final int DEFAULT_MONTHS = 3;

    /**
     * Default constructor
     */
    public Oil()
    {
        miles = DEFAULT_MILES;
        months = DEFAULT_MONTHS;
        isElectric = false;
    }

    public Oil(int miles, int months, boolean isElectric)
    {
        this.miles = miles;
        this.months = months;
        this.isElectric = isElectric;
    }

    /**
     * Returns the oil change rules state that the car is electric
     * @return
     */
    public boolean isElectric()
    {
        return this.isElectric;
    }
}
