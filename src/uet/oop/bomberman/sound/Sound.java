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

    private Map<String, String> listFile;
    private String[] files = {"background", "put_bomb","explosion","enemy_dead", "player_dead","next_level","get_item"};

    public Sound() {
        listFile = new HashMap<>();
        for (String file : files) {
            listFile.put(file, "res/sound/" + file + ".wav");
        }
    }
    /**
     * @param filename the name of the file that is going to be played
     */
    private void playSound(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        file = new File(filename);
        stream = AudioSystem.getAudioInputStream(file);
        format = stream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(stream);
        clip.start();
    }

    public void getBgSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound(listFile.get("background"));
    }

    public void getPutBomSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound(listFile.get("put_bomb"));
    }

    public void getExplosionSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound(listFile.get("explosion"));
    }

    public void getEnemyDeadSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound(listFile.get("enemy_dead"));
    }

    public void getPlayerDeadSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound(listFile.get("player_dead"));
    }

    public void getNextLevelSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound(listFile.get("next_level"));
    }

    public void getItemSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound(listFile.get("get_item"));
    }
}
