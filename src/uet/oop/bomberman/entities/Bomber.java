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
    private static int VELOCITY = 1;
    private boolean alive = true;
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    @Override
    public void update() {
        calculateMove();
    }
    private void calculateMove() {
        if (isAlive()) {
            animate++;
            if (Keyboard.UP) {
                y -= VELOCITY;
                img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, animate, 25).getFxImage();
            }
            if (Keyboard.DOWN) {
                y += VELOCITY;
                img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, animate, 25).getFxImage();
            }
            if (Keyboard.LEFT) {
                x -= VELOCITY;
                img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, animate, 25).getFxImage();
            }
            if (Keyboard.RIGHT) {
                x += VELOCITY;
                img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, animate, 25).getFxImage();
            }
        }
    }
    public static void setVELOCITY(int v) {
        VELOCITY = v;
    }
    public static int getVELOCITY() {
        return VELOCITY;
    }
    public boolean isAlive() {
        return alive;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
