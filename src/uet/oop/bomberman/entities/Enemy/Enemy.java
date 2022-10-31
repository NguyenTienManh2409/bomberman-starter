package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Collision.Collision;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Charactor.Bomb;
import uet.oop.bomberman.entities.Charactor.Bomber;
import uet.oop.bomberman.entities.Enemy.AI.AI;
import uet.oop.bomberman.entities.Enemy.AI.AIHigh;
import uet.oop.bomberman.sound.Sound;

import java.util.Stack;

public abstract class Enemy extends Entity {
    protected int step = 0;
    protected int direction = -1;  //0 : up, 1 : right, 2 : down, 3 : left
    protected int speed;
    protected AI ai;
    protected boolean wallPass = false;
    protected boolean bombPass = false;
    protected boolean flamePass = false;
    protected boolean isAlive = true;
    protected int frameToDisappear = 48;
    protected int maxStep = 90;
    private final int allowDistance = 18;
    private Sound sound = new Sound();

    public Enemy(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Enemy(int x, int y, Image dead, int speed) {
        super(x, y, dead);
        this.speed = speed;
    }

    public void kill() {
        isAlive = false;
        sound.getEnemyDeadSound();
    }

    // Todo: xu li enemy khi va cham voi cac entity khac
    protected void randomMove() {
        if (!isAlive) {
            return;
        } else {
            step++;
            switch (direction) {
                case 0: {
                    for (Entity entity : BombermanGame.entities) {
                        if (!(entity instanceof Bomber || entity instanceof Enemy)) {
                            if (EnemyUp(entity)) return;
                        } else if (entity instanceof Bomber) {
                            if (Collision.checkVaCham(this,entity)) {
                                ((Bomber) entity).kill();
                            }
                        }
                    }

                    for (Bomb bomb : Bomber.bombList) {
                        if (bomb.intersectDown(this) && !bombPass) {
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    for (Stack<Entity> entity : BombermanGame.LayeredEntity.values()) {
                        for (Entity entity1 : entity) {
                            if (entity1 instanceof Brick) {
                                if (!wallPass) {
                                    if (EnemyUp(entity1)) return;
                                }
                            }
                        }
                    }

                    for (Entity entity : BombermanGame.stillObjects) {
                        if (!(entity instanceof Grass)) {
                            if (EnemyUp(entity)) return;
                        }
                    }

                    if (ai instanceof AIHigh) {
                        if (step > maxStep) {
                            step = 0;
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    y -= speed;
                    chooseSprite();
                    break;
                }
                case 1: {
                    for (Entity entity : BombermanGame.entities) {
                        if (!(entity instanceof Bomber || entity instanceof Enemy)) {
                            if (EnemyRight(entity)) return;
                        } else if (entity instanceof Bomber) {
                            if (Collision.checkVaCham(this,entity)) {
                                ((Bomber) entity).kill();
                            }
                        }
                    }

                    for (Bomb bomb : Bomber.bombList) {
                        if (bomb.intersectLeft(this) && !bombPass) {
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    for (Stack<Entity> entity : BombermanGame.LayeredEntity.values()) {
                        for (Entity entity1 : entity) {
                            if (entity1 instanceof Brick) {
                                if (!wallPass) {
                                    if (EnemyRight(entity1)) return;
                                }
                            }
                        }
                    }

                    for (Entity entity : BombermanGame.stillObjects) {
                        if (!(entity instanceof Grass)) {
                            if (EnemyRight(entity)) return;
                        }
                    }

                    if (ai instanceof AIHigh) {
                        if (step > maxStep) {
                            step = 0;
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    x += speed;
                    chooseSprite();
                    break;
                }
                case 2: {
                    for (Entity entity : BombermanGame.entities) {
                        if (!(entity instanceof Bomber || entity instanceof Enemy)) {
                            if (EnemyDown(entity)) return;
                        } else if (entity instanceof Bomber) {
                            if (Collision.checkVaCham(this,entity)) {
                                ((Bomber) entity).kill();
                            }
                        }
                    }

                    for (Bomb bomb : Bomber.bombList) {
                        if (bomb.intersectUp(this) && !bombPass) {
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    for (Stack<Entity> entity : BombermanGame.LayeredEntity.values()) {
                        for (Entity entity1 : entity) {
                            if (entity1 instanceof Brick) {
                                if (!wallPass) {
                                    if (EnemyDown(entity1)) return;
                                }
                            }
                        }
                    }

                    for (Entity entity : BombermanGame.stillObjects) {
                        if (!(entity instanceof Grass)) {
                            if (EnemyDown(entity)) return;
                        }
                    }

                    if (ai instanceof AIHigh) {
                        if (step > maxStep) {
                            step = 0;
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    y += speed;
                    chooseSprite();
                    break;
                }
                case 3: {
                    for (Entity entity : BombermanGame.entities) {
                        if (!(entity instanceof Bomber || entity instanceof Enemy)) {
                            if (EnemyLeft(entity)) return;
                        } else if (entity instanceof Bomber) {
                            if (Collision.checkVaCham(this,entity)) {
                                ((Bomber) entity).kill();
                            }
                        }
                    }

                    for (Bomb bomb : Bomber.bombList) {
                        if (bomb.intersectRight(this) && !bombPass) {
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    for (Stack<Entity> entity : BombermanGame.LayeredEntity.values()) {
                        for (Entity entity1 : entity) {
                            if (entity1 instanceof Brick) {
                                if (!wallPass) {
                                    if (EnemyLeft(entity1)) return;
                                }
                            }
                        }
                    }

                    for (Entity entity : BombermanGame.stillObjects) {
                        if (!(entity instanceof Grass)) {
                            if (EnemyLeft(entity)) return;
                        }
                    }

                    if (ai instanceof AIHigh) {
                        if (step > maxStep) {
                            step = 0;
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    x -= speed;
                    chooseSprite();
                    break;
                }
            }
        }
    }

    // Todo: di chuyen sang trai neu khong co vat can
    private boolean EnemyLeft(Entity entity) {
        if (entity.intersectRight(this)) {
            if (entity.getMaxY() - getY() <= allowDistance) {
                y += entity.getMaxY() - getY();
            }
            if (getMaxY() - entity.getY() <= allowDistance) {
                y -= getMaxY() - entity.getY();
            }
            direction = ai.calculateDirection();
            return true;
        }
        return false;
    }

    // Todo: di chuyen len tren neu khong co vat can
    private boolean EnemyUp(Entity entity) {
        if (entity.intersectDown(this)) {
            if (entity.getMaxX() - getX() <= allowDistance) {
                x += entity.getMaxX() - getX();
            }
            if (getMaxX() - entity.getX() <= allowDistance + 10) {
                x -= getMaxX() - entity.getX();
            }
            direction = ai.calculateDirection();
            return true;
        }
        return false;
    }

    // Todo: di chuyen xuong duoi neu khong co vat can
    private boolean EnemyDown(Entity entity) {
        if (entity.intersectUp(this)) {
            if (getMaxX() - entity.getX() <= allowDistance + 10) {
                x -= getMaxX() - entity.getX();
            }
            if (entity.getMaxX() - getX() <= allowDistance) {
                x += entity.getMaxX() - getX();
            }
            direction = ai.calculateDirection();
            return true;
        }
        return false;
    }

    // Todo: di chuyen sang phai neu khong co vat can
    private boolean EnemyRight(Entity entity) {
        if (entity.intersectLeft(this)) {
            if (getMaxY() - entity.getY() <= allowDistance) {
                y -= getMaxY() - entity.getY();
            }
            if (entity.getMaxY() - getY() <= allowDistance) {
                y += entity.getMaxY() - getY();
            }
            direction = ai.calculateDirection();
            return true;
        }
        return false;
    }

    public abstract void chooseSprite();

    @Override
    public void update() {
    }
}
