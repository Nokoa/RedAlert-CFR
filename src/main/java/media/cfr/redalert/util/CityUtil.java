package media.cfr.redalert.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import media.cfr.redalert.models.Cities;
import media.cfr.redalert.models.City;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//todo add אתר דודאים to cities file Duda'im Site

/**
 * A utility class used to get the files paths that were predefined based on the english names of cities in the cities.json
 */
public class CityUtil {
    public static Cities cities;
    public static HashMap<String, City> cityMap;
    private static Gson gson;

    static {
        gson = new Gson();
        JsonReader reader = null;
        try {
            FileInputStream fis = new FileInputStream("cities.json");
            reader = new JsonReader(new InputStreamReader(fis, "UTF-8"));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        cities = gson.fromJson(reader, Cities.class);

        cityMap =
                new HashMap<>();
        System.out.println("creting ststic");
        for (City city : cities.getCities()) {
            cityMap.put(city.getName(), city);
        }
    }

    /**
     * Gets the city audio file path based on the Hebrew name of the city, or multiple cities.
     * City names are exact and are extracted from the cities.json file
     *
     * If not found a "select all" file that is available will play
     *
     * Not all cities are currently supported in cities.json
     * @param hebrewCityName the hebrew name of the city or cities
     * @return String file path name of the english audio
     */
    public static String getCityFilePath(String hebrewCityName) {
        System.out.println(hebrewCityName + " " + cityMap.size());
        try {
            return "sound/cities/" + cityMap.get(hebrewCityName.trim()).getName_en() + "_en.mp3";
        }catch (Exception e){
            System.out.println("Tried to find " + hebrewCityName.trim() + " but it was not found in the list. cityMap has " + cityMap.size());
            e.printStackTrace();
            return "sound/cities/Select All_en.mp3";
        }
    }

}
