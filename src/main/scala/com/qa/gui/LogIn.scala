package com.qa.gui

//Scalafx imports

import com.qa.databaseconnector.{LogInSQL, EmployeeSQL}
import com.qa.utilities.Encryption

import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.Scene.sfxScene2jfx
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.stage.Stage.sfxStage2jfx
import scalafx.geometry.Insets
import scalafx.scene.text.Text
import scalafx.scene.image.{Image, ImageView}
import scalafx.event.ActionEvent
import scalafx.scene.control.{PasswordField, TextField, Label, Button}
import scalafx.stage.Popup
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView

/**
 * @author tdudley
 */
class LogIn (stage : PrimaryStage)
{
  stage title = "Warehouse Order Tracking Application"
  stage width = 300
  stage height = 400   
  
  
  /**
   * Creates a log in button and has an onAction method that verifies the 
   * inputted data to allow a user to log in
   */
  
  def createLogInButton(usernameField : TextField, passwordField : PasswordField): Button = 
  {
    val button = new Button
    {
       text = ("Log In") 
    
       onAction = (ae: ActionEvent) =>
       {
         val encrypter : Encryption = new Encryption
                  
         val user : String = usernameField.text.getValue 
         val pass : String = encrypter hasher(passwordField.text getValue)
         
         val login = new LogInSQL(user, pass)
         
         val bool = login logIn()
         
         //val bool = login verifyLogIn()
         
         if(bool)
         {
           val employeeSQL = new EmployeeSQL()
           val employee = employeeSQL findByEmployeeUsername(user)
           
           val employeeID = employee employeeID
           
           val gui : GUIMain = new GUIMain(employeeID.value)
           
           gui.showGUI()
           
         }
         else
         {
           val alert: Alert = new Alert(AlertType.Warning);
           alert setTitle("Warning");
           alert setHeaderText(null);
           alert setContentText("Please enter a valid user and or password");

           alert showAndWait()
         }
       }
    
    }
    button
  }

 val username = new TextField()
    {
   
      promptText = "Username: "
    }

  
  val passwordField = new PasswordField
    {
    
      promptText = "Password: "
    }
  
    
  /**
   * Creates rectangle to hold the NB Gardens logo
   */
  def createLogo(): ImageView = 
  {
     val image = new Image("file:src/main/scala/com/qa/gui/logo.png", 125, 125, true, true)
     val imageView = new ImageView(image)
     imageView
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
      
        
        add(createLogo, 1, 1)
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