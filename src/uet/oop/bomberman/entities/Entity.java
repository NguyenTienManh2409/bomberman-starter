package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected double x;
    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected double y;
    protected double width;
    protected double height;
    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(double xUnit, double yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        width = Sprite.SCALED_SIZE;
        height = Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();
    public boolean intersectLeft(Entity other) {
        // TODO: Kiểm tra xem có vật giao nhau bên trái
        boolean impactX = (this.getX() == other.getMaxX());
        boolean intersectY = (other.getY() >= this.getY() && other.getY() < this.getMaxY())
                || (other.getMaxY() > this.getY() && other.getMaxY() <= this.getMaxY());

        return impactX && intersectY;
    }

    public boolean intersectRight(Entity other) {
        // TODO: Kiểm tra xem có vật giao nhau bên phải
        boolean impactX = (this.getMaxX() == other.getX());
        boolean intersectY = (other.getY() >= this.getY() && other.getY() < this.getMaxY())
                || (other.getMaxY() > this.getY() && other.getMaxY() <= this.getMaxY());

        return impactX && intersectY;
    }

    public boolean intersectDown(Entity other) {
        // TODO: Kiểm tra xem có vật giao nhau bên trên
        boolean impactY = (this.getMaxY() == other.getY());
        boolean intersectX = (other.getX() <= this.getX() && other.getMaxX() > this.getX()
                || other.getMaxX() >= this.getMaxX() && other.getX() < this.getMaxX());

        return impactY && intersectX;
    }

    public boolean intersectUp(Entity other) {
        // TODO: Kiểm tra xem có vật giao nhau bên trên
        boolean impactY = (this.getY() == other.getMaxY());
        boolean intersectX = (other.getX() <= this.getX() && other.getMaxX() > this.getX()
                || other.getMaxX() >= this.getMaxX() && other.getX() < this.getMaxX());

        return impactY && intersectX;
    }

    public abstract boolean collide(Entity e);
    public int getTileX() {
        return (int) ((x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
    }
    public int getTileY() {
        return (int) ((y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getMaxX() {
        return x + Sprite.SCALED_SIZE;
    }
    public double getMaxY() {
        return y + Sprite.SCALED_SIZE;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
}