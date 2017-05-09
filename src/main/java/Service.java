import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Kenneth C. LaMarca on 5/6/2017.
 */
public class Service
{
    public final String[] commands = {"add mileage","help","add manufacturer"
            ,"check oil","view repo","add vehicle", "remove vehicle", "add model", "fetch vehicles","Exit"};
    Repository vehicleRepo;

    /**
     * Constructor with startup repo
     * @param temp
     */
    public Service(Repository temp)
    {
        vehicleRepo = temp;
    }

    /**
     * Constructor with empty Repo
     */
    public Service()
    {
        vehicleRepo = new Repository();
    }

    /**
     * Add Mileage to vehicle based on user input VIN
     */
    public void addMileageToVehicle()
    {
        String VIN;
        List<Vehicle> tempVehicle;
        int miles;
        int result;

        try
        {
            Console console = System.console();
            if(console != null)
            {
                System.out.println("\n\n__________Add Mileage____________\n\n");
                System.out.println("What is the VIN of the vehicle you would like to add mileage to?");
                VIN = console.readLine("VIN: ");
                System.out.println("How many miles would you like to add to the vehicle?");
                miles = Integer.parseInt(console.readLine("Miles: "));

                result = vehicleRepo.addMileageByVIN(VIN,miles);
                if(result == -1)
                {
                    System.out.println("VIN not found!");
                }
                else
                {
                    System.out.println("New mileage is: " + result);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * CLI for adding manufacturer to list
     */
    public void addManufacturer()
    {
        String name;
        String countryOrigin;
        String slogan;

        try
        {
            Console console = System.console();
            if(console != null)
            {
                System.out.println("\n\n__________Add Manufacturer____________\n\n");
                System.out.println("What is the Name of the manufacturer?");
                name = console.readLine("Name: ");
                System.out.println("What is the country of origin of the manufacturer?");
                countryOrigin = console.readLine("Country: ");
                System.out.println("What is the slogan of the manufacturer?");
                slogan = console.readLine("Slogan: ");

                vehicleRepo.addManufacturer(new Manufacturer(name,countryOrigin,slogan));
                System.out.println(vehicleRepo.getAllManufacturers());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * CLI for adding model to a manufacturer
     */
    public void addModel()
    {
        String name;
        String nameModel;
        int year;
        int month = 0;
        int miles = 0;
        Model newModel;
        boolean isElectric;
        Manufacturer thisMan;

        try
        {
            Console console = System.console();
            if(console != null)
            {
                System.out.println("\n\n__________Add Model____________\n\n");
                System.out.println("What is the Name of the manufacturer?");
                name = console.readLine("Name: ");

                //if that manufacturer exists
                if(vehicleRepo.getAllManufacturers().containsKey(name)) {
                    thisMan = vehicleRepo.getAllManufacturers().get(name);

                    System.out.println("What is the Name of the model you would like to add?");
                    nameModel = console.readLine();
                    System.out.println("What is the Year of the model you would like to add?");
                    year = Integer.parseInt(console.readLine());
                    System.out.println("Is the car electric? True/False");
                    isElectric = Boolean.parseBoolean(console.readLine());

                    if (!isElectric) {
                        System.out.println("How many miles between oil changes?");
                        miles = Integer.parseInt(console.readLine());
                        System.out.println("How many months between oil changes?");
                        month = Integer.parseInt(console.readLine());
                    }

                    newModel = new Model(nameModel,year, new Oil(miles,month,isElectric));
                    thisMan.addModel(newModel);
                }
                else
                {
                    System.out.println("Manufacturer does not exist!");
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Print all the commands
     */
    public void help()
    {
        System.out.println("\n\n__________Commands____________\n\n");
        for(String c : commands)
        {
            System.out.println(c);
        }
    }

    /**
     * Prints a list of all vehicles in the repository given different settings
     *  Print by VIN, by Model or by Year
     */
    public void viewRepo()
    {
        List<Vehicle> temp;
        System.out.println("\n\n__________View Repo____________\n\n");

        Console console = System.console();
        if(console != null) {
            System.out.println("Would you like to sort by VIN , model or year?  VIN will be used by default");
            String sort = System.console().readLine();

            switch (sort) {
                case "VIN":
                    temp = vehicleRepo.getSortedByVIN();
                    break;
                case "model":
                    temp = vehicleRepo.getSortedByModel();
                    break;
                case "year":
                    temp = vehicleRepo.getSortedByYear();
                    break;
                default:
                    temp = vehicleRepo.getSortedByVIN();
            }
            System.out.println("List of all Vehicles in the repository\n");
        }
        else{
            temp = vehicleRepo.getSortedByVIN();
        }
        for(Vehicle v : temp)
        {
            System.out.println(v);
        }
    }

    /**
     * Print all vehicles that require oil changes
     */
    public void checkOil()
    {
        System.out.println("\n\n__________Check Oil____________\n\n");
        List<Vehicle> temp = vehicleRepo.getDueOilChanges();
        System.out.println("List of all Vehicles that require oil changes\n");

        for(Vehicle v : temp)
        {
            System.out.println(v.toStringOil());
        }
    }

    /**
     * Print all vehicles fetched by a certain parameter
     *  parameters are:
     *      Make, Year, VIN
     */
    public void fetchVehicles()
    {
        System.out.println("\n\n__________Fetch Vehicles_____________\n\n");
        List<Vehicle> temp = new ArrayList<Vehicle>();
        String command;
        try {
            Console console = System.console();
            if (console != null) {
                System.out.println("Fetch by VIN, Year or Manufacturer");
                command = console.readLine();

                switch(command){
                    case "VIN":
                        System.out.println("Input VIN of vehicle");
                        temp = vehicleRepo.getVehicleByVIN(console.readLine());
                        break;
                    case "Manufacturer":
                        System.out.println("Input Manufacturer");
                        temp = vehicleRepo.getVehiclesByManufacturer(console.readLine());
                        break;
                    case "Year":
                        System.out.println("Input Year");
                        temp = vehicleRepo.getVehiclesByYear(Integer.parseInt(console.readLine()));
                        break;
                }
                System.out.println(temp);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Remove a vehicle from the by the VIN
     */
    public void removeVehicle()
    {
        String vin;

        System.out.println("\n\n__________Remove Vehicle____________\n\n");
        try {
            Console console = System.console();
            if (console != null) {
                System.out.println("What is the VIN of the vehicle you would like to remove?");
                vin = console.readLine();

                if(vehicleRepo.removeVehicle(vin))
                    System.out.println("Vehicle Removed!");
                else
                    System.out.println("Vehicle Not Found!");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Add Vehicle to repo
     */
    public void addVehicle()
    {
        System.out.println("\n\n__________Add Vehicle____________\n\n");
        System.out.println("All existing models in the repository: \n");
        System.out.println(vehicleRepo.getAllModels());

        Vehicle newVehicle;
        String input;
        List<String> temp = new ArrayList<String>();
        String VIN;
        String color;
        String make;
        String model;
        int year;
        int mileage;
        int mileageSinceOil;
        LocalDate dateSinceOil;
        Map<String,Manufacturer> man;
        List<Model> myModel;

        try {
            Console console = System.console();
            if (console != null)
            {
                while(temp.size() < 3) {
                    System.out.println("What is the Make,Model,Year?");
                    input = console.readLine();
                    temp = Arrays.asList(input.split(","));
                    if (temp.size() < 3)
                        System.out.println("Missing arguments");
                }
                make = temp.get(0);
                model = temp.get(1);
                year = Integer.parseInt(temp.get(2));

                //Verify existence of make
                man = vehicleRepo.getAllManufacturers();
                if(man.containsKey(make))
                {
                    myModel = man.get(make).fetchModelByNameYear(model,year);
                    //verify model / year combo as model by make
                    if(myModel.size() > 0)
                    {
                        System.out.println("What is the VIN of the vehicle?");
                        VIN = console.readLine();
                        System.out.println("What is the color of the vehicle?");
                        color = console.readLine();
                        System.out.println("What is the mileage of the vehicle?");
                        mileage = Integer.parseInt(console.readLine());
                        System.out.println("What is the mileage of the last oil change?");
                        mileageSinceOil = Integer.parseInt(console.readLine());
                        System.out.println("What is the date of the last oil change? YYYY-MM-DD");
                        dateSinceOil = LocalDate.parse(console.readLine(),DateTimeFormatter.ISO_LOCAL_DATE);
                        vehicleRepo.addNewVehilce(man.get(make),myModel.get(0),VIN,color,mileage,mileageSinceOil,dateSinceOil);
                    }
                    else{
                        System.out.println("Model does not exist!");
                    }
                }
                else{
                    System.out.println("Make does not exist!");
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Very simple Command line interface
     */
    public void CLI()
    {
        String command = "";
        Console myConsole = System.console();

        if(myConsole != null) {
            //Startup
            viewRepo();
            checkOil();
            help();

            while(!(command.equals("Q") || command.equals("q") || command.equals("Exit")))
            {
                command = myConsole.readLine();

                switch(command)
                {
                    case "add model":
                        addModel();
                        break;
                    case "add mileage":
                        addMileageToVehicle();
                        break;
                    case "add manufacturer":
                        addManufacturer();
                        break;
                    case "check oil":
                        checkOil();
                        break;
                    case "help":
                        help();
                        break;
                    case "view repo":
                        viewRepo();
                        break;
                    case "add vehicle":
                        addVehicle();
                        break;
                    case "remove vehicle":
                        removeVehicle();
                        break;
                    case "fetch vehicles":
                        fetchVehicles();
                        break;
                }
            }
        }
        //If there is no console just print out the required elements
        else{
            viewRepo();
            checkOil();
        }
    }

    public static void main(String args[])
    {
        //Oil change rules
        Oil defaultOil = new Oil();
        Oil syntheticOil = new Oil(10000,12,false);
        Oil electric = new Oil(0,0,true);
        Oil HondaOil = new Oil(7000,7,false);

        //Test data
        Manufacturer FORD = new Manufacturer("Ford","USA","Built Ford Tough");
        Model Fusion = new Model("Fusion",2016,defaultOil);
        FORD.addModel(Fusion);

        Manufacturer TESLA = new Manufacturer("Tesla","USA","Tesla: Batteries Included!");
        Model Three = new Model("Model 3",2017,electric);
        TESLA.addModel(Three);

        Manufacturer VOLKSWAGEN = new Manufacturer("VW","Germany","Das Auto");
        Model Golf = new Model("Golf",2016,syntheticOil);
        Model Golf2015 = new Model("Golf",2015,syntheticOil);
        VOLKSWAGEN.addModel(Golf);
        VOLKSWAGEN.addModel(Golf2015);

        Manufacturer BMW = new Manufacturer("BMW","Germany", "\u00A9 BMW AG, Munich,Germany");
        Model X6 = new Model("X6",2012,HondaOil);
        BMW.addModel(X6);


        Repository vehicleRepository = new Repository();
        vehicleRepository.addManufacturer(TESLA);
        vehicleRepository.addManufacturer(FORD);
        vehicleRepository.addManufacturer(VOLKSWAGEN);
        vehicleRepository.addManufacturer(BMW);

        vehicleRepository.addNewVehicle(TESLA,Three,"HAFC34215","Blue");
        vehicleRepository.addNewVehicle(FORD,Fusion,"ZFS34213F","Red");
        vehicleRepository.addNewVehicle(VOLKSWAGEN,Golf,"VWDS52362","Black");

        //VW that needs oil change
        LocalDate lastOilDate = LocalDate.now();
        lastOilDate =lastOilDate.minusMonths(13);
        vehicleRepository.addNewVehilce(VOLKSWAGEN,Golf2015, "VWZZ5251","Blue",11000
                ,50, lastOilDate);


        //BMW with mileage does not need oil change
        lastOilDate = LocalDate.now().minusMonths(4);
        vehicleRepository.addNewVehilce(BMW,X6,"BMW351634","Grey",24000
                ,19000,lastOilDate);


        Service myService = new Service(vehicleRepository);

        //CLI Code
        myService.CLI();
    }
}
