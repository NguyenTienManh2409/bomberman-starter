import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class FlameSegment extends Entity {

    public FlameSegment(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
