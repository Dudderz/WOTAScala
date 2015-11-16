package com.qa.gui

import com.qa.entities.Product
import com.qa.databaseconnector.ProductSQL
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
 * 
 * @Description :  
 * Class that creates a new stage that contains the 
 * list of products that the SQL database contains
 * Also allows logout functionality
 * 
 */
class ProductGUI(stage : PrimaryStage) 
{
 
  val productSQL : ProductSQL = new ProductSQL
  
  //var products = productSQL findAllProducts
  
  val table = new TableView[Product](productSQL findAllProducts)  
  
  /*
   * Columns that are used within the Product Table
   */
  
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
  
  /**
   * Method that updates the contents of the table 
   * 
   */
  
  def updateTable(table : TableView[Product]) : Unit = 
  {
    table.items update(productSQL findAllProducts)
  }
  
  /**
   * @return : Button 
   * Returns a button that is placed within the GUI
   * 
   * This method creates the Update table button and when
   * clicked, calls the updateTable function 
   */
  
  def createUpdateButton() : Button = 
  {
    val button = new Button
    {
      text = "Update Table"
      
      onAction = (ae: ActionEvent) =>
      {
        updateTable(table)    
      }
    }
    
    button
  }
  
  /**
   *  @return : Button 
   *  - Returns a button that is placed within the GUI
   *  
   *  This method creates the Log out button and when clicked,
   *  creates the log in screen and switches to that stage
   */
  
  def createLogOutButton() : Button = 
  {
    val button = new Button
    {
      text = "Log out"
      
      onAction = (ae: ActionEvent) =>
      {
        val logInGui = new LogIn(stage)
        logInGui showLogin
      }
    }
    
    button 
  }
  
  /**
   * @return : ScrollPane 
   * 
   * Returns a scroll pane that contains a table that holds the previously defined columns
   * A scroll pane was used due to the size of the table and not wanting to increase the size
   * of the pane, this was the best solution 
   */
  
  def createProductTable() : ScrollPane = 
  {
    table.columns += (productIDCol, productNameCol, productDescriptionCol, productTypeCol, productPriceCol, productQuantityCol)
    
    val scrollPane = new ScrollPane
       
    scrollPane setContent(table)
    scrollPane prefWidth = 400.0
    
    scrollPane
  }
  
  /**
   * @return : BorderPane
   * 
   * Returns a border pane that contains a grid pane, which in turn 
   * contains the update and log out button. 
   * This was done for layout purposes as the grid allowed for placement 
   * within the center of the border pane 
   * 
   */
  
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