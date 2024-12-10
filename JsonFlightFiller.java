import javax.json.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JsonFlightFiller {
    private ArrayList<Flight> list = new ArrayList<>();

    public JsonFlightFiller(String jsonString, World w) {
        try {
            InputStream is = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            JsonReader rdr = Json.createReader(is);
            JsonObject obj = rdr.readObject();
            JsonArray results = obj.getJsonArray("data");

            for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                try {
                    // Extract flight number
                    JsonObject flightObj = result.getJsonObject("flight");
                    String flightNumber = getJsonStringSafely(flightObj, "number");

                    // Extract flight status
                    String flightStatus = getJsonStringSafely(result, "flight_status");

                    // Extract departure details
                    JsonObject departure = result.getJsonObject("departure");
                    String departureAirport = getJsonStringSafely(departure, "airport");
                    String departureIata = getJsonStringSafely(departure, "iata");
                    String scheduledDeparture = getJsonStringSafely(departure, "scheduled");

                    // Extract arrival details
                    JsonObject arrival = result.getJsonObject("arrival");
                    String arrivalAirport = getJsonStringSafely(arrival, "airport");
                    String arrivalIata = getJsonStringSafely(arrival, "iata");
                    String scheduledArrival = getJsonStringSafely(arrival, "scheduled");

                    // Create Flight object and add to list
                    Flight flight = new Flight(flightNumber, flightStatus, departureIata, arrivalIata, scheduledDeparture, scheduledArrival);
                    list.add(flight);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Flight> getList() {
        return list;
    }

    /**
     * Safely gets a string value from a JSON object.
     */
    private String getJsonStringSafely(JsonObject obj, String key) {
        if (obj.containsKey(key) && !obj.isNull(key)) {
            return obj.getString(key, "Unknown");
        }
        return "Unknown";
    }
}
