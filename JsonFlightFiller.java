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
                    String flightNumber = result.getJsonObject("flight").getString("number");
                    String flightStatus = result.getString("flight_status");
                    JsonObject departure = result.getJsonObject("departure");
                    String departureAirport = departure.getString("airport");
                    String scheduledDeparture = departure.getString("scheduled");
                    JsonObject arrival = result.getJsonObject("arrival");
                    String arrivalAirport = arrival.getString("airport");
                    String scheduledArrival = arrival.getString("scheduled");

                    Flight flight = new Flight(flightNumber, flightStatus, departureAirport, arrivalAirport, scheduledDeparture, scheduledArrival);
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

    public static void main(String[] args) {
        try {
            World w = new World("./data/airport-codes_no_comma.csv");
            BufferedReader br = new BufferedReader(new FileReader("data/test.txt"));
            String test = br.readLine();
            JsonFlightFiller jsonFlightFiller = new JsonFlightFiller(test, w);
            // Print the list of flights to verify
            for (Flight flight : jsonFlightFiller.getList()) {
                System.out.println(flight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}