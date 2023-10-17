import gamePanel.Companion.playMP3
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

class HighScorePage internal constructor() : JFrame(), ActionListener {
    private var returnButton: JButton? = null
    private var highScorePanel: JPanel? = null
    private var crownPic: JLabel? = null
    private var currentScoreHolder: JLabel? = null
    private var bestScoreHolder: JLabel? = null
    private var panelTitle: JLabel? = null
    private var currentScoreLabel: JLabel? = null
    private var bestScoreLabel: JLabel? = null

    // Declare the best_holder variable
    private var bestHolder: Int

    // Declare the score_holder variable
    private var scoreHolder: Int

    init {
        contentPane = highScorePanel
        if (GameWindow.currentScore > GameWindow.bestScore) {
            playMP3("audio/new_highscore.mp3")
            JOptionPane.showMessageDialog(
                    this,
                    "NEW HIGHSCORE",
                    "highscore",
                    JOptionPane.INFORMATION_MESSAGE
            )
        }
        bestHolder = GameWindow.new_bestScore
        scoreHolder = GameWindow.currentScore
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "High-Score"

        // Set the text for the score labels
        bestScoreHolder!!.text = bestHolder.toString()
        currentScoreHolder!!.text = scoreHolder.toString()
        returnButton!!.text = "Back"
        panelTitle!!.text = "HIGH-SCORE"
        bestScoreLabel!!.text = "BEST: "
        currentScoreLabel!!.text = "SCORE: "
        val crownPicFile = ImageIcon("img/crown_1.png")
        crownPic!!.icon = crownPicFile
        returnButton!!.addActionListener(this)
        pack()
        setSize(1300, 700)
        setLocationRelativeTo(null)
        isVisible = true
    }

    override fun actionPerformed(e: ActionEvent) {
        if (e.source === returnButton) {
            playMP3("audio/button_click.mp3")
            dispose()
            val menuWindow = MainMenuWindow()
            menuWindow.isVisible = true
            this.isVisible = false
        }
    }
}
