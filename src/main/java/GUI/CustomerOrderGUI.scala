package GUI

import scalafx.Includes._
import Entities.CustomerOrder
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import scalafx.scene.control.TableView
import scalafx.scene.Node
import scalafx.scene.control.TableColumn
import TableColumn._
import javafx.scene.shape.Rectangle
import scalafx.scene.image.Image
import javafx.scene.paint.ImagePattern

/**
 * @author tdudley
 */
class CustomerOrderGUI 
{
  val custOrder1 : CustomerOrder = new CustomerOrder(1, 1, "date", "closed")
  
  def createCustomerOrderTable() : Node =
  {
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
    
    val table = new TableView[CustomerOrder]()
    {
      columns += (orderIDCol, employeeIDCol, custOrderDate, custStatus)
    }
    
    table
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
       
        }
  }
  
  
}