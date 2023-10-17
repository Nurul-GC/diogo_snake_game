import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class HighScorePage extends JFrame implements ActionListener {
    JButton return_button;
    JPanel high_score_panel;
    JLabel crownPic;
    JLabel current_score_holder;
    JLabel best_score_holder;
    JLabel panelTitle;
    JLabel current_score_label;
    JLabel best_score_label;

    // Declare the best_holder variable
    int best_holder;

    // Declare the score_holder variable
    int score_holder;

    HighScorePage() {
        setContentPane(high_score_panel);
        if (GameWindow.currentScore > GameWindow.bestScore) {
            gamePanel.playMP3("audio/new_highscore.mp3");
            JOptionPane.showMessageDialog(
                    this,
                    "NEW HIGHSCORE",
                    "highscore",
                    JOptionPane.INFORMATION_MESSAGE
                                         );
        }
        best_holder = GameWindow.new_bestScore;
        score_holder = GameWindow.currentScore;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("High-Score");

        // Set the text for the score labels
        best_score_holder.setText(Integer.toString(best_holder));
        current_score_holder.setText(Integer.toString(score_holder));
        return_button.setText("Back");
        panelTitle.setText("HIGH-SCORE");

        best_score_label.setText("BEST: ");
        current_score_label.setText("SCORE: ");
        ImageIcon crown_pic = new ImageIcon("img/crown_1.png");
        crownPic.setIcon(crown_pic);
        return_button.addActionListener(this);
        pack();
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == return_button) {
            gamePanel.playMP3("audio/button_click.mp3");
            dispose();
            MainMenuWindow menuWindow = new MainMenuWindow();
            menuWindow.setVisible(true);
            this.setVisible(false);
        }
    }

}




