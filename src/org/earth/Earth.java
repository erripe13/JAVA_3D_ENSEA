package org.earth;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import org.world.Airport;

public class Earth extends Group {
    private static final double RADIUS = 100; // Radius of the Earth

    public Earth() {
        Sphere sph = new Sphere(RADIUS); // Create a sphere with radius 100

        // Create a new PhongMaterial
        PhongMaterial material = new PhongMaterial();

        // Map the image to the material
        Image earthImage = new Image(getClass().getResource("/org/earth/earth_lights_4800.png").toExternalForm());
        material.setDiffuseMap(earthImage);

        // Set the material to the sphere
        sph.setMaterial(material);

        // Create a Rotate object for rotation around the Y axis
        Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        this.getTransforms().add(ry);

        this.getChildren().add(sph); // Add the sphere to the group

        // Create and start the AnimationTimer
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                ry.setAngle(ry.getAngle() + 0.25); // Increment the angle for rotation
            }
        };
        animationTimer.start();
    }

    public Sphere createSphere(Airport a, Color color) {
        double latitude = (a.getLatitude());
        double longitude = (a.getLongitude());



        Sphere sphere = new Sphere(2); // Create a sphere with radius 2
        PhongMaterial material = new PhongMaterial(color);
        sphere.setMaterial(material);

        sphere.setTranslateZ(-RADIUS);
        // Create a Rotate object for rotation around the Y axis
        Rotate rs = new Rotate(-longitude,-sphere.getTranslateX(),-sphere.getTranslateY(),-sphere.getTranslateZ(), Rotate.Y_AXIS);
        sphere.getTransforms().add(rs);

        Rotate rv = new Rotate(-latitude*(7.0/10.0),-sphere.getTranslateX(),-sphere.getTranslateY(),-sphere.getTranslateZ(), Rotate.X_AXIS);
        sphere.getTransforms().add(rv);
        return sphere;
    }

    public void displayRedSphere(Airport a) {
        Sphere redSphere = createSphere(a, Color.RED);
        this.getChildren().add(redSphere);
    }
}
