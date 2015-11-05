import DatabaseConnector.DBConnector
import GUI.GUIMain
import GUI.LogIn
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.JFXApp

/**
 * @author tdudley
 *
 * This starts the database connection and initialises the gui class
 * 
 */

object Main extends JFXApp{
 
    val db : DBConnector = new DBConnector()
    
    stage = new PrimaryStage
    
    
    val logIn : LogIn = new LogIn(stage)
    
    db connect()   
    
    logIn showLogin()
  
}