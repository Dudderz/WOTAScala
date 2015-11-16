import com.qa.databaseconnector.DBConnector
import com.qa.gui.GUIMain
import com.qa.gui.LogIn
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.JFXApp

/**
 * @author tdudley
 *
 * This initialises the log in class
 * and then calls showLogin to show the newly created window
 * 
 */

object Main extends JFXApp{
 
    stage = new PrimaryStage
    
    val logIn : LogIn = new LogIn(stage)
   
    logIn showLogin()
  
}