package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Collision.Collision;
import uet.oop.bomberman.entities.Charactor.Bomb;
import uet.oop.bomberman.entities.Charactor.Bomber;
import uet.oop.bomberman.entities.Entity;

import java.util.Objects;

import static uet.oop.bomberman.BombermanGame.*;

public abstract class Item extends Entity {
	public Item(int xUnit, int yUnit, Image img) {
		super(xUnit, yUnit, img);
	}

}
