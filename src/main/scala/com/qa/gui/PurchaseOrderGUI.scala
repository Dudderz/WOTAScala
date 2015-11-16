package com.qa.gui

import com.qa.entities.{PurchaseOrder, PurchaseOrderLine, Product}
import com.qa.databaseconnector.{PurchaseOrderSQL, PurchaseOrderLineSQL, ProductSQL}
import scalafx.Includes._
import scalafx.scene.Node
import scalafx.geometry.Insets
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.{TableColumn, TableView, ComboBox, Button}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.layout.GridPane
import scalafx.scene.control.TextInputDialog


/**
 * @author tdudley
 * 
 * Class to create the purchase order gui. This will contain the 
 * relevant tables, labels and buttons to allow the user to 
 * 
 */

class PurchaseOrderGUI(employeeID : Int, stage : PrimaryStage) 
{
  var currentPurchaseOrderID : Int = 0
  
  var updatedStatus : String = ""
  
  val purchaseOrders : PurchaseOrderSQL =  new PurchaseOrderSQL()
  
  var orders = purchaseOrders findAllPurchaseOrders
  
  val table =  new TableView[PurchaseOrder](orders)

  val orderIDCol = new TableColumn[PurchaseOrder, Int]
    {
      text = "Purchase Order ID"
      cellValueFactory = {_.value.purchaseOrderID}
      prefWidth = 120
    }
  
  val orderDateCol = new TableColumn[PurchaseOrder, String]
    {
      text = "Purchase Order Date"
      cellValueFactory = {_.value.purchaseOrderDate}
      prefWidth = 160
    }
  
  val purchaseOrderStatusCol = new TableColumn[PurchaseOrder, String]
    {
      text = "Purchase Order Status"
      cellValueFactory = {_.value.orderStatus}
      prefWidth = 160
    }
  
  val employeeIDCol = new TableColumn[PurchaseOrder, Int]
    {
      text = "Employee ID"
      cellValueFactory = {_.value.employeeID}
      prefWidth = 95
    }
  
  /**
   * Gets the latest purchaseOrders from the database and then
   * updates the contents of the table 
   */
  
  def updateTable(table : TableView[PurchaseOrder], orders : ObservableBuffer[PurchaseOrder]) : Unit = 
  {
    //orders = purchaseOrders findAllPurchaseOrders 
    
    table.items update(orders)
  }
  
  /**
   * @RETURN Node
   * Creates the table that will contain the purchase orders
   * 
   */
  
  def createPurchaseOrderTable() : Node = 
  {
    table.columns += (orderIDCol, orderDateCol, purchaseOrderStatusCol, employeeIDCol)
    
    table.onMouseClicked = handle
    {
      try
      {
        currentPurchaseOrderID = table.getSelectionModel.selectedItemProperty.get.purchaseOrderID.value
      }
      catch
      {
        case e : NullPointerException => println
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
    comboBoxInfo += new String("Received")
        
    val comboBox = new ComboBox[String]
    {
      items = comboBoxInfo
      promptText = "Pick a status"
      
    }

    comboBox.placeholder
    
    comboBox.onAction = (ae: ActionEvent) =>
    {
      updatedStatus = comboBox.value.value
    }
    
    comboBox
  }
  
  /**
   * @Param employeeID - the employeeID that will claim this order
   * @Param purchaseOrderID - the purchaseOrder that will be claimed
   * 
   * Allows the employee to claim the highlighted order
   * 
   */
  
    def claimOrder(employeeID : Int, purchaseOrderID : Int) : Unit = 
  {
    val puchaseOrderSQL = new PurchaseOrderSQL

    puchaseOrderSQL claimPurchaseOrder(employeeID, purchaseOrderID)
        
    updateTable(table, purchaseOrders findAllPurchaseOrders)
  }
    
  /**
   * @Param puchaseOrderID - the purchase order that will be updated
   * @Param updatedStatus - new status for the order
   * 
   * Updates the purchase order with the new status and then updates
   * the table with the new information
   * 
   */
      
  def updateOrder(purchaseOrderID : Int, updatedStatus : String) : Unit = 
  {
    val purchaseOrderSQL = new PurchaseOrderSQL
    
    purchaseOrderSQL updateOrderStatus(purchaseOrderID, updatedStatus)
        
    updateTable(table, purchaseOrders findAllPurchaseOrders)
  }
  
  /**
   * @Param purchaseOrderID - this is the id of the purchase order that wll be used to find the 
   * relevant purchase order lines
   * Goes through the list of the purchase order lines assigned to the highlighted 
   * Purchase Order and then updates the quantity of the product within the database
   */
  def updateOrderQuantity(purchaseOrderID : Int) : Unit =
  {
    val productSQL = new ProductSQL
    val purchaseOrderLineSQL = new PurchaseOrderLineSQL
    val purchaseOrderSQL = new PurchaseOrderSQL
    
    var purchaseOrderLines : ObservableBuffer[PurchaseOrderLine] = ObservableBuffer[PurchaseOrderLine]()
    
    purchaseOrderLines = purchaseOrderLineSQL findByPurchaseOrderID(purchaseOrderID) 
    
    
    
    def forLoop(n : Int)
    {
      if(n <= 0)
      {
       val brokenItems = createBrokenItemsWindow
       productSQL.updateProductQuantity(purchaseOrderLines(n).productID getValue, purchaseOrderLines(n).quantity.getValue.toInt - brokenItems)
      }
      else
      {
       val brokenItems = createBrokenItemsWindow
       productSQL.updateProductQuantity(purchaseOrderLines(n).productID.getValue.toInt, purchaseOrderLines(n).quantity.getValue.toInt - brokenItems)
       forLoop(n - 1)
      }
    }
    
    forLoop(purchaseOrderLines.length - 1)
    
    purchaseOrderSQL updateOrderStatus(purchaseOrderID, "Received")
    
      
    updateTable(table, purchaseOrders findAllPurchaseOrders)
  }
  
  def createBrokenItemsWindow() : Int =
  {
    var int : Int = 0
    
    val dialog = new TextInputDialog(defaultValue = "0")
    {
      initOwner(stage)
      title = "Broken inventory"
      contentText = "Please enter the no. of broken products received: "
    }
    
    val result = dialog showAndWait
    
    result match{
      case Some(noOfBrokenItems) =>
        noOfBrokenItems.toInt
      case None  => 
        println("Dialogue box closed")  
        0       
    }
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
         claimOrder(employeeID, currentPurchaseOrderID)
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
          updateOrder(currentPurchaseOrderID, updatedStatus)
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
          
         val purchaseOrderLineGUI = new PurchaseOrderLineGUI(currentPurchaseOrderID, stage)
         
         purchaseOrderLineGUI showPopUp
      }
    }
    
    button
  }
  
  /**
   * @Return Button 
   * creates a button that receives the highlighted button.
   * This calls updateOrderQuantity
   */
  
  def createReceiveOrderButton() : Button =
  {
     val button = new Button
     {
       text = "Receive Order"
       
       onAction = (ae: ActionEvent) =>
       {
         updateOrderQuantity(currentPurchaseOrderID)
       }
     }
     
     button 
  }
  
  /**
   * Creates a pop up window that allows the creation of 
   * new pop ups
   */
  def createPurchaseOrderButton() : Button = 
  {
    val button = new Button
    {
      text = "Add Purchase Order"
      
      onAction = (ae: ActionEvent) =>
      {
          val purchaseOrderForm = new PurchaseOrderForm(employeeID, stage)
          purchaseOrderForm showPopUp
      }
    }
    
    button
  }
  
  /**
   * Refreshes the purchase order table when clicked
   * Calls the updateTable method
   */
  def createRefreshButton () : Button =
  {
    val button = new Button
    {
      text = "Refresh Table"
      
      onAction = (ae: ActionEvent) =>
      {
        updateTable(table, purchaseOrders findAllPurchaseOrders)    
      }
    }
    
    button
  }

  
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

      add(createPurchaseOrderButton, 2, 1)
      add(createComboBox, 2, 2)
      add(createShowOrderButton, 3, 1)
      add(createUpdateButton, 3, 2)
      add(createReceiveOrderButton, 4, 1)
      add(createRefreshButton, 4, 2)
      add(createClaimButton, 5, 1)
    }
  }  
}