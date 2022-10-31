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
        rightB = leftB + Sprite.SCALED_SIZE/2;
        topB = B.getY();
        bottomB = topB + Sprite.SCALED_SIZE/2;

        leftA = A.getX();
        rightA = A.getX() + Sprite.SCALED_SIZE/2;
        topA = A.getY() +1;
        bottomA = A.getY() + Sprite.SCALED_SIZE/2;

        return !((bottomA <= topB) || (topA >= bottomB) || (rightA <= leftB) || (leftA >= rightB));
    }
}
