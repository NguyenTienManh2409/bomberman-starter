package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    protected int animate = 0;
    protected boolean exploding = false;
    protected int timeToDisapear = 0;
    protected boolean destroyed = false;
    public static final int TIME_TO_DISAPPEAR = 100;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        handleBrick();
        chooseSprite();
        animate++;
    }

    private void handleBrick() {
        if (!destroyed) {
            timeToDisapear++;
            if (timeToDisapear < TIME_TO_DISAPPEAR) {
                exploding = true;
            } else {
                exploding = false;
                timeToDisapear = 0;
                destroyed = true;
            }
        }
    }

    private void chooseSprite() {
        int _time = TIME_TO_DISAPPEAR;
        if (exploding) {
            img = Sprite.movingSprite(
                    Sprite.brick_exploded,
                    Sprite.brick_exploded1,
                    Sprite.brick_exploded2,
                    animate, _time).getFxImage();
        } else {
            img = Sprite.brick.getFxImage();
        }
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
