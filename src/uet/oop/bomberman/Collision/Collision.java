package uet.oop.bomberman.Collision;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Collision {

    public static boolean checkVaCham(Entity A, Entity B) {
        double leftA, leftB;
        double rightA, rightB;
        double topA, topB;
        double bottomA, bottomB;

        leftB = B.getX();
        rightB = leftB + Sprite.SCALED_SIZE;
        topB = B.getY();
        bottomB = topB + Sprite.SCALED_SIZE;

        leftA = A.getX() + 1;
        rightA = A.getX() + Sprite.SCALED_SIZE -1 ;
        topA = A.getY() +1;
        bottomA = A.getY() + Sprite.SCALED_SIZE - 1 ;

        return !((bottomA <= topB) || (topA >= bottomB) || (rightA <= leftB) || (leftA >= rightB));
    }

    public static boolean checkCollisionWithBuffer(Entity A, Entity B) {
        double leftA, leftB;
        double rightA, rightB;
        double topA, topB;
        double bottomA, bottomB;

        double buf = 6;

        leftB = B.getX();
        rightB = leftB + Sprite.SCALED_SIZE;
        topB = B.getY();
        bottomB = topB + Sprite.SCALED_SIZE;

        leftA = A.getX() - buf;
        rightA = A.getX() + Sprite.SCALED_SIZE + buf;
        topA = A.getY() - buf;
        bottomA = A.getY() + Sprite.SCALED_SIZE + buf;

        return !((bottomA <= topB) || (topA >= bottomB) || (rightA <= leftB) || (leftA >= rightB));
    }
}
