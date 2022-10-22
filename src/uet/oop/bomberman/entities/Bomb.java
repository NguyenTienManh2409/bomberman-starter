package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    int animate = 0;
    private long _timeToExplode;
    public static final int TIME_TO_EXPLOSION_BOMB = 300;
    public static final int TIME_TO_DISAPPEAR = 100;
    private boolean _exploding = false;
    private boolean _destroyed = false;
    public Bomb(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    public void render(GraphicsContext gc) {
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 18).getFxImage();
        super.render(gc);
    }

    @Override
    public void update() {
        animate++;
        handleBomb();
    }
    private void handleBomb() {
        if (!_destroyed) {
            _timeToExplode++;

            if (_timeToExplode < TIME_TO_EXPLOSION_BOMB) {
                _exploding = false;
            } else {
                if (_timeToExplode < TIME_TO_EXPLOSION_BOMB + TIME_TO_DISAPPEAR) {
                    _exploding = true;
                } else if (_timeToExplode > TIME_TO_EXPLOSION_BOMB + TIME_TO_DISAPPEAR) {
                    _timeToExplode = 0;
                    _destroyed = true;
                }
            }
        }
    }

    public boolean isDestroyed() {
        return _destroyed;
    }

    public boolean isExploding() {
        return _exploding;
    }
    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
