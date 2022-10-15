package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Control.Keyboard;
import uet.oop.bomberman.graphics.Sprite;


public class Bomber extends Entity {
    private int animate = 0;
    private static final int speed = 1;
    private boolean alive = true;
    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x , y);
    }

    @Override
    public void update() {
        calculateMove();
        checkCollision();
    }

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
        }
    }

    public void checkCollision() {
        for (Entity entity : BombermanGame.stillObjects) {
            boolean check = this.collide(entity);
            caculateCollision(entity,check);
        }
    }

    public void caculateCollision(Entity other, boolean check) {
        double _x = (x + width / 2) - (other.x + other.width / 2) - 5;
        double _y = (y + height / 2) - (other.y + other.height / 2) + 4;
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
        if( e instanceof Brick || e instanceof  Wall) return false;
        return true;
    }

    public boolean isAlive() {
        return alive;
    }

    public static int getSpeed() {
        return speed;
    }
}
