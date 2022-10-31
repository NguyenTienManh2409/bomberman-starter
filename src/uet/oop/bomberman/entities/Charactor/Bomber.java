package uet.oop.bomberman.entities.Charactor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Collision.Collision;
import uet.oop.bomberman.Control.Keyboard;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.graphics.MapCreate;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.sound.Sound;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


public class Bomber extends Entity {
    private int animate = 0;
    private static int speed = 2;
    protected static boolean alive = true;
    protected int timeToDisapear = 0;
    protected boolean destroyed = false;
    protected int lives = 3;
    public static boolean losegame = false;
    public static List<Bomb> bombList = new ArrayList<Bomb>();
    public static int NUMBER_OF_BOMBS = 2;
    private Sound sound = new Sound();

    public Bomber(double x, double y, Image img) {
        super(x, y, img);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
        for (Bomb bomb : bombList) { bomb.render(gc);}
    }
    @Override
    public void update() {
        if (!isAlive()) {
            sound.getEnemyDeadSound();
            afterKill();
            if (this.isDestroyed()) {
                if (lives > 0) {
                    resetBomber();
                } else
                {

                }
            }
        }
        calculateMove();
        checkCollision();
    }

    public void kill() {
        alive = false;
    }

    public void afterKill() {
        if (!destroyed) {
            timeToDisapear++;
            if (timeToDisapear == Bomb.TIME_TO_DISAPPEAR) {
                timeToDisapear = 0;
                destroyed = true;
                lives--;
            }
        }
    }

    public void resetBomber() {
        MapCreate.clear();
        MapCreate.initMap();
        img = Sprite.player_right.getFxImage();
        setAlive(true);
    }

    // ToDo: xu li di chuyen cua bomber va choose sprite
    private void calculateMove() {
        if (isAlive()) {
            animate++;
            if (Keyboard.UP) {
                y -= speed;
                img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, animate, 25).getFxImage();
            }
            if (Keyboard.DOWN) {
                y += speed;
                img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, animate, 25).getFxImage();
            }
            if (Keyboard.LEFT) {
                x -= speed;
                img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, animate, 25).getFxImage();
            }
            if (Keyboard.RIGHT) {
                x += speed;
                img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, animate, 25).getFxImage();
            }
        } else {
            animate++;
            img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 50).getFxImage();
        }
    }

    // Todo: kiem tra va cham cua bomber voi cac entity khac
    public void checkCollision() {
        for (Entity entity : BombermanGame.stillObjects) {
            boolean check = this.collide(entity);
            caculateCollision(entity,check);
        }

        for (Stack<Entity> value : BombermanGame.LayeredEntity.values()) {
            value.forEach(temp -> {
                boolean check = this.collide(temp);
                caculateCollision(temp,check);
            });
        }
    }

    // Todo: khong cho bomber di chuyen khi gap vat can
    public void caculateCollision(Entity other, boolean check) {
        double _x = (x + width / 2) - (other.getX() + other.width / 2) - 5;
        double _y = (y + height / 2) - (other.getY() + other.height / 2) + 4;
        double intersectX = Math.abs(_x) - (width + other.width) / 2 + 4;
        double intersectY = Math.abs(_y) - (height + other.height) / 2 + 6;

        if (intersectX < 0 && intersectY < 0) {
            if (!check) {
                if (intersectX > intersectY && _x > 0) x -= intersectX;
                else if (intersectX > intersectY && _x < 0) x += intersectX;
                else if (intersectX < intersectY && _y > 0) y -= intersectY;
                else y += intersectY;
            }
        }
    }

    public boolean collide(Entity e) {
        if( e instanceof Brick || e instanceof  Wall || e instanceof  Bomb ) return false;
        return true;
    }

    public static void setSpeed(int speed) {
        Bomber.speed = speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

}
