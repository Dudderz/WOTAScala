package GUI

import Entities.Product
import DatabaseConnector.ProductSQL

import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.{TableColumn, TableView}

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
    prefWidth = 100
  }
  
  val productNameCol = new TableColumn[Product, String]
  {
    text = "Product Name"
    cellValueFactory = {_.value.productName}
    prefWidth = 150
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
  
  val productQuantity = new TableColumn[Product, Int]
  {
    text = "Product Quantity"
    cellValueFactory = {_.value.productQuantity}
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
  
}