package uet.oop.bomberman.menu;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.HEIGHT;
import static uet.oop.bomberman.BombermanGame.WIDTH;

public class WinMenu extends Menu {
    public static boolean WIN_BACK = false;

    public WinMenu() {
        create();
    }

    @Override
    public Scene create() {

        VBox vb = initVBox();
        vb.setAlignment(Pos.BOTTOM_CENTER);
        vb.setPadding(new Insets(30));
        vb.setSpacing(30);
        vb.setBackground(new Background(createImage("file:res/BG_win.png")));

        Text backText = new Text("Back");

        TextFlow area = new TextFlow();

        customText(backText);

        vb.getChildren().add(area);
        vb.getChildren().add(backText);
        VBox.setMargin(area, new Insets(10, 200, 50, 250));
        vb.setFillWidth(true);

        Scene winMenuScene = new Scene(vb, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);

        EventHandler<MouseEvent> backHandle = new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                backHandle(event, backText);
            }
        };


        backText.addEventFilter(MouseEvent.MOUSE_ENTERED, backHandle);
        backText.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, backHandle);
        backText.addEventFilter(MouseEvent.MOUSE_PRESSED, backHandle);

        return winMenuScene;
    }

    private static void backHandle(MouseEvent event, Text text) {
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            text.setStyle("-fx-font-size:60");
        }else if (event.getEventType() == MouseEvent.MOUSE_EXITED_TARGET){
            text.setStyle("-fx-font-size:45");
        }else if (event.getEventType() == MouseEvent.MOUSE_PRESSED){
//			System.out.println("chon");
            WIN_BACK = true;
            text.setStyle("-fx-font-size:45");
        }else {
            text.setStyle("-fx-font-size:45");
        }
    }
}
