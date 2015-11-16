package com.qa.gui

import com.qa.databaseconnector.{CustomerOrderSQL, CustomerOrderLineSQL, ProductSQL}
import com.qa.entities.{CustomerOrder, CustomerOrderLine}
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.Node
import scalafx.scene.image.Image
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.control.{ComboBox, Button, TextField, TableView, TableColumn}
import scalafx.event.ActionEvent
import scalafx.collections.ObservableBuffer
import TableColumn._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Pos, Orientation, Insets}
import scalafx.scene.shape.{Rectangle, Circle}


/**
 * @author tdudley
 */
class CustomerOrderGUI(employeeID : Int, stage : PrimaryStage)
{ 
  
  var currentCustOrderID : Int = 0
  
  var updatedStatus : String = ""
   
  val customerOrders : CustomerOrderSQL = new CustomerOrderSQL()
  
  //var orders = customerOrders findAllCustomerOrders
  
  val table = new TableView[CustomerOrder](customerOrders findAllCustomerOrders)
  
  val orderIDCol = new TableColumn[CustomerOrder, Int]
    {
      text = "Customer Order ID"
      cellValueFactory = {_.value.customerOrderID}
      prefWidth = 150
    }
 	
  val employeeIDCol = new TableColumn[CustomerOrder, Int]
    {
      text = "Employee ID"
      cellValueFactory = {_.value.employeeID}
      prefWidth = 123
    }
  
 	val custOrderDate = new TableColumn[CustomerOrder, String]
    {
      text = "Date of Order"
      cellValueFactory = {_.value.customerOrderDate}
      prefWidth = 140
    }
  
 	val custStatus = new TableColumn[CustomerOrder, String]
    {
      text = "Order Status"
      cellValueFactory = {_.value.customerOrderStatus}
      prefWidth = 123
    }

 
 /*
  * Reloads the orders and applies them to the table
  */
 def updateTable(table : TableView[CustomerOrder], orders : ObservableBuffer[CustomerOrder]) : Unit =
 {     
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
      }
      catch
      {
        case e : Throwable => println
      }

      
    }

    table
  }
  
  /**
   * Returns a combo box of strings.
   * These are used to update the customers status
   */
  
  def createComboBox() : ComboBox[String] = 
  {
    val comboBoxInfo : ObservableBuffer[String] = ObservableBuffer[String]()
    
    comboBoxInfo += new String("Order Received")
    comboBoxInfo += new String("Processing")
    comboBoxInfo += new String("Out for delivery")
    comboBoxInfo += new String("Delivered")
        
    val comboBox = new ComboBox[String]
    {
      items = comboBoxInfo
      promptText = "Pick a status"
      
    }

    comboBox placeholder
    
    comboBox.onAction = (ae: ActionEvent) =>
    {
      updatedStatus = comboBox.value.value
    }
    
    comboBox
  }
  
    
   /**
   * @Param employeeID - the employeeID that will claim this order
   * @Param customerOrderID - the customerOrder that will be claimed
   * 
   * Allows the employee to claim the highlighted order
   * 
   */
  
  def claimOrder(employeeID : Int, customerOrderID : Int) : Unit = 
  {
    val custOrderSQL = new CustomerOrderSQL

    custOrderSQL claimCustomerOrder(employeeID, customerOrderID)
        
    updateTable(table, customerOrders.findAllCustomerOrders)

  }
  
  /**
   * @Param customerOrderID - the customer order that will be updated
   * @Param updatedStatus - new status for the order
   * 
   * Updates the customer order with the new status and then updates
   * the table with the new information
   * 
   */
  
  def updateOrder(customerOrderID : Int, updatedStatus : String) : Unit = 
  {
    val custOrderSQL = new CustomerOrderSQL
    
    custOrderSQL.updateOrderStatus(customerOrderID, updatedStatus)
    
    if(updatedStatus.equals("Processing"))
    {
       val productSQL = new ProductSQL
       val customerOrderLineSQL = new CustomerOrderLineSQL
       
       var customerOrderLines : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
       
       customerOrderLines = customerOrderLineSQL.findByCustomerID(customerOrderID)
       
       def forLoop(n : Int)
       {
         if(n <= 0)
         {
           productSQL.updateProductQuantity(customerOrderLines(n).productID.getValue.toInt, -(customerOrderLines(n).quantity.getValue.toInt))
         }
         else
         {
           productSQL.updateProductQuantity(customerOrderLines(n).productID.getValue.toInt, -(customerOrderLines(n).quantity.getValue.toInt))
           forLoop(n - 1)
         }
       }
    
       forLoop(customerOrderLines.length - 1)
     
    }  
        
    updateTable(table, customerOrders.findAllCustomerOrders)
  }
  
  /**
   * @Return Button 
   * creates a button that allows the current user to claim the order
   */
  
  def createClaimButton() : Button = 
  {
    val button = new Button
    {
       text = "Claim order"
    
       onAction = (ae: ActionEvent) =>
       {
         claimOrder(employeeID, currentCustOrderID)
       }
          
    }
    button
  }
  
  /**
   * @Return Button 
   * creates a button that updates the highlighted order's status
   */
  
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
  
  /**
   * @Return Button 
   * creates a button that shows the highlighted order details
   */
  
  def createShowOrderButton() : Button = 
  {
    val button = new Button 
    {
      text = "Show Order"
      
      onAction = (ae: ActionEvent) =>
      {
          
         val custOrderLineGUI = new CustomerOrderLineGUI(currentCustOrderID, stage)
         
         custOrderLineGUI showPopUp
      }
    }
    
    button
  }


  /**
   * @Return returns a rectangle containing the company logo
   */
  
   /*def createRect(): Rectangle = 
  {
     val image = new Image("file:src/main/java/GUI/logo.png")
     val rect = new Rectangle(0, 0, 50, 50)
     rect setFill(new ImagePattern(image))
     rect
  }*/
  
  /**
   * @Return GridPane - contains the buttons for the customer order gui
   * and sorts them in a grid
   */
  
   def createGridPane() : GridPane = 
  {
    new GridPane 
    {
      hgap = 10
      vgap = 10
      padding = Insets(20, 100, 10, 10)
       
      //add(createRect(), 0, 0)
      
      add(createClaimButton, 2, 1)
      add(createShowOrderButton, 4, 1)
      add(createComboBox, 5, 1)
      add(createUpdateButton, 6, 1)
     
   
    }
  }  

}