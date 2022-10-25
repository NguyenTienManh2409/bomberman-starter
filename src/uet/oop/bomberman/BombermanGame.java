package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.MapCreate;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Control.Keyboard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class BombermanGame extends Application {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 13;
    private GraphicsContext gc;
    private Canvas canvas;
    private Camera camera;

    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static Map<Integer, Stack<Entity>> LayeredEntity = new HashMap<>();
    public static Bomber bomberman;
    private String path = "res/levels/Level1.txt";

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT );
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        stage.setTitle("Nhom 11");

        camera = new Camera(0, 0);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                scene.addEventHandler(KeyEvent.KEY_PRESSED, Keyboard::setInputKeyEvent1);
                scene.addEventHandler(KeyEvent.KEY_RELEASED, Keyboard::setInputKeyEvent2);
                try {
                    update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                render();
            }
        };
        timer.start();
        MapCreate.createMap(path);

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }


    public void update() throws IOException {
        entities.forEach(Entity::update);
        camera.tick(Objects.requireNonNull(bomberman));
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

    public static int generateKey(int x, int y) {
        return x * 100 + y;
    }

}