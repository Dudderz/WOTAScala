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
class CustomerOrderGUI(employeeID : Int)
{
  
  var currentCustOrderID : Int = 0
  
  val customerOrders : CustomerOrderSQL = new CustomerOrderSQL()
  
  var orders = customerOrders.findAllCustomerOrders()
  
  val table = new TableView[CustomerOrder](orders)
  
  val orderIDCol = new TableColumn[CustomerOrder, Int]
    {
      text = "Customer Order Date"
      cellValueFactory = {_.value.customerOrderID}
      prefWidth = 150
    }
 val employeeIDCol = new TableColumn[CustomerOrder, Int]
    {
      text = "Employee ID"
      cellValueFactory = {_.value.employeeID}
      prefWidth = 120
    }
 val custOrderDate = new TableColumn[CustomerOrder, String]
    {
      text = "Date of Order"
      cellValueFactory = {_.value.customerOrderDate}
      prefWidth = 130
    }
 val custStatus = new TableColumn[CustomerOrder, String]
    {
      text = "Order Status"
      cellValueFactory = {_.value.customerOrderStatus}
      prefWidth = 120
    }

 
 def updateTable(table : TableView[CustomerOrder]) : Unit =
 {
    orders = customerOrders.findAllCustomerOrders()
     
    println(orders.length)
     
    table.items.update(orders)
 }

  /*
   * Creates a table with column names related to the customer order
   * Also pulls information from the database using the CustomerOrderSQL class
   * and then displays this within the table
   */
  
  def createCustomerOrderTable() : Node =
  {
    
    table.columns += (orderIDCol, employeeIDCol, custOrderDate, custStatus)
    
    table.onMouseClicked = handle
    {
      try
      {
        currentCustOrderID = table.getSelectionModel.selectedItemProperty.get.customerOrderID.value
        println(table.getSelectionModel.selectedItemProperty.get.customerOrderID.value)
        //table.items.get().removeAll(orders)
         //table.items.get().addAll(orders)
      }
      catch
      {
        case e : NullPointerException => e printStackTrace
      }
      
    }
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
  
  def claimOrder(employeeID : Int, customerOrderID : Int) : Unit = 
  {
    
    
    val custOrderSQL = new CustomerOrderSQL
    
    custOrderSQL claimCustomerOrder(employeeID, customerOrderID)
    val custOrder =custOrderSQL findByCustomerID(customerOrderID)
    
    updateTable(table)
    println(custOrder.employeeID)
  }
  
  def createUpdateButton() : Button = 
  {
    val button = new Button
    {
       text = ("Claim order") 
    
       onAction = (ae: ActionEvent) =>
       {
         claimOrder(employeeID, currentCustOrderID)

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