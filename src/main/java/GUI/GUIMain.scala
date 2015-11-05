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
import scalafx.scene.control.TableColumn
import TableColumn._
import Entities.CustomerOrder
import scalafx.scene.control.TableView
import scalafx.scene.Node

import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.JFXApp

/**
 * @author tdudley
 * 
 * This class will start the GUI 
 * 
 * 
 */
class GUIMain extends JFXApp()
{

  
  val custOrder1 : CustomerOrder = new CustomerOrder(1, 1, "date", "closed")
  
  def createCustomerOrderTable() : Node =
  {
    val orderIDCol = new TableColumn[CustomerOrder, Int]
    {
      text = "Customer Order Date"
      cellValueFactory = {_.value.customerOrderID}
      prefWidth = 100
    }
    val employeeIDCol = new TableColumn[CustomerOrder, Int]
    {
      text = "Employee ID"
      cellValueFactory = {_.value.employeeID}
    }
    val custOrderDate = new TableColumn[CustomerOrder, String]
    {
      text = "Date of Order"
      cellValueFactory = {_.value.customerOrderDate}
    }
    val custStatus = new TableColumn[CustomerOrder, String]
    {
      text = "Order Status"
      cellValueFactory = {_.value.customerOrderStatus}
    }
    
    val table = new TableView[CustomerOrder]()
    {
      columns += (orderIDCol, employeeIDCol, custOrderDate, custStatus)
    }
    
    table
  }
  
   def createGridPane() : GridPane = 
  {
    new GridPane {
        hgap = 10
        vgap = 10
        padding = Insets(20, 100, 10, 10)
      
        
        add(createCustomerOrderTable, 1, 1)
       
        }
  }
  
  /*
   * Creates the scene to hold the gridpane
   */
  def createScene() : Scene =
  {
    val scene2 = new Scene
    {
        
      content = new HBox
      {   
        children = Seq(
        
           createGridPane()      
        )
      }
    }
    
    scene2
  }

  def showLogin(): Unit = 
  {
    
    stage = new PrimaryStage()
  
    stage title = "Warehouse Order Tracking Application"
    stage width = 800
    stage height = 600
    
    stage setScene(createScene())
    stage.show()
  }
  
}
