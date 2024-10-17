// Classe JsonFlightFillerOracle (Pour récupérer des données de l'API JSON)
import java.io.*;
import org.json.*;

public class JsonFlightFillerOracle {
    private String jsonData;

    public JsonFlightFillerOracle(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            jsonData = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseAndPrintFlights() {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray flights = jsonObject.getJSONArray("data");
            for (int i = 0; i < flights.length(); i++) {
                JSONObject flight = flights.getJSONObject(i);
                System.out.println(flight);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
} 