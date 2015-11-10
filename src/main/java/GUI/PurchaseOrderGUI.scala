package GUI

import Entities.PurchaseOrder
import DatabaseConnector.PurchaseOrderSQL
import scalafx.Includes._
import scalafx.scene.Node
import scalafx.geometry.Insets
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.{TableColumn, TableView}
import scalafx.scene.control.ComboBox
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafx.scene.layout.GridPane


/**
 * @author tdudley
 */

class PurchaseOrderGUI(employeeID : Int, stage : PrimaryStage) 
{
  var currentPurchaseOrderID : Int = 0
  
  var updatedStatus : String = ""
  
  val purchaseOrders : PurchaseOrderSQL =  new PurchaseOrderSQL()
  
  var orders = purchaseOrders.findAllPurchaseOrders()
  
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
      prefWidth = 120
    }
  
  val purchaseOrderStatusCol = new TableColumn[PurchaseOrder, String]
    {
      text = "Purchase Order Status"
      cellValueFactory = {_.value.orderStatus}
      prefWidth = 120
    }
  
  val employeeIDCol = new TableColumn[PurchaseOrder, Int]
    {
      text = "Employee ID"
      cellValueFactory = {_.value.employeeID}
      prefWidth = 120
    }
  
  def updateTable(table : TableView[PurchaseOrder]) : Unit = 
  {
    orders = purchaseOrders.findAllPurchaseOrders()
    
    table.items.update(orders)
  }
  
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
        case e : NullPointerException => e printStackTrace
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

    puchaseOrderSQL.claimPurchaseOrder(employeeID, purchaseOrderID)
        
    updateTable(table)
  }
    
  /**
   * @Param puchaseOrderID - the purchase order that will be updated
   * @Param updatedStatus - new status for the order
   * 
   * Updates the puchase order with the new status and then updates
   * the table with the new information
   * 
   */
  
  def updateOrder(purchaseOrderID : Int, updatedStatus : String) : Unit = 
  {
    val purchaseOrderSQL = new PurchaseOrderSQL
    
    purchaseOrderSQL.updateOrderStatus(purchaseOrderID, updatedStatus)
        
    updateTable(table)
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
          
         //val purchaseOrderLineGUI = new PurchaseOrderLineGUI(currentPurchaseOrderID, stage)
         
         //purchaseOrderLineGUI showPopUp
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
      
      //add(createClaimButton, 2, 1)
      //add(createShowOrderButton, 4, 1)
      //add(createComboBox, 5, 1)
      //add(createUpdateButton, 6, 1)
 
    }
  }  
}