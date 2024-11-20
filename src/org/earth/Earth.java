package org.earth;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class Earth extends Group {
    public Earth() {
        Sphere sph = new Sphere(100); // Create a sphere with radius 100

        // Create a new PhongMaterial
        PhongMaterial material = new PhongMaterial();

        // Map the image to the material
        Image earthImage = new Image(getClass().getResource("/org/earth/earth_lights_4800.png").toExternalForm());
        material.setDiffuseMap(earthImage);

        // Set the material to the sphere
        sph.setMaterial(material);

        // Create a Rotate object for rotation around the Y axis
        Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        sph.getTransforms().add(ry);

        this.getChildren().add(sph); // Add the cube to the group

        // Create and start the AnimationTimer
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                ry.setAngle(ry.getAngle() + 0.25); // Increment the angle for rotation
            }
        };
        animationTimer.start();
    }
}
