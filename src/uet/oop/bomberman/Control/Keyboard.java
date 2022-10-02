package uet.oop.bomberman.Control;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * sự kiện bàn phím
 */
public class Keyboard {

    public static boolean UP, LEFT, RIGHT, DOWN;

    public static void setInputKeyEvent1(javafx.scene.input.KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getCode() == KeyCode.W) {
                UP = true;
            } else if (event.getCode() == KeyCode.A) {
                LEFT = true;
            } else if (event.getCode() == KeyCode.S) {
                DOWN = true;
            } else if (event.getCode() == KeyCode.D) {
                RIGHT = true;
            }
        }
    }

    public static void setInputKeyEvent2(javafx.scene.input.KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            if (event.getCode() == KeyCode.W) {
                UP = false;
            } else if (event.getCode() == KeyCode.A) {
                LEFT = false;
            } else if (event.getCode() == KeyCode.S) {
                DOWN = false;
            } else if (event.getCode() == KeyCode.D) {
                RIGHT = false;
            }
        }
    }
}

