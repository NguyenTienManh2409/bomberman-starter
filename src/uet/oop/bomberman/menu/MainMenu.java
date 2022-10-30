package uet.oop.bomberman.menu;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.HEIGHT;
import static uet.oop.bomberman.BombermanGame.WIDTH;

public class MainMenu extends Menu {
    public MainMenu() {
        create();
    }
    public static boolean PLAY = false;
    public static boolean ABOUT = false;

    @Override

    public Scene create(){
        VBox vb = initVBox();
        vb.setAlignment(Pos.CENTER);

        Text playText = new Text("Play");
        Text aboutText = new Text("About");

        customText(playText);
        customText(aboutText);

        vb.getChildren().add(playText);
        vb.getChildren().add(aboutText);

        Scene menuScene = new Scene(vb, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);

        EventHandler<MouseEvent> playHandle = new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                playHandle(event, playText);
            }
        };

        EventHandler<MouseEvent> aboutHandle = new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                aboutHandle(event, aboutText);
            }
        };


        playText.addEventFilter(MouseEvent.MOUSE_ENTERED, playHandle);
        playText.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, playHandle);
        playText.addEventFilter(MouseEvent.MOUSE_PRESSED, playHandle);

        aboutText.addEventFilter(MouseEvent.MOUSE_ENTERED, aboutHandle);
        aboutText.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, aboutHandle);
        aboutText.addEventFilter(MouseEvent.MOUSE_PRESSED, aboutHandle);

        return menuScene;
    }

    private static void aboutHandle(MouseEvent event, Text text) {
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            text.setStyle("-fx-font-size:60");
        }else if (event.getEventType() == MouseEvent.MOUSE_EXITED){
            text.setStyle("-fx-font-size:45");
        }else if (event.getEventType() == MouseEvent.MOUSE_PRESSED){
//			System.out.println("chon");
            ABOUT = true;
            text.setStyle("-fx-font-size:45");
        }else {
            text.setStyle("-fx-font-size:45");
        }
    }


    private static void playHandle(MouseEvent event, Text text) {
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            text.setStyle("-fx-font-size:60");
        }else if (event.getEventType() == MouseEvent.MOUSE_EXITED_TARGET){
            text.setStyle("-fx-font-size:45");
        }else if (event.getEventType() == MouseEvent.MOUSE_PRESSED){
//			System.out.println("chon");
            PLAY = true;
            text.setStyle("-fx-font-size:45");
        }else {
            text.setStyle("-fx-font-size:45");
        }
    }

}
