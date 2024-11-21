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
                    } else {
                        LOG.fine("No airport found near the clicked point.");
                    }
                }
            }
        });
        // Add event handler for scroll events (zoom) cause more efficient
        theScene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double zoomFactor = event.getDeltaY();
            camera.getTransforms().add(new Translate(0, 0, zoomFactor));
        });

        // Add the scene to the stage
        primaryStage.setScene(theScene);
        primaryStage.show();

    }


    public static void main(String[] args) {


        // Test airport
        Airport airport = new Airport("Charles de Gaulle", 49.0097, 2.5479, "CDG");


        // Test World
        World world = new World("doc/airport-codes_no_comma.csv");


        Airport paris = world.findNearestAirport(2.316, 48.866);
        Airport cdg = world.findByCode("CDG");
        double distance = world.distance(2.316, 48.866, paris.getLongitude(), paris.getLatitude());


        double distanceCDG = world.distance(2.316, 48.866, cdg.getLongitude(), cdg.getLatitude());

        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Hello world!");

            LOG.fine("Found " + world.getList().size() + " airports.");
            LOG.fine(airport.toString());
            LOG.fine(paris.toString());
            LOG.fine(cdg.toString());
            LOG.fine("Distance nearest: " + distance);
            LOG.fine("Distance CDG: " + distanceCDG);
        }

        launch(args);


    }
}