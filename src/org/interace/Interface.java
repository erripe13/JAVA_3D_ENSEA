package org.interace;


import javafx.scene.PerspectiveCamera;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Translate;
import org.earth.Earth;
import org.world.Airport;
import org.world.World;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class Interface extends Application {
    private static final Logger LOG = Logger.getLogger(Interface.class.getName());
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set the stage title
        primaryStage.setTitle("org.interace.Interface");
        Earth earth = new Earth(); // Create an Earth object
        Scene theScene = new Scene(earth, 600, 400, true);

        // Create and configure the camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);
        theScene.setCamera(camera);

        // Add event handler for mouse events
        theScene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                System.out.println("Clicked on: (" + event.getSceneX() + ", " + event.getSceneY() + ")");
            }
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                double zoomFactor = event.getSceneY() - primaryStage.getHeight() / 2;
                camera.getTransforms().add(new Translate(0, 0, zoomFactor));
            }
        });

        // Add the scene to the stage
        primaryStage.setScene(theScene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        LOG.info("Hello world!");

        // Test airport
        Airport airport = new Airport("Charles de Gaulle", 49.0097, 2.5479, "CDG");
        LOG.info(airport.toString());

        // Test WOrld
        World world = new World("doc/airport-codes_no_comma.csv");

        LOG.info("Found " + world.getList().size() + " airports.");
        Airport paris = world.findNearestAirport(2.316, 48.866);
        Airport cdg = world.findByCode("CDG");
        double distance = world.distance(2.316, 48.866, paris.getLongitude(), paris.getLatitude());
        LOG.info(paris.toString());
        LOG.info(String.valueOf(distance));
        double distanceCDG = world.distance(2.316, 48.866, cdg.getLongitude(), cdg.getLatitude());
        LOG.info(cdg.toString());
        LOG.info(String.valueOf(distanceCDG));

        launch(args);


    }
}