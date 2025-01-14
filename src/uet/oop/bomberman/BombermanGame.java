package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.Collision.Collision;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Charactor.Bomb;
import uet.oop.bomberman.entities.Charactor.Bomber;
import uet.oop.bomberman.entities.Charactor.Flame;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.graphics.MapCreate;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Control.Keyboard;
import uet.oop.bomberman.menu.AboutOption;
import uet.oop.bomberman.menu.LoseMenu;
import uet.oop.bomberman.menu.WinMenu;
import uet.oop.bomberman.menu.MainMenu;
import uet.oop.bomberman.sound.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;

public class BombermanGame extends Application {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 13;
    private GraphicsContext gc;
    private Canvas canvas;
    private Camera camera;
    public static Bomber bomberman;

    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static Map<Integer, Stack<Entity>> LayeredEntity = new HashMap<>();
    private final MainMenu mainMenu = new MainMenu();
    private final AboutOption aboutOption = new AboutOption();
    private final LoseMenu loseMenu = new LoseMenu();
    private final WinMenu winMenu = new WinMenu();


    private Sound sound = new Sound();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT );
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        stage.setTitle("Nhom 11");

        Scene mainMenuScene = mainMenu.create();
        Scene aboutOptionScene = aboutOption.create();
        Scene loseMenuScene =  loseMenu.create();
        Scene winMenuScene = winMenu.create();

        camera = new Camera(0, 0);

        stage.setScene(mainMenuScene);
        stage.show();

        // Them scene vao stage

        sound.getBgSound();

        final boolean[] running = {false};

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                mainMenuScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (MainMenu.PLAY) {
                            stage.setScene(scene);
                            stage.show();
                            MainMenu.PLAY = false;
                            running[0] = true;
                            MapCreate.clearAll();
                            MapCreate.initMap();
                        }
                        if (MainMenu.ABOUT) {
                            stage.setScene(aboutOptionScene);
                            MainMenu.ABOUT = false;
                        }
                    }
                });
                aboutOptionScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (AboutOption.ABOUT_BACK) {
                            stage.setScene(mainMenuScene);
                            AboutOption.ABOUT_BACK = false;
                            MainMenu.ABOUT = false;
                        }
                    }
                });

                loseMenuScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (LoseMenu.LOSE_BACK) {
                            stage.setScene(mainMenuScene);
                            LoseMenu.LOSE_BACK = false;
                        }
                    }
                });

                winMenuScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (WinMenu.WIN_BACK) {
                            stage.setScene(mainMenuScene);
                            WinMenu.WIN_BACK = false;
                        }
                    }
                });

                if (running[0]) {
                    scene.addEventHandler(KeyEvent.KEY_PRESSED, Keyboard::setInputKeyEvent1);
                    scene.addEventHandler(KeyEvent.KEY_RELEASED, Keyboard::setInputKeyEvent2);
                    scene.setOnKeyPressed(keyEvent -> {
                        switch (keyEvent.getCode()) {
                            case SPACE:
                                if (Bomber.NUMBER_OF_BOMBS != 0) {
                                    createBomb();
                                    sound.getPutBomSound();
                                }
                                break;
                            case L:
                                // ToDo: Test kill enemy
                                for (Entity entity : entities) {
                                    if (entity instanceof Enemy) {
                                        ((Enemy) entity).kill();
                                        break;
                                    }
                                }
                                break;
                            case K:
                                // ToDo: Test kill player
                                bomberman.kill();
                                break;
                            case N:
                                // TODO: Test next level
                                MapCreate.nextMap();
                                break;
                        }
                    });
                    if (Bomber.losegame) {
                        running[0] = false;
                        stage.setScene(loseMenuScene);
                    }
                    if (MapCreate.gameLevel == 4) {
                        running[0] = false;
                        stage.setScene(winMenuScene);
                    }
                    try {
                        update();
                    } catch (IOException | ConcurrentModificationException | UnsupportedAudioFileException |
                            LineUnavailableException | NullPointerException ignored) {

                    }
                    render();
                }
            }
        };
        timer.start();
        MapCreate.createMap(MapCreate.getGameLevel());
        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }


    public void update() throws IOException, ConcurrentModificationException, UnsupportedAudioFileException, LineUnavailableException {
        entities.forEach(Entity::update);
        bombUpdate();
        itemUpdate();
        portalUpdate();
        camera.Move(Objects.requireNonNull(getPlayer()));
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.translate(-camera.getX(), 0);
        stillObjects.forEach(g -> g.render(gc));
        if (!LayeredEntity.isEmpty()) {
            for (Integer value : LayeredEntity.keySet()) {
                LayeredEntity.get(value).peek().render(gc);
            }
        }
        entities.forEach(g -> g.render(gc));
        gc.translate(camera.getX(), 0);
    }

    //Todo: xu li bom
    private void bombUpdate() {
        Iterator<Bomb> bombIterator = Bomber.bombList.iterator();
        while (bombIterator.hasNext()) {
            Bomb bomb = bombIterator.next();
            if (bomb != null) {
                bomb.update();
                if (!bomb.isDestroyed() && bomb.isExploding()) {
                    for (int i = 0; i < bomb.getFlameList().size(); i++) {
                        Flame flame = bomb.getFlameList().get(i);
                        flame.update();
                        // xu li bom khi va cham voi enemy hoac bomber
                        Iterator<Entity> itr = BombermanGame.entities.iterator();
                        Entity cur;
                        while (itr.hasNext()) {
                            cur = itr.next();
                            if (cur instanceof Enemy) {
                                if (Collision.checkVaCham(cur, flame)) {
                                    ((Enemy) cur).kill();
                                }
                            }
                            if (cur instanceof Bomber) {
                                if (Collision.checkVaCham(cur, flame)) {
                                    ((Bomber) cur).kill();
                                }
                            }
                        }
                        // xu li bom khi va cham voi brick
                        int xFlame = (int) (flame.getX() / Sprite.SCALED_SIZE);
                        int yFlame = (int) (flame.getY() / Sprite.SCALED_SIZE);
                        if (BombermanGame.LayeredEntity.containsKey(BombermanGame.generateKey(xFlame, yFlame))) {
                            Stack<Entity> tiles = BombermanGame.LayeredEntity.get(BombermanGame.generateKey(xFlame, yFlame));
                            if (tiles.peek() instanceof Brick) {
                                Brick brick = (Brick) tiles.peek();
                                brick.setExploding(true);
                                brick.update();
                                if (brick.isDestroyed()) {
                                    tiles.pop();
                                }
                            }
                        }
                        // xu li khi va cham voi bomb khac
                        for (Bomb b1 : Bomber.bombList) {
                            if (Collision.checkVaCham(b1,flame) && b1 != bomb){
                                b1.set_timeToExplode(Bomb.TIME_TO_EXPLOSION_BOMB - 2);
                            }
                        }

                    }
                }
                if (bomb.isDestroyed()) {
                    Bomber.NUMBER_OF_BOMBS++;
                    bombIterator.remove();
                }
            }
        }
    }

    //Todo: tao bom tai vi tri hien tai cua bomber
    public static void createBomb() {
        int tmpX = (int) ((BombermanGame.getPlayer().getX() + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
        int tmpY = (int) ((BombermanGame.getPlayer().getY() + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
        Bomb bomb = new Bomb(tmpX, tmpY, Sprite.bomb.getFxImage());
        Bomber.bombList.add(bomb);
        Bomber.NUMBER_OF_BOMBS--;
    }

    //ToDo: xu li khi bomber va cham item
    private void itemUpdate() {
        if (!LayeredEntity.isEmpty()) {
            for (Integer value : getLayeredEntitySet()) {
                // xu li bomber va cham flameItem
                if (LayeredEntity.get(value).peek() instanceof FlameItem
                        && Collision.checkVaCham(Objects.requireNonNull(getPlayer()), LayeredEntity.get(value).peek())) {
                    Bomb.LENGTH_OF_FLAME++;
                    LayeredEntity.get(value).pop();
                    sound.getItemSound();
                    FlameItem.timeItem = 0;
                    FlameItem.isPickUp = true;
                }
                // xu li bomber va cham speedItem
                if (LayeredEntity.get(value).peek() instanceof SpeedItem
                        && Collision.checkVaCham(Objects.requireNonNull(getPlayer()), LayeredEntity.get(value).peek())) {
                    LayeredEntity.get(value).pop();
                    Bomber.setSpeed(3);
                    sound.getItemSound();
                    SpeedItem.timeItem = 0;
                    SpeedItem.isPickUp = true;
                }
                // xu li khi bomber va cham bombItem
                if (LayeredEntity.get(value).peek() instanceof BombItem
                        && Collision.checkVaCham(Objects.requireNonNull(getPlayer()), LayeredEntity.get(value).peek())) {
                    LayeredEntity.get(value).pop();
                    Bomber.NUMBER_OF_BOMBS++;
                    sound.getItemSound();
                    BombItem.timeItem = 0;
                    BombItem.isPickUp = true;
                }
            }
        }
    }

    //Todo: xu li khi bomber di qua cong dich chuyen
    private void portalUpdate() {
        // dem so luong enemy
        int count_enemy = 0;
        Iterator<Entity> itr = entities.iterator();
        while (itr.hasNext()) {
            Entity e = itr.next();
            if (e instanceof Enemy) {
                count_enemy++;
            }
        }

        // xu li khi nguoi choi co the qua man
        if (count_enemy == 0) {
            boolean canNextGame = false;
            if (!LayeredEntity.isEmpty()) {
                for (Integer value : getLayeredEntitySet()) {
                    if (LayeredEntity.get(value).peek() instanceof Portal
                            && Collision.checkVaCham(Objects.requireNonNull(getPlayer()), LayeredEntity.get(value).peek())) {
                        canNextGame = true;
                        break;
                    }
                }
            }
            // xu li khi qua man
            if (canNextGame) {
                MapCreate.nextMap();
            }
        }
    }


    // ToDo: tra ve bomber
    public static Bomber getPlayer() {
        Iterator<Entity> itr = entities.iterator();

        Entity cur;
        while (itr.hasNext()) {
            cur = itr.next();

            if (cur instanceof Bomber)
                return (Bomber) cur;
        }

        return null;
    }

    // ToDo: tao key cho bang bam LayereEntity
    public static int generateKey(int x, int y) {
        return x * 100 + y;
    }

    public static Set<Integer> getLayeredEntitySet() {
        return LayeredEntity.keySet();
    }
}