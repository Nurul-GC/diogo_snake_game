import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class GameSettings extends JFrame implements ActionListener {
    JButton return_button, reset_button, save_button;
    JPanel main_Panel;
    JPanel skin_settings_Panel, world_settings_Panel, difficulty_settings_Panel;
    JRadioButton easy_option, medium_option, hard_option, difficulty_default, yellow_option, blue_option, red_option, colour_default, desert_option, water_option, grass_option, world_default;
    ButtonGroup colorGroup;
    ButtonGroup worldGroup;
    ButtonGroup difficultyGroup;

    public static Color newSkinToBeSaved = new Color(0, 199, 63);
    public static Color newWorldToBeSaved = new Color(43, 45, 47);
    public static int newSpeedToBeSaved = 75;
    int newSkinID = 0;
    int newWorldID = 0;
    int newSpeedID = 0;

    public GameSettings() {
        setTitle("Game Settings");
        setPreferredSize(new Dimension(1300, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setContentPane(main_Panel);
        setLocationRelativeTo(null);

        initializeButtonGroups();
        addListenersToButtons();
        setDefaultSelections();

        colorGroup.add(red_option);
        colorGroup.add(yellow_option);
        colorGroup.add(blue_option);
        colorGroup.add(colour_default);

        worldGroup.add(world_default);
        worldGroup.add(desert_option);
        worldGroup.add(water_option);
        worldGroup.add(grass_option);

        difficultyGroup.add(easy_option);
        difficultyGroup.add(medium_option);
        difficultyGroup.add(hard_option);
        difficultyGroup.add(difficulty_default);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeButtonGroups() {
        colorGroup = new ButtonGroup();
        worldGroup = new ButtonGroup();
        difficultyGroup = new ButtonGroup();
    }

    private void addListenersToButtons() {
        return_button.addActionListener(this);
        reset_button.addActionListener(this);
        save_button.addActionListener(this);
    }

    private void setDefaultSelections() {
        colour_default.setSelected(true);
        world_default.setSelected(true);
        difficulty_default.setSelected(true);
    }

    //options-------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        // snake skin color values
        if (e.getSource() == red_option) {
            newSkinID = 0;
            newSkinToBeSaved = STORAGE.get_snake_colors(newSkinID);
        } else if (e.getSource() == blue_option) {
            newSkinID = 1;
            newSkinToBeSaved = STORAGE.get_snake_colors(newSkinID);
        } else if (e.getSource() == colour_default) {
            newSkinID = 2;
            newSkinToBeSaved = STORAGE.get_snake_colors(newSkinID);
        } else if (e.getSource() == yellow_option) {
            newSkinID = 3;
            newSkinToBeSaved = STORAGE.get_snake_colors(newSkinID);
        }

        // world color values
        if (e.getSource() == world_default) {
            newWorldID = 3;
            newWorldToBeSaved = STORAGE.get_world_colors(newWorldID);
        } else if (e.getSource() == desert_option) {
            newWorldID = 2;
            newWorldToBeSaved = STORAGE.get_world_colors(newWorldID);
        } else if (e.getSource() == water_option) {
            newWorldID = 1;
            newWorldToBeSaved = STORAGE.get_world_colors(newWorldID);
        } else if (e.getSource() == grass_option) {
            newWorldID = 0;
            newWorldToBeSaved = STORAGE.get_world_colors(newWorldID);
        }

        // difficulty values
        if (e.getSource() == easy_option) {
            newSpeedID = 2;
            newSpeedToBeSaved = STORAGE.get_game_speeds(newSpeedID);
        } else if (e.getSource() == medium_option) {
            newSpeedID = 1;
            newSpeedToBeSaved = STORAGE.get_game_speeds(newSpeedID);
        } else if (e.getSource() == hard_option) {
            newSpeedID = 0;
            newSpeedToBeSaved = STORAGE.get_game_speeds(newSpeedID);
        } else if (e.getSource() == difficulty_default) {
            newSpeedID = 2;
            newSpeedToBeSaved = STORAGE.get_game_speeds(newSpeedID);
        }

//roles for buttons----------------------------------------------------------------

        if (e.getSource() == reset_button) {
            MainMenuWindow.playMP3("audio/short-success-sound-glockenspiel-treasure-video-game-6346.mp3");
            resetInfo();
            JOptionPane.showMessageDialog(this, "Successfully Reset", "Success", JOptionPane.INFORMATION_MESSAGE);

        } else if (e.getSource() == save_button) {
            // call the updaters from the STORAGE class to save the choices
            STORAGE.save_speed_to_file(newSpeedToBeSaved);
            STORAGE.save_snake_skin_to_file(newSkinToBeSaved);
            STORAGE.save_world_color_to_file(newWorldToBeSaved);
            MainMenuWindow.playMP3("audio/updated_success.mp3");
            JOptionPane.showMessageDialog(this, "Saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } else if (e.getSource() == return_button) {
            closeWindow();
        }
    }

    private void resetInfo() {

        difficulty_default.setSelected(true);
        colour_default.setSelected(true);
        world_default.setSelected(true);

        // default skin
        Color defaultSkin = new Color(0, 199, 63);

        // default world
        Color defaultWorld = new Color(43, 45, 47);

        // default speed
        int defaultSpeed = 75;

        // saves the defaults
        STORAGE.save_snake_skin_to_file(defaultSkin);
        STORAGE.save_speed_to_file(defaultSpeed);
        STORAGE.save_world_color_to_file(defaultWorld);
    }

    public void closeWindow() {
        dispose();
        MainMenuWindow mainMenuWindow = new MainMenuWindow();
        MainMenuWindow.playMP3("audio/button_click.mp3");
        mainMenuWindow.setVisible(true);
        this.setVisible(false);
    }

}