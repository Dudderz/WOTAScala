import DatabaseConnector.DBConnector
import GUI.GUIMain
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
    
    val gui : GUIMain = new GUIMain(stage)
    
    db connect()   
    
    gui showLogin()
  
}