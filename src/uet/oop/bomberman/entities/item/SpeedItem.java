package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class SpeedItem extends Item {
	public static int timeItem = 0;
	public static boolean isPickUp = false;

	public SpeedItem(int xUnit, int yUnit, Image img) {
		super(xUnit, yUnit, img);
	}

	@Override
	public void update() {

	}
	@Override
	public boolean collide(Entity e) {
		return false;
	}
}
