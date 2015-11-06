package GUI

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.Scene.sfxScene2jfx
import scalafx.stage.Stage.sfxStage2jfx
import scalafx.scene.layout.HBox
import scalafx.scene.Node

import scalafx.scene.control.TableView
import scalafx.scene.control.TableColumn

import DatabaseConnector.CustomerOrderLineSQL

import Entities.CustomerOrderLine


/**
 * @author tdudley
 */
class CustomerOrderLineGUI extends JFXApp
{
  /*def createCustomerOrderLineTable() : Node = 
  {
    val customerOrders : CustomerOrderLineSQL = new CustomerOrderLineSQL()
    
    val orders = customerOrders.findAllCustomerOrderLines()
    
    val orderIDCol = new TableColumn[CustomerOrderLine, Int]
    {
      text = "Customer Order Date"
      cellValueFactory = {_.value.customerOrderID}
      prefWidth = 150
    }
    val employeeIDCol = new TableColumn[CustomerOrderLine, Int]
    {
      text = "Employee ID"
      cellValueFactory = {_.value.employeeID}
      prefWidth = 120
    }
    val custOrderDate = new TableColumn[CustomerOrderLine, String]
    {
      text = "Date of Order"
      cellValueFactory = {_.value.customerOrderDate}
      prefWidth = 130
    }
    val custStatus = new TableColumn[CustomerOrderLine, String]
    {
      text = "Order Status"
      cellValueFactory = {_.value.customerOrderStatus}
      prefWidth = 120
    }
        
    val table = new TableView[CustomerOrder](orders)
    {
      columns += (orderIDCol, employeeIDCol, custOrderDate, custStatus)
    }
        
    table.onMouseClicked = handle
    {
      println(table.getSelectionModel.selectedItemProperty.get.customerOrderID.value)
    }

    table
  }
  
  def createScene() : Scene = 
  {
    val scene : Scene = new Scene
    {
      content = new HBox
      {
        children = Seq(
            
            createCustomerOrderLineTable()
            
        )
      }
    }
    
    scene 
  }
  
  def createStage()
  {
    stage = new PrimaryStage()
    
    stage setScene(createScene())
    
    stage show()  
  }
  
  */
}