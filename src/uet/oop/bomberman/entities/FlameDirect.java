package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.io.IOException;

public abstract class FlameDirect extends Entity {
    public FlameDirect(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    @Override
    public void update(){}
    protected boolean leftable(double x_pos, double y_pos) {
        /*
        x1_temp = (y_pos + 1) / Sprite.SCALED_SIZE;
        y1_temp = (x_pos - 1) / Sprite.SCALED_SIZE;

        x2_temp = (y_pos + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
        y2_temp = (x_pos - 1) / Sprite.SCALED_SIZE;

        return GameMap.getMap()[x1_temp][y1_temp] != '0' &&
                GameMap.getMap()[x2_temp][y2_temp] != '0';

         */
        return true;
    }


    protected boolean rightable(int x_pos, int y_pos) {
        /*
        x1_temp = (y_pos + 1) / Sprite.SCALED_SIZE;
        y1_temp = (x_pos + Sprite.SCALED_SIZE + 1) / Sprite.SCALED_SIZE;

        x2_temp = (y_pos + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
        y2_temp = (x_pos + Sprite.SCALED_SIZE + 1) / Sprite.SCALED_SIZE;

        return GameMap.getMap()[x1_temp][y1_temp] != '0' &&
                GameMap.getMap()[x2_temp][y2_temp] != '0';
    */
        return true;
    }


    protected boolean downable(int x_pos, int y_pos) {
        /*
        x1_temp = (y_pos + Sprite.SCALED_SIZE + 1) / Sprite.SCALED_SIZE;
        y1_temp = (x_pos + 1) / Sprite.SCALED_SIZE;

        x2_temp = (y_pos + Sprite.SCALED_SIZE + 1) / Sprite.SCALED_SIZE;
        y2_temp = (x_pos + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

        return GameMap.getMap()[x1_temp][y1_temp] != '0' &&
                GameMap.getMap()[x2_temp][y2_temp] != '0';

         */
        return true;
    }

    protected boolean upable(int x_pos, int y_pos) {
        /*
        x1_temp = (y_pos - 1) / Sprite.SCALED_SIZE;
        y1_temp = (x_pos + 1) / Sprite.SCALED_SIZE;

        x2_temp = (y_pos - 1) / Sprite.SCALED_SIZE;
        y2_temp = (x_pos + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

        return GameMap.getMap()[x1_temp][y1_temp] != '0' &&
                GameMap.getMap()[x2_temp][y2_temp] != '0';

         */
        return true;
    }
    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
