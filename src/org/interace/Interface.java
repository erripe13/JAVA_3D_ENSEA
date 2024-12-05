package org.interace;

import javafx.scene.PerspectiveCamera;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Translate;
import org.earth.Earth;
import org.world.Airport;
import org.world.World;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.ArrayList;

import org.flight.JsonFlightFiller;
import org.flight.Flight;
import java.io.BufferedReader;
import java.io.FileReader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Interface extends Application {
    private static final Logger LOG = Logger.getLogger(Interface.class.getName());
    static {
        // Set the logging level to FINE
        LOG.setLevel(Level.FINE);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        LOG.addHandler(handler);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set the stage title
        primaryStage.setTitle("org.interace.Interface");
        Earth earth = new Earth(); // Create an Earth object
        Scene theScene = new Scene(earth, 600, 400, true);

        // Create and configure the camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-100);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);
        theScene.setCamera(camera);

        // Add event handler for mouse events
        theScene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getButton() == MouseButton.SECONDARY && event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                PickResult pickResult = event.getPickResult();
                if (pickResult.getIntersectedNode() != null) {
                    // Retrieve the intersection point
                    double x = pickResult.getIntersectedTexCoord().getX();
                    double y = pickResult.getIntersectedTexCoord().getY();

                    // Convert to latitude and longitude
                    double latitude = 180 * (0.5 - y);
                    double longitude = 360 * (x - 0.5);

                    // Find the nearest airport
                    World world = new World("doc/airport-codes_no_comma.csv");
                    Airport nearestAirport = world.findNearestAirport(longitude, latitude);

                    // Display the nearest airport in the console and add a red sphere
                    if (nearestAirport != null) {
                        LOG.fine("Nearest airport: " + nearestAirport.toString());
                        earth.displayRedSphere(nearestAirport);

                        // Fetch flight data and display yellow spheres
                        fetchAndDisplayFlights(earth, nearestAirport, world);
                    } else {
                        LOG.fine("No airport found near the clicked point.");
                    }
                }
            }
        });

        // Add event handler for scroll events (zoom)
        theScene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double zoomFactor = event.getDeltaY();
            camera.getTransforms().add(new Translate(0, 0, zoomFactor));
        });

        // Add the scene to the stage
        primaryStage.setScene(theScene);
        primaryStage.show();
    }

    private void fetchAndDisplayFlights(Earth earth, Airport clickedAirport, World world) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.aviationstack.com/v1/flights?access_key=698fa7a1523c8db3c89d206a9bb4ecb0&arr_iata="+clickedAirport.getCodeIATA())) // Replace with the actual API URL
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response with JsonFlightFiller
            JsonFlightFiller jsonFlightFiller = new JsonFlightFiller(response.body(), world);
            ArrayList<Flight> flights = jsonFlightFiller.getList();

            // Display departure airports as yellow spheres for flights arriving at the clicked airport
            for (Flight flight : flights) {
                if (clickedAirport.getCodeIATA().equals(flight.getArrivalIataCode())) {
                    Airport departureAirport = world.findByCode(flight.getDepartureIataCode());
                    if (departureAirport != null) {
                        earth.displayYellowSphere(departureAirport);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}