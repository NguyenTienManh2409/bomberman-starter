package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Collision.Collision;
import uet.oop.bomberman.Control.Keyboard;
import uet.oop.bomberman.entities.Enemy.Balloon;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


public class Bomber extends Entity {
    private int animate = 0;
    private static int speed = 2;
    private boolean alive = true;
    private int lives = 3;
    protected boolean exploding = false;
    protected int timeToDisappear = 0;
    protected boolean destroyed = true;
    public static List<Bomb> bombList = new ArrayList<Bomb>();
    public static int NUMBER_OF_BOMBS = 1;

    public Bomber(double x, double y, Image img) {
        super(x, y, img);
    }
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
        for (Bomb bomb : bombList) { bomb.render(gc);}
    }
    @Override
    public void update() {
        afterKill();
        calculateMove();
        checkCollision();
        bombUpdate();
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
            if (Keyboard.DROP && NUMBER_OF_BOMBS != 0) {
                createBomb();
            }
        } else {
            animate++;
            img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 50).getFxImage();
        }
    }
    public void kill() {
        alive = false;
    }
    public void resetBomber() {
        img = Sprite.player_right.getFxImage();
        this.setX(Sprite.SCALED_SIZE);
        this.setY(Sprite.SCALED_SIZE);
        setAlive(true);
        lives--;
    }
    protected void afterKill() {
        if (!isAlive()) {
            timeToDisappear++;
            if (timeToDisappear < Bomb.TIME_TO_DISAPPEAR) {
            } else {
                timeToDisappear = 0;
                resetBomber();
            }
        }
    }

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

    private void createBomb() {
        int tmpX = (int) ((BombermanGame.bomberman.getX() + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
        int tmpY = (int) ((BombermanGame.bomberman.getY() + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
        Bomb bomb = new Bomb(tmpX, tmpY, Sprite.bomb.getFxImage());
        bombList.add(bomb);
        NUMBER_OF_BOMBS--;
    }

    private void bombUpdate() {
//		System.out.println(NUMBER_OF_BOMBS);
        Iterator<Bomb> bombIterator = bombList.iterator();
        while (bombIterator.hasNext()) {
            Bomb bomb = bombIterator.next();
            if (bomb != null) {
                bomb.update();
                if (!bomb.isDestroyed() && bomb.isExploding()) {
                    for (int i = 0; i < bomb.getFlameList().size(); i++) {
                        Flame flame = bomb.getFlameList().get(i);
                        flame.update();
                        //Kiểm tra và xử lí nếu flame chạm vào người chơi hoặc quái.
                        Iterator<Entity> itr = BombermanGame.entities.iterator();
                        Entity cur;
                        while (itr.hasNext()) {
                            cur = itr.next();
                            if (cur instanceof Enemy) {
                                if (Collision.checkVaCham(cur, flame)) {
                                    ((Enemy) cur).setAlive(false);
                                }
                            }
                            if (cur instanceof Bomber) {
                                if (Collision.checkVaCham(cur, flame)) {
                                    ((Bomber) cur).setAlive(false);
                                }
                            }
                        }
                        //Kiểm tra và xử lí nếu flame chạm vào brick không.
                        int xFlame = (int) (flame.getX() / Sprite.SCALED_SIZE);
                        int yFlame = (int) (flame.getY() / Sprite.SCALED_SIZE);
                        if (BombermanGame.LayeredEntity.containsKey(BombermanGame.generateKey(xFlame, yFlame))) {
                            Stack<Entity> tiles = BombermanGame.LayeredEntity.get(BombermanGame.generateKey(xFlame, yFlame));
                            if (tiles.peek() instanceof Brick) {
                                Brick brick = (Brick) tiles.peek();
                                brick.setExploding(true);
                                brick.update();
                                if (brick.isDestroyed()) {
                                    tiles.pop();
                                }
                            }
                        }
                    }
                }
                if (bomb.isDestroyed()) {// && NUMBER_OF_BOMBS < 1) {
                    NUMBER_OF_BOMBS++;
                    bombIterator.remove();
                }
            }
        }
    }

    public boolean collide(Entity e) {
        if( e instanceof Brick || e instanceof  Wall || e instanceof  Bomb ) return false;
        return true;
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

    public static List<Bomb> getBombList() {
        return bombList;
    }
}
