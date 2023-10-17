import java.awt.Color
import java.io.*
import java.text.MessageFormat
import java.util.prefs.Preferences

object STORAGE {
    // colour values,fetchers,savers and updaters
    var snake_colors = arrayOf( // colours for skin
            Color(255, 64, 64),  //  0
            Color(23, 92, 161),  //  1
            Color(0, 199, 63),  //  2 default
            Color(253, 195, 0))
    var world_colors = arrayOf( // world colours
            Color(1, 50, 32),  //  0
            Color(1, 26, 96),  //  1
            Color(244, 145, 70),  //  2
            Color(43, 45, 47) //  3 default
    )

    // speeds of game level
    var GameSpeeds = intArrayOf(35, 75, 100)

    // default best score
    var Best = intArrayOf(0)

    // UPDATERS
    fun UpdateInt(arrayDestination: IntArray, index: Int, value: Int) {
        // update the value of the chosen speed
        if (index >= 0 && index < arrayDestination.size) {
            arrayDestination[index] = value
        } else {
            println("Invalid index. Index must be within the bounds of the array.")
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
    @JvmStatic
    fun get_snake_colors(colorID: Int): Color {
        // fetcher for the skin
        return if (colorID >= 0) {
            // && colorID < snake_colors.length
            snake_colors[colorID]
        } else {
            Color(0, 199, 63)
        }
    }

    @JvmStatic
    fun get_world_colors(world_colorID: Int): Color {
        // fetcher for world
        return if (world_colorID >= 0) {
            //  && world_colorID < world_colors.length
            world_colors[world_colorID]
        } else {
            Color(0, 199, 63)
        }
    }

    @JvmStatic
    fun get_game_speeds(gameSpeedID: Int): Int {
        // fetch the game speed
        return if (gameSpeedID >= 0) {
            //  && gameSpeedID < GameSpeeds.length
            GameSpeeds[gameSpeedID]
        } else {
            // default value
            75
        }
    }

    fun Best(id: Int): Int {
        // fetch the best score
        return if (id >= 0 && id < Best.size) {
            Best[id]
        } else 0
        // default value
    }

    // FETCHERS
    fun fetchSavedSpeed(): Int {
        val savedOptions = Preferences.userNodeForPackage(STORAGE::class.java)
        return savedOptions.getInt("SavedSpeed", 75) // default value
    }

    fun fetchSavedSkin(): Color {
        val savedOptions = Preferences.userNodeForPackage(STORAGE::class.java)
        val skinRed = savedOptions.getInt("SavedSkinRed", 255) // Default red value
        val skinGreen = savedOptions.getInt("SavedSkinGreen", 64) // Default green value
        val skinBlue = savedOptions.getInt("SavedSkinBlue", 64) // Default blue value
        return Color(skinRed, skinGreen, skinBlue)
    }

    fun fetchSavedWorldColor(): Color {
        val savedOptions = Preferences.userNodeForPackage(STORAGE::class.java)
        val worldRed = savedOptions.getInt("SavedWorldRed", 244) // Default red value
        val worldGreen = savedOptions.getInt("SavedWorldGreen", 145) // Default green value
        val worldBlue = savedOptions.getInt("SavedWorldBlue", 70) // Default blue value
        return Color(worldRed, worldGreen, worldBlue)
    }

    /*
    public static int fetchBestScore() {
        Preferences savedOptions = Preferences.userNodeForPackage(STORAGE.class);
        return savedOptions.getInt("Best",0  ); // default value
    }
    */
    // WORLD COLOR
    @JvmStatic
    fun save_world_color_to_file(worldColor: Color) {
        // File savedWorld = new File("misc_src/saved_world_color.txt");
        try {
            BufferedWriter(FileWriter("misc_src/saved_world_color.txt")).use { bw ->
                val textRepresentation = MessageFormat.format(
                        "{0},{1},{2}",
                        worldColor.red, worldColor.green, worldColor.blue
                )
                bw.write(textRepresentation)
            }
        } catch (e: IOException) {
            e.printStackTrace()
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
    @JvmStatic
    fun save_speed_to_file(speed: Int) {
        // File savedSpeed = new File("misc_src/saved_speed.txt");
        try {
            BufferedWriter(FileWriter("misc_src/saved_speed.txt")).use { bw ->
                val textRepresentation = speed.toString()
                bw.write(textRepresentation)
            }
        } catch (e: IOException) {
            e.printStackTrace()
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
    @JvmStatic
    fun saveSnakeSkinToFile(skinColor: Color) {
        try {
            BufferedWriter(FileWriter("misc_src/saved_snake_skin.txt")).use { bw ->
                val textRepresentation = MessageFormat.format(
                        "{0},{1},{2}",
                        skinColor.red,
                        skinColor.green,
                        skinColor.blue
                )
                bw.write(textRepresentation)
            }
        } catch (e: IOException) {
            e.printStackTrace()
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
    fun save_best_score_to_file(best: Int) {
        // File savedBest = new File("misc_src/saved_best.txt");
        try {
            BufferedWriter(FileWriter("misc_src/saved_best.txt")).use { bw ->
                val textRepresentation = best.toString()
                bw.write(textRepresentation)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun load_saved_best_score(): Int {
        try {
            BufferedReader(FileReader("misc_src/saved_best.txt")).use { br ->
                val line = br.readLine()
                return line.toInt()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return 0 // Default best score
    }
}