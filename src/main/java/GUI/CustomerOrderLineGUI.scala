package GUI

import DatabaseConnector.{CustomerOrderLineSQL, ProductSQL}
import Entities.{CustomerOrderLine, Product}
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
class CustomerOrderLineGUI(stage : PrimaryStage)
{
  var customerOrderLineID : Int = 0
  
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
    
    comboBox.onAction = (ae: ActionEvent) =>
    {
        println(comboBox.value.value)
    }
    
     
    comboBox
  }
  
  def addProductNameLabel(customerOrderLineID : Int) : Label = 
  {
    val customerOrderLineSQL : CustomerOrderLineSQL = new CustomerOrderLineSQL()
  
    var orderLine = customerOrderLineSQL.findByCustomerOrderLineID(customerOrderLineID)
    
    val productSQL : ProductSQL = new ProductSQL()
    
    var product = productSQL.findByProductID(orderLine.productID.value)
    
    val label = new Label(product.productName.value)
    
    label
  }
  
  def addProductIDLabel(customerOrderLineID : Int) : Label = 
  {
    val customerOrderLineSQL : CustomerOrderLineSQL = new CustomerOrderLineSQL()
  
    var orderLine = customerOrderLineSQL.findByCustomerOrderLineID(customerOrderLineID)
    
    val productSQL : ProductSQL = new ProductSQL()
    
    var product = productSQL.findByProductID(orderLine.productID.value)
    
    val label = new Label(product.productID.value+"")
    
    label
  }
  
  def addProductDescriptionLabel(customerOrderLineID : Int) : Label = 
  {
    val customerOrderLineSQL : CustomerOrderLineSQL = new CustomerOrderLineSQL()
  
    var orderLine = customerOrderLineSQL.findByCustomerOrderLineID(customerOrderLineID)
    
    val productSQL : ProductSQL = new ProductSQL()
    
    var product = productSQL.findByProductID(orderLine.productID.value)
    
    val label = new Label(product.productDescription.value)
  
    label
  }
  
  def addProductQuantityLabel(customerOrderLineID : Int) : Label = 
  {
    val customerOrderLineSQL : CustomerOrderLineSQL = new CustomerOrderLineSQL()
  
    var orderLine = customerOrderLineSQL.findByCustomerOrderLineID(customerOrderLineID)
    
    val label = new Label(orderLine.quantity.value+"")
  
    label
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
    val customerOrderLineSQL : CustomerOrderLineSQL = new CustomerOrderLineSQL()
  
    var orderLine = customerOrderLineSQL.findByCustomerOrderLineID(customerOrderLineID)
    
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
           width = 300
           height = 200
           arcWidth = 20
           arcHeight = 20
           fill = Color.LIGHTBLUE
           stroke = Color.GRAY
           strokeWidth = 2
         
           
         },
         new BorderPane 
         {
           center = new GridPane
           {
             add(new Label("Pick customer order line: "), 1, 1)
             add(comboBoxOfCustomerOrderLines(1), 1, 2)
             add(new Label("Product name: "), 1, 3)
             add(addProductNameLabel(1), 1, 4)
             add(new Label("Product Description: "), 1, 5)
             add(addProductDescriptionLabel(1), 1, 6)
             add(new Label("Product Quantity: "), 1, 7)
             add(addProductQuantityLabel(1), 1, 8)
             
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
    popup show(stage, 200, 200)
  }
}