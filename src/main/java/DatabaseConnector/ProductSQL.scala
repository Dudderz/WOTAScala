package DatabaseConnector

import Entities.Product
import java.sql.SQLException
import java.sql.Connection
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class ProductSQL {
  
  val dbConnection = new DBConnector()
  
  /**
   * @return : ObservableBuffer of Product
   * 
   * Finds all of the products within the SQL database 
   * and return them as an ObservableBuffer
   */
  
  def findAllProducts() : ObservableBuffer[Product] = 
  {
    var productArray : ObservableBuffer[Product] = ObservableBuffer[Product]()
    
    val resultSet = dbConnection findAllSQL("SELECT * FROM product")
   
    while(resultSet next())
    {
      productArray += new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7))
    } 

    dbConnection closeConnection
      
    productArray
  }
  
  /**
   * @return : product
   * 
   * Finds the product within the SQL database with the given
   * productID
   */
  
  def findByProductID(productID : Int) : Product =
  {
    var product = new Product(99999999, "", "", 0, "", "", 0)
    
    val resultSet = dbConnection runSQLQuery("SELECT * FROM product WHERE product_id =", productID)
    
    while(resultSet.next())
    {
      product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7))
    }

    product
  }
  
  /**
   * @param : productID the product who's quantity will be updated
   * @param : quantity the products new quantity 
   * 
   * Calls the relevant class within the dbConnection class and runs the update query
   * to allow the update of the chosen products quantity
   * 
   */
  
  def updateProductQuantity(productID : Int, quantity : Int) : Unit =
  {
    val initialProductQuantity = findByProductID(productID).productQuantity.value
    
    val updatedQuantity = initialProductQuantity + quantity
    
    val resultSet = dbConnection runSQLUpdateInt("UPDATE product SET product_quantity = '", "' WHERE product_id = ", updatedQuantity, productID)
   
    dbConnection closeConnection()
  }
}