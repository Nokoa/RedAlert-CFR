package media.cfr.redalert;


import media.cfr.redalert.models.Cities;

import media.cfr.redalert.scraper.Scraper;

import java.io.*;

public class RedAlert {
    private Cities cities;

    /**
     * The main method of the program
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scraper scraper = new Scraper();
        scraper.scrape();

    }
}





