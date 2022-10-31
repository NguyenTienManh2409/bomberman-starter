package uet.oop.bomberman.sound;

import uet.oop.bomberman.BombermanGame;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Sound {
    private File file;
    private AudioInputStream stream;
    private AudioFormat format;
    private Clip clip;

    public Sound() {

    }

    // lay am thanh de play Sound
    private void playSound(String filename){
        try {
            file = new File(filename);
            stream = AudioSystem.getAudioInputStream(file);
            format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch (UnsupportedAudioFileException| IOException| LineUnavailableException ignored) {
        }

    }

    // lay cac sound effect
    public void getBgSound() {
        playSound("res/sound/soundtrack.wav");
    }

    public void getPutBomSound() {
        playSound("res/sound/putbomb.wav");
    }

    public void getExplosionSound() {
        playSound("res/sound/explosion.wav");
    }

    public void getEnemyDeadSound() {
        playSound("res/sound/enemy_dead.wav");
    }

    public void getPlayerDeadSound() {
        playSound("res/sound/player_dead.wav");
    }

    public void getNextLevelSound() {
        playSound("res/sound/next_level.wav");
    }

    public void getItemSound() {
        playSound("res/sound/get_item.wav");
    }

    public void getBgLoseSound() {
        playSound("res/sound/end_game_lose.wav");
    }
}
