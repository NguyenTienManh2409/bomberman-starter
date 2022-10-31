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

    public Sound() {

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
        playSound("res/sound/soundtrack.wav");
    }

    public void getPutBomSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound("res/sound/putbomb.wav");
    }

    public void getExplosionSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound("res/sound/explosion.wav");
    }

    public void getEnemyDeadSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound("res/sound/enemy_dead.wav");
    }

    public void getPlayerDeadSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound("res/sound/player_dead.wav");
    }

    public void getNextLevelSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound("res/sound/next_level.wav");
    }

    public void getItemSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound("res/sound/get_item.wav");
    }

    public void getBgLoseSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playSound("res/sound/end_game_lose.wav");
    }
}
