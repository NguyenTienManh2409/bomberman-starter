package uet.oop.bomberman.graphics;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;

import java.io.File;
import java.io.FileReader;
import java.io.IOException; // IOException is the base class for exceptions thrown while accessing information using streams, files and directories.
import java.util.Scanner;
import java.util.StringTokenizer; // The java.util.StringTokenizer class allows you to split a string into its token elements.

public class MapCreate {
    public MapCreate(String level) {
        System.out.println(System.getProperty("user.dir"));
        final File fileName = new File(level);                      // Create object fileName from class File in File library imported.
        try (FileReader inputFile = new FileReader(fileName)) {     // Try to create new object from class FileReader.
            Scanner ip = new Scanner(inputFile);                    // Create object ip from class Scanner.
            String line = ip.nextLine();                            // Input variable line in string data type.

            StringTokenizer tokens = new StringTokenizer(line);     // Create object tokens from class StringTokenizer in library imported.

            // parseInt(): Method that parses the string argument and returns a primitive int.
            BombermanGame.level = Integer.parseInt(tokens.nextToken());   // To refer to variable level in main file.
            BombermanGame.height = Integer.parseInt(tokens.nextToken());
            BombermanGame.width = Integer.parseInt(tokens.nextToken());

            while (ip.hasNextLine()) {
                // Create new object lít_kill from main file.   Main file: RunBomberman.java
                for (int i = 0; i < BombermanGame.height; ++i) {
                    String lineTile = ip.nextLine();                // Input variable lineTile in string data type.
                    StringTokenizer tokenTile = new StringTokenizer(lineTile);      // Create object tokenTile from class StringTokenizer in library imported.

                    for (int j = 0; j < BombermanGame.width; j++) {
                        int token = Integer.parseInt(tokenTile.nextToken());
                        Entity entity;                              // Create object entity from class Entity.

                        // This switch statement running, and we got a full map for a game.
                        // Through the program, in the for-loop statement, we can get the map according to each loop it passed.
                        switch (token) {
                            case 0:
                                entity = new Wall(j, i, Sprite.wall.getFxImage());          // In case 2, set entity object equal to object wall with scaled size.
                                break;
                            default:
                                entity = new Grass(j, i, Sprite.grass.getFxImage());
                        }
                    }
                }
            }
        } catch (IOException e) {                       // Catch exception
            e.printStackTrace();                        // printStackTrace(): Help to understand where the problem is actually happening.
        }
    }
}
