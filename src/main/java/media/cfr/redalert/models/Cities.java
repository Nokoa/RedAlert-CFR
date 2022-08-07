package media.cfr.redalert.models;

import java.util.List;

/**
 * The Cities model for the city locale json file
 */
public class Cities {
    private List<City> cities;

    public Cities(List<City> cities) {
        this.cities = cities;
    }

    public Cities() {
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
