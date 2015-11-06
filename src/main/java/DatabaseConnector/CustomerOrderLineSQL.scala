package DatabaseConnector

import Entities.CustomerOrderLine
import java.sql.SQLException
import java.sql.Connection
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class CustomerOrderLineSQL {
  
  val dbConnection = new DBConnector()
  
  def findAllCustomerOrderLines() : ObservableBuffer[CustomerOrderLine] = 
  {
    val customerOrderArray : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
    
    try{
      
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT customerorder_id, employee_id, order_date, order_status FROM customerorder")
    
      while(resultSet next())
      {     
        customerOrderArray += new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3))
      }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    
    dbConnection closeConnection()
    
    customerOrderArray
  }
  
  def findByCustomerID(customerID : Int) : CustomerOrderLine = 
  {
    var customerOrder : CustomerOrderLine = new CustomerOrderLine(999999999, 0, 0)
    
    try{
      
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT customerorder_id, employee_id, order_date, order_status FROM customerorder WHERE customerorder_id = " + customerID)
      
      while(resultSet next())
      {
        customerOrder = new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3))
      }
      
     
      
    } catch {
      case e : SQLException => e printStackTrace
      
    }
    
    dbConnection closeConnection() 
    customerOrder
  }
  
  /*
   * Returns an ObservableBuffer of CustomerOrder with the given employeeID
   * @Param employeeID, used to find selected orders with this employeeID
   */
  
  def findByEmployeeID(employeeID : Int) : ObservableBuffer[CustomerOrderLine] = 
  {
    val customerOrderArray : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
    
    try{
      
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT customerorder_id, employee_id, order_date, order_status FROM customerorder WHERE employee_id = " + employeeID)
    
      while(resultSet next())
      {     
        customerOrderArray += new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3))
      }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    
    dbConnection closeConnection()
    
    customerOrderArray
  }
  
}