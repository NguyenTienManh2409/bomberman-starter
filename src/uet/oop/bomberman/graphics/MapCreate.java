package uet.oop.bomberman.graphics;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer; // The java.util.StringTokenizer class allows you to split a string into its token elements.

public class MapCreate extends BombermanGame {
    private static char[][] map;
    public static void createMap(String path) throws FileNotFoundException {
        FileInputStream reader = new FileInputStream(path);
        Scanner scanner = new Scanner(reader);
        int lv = scanner.nextInt();
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        map = new char[row][col];
        scanner.nextLine();

        for (int i = 0; i < row; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < col; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                char c = map[i][j];
                Entity obj;
                Stack<Entity> layer = new Stack<>();
                switch (c) {
                    case '0':
                        obj = new Wall(j, i, Sprite.wall.getFxImage());
                        BombermanGame.stillObjects.add(obj);
                        break;
                    case '*':
                        layer.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        layer.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    case 'p':
                        Bomber bomber = new Bomber(j, i, Sprite.player_right.getFxImage());
                        obj = new Grass(j, i, Sprite.grass.getFxImage());
                        BombermanGame.entities.add(bomber);
                        BombermanGame.stillObjects.add(obj);
                        break;
                    default:
                        obj = new Grass(j, i, Sprite.grass.getFxImage());
                        BombermanGame.stillObjects.add(obj);
                        break;
                }
            }
        }
        scanner.close();
    }

    public static char[][] getMap() {
        return map;
    }
}
