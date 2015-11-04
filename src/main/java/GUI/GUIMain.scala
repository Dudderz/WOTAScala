package GUI

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

/**
 * @author tdudley
 */
class GUIMain (stage : PrimaryStage)
{

  stage title = "Warehouse Order Tracking Application"
  stage width = 800
  stage height = 600   
 
  val username = new TextField()
  {
    promptText = "Username: "
  }
  
  val passwordField = new PasswordField
  {
    promptText = "Password: "
  }
     
  val scene = new Scene
  {
        
    content = new HBox
    {   
      children = Seq(
      new GridPane {
                  hgap = 10
      vgap = 10
      padding = Insets(20, 100, 10, 10)
      
      add(new Label("Username: "), 0, 0)
      add(username, 1, 0)
      add(new Label("Password: "), 0, 1)
      add(passwordField, 1, 1)
            
          },
          
      new Text {
        text = "Scala"
        style = "-fx-font: italic bold 100pt sans-serif"
        fill = new LinearGradient(
          endX = 0,
          stops = Stops(White, DarkGray)
        )
             
         effect = new DropShadow{
         color = DarkGray
         radius = 15
         spread - 0.25
        }
      },
               
      new Text{
        text = "FX"
        style = "-fx-font: italic bold 100pt sans-serif"
        fill = new LinearGradient(
          endX = 0,
          stops = Stops(White, DarkGray)
        )
              
          effect = new DropShadow{
          color = DarkGray
          radius = 15
          spread = 0.25
         }
        }
       )
     }
   }
  
  def showLogin(): Unit = 
  {
    val grid = new GridPane()
    {
      hgap = 10
      vgap = 10
      padding = Insets(20, 100, 10, 10)
      
      add(new Label("Username: "), 0, 0)
      add(username, 1, 0)
      add(new Label("Password: "), 0, 1)
      add(passwordField, 1, 1)
    }
  }
  
  stage setScene(scene)
}
