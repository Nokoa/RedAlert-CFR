package media.cfr.redalert.scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebRequest {

    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36";

    private final String GET_URL = "https://www.oref.org.il/WarningMessages/alert/alerts.json";

    private final String REFERRER = "https://www.oref.org.il/12481-he/Pakar.aspx";


    /**
     * Make a get request to Pikud Haoref website to get active alerts.
     * Submitting the request with the proper headers
     *
     * Encodes to UTF-8
     * @return String response if code 200, null if anything else
     * @throws IOException
     */
    public String requestAlertsFromWeb() throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Referer", REFERRER);
        con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream(),  "UTF-8")); //Must encode to UTF-8 for Hebrew
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("Error with the get request");
        }
        return null;
    }

}
