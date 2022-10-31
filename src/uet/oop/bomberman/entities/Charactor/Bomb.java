package uet.oop.bomberman.entities.Charactor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.MapCreate;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

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
    protected int x1_temp, x2_temp, y1_temp, y2_temp;
    protected final int pixel = 1;
    private Sound sound = new Sound();
    private final List<Flame> flameList = new ArrayList<>();

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void render(GraphicsContext gc) {
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 36).getFxImage();
        super.render(gc);
        for(Flame flame : flameList) {flame.render(gc);}
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

    // ToDo: xu li bom khi nao no
    private void handleBomb() {
        if (!_destroyed) {
            _timeToExplode++;

            if (_timeToExplode < TIME_TO_EXPLOSION_BOMB) {
                _exploding = false;
            } else {
                if (_timeToExplode == TIME_TO_EXPLOSION_BOMB) {
                    _exploding = true;
                    initAndHandleFlame();
                    sound.getExplosionSound();
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

    // ToDo: tao flame
    private void initAndHandleFlame() {
        flameList.clear();
        Flame newFlame = null;
        for (int i = 0; i < Bomb.LENGTH_OF_FLAME; i++) {
            if (!leftable(x, y)) {
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
            if (!rightable(x, y)) {
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
            if (!upable(x, y)) {
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
            if (!downable(x, y)) {
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

    // Todo: check va cham cua flame va tuong
    protected boolean rightable(double x_pos, double y_pos) {
        x1_temp = (int) ((y_pos + pixel) / Sprite.SCALED_SIZE);
        y1_temp = (int) ((x_pos + Sprite.SCALED_SIZE + pixel) / Sprite.SCALED_SIZE);

        x2_temp = (int) ((y_pos + Sprite.SCALED_SIZE - pixel) / Sprite.SCALED_SIZE);
        y2_temp = (int) ((x_pos + Sprite.SCALED_SIZE + pixel) / Sprite.SCALED_SIZE);

        return MapCreate.getMap()[x1_temp][y1_temp] != '#' &&
                MapCreate.getMap()[x2_temp][y2_temp] != '#';
    }

    // Todo: check va cham cua flame va tuong
    protected boolean downable(double x_pos, double y_pos) {
        x1_temp = (int) ((y_pos + Sprite.SCALED_SIZE + pixel) / Sprite.SCALED_SIZE);
        y1_temp = (int) ((x_pos + pixel) / Sprite.SCALED_SIZE);

        x2_temp = (int) ((y_pos + Sprite.SCALED_SIZE + pixel) / Sprite.SCALED_SIZE);
        y2_temp = (int) ((x_pos + Sprite.SCALED_SIZE - pixel) / Sprite.SCALED_SIZE);

        return MapCreate.getMap()[x1_temp][y1_temp] != '#' &&
                MapCreate.getMap()[x2_temp][y2_temp] != '#';
    }

    // Todo: check va cham cua flame va tuong
    protected boolean upable(double x_pos, double y_pos) {
        x1_temp = (int) ((y_pos - pixel) / Sprite.SCALED_SIZE);
        y1_temp = (int) ((x_pos + pixel) / Sprite.SCALED_SIZE);

        x2_temp = (int) ((y_pos - pixel) / Sprite.SCALED_SIZE);
        y2_temp = (int) ((x_pos + Sprite.SCALED_SIZE - pixel) / Sprite.SCALED_SIZE);

        return MapCreate.getMap()[x1_temp][y1_temp] != '#' &&
                MapCreate.getMap()[x2_temp][y2_temp] != '#';
    }

    // Todo: check va cham cua flame va tuong
    protected boolean leftable(double x_pos, double y_pos) {
        x1_temp = (int) ((y_pos + pixel) / Sprite.SCALED_SIZE);
        y1_temp = (int) ((x_pos - pixel) / Sprite.SCALED_SIZE);

        x2_temp = (int) ((y_pos + Sprite.SCALED_SIZE - pixel) / Sprite.SCALED_SIZE);
        y2_temp = (int) ((x_pos - pixel) / Sprite.SCALED_SIZE);

        return MapCreate.getMap()[x1_temp][y1_temp] != '#' &&
                MapCreate.getMap()[x2_temp][y2_temp] != '#';
    }

    public List<Flame> getFlameList() {
        return flameList;
    }

    public boolean isDestroyed() {
        return _destroyed;
    }

    public boolean isExploding() {
        return _exploding;
    }

    public void set_exploding(boolean _exploding) {
        this._exploding = _exploding;
    }

    public void set_timeToExplode(long _timeToExplode) {
        this._timeToExplode = _timeToExplode;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
