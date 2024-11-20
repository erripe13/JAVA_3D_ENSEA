import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Earth extends Group {
    private Sphere sph;

    public Earth() {
        // Créer une sphère représentant la Terre avec un rayon de 300 pixels
        sph = new Sphere(300);

        // Charger la texture de la Terre en utilisant PhongMaterial
        PhongMaterial earthMaterial = new PhongMaterial();
        Image earthImage = new Image("data/earth_lights_4800.png"); // Remplacez par le chemin correct de l'image
        earthMaterial.setDiffuseMap(earthImage);
        sph.setMaterial(earthMaterial);

        // Ajouter la sphère au groupe
        this.getChildren().add(sph);
    }
}
