import javafx.scene.Group;
import javafx.scene.shape.Sphere;

public class Earth extends Group {
    private Sphere sph;

    public Earth() {
        sph = new Sphere(300);
        this.getChildren().add(sph);
    }
} 