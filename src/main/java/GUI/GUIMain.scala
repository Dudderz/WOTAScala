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
  stage width = 300
  stage height = 400   
  
  
  def createLogInButton(): Button = 
  {
    val button = new Button
    {
       text = ("Log In") 
    
       onAction = (ae: ActionEvent) =>
       {
         val login = new LogInSQL("alstock", "password")
         
         login logIn()
         
         val bool = login verifyLogIn()
         
         if(bool)
         {
           println("Success!")
         }
       }
    
    }
    button
  }
 
  def createUsernameField() : TextField = 
  {
    val username = new TextField()
    {
      promptText = "Username: "
    }
    username
  }
  
  def createPasswordField() : PasswordField = 
  {
    val passwordField = new PasswordField
    {
      promptText = "Password: "
    }
    passwordField
  }
    
  def createRect(): Rectangle = 
  {
     val image = new Image("file:src/main/java/GUI/logo.png")
     val rect = new Rectangle(0, 0, 125, 125)
     rect setFill(new ImagePattern(image))
     rect
  }
  
  def createGridPane() : GridPane = 
  {
    new GridPane {
        hgap = 10
        vgap = 10
        padding = Insets(20, 100, 10, 10)
      
        
        add(createRect, 1, 1)
        add(new Label("Please Log In: "), 1, 3)
        add(new Label("Username: "), 0, 4)
        add(createUsernameField(), 1, 4)
        add(new Label("Password: "), 0, 5)
        add(createPasswordField(), 1, 5)
        add(createLogInButton(), 1, 7)
            
        }
  }
  
  def createScene() : Scene =
  {
    val scene = new Scene
    {
        
      content = new HBox
      {   
        children = Seq(
        
           createGridPane()      
        )
      }
    }
    
    scene
  }

  def showLogin(): Unit = 
  {
    stage setScene(createScene())
  }
  
}
