package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Enemy.AI.AIHigh;
import uet.oop.bomberman.entities.Enemy.AI.AILow;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img, 1);
        ai = new AILow();
        direction = ai.calculateDirection();
    }

    @Override
    public void chooseSprite() {
        switch (step % 30) {
            case 0: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.balloom_right1.getFxImage();
                } else {
                    img = Sprite.balloom_left1.getFxImage();
                }
                break;
            }
            case 10: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.balloom_right2.getFxImage();
                } else {
                    img = Sprite.balloom_left2.getFxImage();
                }
                break;
            }
            case 20: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.balloom_right3.getFxImage();
                } else {
                    img = Sprite.balloom_left3.getFxImage();
                }
                break;
            }
        }
    }

    @Override
    public void update() {
        if (isAlive) {
            randomMove();
            chooseSprite();
        } else {
            if (frameToDisappear > 0) {
                switch (frameToDisappear) {
                    case 36: {
                        img = Sprite.balloom_dead.getFxImage();
                        break;
                    }
                    case 24: {
                        img = Sprite.mob_dead1.getFxImage();
                        break;
                    }
                    case 12: {
                        img = Sprite.mob_dead2.getFxImage();
                        break;
                    }
                    case 0: {
                        img = Sprite.mob_dead3.getFxImage();
                        break;
                    }
                }
                frameToDisappear--;
            } else {
                BombermanGame.entities.remove(this);
            }
        }
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
