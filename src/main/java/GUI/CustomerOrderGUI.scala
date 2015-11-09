package GUI

import DatabaseConnector.CustomerOrderSQL
import DatabaseConnector.CustomerOrderLineSQL
import Entities.CustomerOrder
import Entities.CustomerOrderLine
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
import scalafx.stage.Popup
import scalafx.scene.layout.StackPane
import scalafx.scene.layout.BorderPane
import javafx.event.EventHandler
import javafx.scene.paint.ImagePattern
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import TableColumn._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Pos, Orientation, Insets}
import scalafx.scene.shape.{Rectangle, Circle}
import scalafx.stage.Popup
import scalafx.collections.ObservableBuffer



/**
 * @author tdudley
 */
class CustomerOrderGUI(employeeID : Int, stage : PrimaryStage)
{ 
  /*
   * Below are the variables and values used within this class
   * TODO refactor the vars and vals so that there are no
   * global variables
   */
  
  var currentCustOrderID : Int = 0
  
  var updatedStatus : String = ""
   
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

 
 /*
  * Reloads the orders and applies them to the table
  */
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

      }
      catch
      {
        case e : NullPointerException => e printStackTrace
      }
      
    }
    table
  }
  
  def createComboBox() : ComboBox[String] = 
  {
    val comboBoxInfo : ObservableBuffer[String] = ObservableBuffer[String]()
    
    comboBoxInfo += new String("Dispatched")
    comboBoxInfo += new String("")
        
    val comboBox = new ComboBox[String]
    {
      items = comboBoxInfo
    }

    comboBox.onAction = (ae: ActionEvent) =>
    {
      updatedStatus = comboBox.value.value
      //println(comboBox.value.value)
    }
    
    comboBox
  }
  
  def claimOrder(employeeID : Int, customerOrderID : Int) : Unit = 
  {
    val custOrderSQL = new CustomerOrderSQL

    custOrderSQL.claimCustomerOrder(employeeID, customerOrderID)
        
    updateTable(table)

  }
  
  def updateOrder(customerOrderID : Int, updatedStatus : String) : Unit = 
  {
    val custOrderSQL = new CustomerOrderSQL
    
    custOrderSQL.updateOrderStatus(customerOrderID, updatedStatus)
        
    updateTable(table)
  }
  
  def createClaimButton() : Button = 
  {
    val button = new Button
    {
       text = "Claim order"
    
       onAction = (ae: ActionEvent) =>
       {
         claimOrder(employeeID, currentCustOrderID)
         val popup = createAlertPopup("Hello")
         popup.show(stage, 200, 200)
         
         comboBoxOfCustomerOrderLines(1)
         
       }
          
    }
    button
  }
  
  def createUpdateButton() : Button = 
  {
    val button = new Button
    {
      text = "Update"
      
      onAction = (ae: ActionEvent) =>
      {
          updateOrder(currentCustOrderID, updatedStatus)
      }
    }
    
    button
  }
  
  def comboBoxOfCustomerOrderLines(customerOrderID : Int) : ComboBox[String] = 
  {
    val comboBoxInfo : ObservableBuffer[String] = ObservableBuffer[String]()
    
    val customerOrderLineSQL : CustomerOrderLineSQL = new CustomerOrderLineSQL()
  
    var orderLines : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
    orderLines = customerOrderLineSQL.findByCustomerID(customerOrderID)
    
    val i = orderLines.delegate.length
    
    println(orderLines.delegate.length)
      
    def loop(i : Int) : Unit = 
    {
      if(i > 0)
      {
        comboBoxInfo += new String(orderLines.delegate.get(i - 1).customerOrderLineID.value+"")
        loop(i - 1)
      }
        
    }

     loop(i)
    
    
    val comboBox = new ComboBox[String]
    {
      items = comboBoxInfo
    }
    
    comboBox
  }
  
  def createAlertPopup(popupText: String) = new Popup 
  {
   inner =>
   content.add(new StackPane 
     {
      children = List(
         new Rectangle 
         {
           width = 300
           height = 200
           arcWidth = 20
           arcHeight = 20
           strokeWidth = 2
         },
         new BorderPane 
         {
           center = new GridPane
           {
             add(comboBoxOfCustomerOrderLines(1), 1, 1)
           }
           bottom = new Button("Close") 
           {
             onAction = 
             {
               e: ActionEvent => inner.hide
             }
             alignmentInParent = Pos.CENTER
             margin = Insets(10, 0, 10, 0)
           }
         }
     )
   }.delegate
   )
  }

  
   /*def createRect(): Rectangle = 
  {
     val image = new Image("file:src/main/java/GUI/logo.png")
//     val rect = new Rectangle(0, 0, 50, 50)
     rect setFill(new ImagePattern(image))
     rect
  }*/
  
  
   def createGridPane() : GridPane = 
  {
    new GridPane {
        hgap = 10
        vgap = 10
        padding = Insets(20, 100, 10, 10)
      
        
//        /add(createRect(), 0, 0)
        add(createCustomerOrderTable, 1, 1)
        add(createClaimButton, 1, 2)
        add(createComboBox, 2, 2)
        add(createUpdateButton, 2, 3)
        
        
       
        }
  }  
}