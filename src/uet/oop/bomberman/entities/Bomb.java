package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private int animate;
    //thoi gian phat no
    protected double _timeToExplode = 120;
    // thoi gian hieu ung no
    protected int _timeAfter = 15;
    protected boolean _exploded = false;
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        animate++;
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60).getFxImage();
        int xt = (int)_x << 4;
        int yt = (int)_y << 4;

        screen.renderEntity(xt, yt , this);
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
