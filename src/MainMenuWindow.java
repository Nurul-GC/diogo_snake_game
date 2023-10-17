import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

public class MainMenuWindow extends JFrame implements ActionListener {

    JButton play_button;
    JPanel main_menu_panel;
    JButton high_score_button;
    JButton game_settings_button;
    JLabel game_title;
    JLabel snake_Picture;

    static void playMP3(String filename) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            AdvancedPlayer player = new AdvancedPlayer(fileInputStream);

            Thread playerThread = new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            });
            playerThread.start();
        } catch (JavaLayerException | IOException e) {
            e.printStackTrace();
        }
    }

    MainMenuWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Main Menu");
        ImageIcon snake_pic = new ImageIcon("img/rygle_snake_colour_outline_1.png");
        snake_Picture.setIcon(snake_pic);

        setContentPane(main_menu_panel);
        main_menu_panel.setVisible(true);

        pack();
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setVisible(true);

        game_settings_button.addActionListener(this);
        high_score_button.addActionListener(this);
        play_button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play_button) {
            playMP3("audio/button_click.mp3");
            dispose();
            GameWindow gameWindow = new GameWindow();
            gameWindow.setVisible(true);
            this.setVisible(false);
        } else if (e.getSource() == high_score_button) {
            playMP3("audio/button_click.mp3");
            dispose();
            HighScorePage highScorePage = new HighScorePage();
            highScorePage.setVisible(true);
            this.setVisible(false);
        } else if (e.getSource() == game_settings_button) {
            playMP3("audio/button_click.mp3");
            dispose();
            GameSettings gameSettings = new GameSettings();
            gameSettings.setVisible(true);
            this.setVisible(false);
        }
    }

}
