import java.awt.*;
import java.io.*;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

public class STORAGE {
    // colour values,fetchers,savers and updaters

    public static Color[] snake_colors = {
            // colours for skin
            new Color(255, 64, 64),   //  0
            new Color(23, 92, 161),   //  1
            new Color(0, 199, 63),    //  2 default
            new Color(253, 195, 0),   //  3
    };

    public static Color[] world_colors = {
            // world colours
            new Color(1,50,32),       //  0
            new Color(1, 26, 96),     //  1
            new Color(244,145,70),    //  2
            new Color(43,45,47)       //  3 default

    };

    // speeds of game level
    public static int[] GameSpeeds = {35, 75, 100};

    // default best score
    public static int[] Best = {0};

    // UPDATERS
    public static void UpdateInt(int[] arrayDestination, int index, int value) {
        // update the value of the chosen speed
        if (index >= 0 && index < arrayDestination.length) {
            arrayDestination[index] = value;
        } else {
            System.out.println("Invalid index. Index must be within the bounds of the array.");
        }
    }

    /*
    public static void UpdateSavedColor(Color[] arrayDestination, int index, Color value) {
        // update the values of chosen choices
        if (index >= 0 && index < arrayDestination.length) {
            arrayDestination[index] = value;
        } else {
            System.out.println("Invalid index. Index must be within the bounds of the array.");
        }
    }
    */

    // GETTERS
    public static Color get_snake_colors(int colorID) {
        // fetcher for the skin
        if (colorID >= 0) {
            // && colorID < snake_colors.length
            return snake_colors[colorID];
        }
        else {
            return new Color(0, 199, 63);
        }
    }
    public static Color get_world_colors(int world_colorID) {
        // fetcher for world
        if (world_colorID >= 0) {
            //  && world_colorID < world_colors.length
            return world_colors[world_colorID];
        }
        else {
            return new Color(0, 199, 63);
        }
    }
    public static int get_game_speeds(int gameSpeedID) {
        // fetch the game speed
        if (gameSpeedID >= 0) {
            //  && gameSpeedID < GameSpeeds.length
            return GameSpeeds[gameSpeedID];
        } else {
            // default value
            return 75;
        }
    }

    public static int Best(int id) {
        // fetch the best score
        if (id >= 0 && id < Best.length) {
            return Best[id];
        }
        return 0; // default value
    }

    // FETCHERS
    public static int fetchSavedSpeed() {
        Preferences savedOptions = Preferences.userNodeForPackage(STORAGE.class);
        return savedOptions.getInt("SavedSpeed", 75); // default value
    }
    public static Color fetchSavedSkin() {
        Preferences savedOptions = Preferences.userNodeForPackage(STORAGE.class);
        int skinRed = savedOptions.getInt("SavedSkinRed", 255);    // Default red value
        int skinGreen = savedOptions.getInt("SavedSkinGreen", 64); // Default green value
        int skinBlue = savedOptions.getInt("SavedSkinBlue", 64);   // Default blue value
        return new Color(skinRed, skinGreen, skinBlue);
    }
    public static Color fetchSavedWorldColor() {
        Preferences savedOptions = Preferences.userNodeForPackage(STORAGE.class);
        int worldRed = savedOptions.getInt("SavedWorldRed", 244);     // Default red value
        int worldGreen = savedOptions.getInt("SavedWorldGreen", 145); // Default green value
        int worldBlue = savedOptions.getInt("SavedWorldBlue", 70);    // Default blue value
        return new Color(worldRed, worldGreen, worldBlue);
    }
    /*
    public static int fetchBestScore() {
        Preferences savedOptions = Preferences.userNodeForPackage(STORAGE.class);
        return savedOptions.getInt("Best",0  ); // default value
    }
    */

    // WORLD COLOR
    public static void save_world_color_to_file(Color worldColor) {
        // File savedWorld = new File("misc_src/saved_world_color.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("misc_src/saved_world_color.txt"))) {
            String textRepresentation = MessageFormat.format(
                    "{0},{1},{2}",
                    worldColor.getRed(), worldColor.getGreen(), worldColor.getBlue()
            );
            bw.write(textRepresentation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    public static Color load_saved_world_color() {
        try (BufferedReader br = new BufferedReader(new FileReader("misc_src/saved_world_color.txt"))) {
            String line = br.readLine();
            String[] parts = line.split(",");
            if (parts.length == 3) {
                int red = Integer.parseInt(parts[0]);
                int green = Integer.parseInt(parts[1]);
                int blue = Integer.parseInt(parts[2]);
                return new Color(red, green, blue);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return new Color(1, 26, 96);  // Default color
    }
    */

    // SAVED SPEED
    public static void save_speed_to_file(int speed) {
        // File savedSpeed = new File("misc_src/saved_speed.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("misc_src/saved_speed.txt"))) {
            String textRepresentation = String.valueOf(speed);
            bw.write(textRepresentation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    public static int load_saved_speed() {
        try (BufferedReader br = new BufferedReader(new FileReader("misc_src/saved_speed.txt"))) {
            String line = br.readLine();
            return Integer.parseInt(line);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 75;  // Default speed
    }
    */

    // SNAKE SKIN
    public static void save_snake_skin_to_file(Color skinColor) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("misc_src/saved_snake_skin.txt"))) {
            String textRepresentation = MessageFormat.format(
                    "{0},{1},{2}",
                    skinColor.getRed(),
                    skinColor.getGreen(),
                    skinColor.getBlue()
            );
            bw.write(textRepresentation);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*
    public static Color load_saved_snake_skin() {
        try (BufferedReader br = new BufferedReader(new FileReader("misc_src/saved_snake_skin.txt"))) {
            String line = br.readLine();
            String[] parts = line.split(",");
            if (parts.length == 3) {
                int red = Integer.parseInt(parts[0]);
                int green = Integer.parseInt(parts[1]);
                int blue = Integer.parseInt(parts[2]);
                return new Color(red, green, blue);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return new Color(0, 199, 63);  // Default color
    }
    */

    // BEST SCORE
    public static void save_best_score_to_file(int best) {
        // File savedBest = new File("misc_src/saved_best.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("misc_src/saved_best.txt"))) {
            String textRepresentation = String.valueOf(best);
            bw.write(textRepresentation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int load_saved_best_score() {

        try (BufferedReader br = new BufferedReader(new FileReader("misc_src/saved_best.txt"))) {
            String line = br.readLine();
            return Integer.parseInt(line);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;  // Default best score
    }

}