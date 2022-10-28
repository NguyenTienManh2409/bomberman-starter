package uet.oop.bomberman.graphics;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemy.*;
import uet.oop.bomberman.entities.Enemy.AI.Doll;
import uet.oop.bomberman.entities.Enemy.AI.Kondoria;
import uet.oop.bomberman.entities.Enemy.AI.Minvo;
import uet.oop.bomberman.entities.Enemy.AI.Oneal;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

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
                        BombermanGame.LayeredEntity.put(BombermanGame.generateKey(j, i), layer);
                        BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                    case 'p':
                        Bomber bomber = new Bomber(j, i, Sprite.player_right.getFxImage());
                        obj = new Grass(j, i, Sprite.grass.getFxImage());
                        BombermanGame.entities.add(bomber);
                        BombermanGame.stillObjects.add(obj);
                        break;
                    case '1':
                        Entity balloon = new Balloon(j, i, Sprite.balloom_left1.getFxImage());
                        obj = new Grass(j, i, Sprite.grass.getFxImage());
                        BombermanGame.entities.add(balloon);
                        BombermanGame.stillObjects.add(obj);
                        break;
                    case '2':
                        Entity oneal = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                        obj = new Grass(j, i, Sprite.grass.getFxImage());
                        BombermanGame.entities.add(oneal);
                        BombermanGame.stillObjects.add(obj);
                        break;
                    case '3':
                        Entity doll = new Doll(j, i, Sprite.doll_left1.getFxImage());
                        obj = new Grass(j, i, Sprite.grass.getFxImage());
                        BombermanGame.entities.add(doll);
                        BombermanGame.stillObjects.add(obj);
                        break;
                    case '4':
                        Entity minvo = new Minvo(j, i, Sprite.minvo_left1.getFxImage());
                        obj = new Grass(j, i, Sprite.grass.getFxImage());
                        BombermanGame.entities.add(minvo);
                        BombermanGame.stillObjects.add(obj);
                        break;
                    case '5':
                        Entity kondoria = new Kondoria(j, i, Sprite.kondoria_left1.getFxImage());
                        obj = new Grass(j, i, Sprite.grass.getFxImage());
                        BombermanGame.entities.add(kondoria);
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
