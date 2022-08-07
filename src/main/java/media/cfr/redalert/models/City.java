package media.cfr.redalert.models;

/**
 * City model for the the city object in the json cities locale file
 */
public class City {
    private String name;
    private String name_en;
    private String zone;
    private String time;
    private String time_en;
    private int countdown;
    private String value;
    private double lat;
    private double log;

    public City(String name, String name_en, String zone, String time, String time_en, int countdown, String value, double lat, double log) {
        this.name = name;
        this.name_en = name_en;
        this.zone = zone;
        this.time = time;
        this.time_en = time_en;
        this.countdown = countdown;
        this.value = value;
        this.lat = lat;
        this.log = log;
    }

    public City() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime_en() {
        return time_en;
    }

    public void setTime_en(String time_en) {
        this.time_en = time_en;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }
}
