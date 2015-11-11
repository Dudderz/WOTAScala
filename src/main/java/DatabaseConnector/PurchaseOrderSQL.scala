package DatabaseConnector

import Entities.PurchaseOrder
import java.sql.SQLException
import java.sql.Connection
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class PurchaseOrderSQL {
  
  val dbConnection = new DBConnector()
  
  def findAllPurchaseOrders() : ObservableBuffer[PurchaseOrder] = 
  {
    val purchaseOrderArray : ObservableBuffer[PurchaseOrder] = ObservableBuffer[PurchaseOrder]()
    
    try{
      
       val connection : Connection = dbConnection connect
       
       val statement = connection createStatement
       val resultSet = statement executeQuery("SELECT * FROM purchaseorder")
       
       while(resultSet next)
       {
         purchaseOrderArray += new PurchaseOrder(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4))
       }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    dbConnection closeConnection
    
    purchaseOrderArray
  }
  
   def findByPurchaseOrderID(purchaseOrderID : Int) : PurchaseOrder = 
  {
    var purchaseOrder : PurchaseOrder = new PurchaseOrder(999999999, "", "", 0)
    
    try{
      
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT * FROM customerorder WHERE purchase_order_id = " + purchaseOrderID)
      
      while(resultSet next())
      {
        purchaseOrder = new PurchaseOrder(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4))
      }
     
    } catch {
      case e : SQLException => e printStackTrace
      
    }
    
    dbConnection closeConnection() 
    purchaseOrder
  }
  
  /*
   * Returns an ObservableBuffer of PurchaseOrder with the given employeeID
   * @Param employeeID, used to find selected orders with this employeeID
   */
  
  def findByEmployeeID(employeeID : Int) : ObservableBuffer[PurchaseOrder] = 
  {
    val purchaseOrderArray : ObservableBuffer[PurchaseOrder] = ObservableBuffer[PurchaseOrder]()
    
    try{
      
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT * FROM purchaseorder WHERE employee_id = " + employeeID)
    
      while(resultSet next())
      {     
        purchaseOrderArray += new PurchaseOrder(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4))
      }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    
    dbConnection closeConnection()
    
    purchaseOrderArray
  }
  
  /**
   * Updates the customer order table with a new employee ID for the selected customer order id
   * @PARAM employeeID will be the new employee id assigned to this order
   * @PARAM purchaseOrderID is the order that will be updated by this method
   */
  
  def claimPurchaseOrder(employeeID : Int, purchaseOrderID : Int) : Unit = 
  {
    try
    {
      val connection : Connection = dbConnection connect()
      val statement = connection createStatement()
      val resultSet = statement executeUpdate("UPDATE purchaseorder SET employee_id= '" + employeeID + "' WHERE purchase_order_id= " + purchaseOrderID)

    }
    catch {
      case e : SQLException => e printStackTrace
    }
    
    dbConnection closeConnection()
  }
  
    /**
   * Updates the purchase order table with a new employee ID for the selected purchase order id
   * @PARAM updatedStatus will be the new status assigned to this order
   * @PARAM purchaseOrderID is the order that will be updated by this method
   */
  
  def updateOrderStatus(purchaseOrderID : Int, updatedStatus : String) : Unit = 
  {
    try
    {
      val connection : Connection = dbConnection connect()
      val statement = connection createStatement()
      val resultSet = statement executeUpdate("UPDATE purchaseorder SET order_status= '" + updatedStatus + "' WHERE purchase_order_id= " + purchaseOrderID)
    }
    catch {
      case e : SQLException => e printStackTrace
    }
    
    dbConnection closeConnection()
   
  }
  
}