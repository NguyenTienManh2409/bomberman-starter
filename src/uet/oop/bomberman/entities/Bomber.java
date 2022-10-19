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
import uet.oop.bomberman.entities.*;

import java.util.ArrayList;
import java.util.List;


public class Bomber extends Entity {
    private int animate = 0;
    private List<Bomb> bombList = new ArrayList<>();
    private static int maxBomb = 1;
    private static final int VELOCITY = 1;
    private boolean alive = true;
    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    private GraphicsContext gc;
    @Override
    public void update() {
        calculateMove();
        checkCollision();
        detectPlaceBomb();
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

    private void detectPlaceBomb() {
        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
        if(Keyboard.DROP) {

            int xt = 3;
            int yt = 2;
            bombList.get(0).render(gc);
            placeBomb(xt,yt);

        }
    }
    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb b = new Bomb(x, y, img);
        bombList.add(b);
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
}
