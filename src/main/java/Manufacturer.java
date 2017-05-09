import java.util.*;
import java.util.stream.Collectors;

/**
 * Manufacturer Class
 *  Members:    Name, CountryOfOrigin, List<Models>, Map<Models,Oil>, Slogan
 * Created by Kenneth C. LaMarca on 5/6/2017.
 */
public class Manufacturer
{

    private String name;
    private String countryOfOrigin;
    private String slogan;
    private ArrayList<Model> models;

    /**
     * Default Constructor
     */
    public Manufacturer()
    {
        this.name = "3909 LLC";
        this.countryOfOrigin = "Arstotzka";
        this.slogan = "Papers Please";

        models = new ArrayList<Model>();
    }

    /**
     * Constructor that will actually be used
     */
    public Manufacturer(String name, String coutnryOfOrigin, String slogan)
    {
        this.name = name;
        this.countryOfOrigin = coutnryOfOrigin;
        this.slogan = slogan;

        models = new ArrayList<Model>();
    }

    /**
     * Constructor for manufacturer with existing models
     */
    public Manufacturer(String name, String countryOfOrigin, String slogan,
                        List<Model> models)
    {
        this.name = name;
        this.countryOfOrigin = countryOfOrigin;
        this.slogan = slogan;
        this.models = new ArrayList<Model>(models);
    }

    /**
     * add new model with default oil change rules
     * @param newModel
     */
    public void addModel(Model newModel)
    {
        models.add(newModel);
    }

    public List<Model> fetchModelByNameYear(String name, int year)
    {
        return models.stream()
                .filter(x -> x.getName().equals(name))
                .filter(x -> x.getYear() == year)
                .collect(Collectors.toList());
    }

    /**
     * Get the list of models by this manufacturer
     * @return
     */
    public List<Model> getAllModels()
    {
        return models;
    }

    /**
     * Return the name of the manufacturer
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the slogan of the manufacturer
     * @return
     */
    public String getSlogan()
    {
        return slogan;
    }

    /**
     * String representation of Manufacturer
     * @return
     */
    public String toString()
    {
       return name + ", Country : " + countryOfOrigin + ", Slogan : " + slogan + "\n";
    }
}
