import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;

public class Interface extends Application {
    private static final Logger LOG = Logger.getLogger(Interface.class.getName());
    static {
        LOG.setLevel(Level.FINE);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        LOG.addHandler(handler);
    }

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Terre");

        Earth earth = new Earth();
        World world = new World("data/airport-codes_no_comma.csv");

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-300);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);

        // Ajout des rotations à la caméra
        camera.getTransforms().addAll(rotateX, rotateY);

        Scene scene = new Scene(earth, 800, 600, true);
        scene.setCamera(camera);

        // Gestion des événements de la souris pour la rotation de la caméra
//        scene.setOnMousePressed(event -> {
//            if (event.getButton() == MouseButton.PRIMARY) {
//                anchorX = event.getSceneX();
//                anchorY = event.getSceneY();
//                anchorAngleX = rotateX.getAngle();
//                anchorAngleY = rotateY.getAngle();
//            }
//        });
//
//        scene.setOnMouseDragged(event -> {
//            if (event.getButton() == MouseButton.PRIMARY) {
//                double deltaX = event.getSceneX() - anchorX;
//                double deltaY = event.getSceneY() - anchorY;
//                rotateY.setAngle(anchorAngleY + deltaX / 2);
//                rotateX.setAngle(anchorAngleX - deltaY / 2);
//            }
//        });

        // Gestion du zoom avec la molette de la souris
        scene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double zoomFactor = event.getDeltaY() * 0.1;
            camera.getTransforms().add(new Translate(0, 0, zoomFactor));
        });

        // Ajout de l'événement clic droit pour afficher un marqueur sur le point cliqué
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                PickResult pickResult = event.getPickResult();
                if (pickResult.getIntersectedNode() != null) {
                    double x = pickResult.getIntersectedTexCoord().getX();
                    double y = pickResult.getIntersectedTexCoord().getY();

                    double latitude = 180 * (0.5 - y);
                    double longitude = 360 * (x - 0.5);

                    Aeroport nearestAirport = world.trouverAeroportLePlusProche(latitude, longitude);

                    if (nearestAirport != null) {
                        LOG.fine("Nearest airport: " + nearestAirport.toString());
                        earth.afficherMarqueur(nearestAirport);
                        GatherFlightsTask fetchFlightsTask = new GatherFlightsTask(earth, nearestAirport, world);
                        new Thread(fetchFlightsTask).start();
                    } else {
                        LOG.fine("No airport found near the clicked point.");
                    }
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
