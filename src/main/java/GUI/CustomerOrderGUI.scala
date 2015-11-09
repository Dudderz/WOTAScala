package GUI

import DatabaseConnector.CustomerOrderSQL
import Entities.CustomerOrder
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.Node
import scalafx.scene.image.Image
import scalafx.scene.control.Button
import scalafx.scene.layout.GridPane
import scalafx.scene.control.ComboBox
import scalafx.scene.control.TextField
import scalafx.scene.control.TableView
import scalafx.scene.control.TableColumn
import scalafx.scene.control.PasswordField
import scalafx.event.ActionEvent
import scalafx.collections.ObservableBuffer

import javafx.event.EventHandler
import javafx.scene.paint.ImagePattern
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.shape.Rectangle
import TableColumn._


/**
 * @author tdudley
 */
class CustomerOrderGUI 
{
  
  /*
   * Creates a table with column names related to the customer order
   * Also pulls information from the database using the CustomerOrderSQL class
   * and then displays this within the table
   */
  
  def createCustomerOrderTable() : Node =
  {
    val customerOrders : CustomerOrderSQL = new CustomerOrderSQL()
    
    val orders = customerOrders.findAllCustomerOrders()
   
        
    val table = new TableView[CustomerOrder](orders)
    {
      columns ++= List(
       
        new TableColumn[CustomerOrder, Int]
        {
          text = "Customer Order Date"
          cellValueFactory = {_.value.customerOrderID}
          prefWidth = 150
        },
        
       new TableColumn[CustomerOrder, Int]
       {
          text = "Employee ID"
          cellValueFactory = {_.value.employeeID}
          prefWidth = 120
       },
       
       new TableColumn[CustomerOrder, String]
       {
          text = "Date of Order"
          cellValueFactory = {_.value.customerOrderDate}
          prefWidth = 130
       },
       
       new TableColumn[CustomerOrder, String]
       {
          text = "Order Status"
          cellValueFactory = {_.value.customerOrderStatus}
          prefWidth = 120
       }    
      
      )
    }
        
    table.onMouseClicked = handle
    {
      try
      {
        println(table.getSelectionModel.selectedItemProperty.get.customerOrderID.value)
      }
      catch
      {
        case e : NullPointerException => e printStackTrace
      }
      
    }
    /*table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler[MouseEvent]()
        {
          def handle(event : MouseEvent)
          {
            println(table.getSelectionModel.selectedItemProperty.get.customerOrderID.value)
            
          }
        })*/

    table
  }
  
  /*def createComboBox() : ComboBox = 
  {
    val comboBoxInfo : ObservableBuffer[String] = ObservableBuffer[String]()
    
    comboBoxInfo += new String("Dispatched")
    
    
    val comboBox = new ComboBox 
    {
      
    }
    
    comboBox
  }*/
  
  def claimOrder() : Unit = 
  {
    
  }
  
  def createUpdateButton() : Button = 
  {
    val button = new Button
    {
       text = ("Update") 
    
       onAction = (ae: ActionEvent) =>
       {
                  
         //val user : String = usernameField.text getValue 
         //val pass : String = passwordField.text getValue
       }
          
    }
    button
  }
  
  def showCustomerOrderInfo() : Unit = 
  {
    
  }
  
   def createRect(): Rectangle = 
  {
     val image = new Image("file:src/main/java/GUI/logo.png")
     val rect = new Rectangle(0, 0, 50, 50)
     rect setFill(new ImagePattern(image))
     rect
  }
  
  
   def createGridPane() : GridPane = 
  {
    new GridPane {
        hgap = 10
        vgap = 10
        padding = Insets(20, 100, 10, 10)
      
        
        add(createRect(), 0, 0)
        add(createCustomerOrderTable, 1, 1)
        add(createUpdateButton, 1, 2)
       
        }
  }  
}