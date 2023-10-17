import javazoom.jl.decoder.JavaLayerException
import javazoom.jl.player.advanced.AdvancedPlayer
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.io.FileInputStream
import java.io.IOException
import java.util.*
import javax.swing.*
import javax.swing.Timer

internal class gamePanel : JPanel(), ActionListener {
    private val x = IntArray(GAME_UNITS)
    private val y = IntArray(GAME_UNITS)

    // fetched from saved world
    private var worldColor = STORAGE.fetchSavedWorldColor()

    // fetched from saved skin
    private var snakeSkin = STORAGE.fetchSavedSkin()

    // fetched from saved speed
    private var gameSpeed = STORAGE.fetchSavedSpeed()

    private var applesEaten = 0
    private var appleX = 0
    private var appleY = 0
    private var bodyLength = 6
    private var direction = 'R'
    private var running = false
    private var timer: Timer? = null
    private val random: Random = Random()
    private val quitButton: JButton
    private val restartButton: JButton

    // all the game components are placed here
    init {
        this.preferredSize = Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)
        background = worldColor
        this.isFocusable = true
        addKeyListener(MyKeyAdapter())
        StartGame()
        isVisible = true
        val buttonFont = Font("Arial", Font.BOLD, 18)
        val buttonColor = Color(255, 255, 255)
        val forColor = Color(0, 0, 0)
        quitButton = JButton("Quit")
        quitButton.isVisible = false
        quitButton.addActionListener(this)
        quitButton.font = buttonFont
        quitButton.background = buttonColor
        quitButton.foreground = forColor
        restartButton = JButton("Restart")
        restartButton.isVisible = false
        restartButton.addActionListener(this)
        restartButton.font = buttonFont
        restartButton.background = buttonColor
        restartButton.foreground = forColor
        add(restartButton)
        add(quitButton)
    }

    override fun actionPerformed(e: ActionEvent) {
        if (e.source === quitButton) {
            playMP3("audio/button_click.mp3")
            SwingUtilities.getWindowAncestor(this).dispose()
            val menuWindow = MainMenuWindow()
            menuWindow.isVisible = true
            this.isVisible = false
        } else if (e.source === restartButton) {
            playMP3("audio/button_click.mp3")
            SwingUtilities.getWindowAncestor(this).dispose()
            val gameWindow = GameWindow()
            gameWindow.isVisible = true
            this.isVisible = false
        }
    }

    fun StartGame() {
        newApple()
        running = true
        timer = Timer(gameSpeed) { _: ActionEvent? ->
            if (running) {
                move()
                checkApple()
                checkCollisions()
            }
            repaint()
        }
        timer!!.start()
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        draw(g)
    }

    //objects in the game and their properties
    fun draw(g: Graphics) {
        if (running) {
            val apple_colour = Color(217, 33, 33)
            g.color = apple_colour
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE)
            for (i in 0 until bodyLength) {
                if (i == 0) {
                    //head colour-----------------
                    g.color = Color.white
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE)
                } else {
                    //body colour------------------
                    g.color = snakeSkin
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE)
                }
            }
            g.color = Color.red
            g.font = Font("Ink Free", Font.BOLD, 40)
            val metrics = getFontMetrics(g.font)
            g.drawString(
                    "Score: $applesEaten",
                    (SCREEN_WIDTH - metrics.stringWidth("Score: $applesEaten")) / 2,
                    g.font.size
            )
        } else {
            gameOver(g)
        }
    }

    fun newApple() {
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE
    }

    fun move() {
        for (i in bodyLength downTo 1) {
            x[i] = x[i - 1]
            y[i] = y[i - 1]
        }
        when (direction) {
            'U' -> y[0] = y[0] - UNIT_SIZE
            'D' -> y[0] = y[0] + UNIT_SIZE
            'L' -> x[0] = x[0] - UNIT_SIZE
            'R' -> x[0] = x[0] + UNIT_SIZE
        }
    }

    fun checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyLength++
            applesEaten++
            newApple()
            playMP3("audio/food_collected.mp3")
        }
    }

    fun checkCollisions() {
        for (i in bodyLength downTo 1) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false
                break
            }
        }
        if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT) {
            running = false
        }
        if (!running) {
            timer!!.stop()
        }
    }

    fun gameOver(g: Graphics) {
        g.color = Color.red
        g.font = Font("Arial", Font.BOLD, 40)
        g.drawString(
                "Score: $applesEaten",
                10,
                g.font.size + 10
        )
        g.color = Color.red
        g.font = Font("Ink Free", Font.BOLD, 75)
        val metrics2 = getFontMetrics(g.font)
        g.drawString("Game Over :(", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2)
        playMP3("audio/lose.mp3")
        GameWindow.currentScore = applesEaten
        GameWindow.bestScore = STORAGE.Best(0)
        GameWindow.new_bestScore = STORAGE.Best(0)
        if (GameWindow.currentScore > GameWindow.bestScore) {
            GameWindow.new_bestScore = GameWindow.currentScore
            STORAGE.UpdateInt(STORAGE.Best, 0, GameWindow.new_bestScore)
        }
        quitButton.isVisible = true
        restartButton.isVisible = true
    }

    private inner class MyKeyAdapter : KeyAdapter() {
        override fun keyPressed(e: KeyEvent) {
            when (e.keyCode) {
                KeyEvent.VK_LEFT -> {
                    if (direction != 'R') {
                        direction = 'L'
                    }
                }

                KeyEvent.VK_RIGHT -> {
                    if (direction != 'L') {
                        direction = 'R'
                    }
                }

                KeyEvent.VK_UP -> {
                    if (direction != 'D') {
                        direction = 'U'
                    }
                }

                KeyEvent.VK_DOWN -> {
                    if (direction != 'U') {
                        direction = 'D'
                    }
                }
            }
        }
    }

    companion object {
        private const val SCREEN_WIDTH = 1300
        private const val SCREEN_HEIGHT = 700
        private const val UNIT_SIZE = 25
        private const val GAME_UNITS = SCREEN_WIDTH * SCREEN_HEIGHT / UNIT_SIZE

        //method for playing sound FX
        @JvmStatic
        fun playMP3(filePath: String?) {
            try {
                val fileInputStream = FileInputStream(filePath)
                val player = AdvancedPlayer(fileInputStream)
                val playerThread = Thread {
                    try {
                        player.play()
                    } catch (e: JavaLayerException) {
                        e.printStackTrace()
                    }
                }
                playerThread.start()
            } catch (e: JavaLayerException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

class GameWindow : JFrame() {
    init {
        add(gamePanel())
        title = "Snake Game"
        defaultCloseOperation = EXIT_ON_CLOSE
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }

    companion object {
        @JvmField
        var currentScore = 0
        @JvmField
        var bestScore = STORAGE.load_saved_best_score()
        @JvmField
        var new_bestScore = 0
    }
}
