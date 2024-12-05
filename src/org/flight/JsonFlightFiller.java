package org.flight;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.world.World;

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
                    JsonObject departure = result.getJsonObject("departure");
                    JsonObject arrival = result.getJsonObject("arrival");
                    String departureIataCode = departure.getString("iata");
                    String arrivalIataCode = arrival.getString("iata");
                    Flight flight = new Flight(departureIataCode, arrivalIataCode);
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
}