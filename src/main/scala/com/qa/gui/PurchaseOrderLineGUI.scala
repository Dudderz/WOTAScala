package com.qa.gui

import com.qa.databaseconnector.{PurchaseOrderLineSQL, ProductSQL}
import com.qa.entities.{PurchaseOrderLine, Product}
import scalafx.Includes._
import scalafx.scene.image.Image
import scalafx.scene.control.Button
import scalafx.scene.layout.GridPane
import scalafx.scene.control.{Label, ComboBox}
import scalafx.event.{ActionEvent, EventHandler}
import scalafx.collections.ObservableBuffer
import scalafx.stage.Popup
import scalafx.scene.layout.{StackPane, BorderPane}
import javafx.scene.paint.ImagePattern
import scalafx.geometry.{Pos, Orientation, Insets}
import scalafx.scene.shape.{Rectangle}
import scalafx.stage.Popup
import scalafx.collections.ObservableBuffer
import scalafx.scene.paint.Color
import scalafx.application.JFXApp.PrimaryStage

/**
 * @author tdudley
 */

class PurchaseOrderLineGUI(purchaseOrderID : Int, stage : PrimaryStage) 
{  
  var purchaseOrderLineID : Int = 0
  val productNameLabel : Label = new Label("")
  val productIDLabel : Label = new Label("")
  val productDescriptionLabel : Label = new Label("")
  val productQuantityLabel : Label = new Label("")
  
    /**
   * @return : ComboBox[String]
   * 
   * Creates a combo box of the customer order lines within the chosen 
   * purchase order. It then updates the relevant labels and information on
   * the pop up window
   */
  
  def comboBoxOfPurchaseOrderLines(purchaseOrderID : Int) : ComboBox[String] = 
  {
    val comboBoxInfo : ObservableBuffer[String] = ObservableBuffer[String]()
    
    val purchaseOrderLineSQL : PurchaseOrderLineSQL = new PurchaseOrderLineSQL()
  
    val orderLines = purchaseOrderLineSQL.findByPurchaseOrderID(purchaseOrderID)
    
    val i = orderLines.delegate.length
          
    def loop(i : Int) : Unit = 
    {
      if(i > 0)
      {
        comboBoxInfo += new String(orderLines.delegate.get(i - 1).purchaseOrderLineID.getValue.toInt+"")
        loop(i - 1)
      }
        
    }

     loop(i)
    
    
    val comboBox = new ComboBox[String]
    {       
      items = comboBoxInfo
    }
    
    comboBox.onAction = (ae: ActionEvent) =>
    {
     purchaseOrderLineID =comboBox.value.value.toInt
    
      updateLabelText(productNameLabel, addProductNameLabel)
      updateLabelText(productIDLabel, addProductIDLabel)
      updateLabelText(productDescriptionLabel, addProductDescriptionLabel)
      updateLabelText(productQuantityLabel, addProductQuantityLabel)
    }
    
     
    comboBox
  }
  
    /**
   * @param : label
   * @param : updatedText
   * 
   * this method takes in a Label and a String and then updates the 
   * text on the label with the given updated text
   * 
   */
  
  def updateLabelText(label : Label, updatedText : String) : Unit = 
  {
    label.text.value_=(updatedText)
  }
  
    /**
   * @return : String 
   * 
   * returns the name of the product within the given purchase
   * order line
   */
  
  def addProductNameLabel() : String = 
  {
    val purchaseOrderLineSQL : PurchaseOrderLineSQL = new PurchaseOrderLineSQL()
  
    val orderLine = purchaseOrderLineSQL.findByPurchaseOrderLineID(purchaseOrderLineID)
    
    val productSQL : ProductSQL = new ProductSQL()
    
    val product = productSQL.findByProductID(orderLine.productID.get)
    
    product.productName.value
  }
  
  /**
   * @return : String
   * 
   * gets the product id for the current purchase order line and 
   * returns this a string
   */
  
  def addProductIDLabel() : String = 
  {
    val purchaseOrderLineSQL : PurchaseOrderLineSQL = new PurchaseOrderLineSQL()
  
    val orderLine = purchaseOrderLineSQL.findByPurchaseOrderLineID(purchaseOrderLineID)
    
    val productSQL : ProductSQL = new ProductSQL()
    
    val product = productSQL.findByProductID(orderLine.productID.value)
    
    product.productID.value+""
  }
    
  /**
   * @return : String 
   * 
   * gets the product description for the current purchase order line and 
   * returns this a string
   */
  
  def addProductDescriptionLabel() : String = 
  {
    val purchaseOrderLineSQL : PurchaseOrderLineSQL = new PurchaseOrderLineSQL()
  
    val orderLine = purchaseOrderLineSQL.findByPurchaseOrderLineID(purchaseOrderLineID)
    
    val productSQL : ProductSQL = new ProductSQL()
    
    val product = productSQL.findByProductID(orderLine.productID.value)
    
    product.productDescription.value

  }
  
    /**
   * @return : String
   * 
   * gets the product quantity for the current purchase order line and 
   * returns this a string
   */
  
  def addProductQuantityLabel() : String = 
  {
    val purchaseOrderLineSQL : PurchaseOrderLineSQL = new PurchaseOrderLineSQL()
  
    val orderLine = purchaseOrderLineSQL.findByPurchaseOrderLineID(purchaseOrderLineID)
    
    orderLine.quantity.value+""
  }

  /**
   * @return : Popup
   * 
   * creates the pop up that will contain all of the relevant labels for
   * the current purchase order line. Also contains the a button and a 
   * combo box so the purchase order lines can be navigated through
   */
  
  def createAlertPopup() = new Popup 
  {
   inner =>
   content.add(new StackPane 
     {
     
      children = List(
         new Rectangle 
         {
           width = 400
           height = 300
           fill = Color.White
           stroke = Color.WhiteSmoke
           strokeWidth = 4
         
           
         },
         new BorderPane 
         {
           center = new GridPane
           {
             padding = Insets(20, 20, 20, 20)
             add(
                 new Label
                 {
                   text = "Pick purchase order line: "
                   style = "-fx-font-size: 12pt"
                 }, 1, 1)
                 
                 
                 
             add(comboBoxOfPurchaseOrderLines(purchaseOrderID), 2, 1)
             add(
               new Label
               {
                 text = "Product Name: "
                 style = "-fx-font-size: 10pt"
               }, 1, 3)

             add(productNameLabel, 2, 3)
             add(
                 new Label
                 {
                   text = "Product Description: "
                   style = "-fx-font-size: 10pt"
                 }, 1, 5)
             add(productDescriptionLabel, 2, 5)
             add(
                 new Label
                 {
                   text = "Product Quantity: "
                   style = "-fx-font-size: 10pt"
                   
                 }, 1, 7)
             add(productQuantityLabel, 2, 7)
             
           }
           bottom = new Button("Close") 
           {
             onAction = 
             {
               e: ActionEvent => inner.hide
             }
             alignmentInParent = Pos.Center
             margin = Insets(10, 0, 10, 0)
           }
         }
     )
   }.delegate
   )
  }
  
  def showPopUp() : Unit = 
  {
    val popup = createAlertPopup()
    popup show(stage, 800 , 400)
  }
}