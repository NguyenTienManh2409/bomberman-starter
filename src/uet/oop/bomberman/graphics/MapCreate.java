package uet.oop.bomberman.graphics;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Charactor.Bomb;
import uet.oop.bomberman.entities.Charactor.Bomber;
import uet.oop.bomberman.entities.Enemy.*;
import uet.oop.bomberman.entities.Enemy.Doll;
import uet.oop.bomberman.entities.Enemy.Kondoria;
import uet.oop.bomberman.entities.Enemy.Minvo;
import uet.oop.bomberman.entities.Enemy.Oneal;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.SpeedItem;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class MapCreate extends BombermanGame {
    public static int gameLevel = 1;
    private static char[][] map;
    private static int row;
    private static int col;

    public static int getCol() {
        return col;
    }

    public static int getRow() {
        return row;
    }

    public static void initMap() {
        BombermanGame.entities = new ArrayList<>();
        BombermanGame.stillObjects = new ArrayList<>();
        BombermanGame.LayeredEntity = new HashMap<>();
        Bomber.NUMBER_OF_BOMBS = 1;
        Bomb.LENGTH_OF_FLAME = 1;
        Bomber.setSpeed(2);

        map = new char[getCol()][getRow()];
        createMap(getGameLevel());
    }

    public static void clear() {
        BombermanGame.entities.clear();
        BombermanGame.stillObjects.clear();
        BombermanGame.LayeredEntity.clear();
        Bomb.flameList.clear();
        map = new char[getCol()][getRow()];
    }

    public static void nextMap() {
        clear();
        gameLevel++;
        initMap();
    }
    public static void createMap(int gameLevel) {
        fileLoad(gameLevel);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                char c = map[i][j];
                Entity obj;
                Stack<Entity> layer = new Stack<>();
                switch (c) {
                    case '#':
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
                    case 'f':
                        layer.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        layer.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                        layer.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.LayeredEntity.put(BombermanGame.generateKey(j, i), layer);
                        break;
                    case 'b':
                        layer.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        layer.add(new BombItem(j, i, Sprite.powerup_bombpass.getFxImage()));
                        layer.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.LayeredEntity.put(BombermanGame.generateKey(j, i), layer);
                        break;
                    case 's':
                        layer.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        layer.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                        layer.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.LayeredEntity.put(BombermanGame.generateKey(j, i), layer);
                        break;
                    case 'x':
                        layer.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        layer.add(new Portal(j, i, Sprite.portal.getFxImage()));
                        layer.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.LayeredEntity.put(BombermanGame.generateKey(j, i), layer);
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
    }

    public static void fileLoad(int gameLevel) {
        try {
            String path = String.format("res/levels/Level" + gameLevel + ".txt");
            FileInputStream reader = new FileInputStream(path);
            Scanner scanner = new Scanner(reader);

            int lv = scanner.nextInt();
            row = scanner.nextInt();
            col = scanner.nextInt();

            map = new char[row][col];
            scanner.nextLine();

            for (int i = 0; i < row; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < col; j++) {
                    map[i][j] = line.charAt(j);
                }
            }
            reader.close();
            scanner.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static int getGameLevel() {
        return gameLevel;
    }

    public static char[][] getMap() {
        return map;
    }
}
