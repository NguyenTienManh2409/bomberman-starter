package uet.oop.bomberman.entities.Charactor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Entity;

public class Flame extends Entity {
    int animate = 0;
    int time = 36;
    private String status;

    public Flame(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void render(GraphicsContext gc) {
        chooseSprite();
        super.render(gc);
    }

    @Override
    public void update() {
        chooseSprite();
        animate++;
    }

    private void chooseSprite() {
        switch (status) {
            case "LEFT_LAST":
                img =
                        Sprite.movingSprite(
                                Sprite.explosion_horizontal_left_last,
                                Sprite.explosion_horizontal_left_last1,
                                Sprite.explosion_horizontal_left_last2,
                                animate,
                                time).getFxImage();
                break;

            case "RIGHT_LAST":
                img =
                        Sprite.movingSprite(
                                Sprite.explosion_horizontal_right_last,
                                Sprite.explosion_horizontal_right_last1,
                                Sprite.explosion_horizontal_right_last2,
                                animate,
                                time).getFxImage();
                break;

            case "TOP_LAST":
                img =
                        Sprite.movingSprite(
                                Sprite.explosion_vertical_top_last,
                                Sprite.explosion_vertical_top_last1,
                                Sprite.explosion_vertical_top_last2,
                                animate,
                                time).getFxImage();

                break;

            case "DOWN_LAST":
                img =
                        Sprite.movingSprite(
                                Sprite.explosion_vertical_down_last,
                                Sprite.explosion_vertical_down_last1,
                                Sprite.explosion_vertical_down_last2,
                                animate,
                                time).getFxImage();

                break;

            case "HORIZONTAL":
                img =
                        Sprite.movingSprite(
                                Sprite.explosion_horizontal,
                                Sprite.explosion_horizontal1,
                                Sprite.explosion_horizontal2,
                                animate,
                                time).getFxImage();
                break;

            case "VERTICAL":
                img =
                        Sprite.movingSprite(
                                Sprite.explosion_vertical,
                                Sprite.explosion_vertical1,
                                Sprite.explosion_vertical2,
                                animate,
                                time).getFxImage();
                break;
            default:
                img =
                        Sprite.movingSprite(
                                Sprite.bomb_exploded,
                                Sprite.bomb_exploded1,
                                Sprite.bomb_exploded2,
                                animate,
                                time).getFxImage();
                break;
        }
    }


    @Override
    public boolean collide(Entity e) {
        return false;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
