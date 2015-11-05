package GUI

import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.Scene.sfxScene2jfx
import scalafx.scene.control.Label
import javafx.scene.control.PasswordField
import scalafx.scene.layout.GridPane
import scalafx.stage.Stage.sfxStage2jfx
import javafx.scene.control.TextField
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
 */
class LogIn (stage : PrimaryStage)
{
  stage title = "Warehouse Order Tracking Application"
  stage width = 300
  stage height = 400   
  
  
  /**
   * Creates a log in button and has an onAction method that verfies the 
   * inputted data to allow a user to log in
   */
  
  def createLogInButton(usernameField : TextField, passwordField : PasswordField): Button = 
  {
    val button = new Button
    {
       text = ("Log In") 
    
       onAction = (ae: ActionEvent) =>
       {
         val user : String = usernameField getText()
         val pass : String = passwordField getText()
         
         val login = new LogInSQL(user, pass)
         
         login logIn()
         
         val bool = login verifyLogIn()
         
         if(bool)
         {
           println("Success!")
           val gui : GUIMain = new GUIMain(stage)
           
         }
       }
    
    }
    button
  }
 
 val username = new TextField()
    {
   
      promptTextProperty() = "Username: "
    }

  
  val passwordField = new PasswordField
    {
      promptTextProperty() = "Password: "
    }
  
    
  /**
   * Creates rectangle to hold the NB Gardens logo
   */
  def createRect(): Rectangle = 
  {
     val image = new Image("file:src/main/java/GUI/logo.png")
     val rect = new Rectangle(0, 0, 125, 125)
     rect setFill(new ImagePattern(image))
     rect
  }
  
  /**
   * Grid pane that holds all of the labels, text fields and the logo 
   * for the log in application
   * 
   */
  def createGridPane() : GridPane = 
  {
    new GridPane {
        hgap = 10
        vgap = 10
        padding = Insets(20, 100, 10, 10)
      
        
        add(createRect, 1, 1)
        add(new Label("Please Log In: "), 1, 3)
        add(new Label("Username: "), 0, 4)
        add(username, 1, 4)
        add(new Label("Password: "), 0, 5)
        add(passwordField, 1, 5)
        add(createLogInButton(username, passwordField), 1, 7)
            
        }
  }
  
  /*
   * Creates the scene to hold the gridpane
   */
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