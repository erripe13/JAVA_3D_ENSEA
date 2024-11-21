import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
//import org.world.Aeroport;

public class Earth extends Group {
    private static final double RAYON = 150;

    public Earth() {
        Sphere sph = new Sphere(RAYON);

        PhongMaterial materialTerre = new PhongMaterial();
        Image textureTerre = new Image(getClass().getResource("data/earth_lights_4800.png").toExternalForm());
        materialTerre.setDiffuseMap(textureTerre);
        sph.setMaterial(materialTerre);

        Rotate rotation = new Rotate(0, Rotate.Y_AXIS);
        this.getTransforms().add(rotation);
        this.getChildren().add(sph);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long maintenant) {
                rotation.setAngle(rotation.getAngle() + 0.1);
            }
        };
        animationTimer.start();
    }

    public void afficherMarqueur(Aeroport aeroport) {
        double latitude = aeroport.getLatitude();
        double longitude = aeroport.getLongitude();

        Sphere sphere = new Sphere(3);
        PhongMaterial materialRouge = new PhongMaterial(Color.RED);
        sphere.setMaterial(materialRouge);

        sphere.setTranslateX(RAYON * Math.cos(Math.toRadians(latitude)) * Math.sin(Math.toRadians(longitude)));
        sphere.setTranslateY(-RAYON * Math.sin(Math.toRadians(latitude)));
        sphere.setTranslateZ(-RAYON * Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(longitude)));

        this.getChildren().add(sphere);
    }
}
