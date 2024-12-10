import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import javafx.application.Platform;

public class GatherFlightsTask implements Runnable {
    private final Earth earth;
    private final Aeroport clickedAirport;
    private final World world;

    public GatherFlightsTask(Earth earth, Aeroport clickedAirport, World world) {
        this.earth = earth;
        this.clickedAirport = clickedAirport;
        this.world = world;
    }

    @Override
    public void run() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.aviationstack.com/v1/flights?access_key=e7d8f8fb854d80436dcf06980e0b4c5b&arr_iata=" + clickedAirport.getIATA())) // Replace with the actual API URL
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response with JsonFlightFiller
            JsonFlightFiller jsonFlightFiller = new JsonFlightFiller(response.body(), world);
            ArrayList<Flight> flights = jsonFlightFiller.getList();

            // Display departure airports as yellow spheres for flights arriving at the clicked airport
            Platform.runLater(() -> {
                for (Flight flight : flights) {
                    if (clickedAirport.getIATA().equals(flight.getArrivalAirport())) {
                        Aeroport departureAirport = world.findByCode(flight.getDepartureAirport());
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
