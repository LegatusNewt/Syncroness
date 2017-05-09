import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Kenneth C. LaMarca on 5/6/2017.
 */
public class Repository implements Iterable<Vehicle> {
    ArrayList<Vehicle> vehicles;
    HashMap<String,Manufacturer> manufacturers;

    public Repository() {
        this.vehicles = new ArrayList<Vehicle>();
        this.manufacturers = new HashMap<String,Manufacturer>();
    }

    public Repository(List<Vehicle> vehicles, Map<String,Manufacturer> manufacturers) {
        this.vehicles = new ArrayList<>(vehicles);
        this.manufacturers = new HashMap<String,Manufacturer>(manufacturers);
    }

    /**
     * Add a new manufacturer to the list of manufacturers
     *
     * @param newManufacturer
     */
    public void addManufacturer(Manufacturer newManufacturer) {
        manufacturers.put(newManufacturer.getName(),newManufacturer);
    }

    /**
     * Return list of all manufacturers
     * @return
     */
    public Map<String,Manufacturer> getAllManufacturers()
    {
        return manufacturers;
    }

    /**
     * Adds a new vehicle to the vehicle list first verifies that the manufacturer exists,
     *  then verifies that the model exists in the database. If either of these fail then this
     *  will return false
     * @param manufacturer
     * @param model
     */
    public boolean addNewVehicle(Manufacturer manufacturer, Model model,String VIN,String color)
    {
        //Check manufactureres list
        if(manufacturers.containsKey(manufacturer.getName())){
            //Check if manufacturer has that model
            if(manufacturer.getAllModels().contains(model))
            {
                //Create a new vehicle object and add it to the arrayList
                vehicles.add(new Vehicle(manufacturer,model,VIN
                        ,color,0,0, LocalDate.now()));
                return true;
            }
        }
        return false;
    }

    /**
     * Remove vehicle by comparing VIN to existing vehicles
     * @param VIN
     * @return
     */
    public boolean removeVehicle(String VIN)
    {
        if(getVehicleByVIN(VIN).size() > 0) {
            vehicles.remove(getVehicleByVIN(VIN).get(0));
            return true;
        }
        else
            return false;
    }

    /**
     * Adds a new vehicle to the vehicle list with oil change and mileage data
     * @param manufacturer
     * @param model
     * @param VIN
     * @param color
     * @param mileage
     * @param lastOilMiles
     * @param lastOilDate
     * @return
     */
    public boolean addNewVehilce(Manufacturer manufacturer, Model model, String VIN, String color,
                                 int mileage, int lastOilMiles, LocalDate lastOilDate)
    {
        //Check manufactureres list
        if(manufacturers.containsKey(manufacturer.getName())){
            //Check if manufacturer has that model
            if(manufacturer.getAllModels().contains(model))
            {
                //Create a new vehicle object and add it to the arrayList
                vehicles.add(new Vehicle(manufacturer,model,VIN
                        ,color,mileage,lastOilMiles, lastOilDate));
                return true;
            }
        }
        return false;
    }

    /**
     * Return a list of all models from all manufacturers sorted by manufacturer
     *
     * @return
     */
    public HashMap<Manufacturer,List<Model>> getAllModels() {
        HashMap<Manufacturer,List<Model>> returnModelList = new HashMap<Manufacturer,List<Model>>();
        for (Manufacturer m : manufacturers.values()) {
            returnModelList.put(m,m.getAllModels());
        }
        return returnModelList;
    }

    /**
     * Sort by default comparison of strings VIN
     * So in this case String are sorted alphabetically but Uppercase is before lower case
     * So upper Z is before lower a
     */
    public Comparator<Vehicle> byVIN = new Comparator<Vehicle>() {
        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o1.getVIN().compareTo(o2.getVIN());
        }
    };

    /**
     * Compare by Model Names
     */
    public Comparator<Vehicle> byModel = new Comparator<Vehicle>() {
        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o1.getModel().getName().compareTo(o2.getModel().getName());
        }
    };

    /**
     * Compare by Model Year
     */
    public Comparator<Vehicle> byYear = new Comparator<Vehicle>() {
        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return Integer.compare(o1.getModel().getYear(), o2.getModel().getYear());
        }
    };

    /**
     * Sort Vehicles by year and then return the list
     *
     * @return
     */
    public List<Vehicle> getSortedByYear() {
        vehicles.sort(byYear);
        return vehicles;
    }

    /**
     * Sort Vehicles by VIN and then return list
     *
     * @return
     */
    public List<Vehicle> getSortedByVIN() {
        vehicles.sort(byVIN);
        return vehicles;
    }

    /**
     * Sort Vehicles by Model then return the list
     * @return
     */
    public List<Vehicle> getSortedByModel() {
        vehicles.sort(byModel);
        return vehicles;
    }

    /**
     * Using JAVA8 functionality create a stream which filters the collection of vehicles
     *  by Model.getYear()
     * @param year
     * @return
     */
    public List<Vehicle> fetchByYear(int year)
    {
        return vehicles
                .stream()
                .filter(x -> x.getModel().getYear() == year)
                .collect(Collectors.toList());
    }

    /**
     * Collects from a stream filtered by manufacturer name
     * @param manName
     * @return
     */
    public List<Vehicle> fetchByManufacturer(String manName)
    {
        return vehicles
                .stream()
                .filter(x -> x.getMake().getName().equals(manName))
                .collect(Collectors.toList());
    }

    /**
     * Collects from a stream filtered by model name
     * @param modelName
     * @return
     */
    public List<Vehicle> fetchByModel(String modelName)
    {
        return vehicles
                .stream()
                .filter(x -> x.getModel().getName().equals(modelName))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of vehicles with the same VIN
     *  This will return a list however conceptually VINs should be unique however for the
     *  scope of this project the uniqueness of the VIN is up to the end user
     * @param VIN
     * @return
     */
    public List<Vehicle> getVehicleByVIN(String VIN) {
        return vehicles
                .stream()
                .filter(x -> x.getVIN().equals(VIN))
                .collect(Collectors.toList());
    }

    /**
     * Returns list of vehicles by manufacturer name
     * @param man
     * @return
     */
    public List<Vehicle> getVehiclesByManufacturer(String man)
    {
        return vehicles.stream()
                .filter(x -> x.getMake().getName().equals(man))
                .collect(Collectors.toList());
    }

    /**
     * Returns list of vehicles by year
     * @param year
     * @return
     */
    public List<Vehicle> getVehiclesByYear(int year)
    {
        return vehicles.stream()
                .filter(x -> x.getModel().getYear() == year)
                .collect(Collectors.toList());
    }

    /**
     * Adds mileage to vehicle by VIN, returns -1 if failure
     * @param VIN
     * @param miles
     * @return
     */
    public int addMileageByVIN(String VIN, int miles)
    {
        List<Vehicle> temp = getVehicleByVIN(VIN);
        if(temp.isEmpty())
        {
            return -1;
        }
        else
        {
            //Add mileage to first vehicle found with that VIN since there should only be one
            temp.get(0).addMileage(miles);
            return temp.get(0).getMileage();
        }
    }

    public List<Vehicle> getDueOilChanges()
    {
        return vehicles
                .stream()
                .filter(x -> x.isDue() == true)
                .collect(Collectors.toList());
    }

    /**
     * Repository just contains an ArrayList<Vehicles>
     *     So the iterator just returns the iterator of that arraylist
     * @return
     */
    @Override
    public Iterator<Vehicle> iterator() {
        return vehicles.iterator();
    }
}
