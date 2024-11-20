package org.earth;

import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Earth extends Group {
    public Earth() {
        Sphere sph = new Sphere(100); // Create a sphere with radius 100
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(javafx.scene.paint.Color.BLUE);
        sph.setMaterial(material);
        sph.setTranslateX(300); // Center the sphere horizontally
        sph.setTranslateY(200); // Center the sphere vertically
        this.getChildren().add(sph); // Add the sphere to the group
    }
}