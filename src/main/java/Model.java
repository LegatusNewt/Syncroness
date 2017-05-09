import java.util.Comparator;

/**
 * Created by Kenneth C. LaMarca on 5/6/2017.
 */
public class Model
{
    private Oil oilChangeRules;
    private String name;
    private int year; //Datetime is unnecessary for our use case

    /**
     * Constructor for Model class
     * @param name
     * @param year
     */
    public Model(String name, int year, Oil oilRule)
    {
        this.oilChangeRules = oilRule;
        this.name = name;
        this.year = year;
    }

    public String getName(){ return name; }

    public int getYear(){ return year; }

    public Oil getOilChangeRules(){ return oilChangeRules; }

    /**
     * ToString override returns year and name in string representation
     * @return
     */
    @Override
    public String toString()
    {
        return year + " " + name + "\n";
    }

    @Override
    /**
     * Equals function override
     */
    public boolean equals(Object o)
    {
        if(o instanceof Model)
        {
            //Check name and year for equality
            if(((Model) o).getName().equals(this.getName()) && ((Model) o).getYear()==this.getYear())
                return true;
            else
                return false;
        }
        else
            return false;
    }
}
