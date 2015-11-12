package GUI

import Entities.Product
import DatabaseConnector.ProductSQL
import scalafx.Includes._
import scalafx.scene.Node
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.{TableColumn, TableView, ScrollPane, Button}
import scalafx.scene.layout.{BorderPane, GridPane}
import TableColumn._

/**
 * @author tdudley
 */
class ProductGUI(stage : PrimaryStage) 
{
  val productSQL : ProductSQL = new ProductSQL
  
  var products = productSQL.findAllProducts()
  
  val table = new TableView[Product](products)  
  
  val productIDCol = new TableColumn[Product, Int]
  {
    text = "Product ID"
    cellValueFactory = {_.value.productID}
    prefWidth = 90
  }
  
  val productNameCol = new TableColumn[Product, String]
  {
    text = "Product Name"
    cellValueFactory = {_.value.productName}
    prefWidth = 120
  }
  
  val productDescriptionCol = new TableColumn[Product, String]
  {
    text = "Product Description"
    cellValueFactory = {_.value.productDescription}
    prefWidth = 150
  }
  
  val productTypeCol = new TableColumn[Product, String]
  {
    text = "Product Type"
    cellValueFactory = {_.value.productType}
    prefWidth = 100
  }
  
  val productPriceCol = new TableColumn[Product, Float]
  {
    text = "Product Price"
    cellValueFactory = {_.value.productPrice}
    prefWidth = 100
  }
  
  val productQuantityCol = new TableColumn[Product, Int]
  {
    text = "Product Quantity"
    cellValueFactory = {_.value.productQuantity}
    prefWidth = 120
  }
  
  /*val productPorousWareCol = new TableColumn[Product, Int]
  {
    text = "Product Porousware"
    cellValueFactory = {_.value.}
  }*/
  
  def updateTable(table : TableView[Product]) : Unit = 
  {
    products = productSQL.findAllProducts()
    
    table.items.update(products)

  }
  
  def createUpdateButton() : Button = 
  {
    val button = new Button()
    {
      text = "Update Table"
      
      onAction = (ae: ActionEvent) =>
      {
        updateTable(table)    
      }
    }
    
    button
  }
  
  def createLogOutButton() : Button = 
  {
    val button = new Button()
    {
      text = "Log out"
      
      onAction = (ae: ActionEvent) =>
      {
        val logInGui = new LogIn(stage)
        logInGui.showLogin()
      }
    }
    
    button 
  }
  
  def createProductTable() : ScrollPane = 
  {
    table.columns += (productIDCol, productNameCol, productDescriptionCol, productTypeCol, productPriceCol, productQuantityCol)
    
    val scrollPane = new ScrollPane
       
    scrollPane.setContent(table)
    scrollPane.prefWidth = 400.0
    
    scrollPane
  }
  
  def createBorderPane() : BorderPane = 
  {
    new BorderPane
    {
      padding = Insets(20, 20, 20, 20)
      
      center = 
        new GridPane 
        {
          add(createUpdateButton, 5, 1)
          add(createLogOutButton, 5, 3)
        }
    }
    
  }
  
  
  
}