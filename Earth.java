import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class Earth extends Group {
    private static final double RADIUS = 50; // Rayon de la Terre

    public Earth() {
        Sphere sph = new Sphere(RADIUS); // Créer une sphère avec le nouveau rayon

        PhongMaterial material = new PhongMaterial();
        Image earthImage = new Image(getClass().getResource("data/earth_lights_4800.png").toExternalForm());
        material.setDiffuseMap(earthImage);
        sph.setMaterial(material);

        Rotate rotation = new Rotate(0, Rotate.Y_AXIS);
        this.getTransforms().add(rotation);
        this.getChildren().add(sph);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long maintenant) {
                rotation.setAngle(rotation.getAngle() + 0.25); // Vitesse de rotation ajustée
            }
        };
        animationTimer.start();
    }

    public Sphere createSphere(Aeroport aeroport, Color color) {
        double latitude = aeroport.getLatitude();
        double longitude = aeroport.getLongitude();

        Sphere sphere = new Sphere(2);
        PhongMaterial material = new PhongMaterial(color);
        sphere.setMaterial(material);

        sphere.setTranslateZ(-RADIUS);
        Rotate rs = new Rotate(-longitude, -sphere.getTranslateX(), -sphere.getTranslateY(), -sphere.getTranslateZ(), Rotate.Y_AXIS);
        sphere.getTransforms().add(rs);
        Rotate rv = new Rotate(-latitude * (7.0 / 10.0), -sphere.getTranslateX(), -sphere.getTranslateY(), -sphere.getTranslateZ(), Rotate.X_AXIS);
        sphere.getTransforms().add(rv);

        return sphere;
    }

    public void afficherMarqueur(Aeroport aeroport) {
        Sphere sphereRouge = createSphere(aeroport, Color.RED);
        this.getChildren().add(sphereRouge);
    }
}
