package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemy.AI.AI;
import uet.oop.bomberman.entities.Enemy.AI.AIHigh;

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

    public Enemy(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Enemy(int x, int y, Image dead, int speed) {
        super(x, y, dead);
        this.speed = speed;
    }

    public boolean isFlamePass() {
        return flamePass;
    }

    public void kill() {
        isAlive = false;
    }

    public void afterKill() {

    }

    protected void randomMove() {
        if (!isAlive) {
            System.out.println("Enemy Die");
            return;
        } else {
            step++;
            switch (direction) {
                case 0: {
                    for (Entity entity : BombermanGame.entities) {
                        if (!(entity instanceof Bomber || entity instanceof Enemy)) {
                            if (EnemyUp(entity)) return;
                        } else if (entity instanceof Bomber) {
                            if (entity.intersectDown(this)) {
                                ((Bomber) entity).setAlive(false);
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
                            if (entity.intersectLeft(this)) {
                                ((Bomber) entity).setAlive(false);
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
                            if (entity.intersectUp(this)) {
                                ((Bomber) entity).setAlive(false);
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
                            if (entity.intersectRight(this)) {
                                ((Bomber) entity).setAlive(false);
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

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
