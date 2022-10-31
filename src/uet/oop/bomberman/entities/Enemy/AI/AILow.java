package uet.oop.bomberman.entities.Enemy.AI;

/**
 * Enemy se di chuyen ngau nhien
 */
public class AILow extends AI{
    @Override
    public int calculateDirection() {
        return random.nextInt(4);
    }
}
