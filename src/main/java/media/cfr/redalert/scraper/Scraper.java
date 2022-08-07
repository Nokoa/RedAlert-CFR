package media.cfr.redalert.scraper;

import com.google.gson.Gson;
import media.cfr.redalert.models.Response;
import media.cfr.redalert.player.PlayerManager;

import java.io.IOException;
import java.util.*;

/**
 * In charge of scraping the data from the API, and submitting the data to the media player manager
 */
public class Scraper {
    private final int DELAY = 3000;
    Gson gson;
    WebRequest webRequest;
    long previousId;
    private PlayerManager playerManager;


    public Scraper() {
        gson = new Gson();
        webRequest = new WebRequest();
        playerManager = new PlayerManager();


    }

    String fakeResult = "{\n" +
            "    \"id\": \"133043619500000000\",\n" +
            "    \"cat\": \"1\",\n" +
            "    \"title\": \"ירי רקטות וטילים\",\n" +
            "    \"data\": [\n" +
            "        \"מפלסים\",\n" +
            "        \"שדרות, איבים, ניר עם\",\n" +
            "        \"מטווח ניר עם\"\n" +
            "    ],\n" +
            "    \"desc\": \"היכנסו למרחב המוגן ושהו בו 10 דקות\"\n" +
            "}";

    /**
     * Begin the repeating scrape process based on the defined delay
     * If results can be parsed to JSON they will be submitted to the media player to alert of the event
     *
     * Improper response from the API may break this. todo
     */
    public void scrape() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String result = webRequest.requestAlertsFromWeb();

                    System.out.println("Result " + result.length() + " :" + result);
                    if (result.trim().isEmpty())
                        return;
                    Response response = gson.fromJson(result, Response.class); // todo
                    if (response != null && response.getId() != previousId) {
                        previousId = response.getId();
                        ArrayList<String> alertLocations = new ArrayList<>();
                        for (String data : response.getData()) {
                            System.out.println(data);
                            alertLocations.add(data);

                        }
                        playerManager.addLocationAlerts(alertLocations);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask,
                0, DELAY);
        ;
    }
}
