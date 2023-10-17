import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

class gamePanel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 700;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;

    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];

    // fetched from saved world
    Color worldColor = STORAGE.fetchSavedWorldColor();

    // fetched from saved skin
    Color snakeSkin = STORAGE.fetchSavedSkin();

    // fetched from saved speed
    int gameSpeed = STORAGE.fetchSavedSpeed();

    private int applesEaten;
    private int appleX;
    private int appleY;
    private int bodyLength = 6;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private final Random random;
    private final JButton quit_button;
    private final JButton restart_button;

    //method for playing sound FX
    public static void playMP3(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
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

    // all the game components are placed here
    public gamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        this.setBackground(worldColor);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        StartGame();
        setVisible(true);


        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        Color buttonColor = new Color(255, 255, 255);
        Color forColor=new Color(0, 0, 0);

        quit_button=new JButton("Quit");
        quit_button.setVisible(false);
        quit_button.addActionListener(this);
        quit_button.setFont(buttonFont);
        quit_button.setBackground(buttonColor);
        quit_button.setForeground(forColor);



        restart_button=new JButton("Restart");
        restart_button.setVisible(false);
        restart_button.addActionListener(this);
        restart_button.setFont(buttonFont);
        restart_button.setBackground(buttonColor);
        restart_button.setForeground(forColor);


        add(restart_button);
        add(quit_button);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quit_button) {
            playMP3("audio/button_click.mp3");
            SwingUtilities.getWindowAncestor(this).dispose();
            MainMenuWindow menuWindow = new MainMenuWindow();
            menuWindow.setVisible(true);
            this.setVisible(false);
        }
        else if (e.getSource() == restart_button) {
            playMP3("audio/button_click.mp3");
            SwingUtilities.getWindowAncestor(this).dispose();
            GameWindow gameWindow=new GameWindow();
            gameWindow.setVisible(true);
            this.setVisible(false);

        }
    }

    public void StartGame() {
        newApple();
        running = true;
        timer = new Timer(gameSpeed, e -> {
            if (running) {
                move();
                checkApple();
                checkCollisions();
            }
            repaint();
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    //objects in the game and their properties
    public void draw(Graphics g) {
        if (running) {
            Color apple_colour=new Color(217,33,33);
            g.setColor(apple_colour);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyLength; i++) {

                if (i == 0) {
                    //head colour-----------------
                    g.setColor(Color.white);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                } else {
                    //body colour------------------
                    g.setColor(snakeSkin);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(
                    "Score: " + applesEaten,
                    (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize()
            );
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyLength; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyLength++;
            applesEaten++;
            newApple();
            playMP3("audio/food_collected.mp3");
        }
    }

    public void checkCollisions() {
        for (int i = bodyLength; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
        if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(
            "Score: " + applesEaten,
            10,
            g.getFont().getSize() + 10
        );
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over :(", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        playMP3("audio/lose.mp3");
        GameWindow.currentScore = applesEaten;
        GameWindow.bestScore = STORAGE.Best(0);
        GameWindow.new_bestScore = STORAGE.Best(0);
        if (GameWindow.currentScore > GameWindow.bestScore){
            GameWindow.new_bestScore = GameWindow.currentScore;
            STORAGE.UpdateInt(STORAGE.Best, 0, GameWindow.new_bestScore);
        }
        quit_button.setVisible(true);
        restart_button.setVisible(true);
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                }
                case KeyEvent.VK_UP -> {
                    if (direction != 'D') {
                        direction = 'U';
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != 'U') {
                        direction = 'D';
                    }
                }
            }
        }
    }
}

public class GameWindow extends JFrame {
    public static int currentScore = 0;
    public static int bestScore = STORAGE.load_saved_best_score();
    public static int new_bestScore = 0;


    public GameWindow() {
        add(new gamePanel());
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}

