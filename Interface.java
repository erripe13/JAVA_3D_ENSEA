import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
// World;

public class Interface extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Terre");

        Earth earth = new Earth();
        World world = new World("data/airport-codes_no_comma.csv");

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-300);

        Scene scene = new Scene(earth, 800, 600, true);
        scene.setCamera(camera);

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            System.out.println("Coordonnées cliquées : " + x + ", " + y);
        });

        scene.addEventHandler(ScrollEvent.SCROLL, event -> {
            camera.setTranslateZ(camera.getTranslateZ() + event.getDeltaY() * 0.1);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
