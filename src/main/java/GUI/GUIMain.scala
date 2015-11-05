package GUI

import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.Scene.sfxScene2jfx
import scalafx.scene.control.Label
import scalafx.scene.control.PasswordField
import scalafx.scene.layout.GridPane
import scalafx.stage.Stage.sfxStage2jfx
import scalafx.scene.control.TextField
import scalafx.geometry.Insets
import scalafx.scene.layout.HBox
import scalafx.scene.paint.LinearGradient
import scalafx.scene.effect.DropShadow
import scalafx.scene.text.Text
import scalafx.scene.paint.Stops
import scalafx.scene.paint.Color._
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import javafx.scene.shape.Rectangle
import javafx.scene.paint.ImagePattern
import scalafx.scene.control.Button
import scalafx.event.ActionEvent

import DatabaseConnector.LogInSQL

/**
 * @author tdudley
 * 
 * This class will start the GUI and display the log in screen.
 * TODO refactor the log in from this when other screens are available
 * 
 */
class GUIMain (stage : PrimaryStage)
{
  stage title = "Warehouse Order Tracking Application"
  stage width = 800
  stage height = 600
  
  
  
}
