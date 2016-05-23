package travelcalculation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 *
 * @author Seán Hanway
 */

public class TravelCalculation {

    private final String apiKey = "AIzaSyCLRBCto16BSB8G3s7nHkDEWi0mC-cKxFA";
    private final String baseURL = "https://maps.googleapis.com/maps/api/directions/json?";
    private final String region;
    
    /** Default constructor sets the region to 'IE'
     *
     */
    public TravelCalculation(){
        Locale loc = Locale.getDefault();
        region = loc.getCountry();
    }
    
    /** Constructor with one String parameter changes the region to the inputted String.
     *
     * @param region Use ISO 3166-2 values. Example: IE, GB, FR.
     */
    public TravelCalculation(final String region){
        this.region = region;
    }
    
    /** Calculates the distance between to given points, giving priority to points found in the region specified by TravelCalculation's constructors.
     *
     * @param origin The starting location.
     * @param destination The finishing location.
     */
    public void calculateDistance(final String origin, final String destination){
        try{
            final URL google = buildURL(origin, destination);

            try(InputStreamReader is = new InputStreamReader(google.openStream())){
                JSONParser rdr = new JSONParser();
                Object obj = rdr.parse(is);
                JSONObject JsonObj = (JSONObject)obj;
                String status = "" + JsonObj.get("status");
                if (!(status.equals("OK"))) throw new IllegalArgumentException("No results found");
                JSONArray results = (JSONArray)JsonObj.get("routes");
                JSONObject result = (JSONObject)results.get(0);
                JSONArray legs = (JSONArray)result.get("legs");
                JSONObject routes = (JSONObject)legs.get(0);
                JSONObject resultDistance = (JSONObject)routes.get("distance");
                JSONObject resultTime = (JSONObject)routes.get("duration");

                final String distance = (String)resultDistance.get("text");
                final String time = (String)resultTime.get("text");

                System.out.println("Distance from " + origin + " to " + destination + " is " + distance + ".");
                System.out.println("It takes " + time + " to get from " + origin + " to " + destination + ".");

            } catch(IOException | IllegalArgumentException | org.json.simple.parser.ParseException f ){
                    System.out.println(f.getMessage());
                }
        } catch(MalformedURLException e){
            System.out.println("Error creating url");
        }
    }
    
    private URL buildURL(final String origin, final String destination) throws MalformedURLException{
        return new URL(baseURL + "origin=" + origin + "&destination="+destination+"&region=" + region + "&key=" + apiKey);
    }
}
