package uet.oop.bomberman.menu;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.HEIGHT;
import static uet.oop.bomberman.BombermanGame.WIDTH;

public class AboutOption extends Menu{

    public static boolean ABOUT_BACK = false;

    public AboutOption() {
        create();
    }

    // tao Scene
    @Override
    public Scene create() {

        VBox vb = initVBox();
        vb.setAlignment(Pos.BOTTOM_CENTER);

        Text backText = new Text("Back");

        String str =  " Bomberman Game\n"
                + "\n"
                + " Members: \n"
                + " - Nguyen Tien Manh\n"
                + " - Nguyen Khac Nam Huy\n";
        TextFlow area = new TextFlow();

        Text info = new Text(str);

        customText(backText);
        customText(info);
        info.setStyle("-fx-font-size:35");
        info.setEffect(null);

        area.getChildren().add(info);
        vb.getChildren().add(area);
        vb.getChildren().add(backText);
        VBox.setMargin(area, new Insets(10, 100, 50, 100));
        vb.setFillWidth(true);

        Scene aboutOptionScene = new Scene(vb, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);

        EventHandler<MouseEvent> playHandle = new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                backHandle(event, backText);
            }
        };


        backText.addEventFilter(MouseEvent.MOUSE_ENTERED, playHandle);
        backText.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, playHandle);
        backText.addEventFilter(MouseEvent.MOUSE_PRESSED, playHandle);

        return aboutOptionScene;
    }


    // Xu ly vs chuot va nut
    private static void backHandle(MouseEvent event, Text text) {
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            text.setStyle("-fx-font-size:60");
        }else if (event.getEventType() == MouseEvent.MOUSE_EXITED_TARGET){
            text.setStyle("-fx-font-size:45");
        }else if (event.getEventType() == MouseEvent.MOUSE_PRESSED){
//			System.out.println("chon");
            ABOUT_BACK = true;
            text.setStyle("-fx-font-size:45");
        }else {
            text.setStyle("-fx-font-size:45");
        }
    }
}

