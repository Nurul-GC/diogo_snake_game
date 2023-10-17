import javazoom.jl.decoder.JavaLayerException
import javazoom.jl.player.advanced.AdvancedPlayer
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.FileInputStream
import java.io.IOException
import javax.swing.*

class MainMenuWindow internal constructor() : JFrame(), ActionListener {
    private var playButton: JButton? = null
    private var mainMenuPanel: JPanel? = null
    private var highScoreButton: JButton? = null
    private var gameSettingsButton: JButton? = null
    var gameTitle: JLabel? = null
    private var snakePicture: JLabel? = null

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Main Menu"
        val snakePic = ImageIcon("img/rygle_snake_colour_outline_1.png")
        snakePicture!!.icon = snakePic
        contentPane = mainMenuPanel
        mainMenuPanel!!.isVisible = true
        pack()
        setSize(1300, 700)
        setLocationRelativeTo(null)
        isVisible = true
        gameSettingsButton!!.addActionListener(this)
        highScoreButton!!.addActionListener(this)
        playButton!!.addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent) {
        if (e.source === playButton) {
            playMP3("audio/button_click.mp3")
            dispose()
            val gameWindow = GameWindow()
            gameWindow.isVisible = true
            this.isVisible = false
        } else if (e.source === highScoreButton) {
            playMP3("audio/button_click.mp3")
            dispose()
            val highScorePage = HighScorePage()
            highScorePage.isVisible = true
            this.isVisible = false
        } else if (e.source === gameSettingsButton) {
            playMP3("audio/button_click.mp3")
            dispose()
            val gameSettings = GameSettings()
            gameSettings.isVisible = true
            this.isVisible = false
        }
    }

    companion object {
        fun playMP3(filename: String?) {
            try {
                val fileInputStream = filename?.let { FileInputStream(it) }
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
