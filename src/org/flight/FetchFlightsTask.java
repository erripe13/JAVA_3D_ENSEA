package org.flight;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.earth.Earth;
import org.world.Airport;
import org.world.World;
import javafx.application.Platform;

public class FetchFlightsTask implements Runnable {
    private final Earth earth;
    private final Airport clickedAirport;
    private final World world;

    public FetchFlightsTask(Earth earth, Airport clickedAirport, World world) {
        this.earth = earth;
        this.clickedAirport = clickedAirport;
        this.world = world;
    }

    @Override
    public void run() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.aviationstack.com/v1/flights?access_key=698fa7a1523c8db3c89d206a9bb4ecb0&arr_iata=" + clickedAirport.getCodeIATA())) // Replace with the actual API URL
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response with JsonFlightFiller
            JsonFlightFiller jsonFlightFiller = new JsonFlightFiller(response.body(), world);
            ArrayList<Flight> flights = jsonFlightFiller.getList();

            // Display departure airports as yellow spheres for flights arriving at the clicked airport
            Platform.runLater(() -> {
                for (Flight flight : flights) {
                    if (clickedAirport.getCodeIATA().equals(flight.getArrivalIataCode())) {
                        Airport departureAirport = world.findByCode(flight.getDepartureIataCode());
                        if (departureAirport != null) {
                            earth.displayYellowSphere(departureAirport);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
