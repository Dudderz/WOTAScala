package DatabaseConnector

import Entities.CustomerOrderLine
import scalafx.collections.ObservableBuffer
import java.sql.Connection
import java.sql.SQLException
import scala.collection.mutable.ArrayBuffer

/**
 * @author tdudley
 */
class CustomerOrderLineSQL {
  
  val dbConnection = new DBConnector()
  
  def findAllCustomerOrderLines() : ObservableBuffer[CustomerOrderLine] = 
  {
    val customerOrderLineArray : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
    
    try{
      
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT * FROM customerorderline")
    
      while(resultSet next())
      {     
        customerOrderLineArray += new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
      }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    
    dbConnection closeConnection()
    
    customerOrderLineArray
  }
  
  def findByCustomerID(customerID : Int) : ObservableBuffer[CustomerOrderLine] = 
  {
    var customerOrderLineArray : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
    
    try{
      
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT * FROM customerorderline WHERE customerorder_id = " + customerID)
      
      while(resultSet next())
      {
        customerOrderLineArray += new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
      }
      
    } catch {
      case e : SQLException => e printStackTrace
      
    }
    
    dbConnection closeConnection() 
    
    customerOrderLineArray
  }
  
  /*
   * Returns an ObservableBuffer of CustomerOrder with the given employeeID
   * @Param employeeID, used to find selected orders with this employeeID
   */
  
  def findByProductID(productID : Int) : ObservableBuffer[CustomerOrderLine] = 
  {
    val customerOrderArray : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
    
    try{
      
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT * FROM customerorderline WHERE product_id = " + productID)
    
      while(resultSet next())
      {     
        customerOrderArray += new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
      }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    
    dbConnection closeConnection()
    
    customerOrderArray
  }
  
  def findByCustomerOrderLineID(orderLineID : Int) : CustomerOrderLine = 
  {
    var customerOrderLine : CustomerOrderLine = new CustomerOrderLine(99999999, 0, 0, 0)
    
    try{
      
      val connection : Connection = dbConnection connect
      
      val statement = connection createStatement
      val resultSet = statement executeQuery("SELECT * FROM customerorderline WHERE customer_orderline_id = " + orderLineID)
      
      while(resultSet next())
      {
        customerOrderLine = new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
      }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    dbConnection closeConnection()
    
    customerOrderLine
  }
  
}