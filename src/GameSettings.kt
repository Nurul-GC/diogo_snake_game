import STORAGE.get_game_speeds
import STORAGE.get_snake_colors
import STORAGE.get_world_colors
import STORAGE.saveSnakeSkinToFile
import STORAGE.save_speed_to_file
import STORAGE.save_world_color_to_file
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

class GameSettings : JFrame(), ActionListener {
    private var backButton: JButton? = null
    private var resetButton: JButton? = null
    private var saveButton: JButton? = null
    private var mainPanel: JPanel? = null
    var skinSettingsPanel: JPanel? = null
    var worldSettingsPanel: JPanel? = null
    var difficultySettingsPanel: JPanel? = null
    private var easyOption: JRadioButton? = null
    private var mediumOption: JRadioButton? = null
    private var hardOption: JRadioButton? = null
    private var difficultyDefault: JRadioButton? = null
    private var yellowOption: JRadioButton? = null
    private var blueOption: JRadioButton? = null
    private var redOption: JRadioButton? = null
    private var colourDefault: JRadioButton? = null
    private var desertOption: JRadioButton? = null
    private var waterOption: JRadioButton? = null
    private var grassOption: JRadioButton? = null
    private var worldDefault: JRadioButton? = null
    private var colorGroup: ButtonGroup? = null
    private var worldGroup: ButtonGroup? = null
    private var difficultyGroup: ButtonGroup? = null
    private var newSkinID = 0
    private var newWorldID = 0
    private var newSpeedID = 0

    init {
        title = "Game Settings"
        preferredSize = Dimension(1300, 700)
        defaultCloseOperation = EXIT_ON_CLOSE
        pack()
        contentPane = mainPanel
        setLocationRelativeTo(null)
        initializeButtonGroups()
        addListenersToButtons()
        setDefaultSelections()
        colorGroup!!.add(redOption)
        colorGroup!!.add(yellowOption)
        colorGroup!!.add(blueOption)
        colorGroup!!.add(colourDefault)
        worldGroup!!.add(worldDefault)
        worldGroup!!.add(desertOption)
        worldGroup!!.add(waterOption)
        worldGroup!!.add(grassOption)
        difficultyGroup!!.add(easyOption)
        difficultyGroup!!.add(mediumOption)
        difficultyGroup!!.add(hardOption)
        difficultyGroup!!.add(difficultyDefault)
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun initializeButtonGroups() {
        colorGroup = ButtonGroup()
        worldGroup = ButtonGroup()
        difficultyGroup = ButtonGroup()
    }

    private fun addListenersToButtons() {
        backButton!!.addActionListener(this)
        resetButton!!.addActionListener(this)
        saveButton!!.addActionListener(this)
    }

    private fun setDefaultSelections() {
        colourDefault!!.isSelected = true
        worldDefault!!.isSelected = true
        difficultyDefault!!.isSelected = true
    }

    //options-------------------------------------------------------------------------
    override fun actionPerformed(e: ActionEvent) {
        // snake skin color values
        if (e.source === redOption) {
            newSkinID = 0
            newSkinToBeSaved = get_snake_colors(newSkinID)
        } else if (e.source === blueOption) {
            newSkinID = 1
            newSkinToBeSaved = get_snake_colors(newSkinID)
        } else if (e.source === colourDefault) {
            newSkinID = 2
            newSkinToBeSaved = get_snake_colors(newSkinID)
        } else if (e.source === yellowOption) {
            newSkinID = 3
            newSkinToBeSaved = get_snake_colors(newSkinID)
        }

        // world color values
        if (e.source === worldDefault) {
            newWorldID = 3
            newWorldToBeSaved = get_world_colors(newWorldID)
        } else if (e.source === desertOption) {
            newWorldID = 2
            newWorldToBeSaved = get_world_colors(newWorldID)
        } else if (e.source === waterOption) {
            newWorldID = 1
            newWorldToBeSaved = get_world_colors(newWorldID)
        } else if (e.source === grassOption) {
            newWorldID = 0
            newWorldToBeSaved = get_world_colors(newWorldID)
        }

        // difficulty values
        if (e.source === easyOption) {
            newSpeedID = 2
            newSpeedToBeSaved = get_game_speeds(newSpeedID)
        } else if (e.source === mediumOption) {
            newSpeedID = 1
            newSpeedToBeSaved = get_game_speeds(newSpeedID)
        } else if (e.source === hardOption) {
            newSpeedID = 0
            newSpeedToBeSaved = get_game_speeds(newSpeedID)
        } else if (e.source === difficultyDefault) {
            newSpeedID = 2
            newSpeedToBeSaved = get_game_speeds(newSpeedID)
        }

//roles for buttons----------------------------------------------------------------
        if (e.source === resetButton) {
            MainMenuWindow.playMP3("audio/short-success-sound-glockenspiel-treasure-video-game-6346.mp3")
            resetInfo()
            JOptionPane.showMessageDialog(this, "Successfully Reset", "Success", JOptionPane.INFORMATION_MESSAGE)
        } else if (e.source === saveButton) {
            // call the updaters from the STORAGE class to save the choices
            save_speed_to_file(newSpeedToBeSaved)
            saveSnakeSkinToFile(newSkinToBeSaved)
            save_world_color_to_file(newWorldToBeSaved)
            MainMenuWindow.playMP3("audio/updated_success.mp3")
            JOptionPane.showMessageDialog(this, "Saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE)
        } else if (e.source === backButton) {
            closeWindow()
        }
    }

    private fun resetInfo() {
        difficultyDefault!!.isSelected = true
        colourDefault!!.isSelected = true
        worldDefault!!.isSelected = true

        // default skin
        val defaultSkin = Color(0, 199, 63)

        // default world
        val defaultWorld = Color(43, 45, 47)

        // default speed
        val defaultSpeed = 75

        // saves the defaults
        saveSnakeSkinToFile(defaultSkin)
        save_speed_to_file(defaultSpeed)
        save_world_color_to_file(defaultWorld)
    }

    private fun closeWindow() {
        dispose()
        val mainMenuWindow = MainMenuWindow()
        MainMenuWindow.playMP3("audio/button_click.mp3")
        mainMenuWindow.isVisible = true
        this.isVisible = false
    }

    companion object {
        var newSkinToBeSaved = Color(0, 199, 63)
        var newWorldToBeSaved = Color(43, 45, 47)
        var newSpeedToBeSaved = 75
    }
}