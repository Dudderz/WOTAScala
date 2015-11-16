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
  
  def comboBoxOfPurchaseOrderLines(purchaseOrderID : Int) : ComboBox[String] = 
  {
    val comboBoxInfo : ObservableBuffer[String] = ObservableBuffer[String]()
    
    val purchaseOrderLineSQL : PurchaseOrderLineSQL = new PurchaseOrderLineSQL()
  
    //var orderLines : ObservableBuffer[PurchaseOrderLine] = ObservableBuffer[PurchaseOrderLine]()
    val orderLines = purchaseOrderLineSQL.findByPurchaseOrderID(purchaseOrderID)
    
    val i = orderLines.delegate.length
    
    
    println(orderLines.delegate.length)
      
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
        println(comboBox.value.value)
      
      updateLabelText(productNameLabel, addProductNameLabel)
      updateLabelText(productIDLabel, addProductIDLabel)
      updateLabelText(productDescriptionLabel, addProductDescriptionLabel)
      updateLabelText(productQuantityLabel, addProductQuantityLabel)
    }
    
     
    comboBox
  }
  
  def updateLabelText(label : Label, updatedText : String) : Unit = 
  {
    label.text.value_=(updatedText)
  }
  
  def addProductNameLabel() : String = 
  {
    val purchaseOrderLineSQL : PurchaseOrderLineSQL = new PurchaseOrderLineSQL()
  
    val orderLine = purchaseOrderLineSQL.findByPurchaseOrderLineID(purchaseOrderLineID)
    
    val productSQL : ProductSQL = new ProductSQL()
    
    val product = productSQL.findByProductID(orderLine.productID.get)
    
    product.productName.value
  }
  
  def addProductIDLabel() : String = 
  {
    val purchaseOrderLineSQL : PurchaseOrderLineSQL = new PurchaseOrderLineSQL()
  
    val orderLine = purchaseOrderLineSQL.findByPurchaseOrderLineID(purchaseOrderLineID)
    
    val productSQL : ProductSQL = new ProductSQL()
    
    val product = productSQL.findByProductID(orderLine.productID.value)
    
    product.productID.value+""
  }
    
  def addProductDescriptionLabel() : String = 
  {
    val purchaseOrderLineSQL : PurchaseOrderLineSQL = new PurchaseOrderLineSQL()
  
    val orderLine = purchaseOrderLineSQL.findByPurchaseOrderLineID(purchaseOrderLineID)
    
    val productSQL : ProductSQL = new ProductSQL()
    
    val product = productSQL.findByProductID(orderLine.productID.value)
    
    product.productDescription.value

  }
  
  def addProductQuantityLabel() : String = 
  {
    val purchaseOrderLineSQL : PurchaseOrderLineSQL = new PurchaseOrderLineSQL()
  
    val orderLine = purchaseOrderLineSQL.findByPurchaseOrderLineID(purchaseOrderLineID)
    
    orderLine.quantity.value+""
  }
  
  /**
   * TODO return a rectangle containing the picture of the product
   * the picture will have to be sourced and saved into the resources folder
   * an image loader will have to be created
   * this can be done through a loader. 
   * use the log in page as an example
   */
  
  /*def addProductPicture(customerOrderLineID : Int) : Rectangle = 
  {
    val purchaseOrderLineSQL : PurchaseOrderLineSQL = new PurchaseOrderLineSQL()
  
    var orderLine = purchaseOrderLineSQL.findByCustomerOrderLineID(customerOrderLineID)
    
    val productSQL : ProductSQL = new ProductSQL()
    
    var product = productSQL.findByProductID(orderLine.productID.value)
    
    val label = new Label(product.productDescription.value)
     
    label
  }*/
  
  def createAlertPopup(popupText: String) = new Popup 
  {
   inner =>
   content.add(new StackPane 
     {
     println("called")
     
      children = List(
         new Rectangle 
         {
           width = 400
           height = 300
           //arcWidth = 20
           //arcHeight = 20
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
             alignmentInParent = Pos.CENTER
             margin = Insets(10, 0, 10, 0)
           }
         }
     )
   }.delegate
   )
  }
  
  def showPopUp() : Unit = 
  {
    val popup = createAlertPopup("Hello")
    popup show(stage, 800 , 400)
  }
}