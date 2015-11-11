
package DatabaseConnector

import Entities.PurchaseOrderLine
import java.sql.SQLException
import java.sql.Connection
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class PurchaseOrderLineSQL {
  
  val dbConnection = new DBConnector()
      
  /*
   * Finds all customer orders within the SQL database 
   * and returns them as an ObservableBuffer
   */
  
  def findAllPurchaseOrderLines() : ObservableBuffer[PurchaseOrderLine] = 
  {
    val purchaseOrderArray : ObservableBuffer[PurchaseOrderLine] = ObservableBuffer[PurchaseOrderLine]()
    
    try{
      
      val connection : Connection = dbConnection connect
      
      val statement = connection createStatement
      val resultSet = statement executeQuery("SELECT purchaseorder_id, employee_id, order_date, order_status FROM purchaseorder")
    
      while(resultSet next)
      {     
        purchaseOrderArray += new PurchaseOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
      }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    
    dbConnection closeConnection
    
    purchaseOrderArray
  }
  
   /*
   * Returns a single PurchaseOrder with the given purchase id
   * @PARAM customerID, used to find selected orders with this customerID
   */
  
  def findByPurchaseOrderID(purchaseID : Int) : ObservableBuffer[PurchaseOrderLine] = 
  {
    var purchaseOrderLineArray : ObservableBuffer[PurchaseOrderLine] = ObservableBuffer[PurchaseOrderLine]()
    
    try{
      
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT * FROM purchaseorderline WHERE purchase_order_id = " + purchaseID)
      
      while(resultSet next())
      {
        purchaseOrderLineArray += new PurchaseOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
      }
      
    } catch {
      case e : SQLException => e printStackTrace
      
    }
    
    dbConnection closeConnection() 
    purchaseOrderLineArray
  }
  
    /*
   * Returns an ObservableBuffer of PurchaseOrder with the given employeeID
   * @Param employeeID, used to find selected orders with this employeeID
   */
  
  def findByProductID(productID : Int) : ObservableBuffer[PurchaseOrderLine] = 
  {
    val purchaseOrderLineArray : ObservableBuffer[PurchaseOrderLine] = ObservableBuffer[PurchaseOrderLine]()
    
    try{
      
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT * FROM purchaseorderline WHERE product_id = " + productID)
    
      while(resultSet next())
      {     
        purchaseOrderLineArray += new PurchaseOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
      }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    
    dbConnection closeConnection()
    
    purchaseOrderLineArray
  }
  
  def findByPurchaseOrderLineID(orderLineID : Int) : PurchaseOrderLine = 
  {
    var purchaseOrderLine : PurchaseOrderLine = new PurchaseOrderLine(99999999, 0, 0, 0)
    
    try{
      
      val connection : Connection = dbConnection connect
      
      val statement = connection createStatement
      val resultSet = statement executeQuery("SELECT * FROM purchaseorderline WHERE purchase_orderline_id = " + orderLineID)
      
      while(resultSet next())
      {
        purchaseOrderLine = new PurchaseOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
      }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    dbConnection closeConnection()
    
    purchaseOrderLine
  }

  
}