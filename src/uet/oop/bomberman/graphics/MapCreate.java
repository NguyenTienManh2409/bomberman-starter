package uet.oop.bomberman.graphics;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer; // The java.util.StringTokenizer class allows you to split a string into its token elements.

public class MapCreate extends BombermanGame {
    public static void createMap(String path) throws FileNotFoundException {
        FileInputStream reader = new FileInputStream(path);
        Scanner scanner = new Scanner(reader);
        int lv = scanner.nextInt();
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        String line = scanner.nextLine();

        for (int i = 0; i < row; i++) {
            line = scanner.nextLine();
            for (int j = 0; j < col; j++) {
                Entity object;
                if (line.charAt(j) == '0') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                } else if (line.charAt(j) == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                } else {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
        scanner.close();
    }
}
