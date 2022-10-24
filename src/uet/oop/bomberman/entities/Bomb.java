package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.FlameDirect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomb extends Entity {
    int animate = 0;
    private long _timeToExplode;
    public static int LENGTH_OF_FLAME = 1;
    public static final int TIME_TO_EXPLOSION_BOMB = 300;
    public static final int TIME_TO_DISAPPEAR = 100;
    private boolean _exploding = false;
    private boolean _destroyed = false;

    private final List<Flame> flameList = new ArrayList<>();

    public List<Flame> getFlameList() {
        return flameList;
    }
    public Bomb(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    public void render(GraphicsContext gc) {
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 18).getFxImage();
        super.render(gc);
        for(Flame flame : flameList) { flame.render(gc);}
    }

    @Override
    public void update() {
        animate++;
        handleBomb();
        flameUpdate();
    }

    private void flameUpdate() {
        Iterator<Flame> flameIterator = flameList.iterator();
        while (flameIterator.hasNext()) {
            Flame flame = flameIterator.next();
            if (flame != null) {
                flame.update();
            }
        }
    }
    private void handleBomb() {
        if (!_destroyed) {
            _timeToExplode++;

            if (_timeToExplode < TIME_TO_EXPLOSION_BOMB) {
                _exploding = false;
            } else {
                if (_timeToExplode == TIME_TO_EXPLOSION_BOMB) {
                    _exploding = true;
                    initAndHandleFlame();
                }
                if (_timeToExplode < TIME_TO_EXPLOSION_BOMB + TIME_TO_DISAPPEAR) {
                    _exploding = true;
                } else if (_timeToExplode > TIME_TO_EXPLOSION_BOMB + TIME_TO_DISAPPEAR) {
                    _timeToExplode = 0;
                    _destroyed = true;
                }
            }
        }
    }

    private void initAndHandleFlame() {
        flameList.clear();
        Flame newFlame = null;
        for (int i = 0; i < Bomb.LENGTH_OF_FLAME; i++) {
            if (!leftable(x - i * Sprite.SCALED_SIZE, y)) {
                break;
            }
            newFlame =
                    new Flame(
                            (x - (1 + i) * Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE,
                            y / Sprite.SCALED_SIZE,
                            Sprite.explosion_horizontal.getFxImage());

            if (i != Bomb.LENGTH_OF_FLAME - 1) {
                newFlame.setStatus("HORIZONTAL");
            } else {
                newFlame.setStatus("LEFT_LAST");
            }
            flameList.add(newFlame);
        }

        for (int i = 0; i < Bomb.LENGTH_OF_FLAME; i++) {
            if (!rightable(x + i * Sprite.SCALED_SIZE, y)) {
                break;
            }
            newFlame =
                    new Flame(
                            (x + (1 + i) * Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE,
                            y / Sprite.SCALED_SIZE,
                            Sprite.explosion_horizontal.getFxImage());
            if (i != Bomb.LENGTH_OF_FLAME - 1) {
                newFlame.setStatus("HORIZONTAL");
            } else {
                newFlame.setStatus("RIGHT_LAST");
            }
            flameList.add(newFlame);
        }

        for (int i = 0; i < Bomb.LENGTH_OF_FLAME; i++) {
            if (!upable(x, y - i * Sprite.SCALED_SIZE)) {
                break;
            }
            newFlame =
                    new Flame(
                            x / Sprite.SCALED_SIZE,
                            (y - (1 + i) * Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE,
                            Sprite.explosion_vertical.getFxImage());
            if (i != Bomb.LENGTH_OF_FLAME - 1) {
                newFlame.setStatus("VERTICAL");
            } else {
                newFlame.setStatus("TOP_LAST");
            }
            flameList.add(newFlame);
        }

        for (int i = 0; i < Bomb.LENGTH_OF_FLAME; i++) {
            if (!downable(x, y + i * Sprite.SCALED_SIZE)) {
                break;
            }
            newFlame =
                    new Flame(
                            x / Sprite.SCALED_SIZE,
                            (y + (1 + i) * Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE,
                            Sprite.explosion_vertical.getFxImage());
            if (i != Bomb.LENGTH_OF_FLAME - 1) {
                newFlame.setStatus("VERTICAL");
            } else {
                newFlame.setStatus("DOWN_LAST");
            }
            flameList.add(newFlame);
        }
        newFlame =
                new Flame(
                        x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.explosion_vertical.getFxImage());
        newFlame.setStatus("CENTER");
        flameList.add(newFlame);
    }

    private boolean downable(double x, double v) {
        return true;
    }

    private boolean upable(double x, double v) {
        return true;
    }

    private boolean rightable(double v, double y) {
        return true;
    }

    private boolean leftable(double v, double y) {
        return true;
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
