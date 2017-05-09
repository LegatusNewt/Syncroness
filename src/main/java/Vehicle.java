import sun.util.calendar.BaseCalendar;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import static java.time.temporal.ChronoUnit.MONTHS;

/**
 * Created by Kenneth C. LaMarca on 5/6/2017.
 */
public class Vehicle
{
    private Manufacturer make;
    private Model model;
    private String VIN; //VINs are strings i need to verify that these are unique
    private String color;
    private int mileage;
    private LocalDate lastOilChange_date;
    private int lastOilChange_mileage;
    private static ArrayList<String> VIN_IN_USE = new ArrayList<String>();

    public Manufacturer getMake() {
        return make;
    }

    public Model getModel() {
        return model;
    }

    public String getVIN() {
        return VIN;
    }

    public String getColor() {
        return color;
    }

    public int getMileage() {
        return mileage;
    }

    public LocalDate getLastOilChange_date() {
        return lastOilChange_date;
    }

    public int getLastOilChange_mileage() {
        return lastOilChange_mileage;
    }

    /**
     * Constructor with everything in it. VIN is added to VIN_IN_USE static arraylist
     *  verification on whether or not this constructor should even be called should be handled
     *  by whomever is calling it.
     * @param make
     * @param model
     * @param VIN
     * @param color
     * @param mileage
     * @param lastOilChange_mileage
     * @param lastOilChange_date
     */
    public Vehicle(Manufacturer make, Model model, String VIN, String color, int mileage,
                   int lastOilChange_mileage, LocalDate lastOilChange_date)
    {
        if(!VIN_IN_USE.contains(VIN))
        {
            VIN_IN_USE.add(VIN);
        }

        this.VIN = VIN;
        this.make = make;
        this.model = model;
        this.color = color;
        this.mileage = mileage;
        this.lastOilChange_date = lastOilChange_date;
        this.lastOilChange_mileage = lastOilChange_mileage;
    }

    /**
     * Constructor not including oil change information or mileage. This is the *New* Car constructor
     * @param make
     * @param model
     * @param VIN
     * @param color
     */
    public Vehicle(Manufacturer make, Model model, String VIN, String color)
    {
        if(!VIN_IN_USE.contains(VIN))
        {
            VIN_IN_USE.add(VIN);
        }

        this.VIN = VIN;
        this.make = make;
        this.model = model;
        this.color = color;
        this.mileage = 0;
        this.lastOilChange_mileage = 0;
        this.lastOilChange_date =  LocalDate.now(); //Current date
    }

    /**
     * Constructor not including oil change information but including mileage, assumes oil change happened at creation
     *  of vehicle in repository
     * @param make
     * @param model
     * @param VIN
     * @param color
     * @param mileage
     */
    public Vehicle(Manufacturer make, Model model, String VIN, String color, int mileage)
    {
        if(!VIN_IN_USE.contains(VIN))
        {
            VIN_IN_USE.add(VIN);
        }

        this.VIN = VIN;
        this.make = make;
        this.model = model;
        this.color = color;
        this.mileage = mileage;
        this.lastOilChange_mileage = mileage;
        this.lastOilChange_date =  LocalDate.now(); //Current date
    }

    /**
     * Checks Model's Oil change rules against last oil change date and mileage
     * @return
     */
    public boolean isDue()
    {
        if(model.getOilChangeRules().isElectric())
            return false;
        else
        {
            //Check last oil change by time, take difference of Months between LocalDate.now and LastOilChange_Date
            if (MONTHS.between(lastOilChange_date, LocalDate.now()) >= model.getOilChangeRules().months) {
                return true;
            }
            //Check last oil change by Mileage
            if (mileage - lastOilChange_mileage >= model.getOilChangeRules().miles) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates oil change date and mileage to the current date and mileage of this vehicle
     *  Signifies that an oil change had just occurred.
     */
    public void oilChange()
    {
        if(!model.getOilChangeRules().isElectric())
        {
            lastOilChange_date = LocalDate.now();
            lastOilChange_mileage = this.mileage;
        }
    }

    /**
     * Specifiy that an oil change happened at some point at a certain mileage
     * @param when
     */
    public void oilChange(LocalDate when, int whatMileage)
    {
        if(!model.getOilChangeRules().isElectric())
        {
            //Should verify that this date is in the past
            lastOilChange_date = when;

            //Should verify that this mileage is less than the current mileage of the vehicle
            lastOilChange_mileage = whatMileage;
        }
    }

    /**
     * Increase mileage of vehicle
     * @param addedMiles
     */
    public void addMileage(int addedMiles)
    {
        mileage += addedMiles;
    }

    /**
     * Retunrs if vehicle is equal to object o
     * @return
     */
    public boolean equals(Object o)
    {
        if(o instanceof Vehicle)
        {
            if(((Vehicle) o).getVIN().equals(VIN))
                return true;
            else
                return false;
        }
        else{
            return false;
        }
    }

    /**
     * String representation of the vehicle including make model year and vin. Along with an optional
     *  manufacturer slogan.
     * @return
     */
    @Override
    public String toString()
    {
        return model.getYear() + " " + this.color
            + " " + make.getName()
            + " " + model.getName()
            + " " + make.getSlogan()
            + " " + VIN
            + " MILEAGE : " + mileage;
    }

    public String toStringOil()
    {
        return model.getYear() + " " + this.color
                + " " + make.getName()
                + " " + model.getName()
                + " " + make.getSlogan()
                + " " + VIN
                + " MILEAGE : " + mileage
                + "\nLast Oil Change : " + lastOilChange_mileage + " , " + lastOilChange_date;
    }
}
