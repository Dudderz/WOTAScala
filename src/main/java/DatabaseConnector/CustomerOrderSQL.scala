package DatabaseConnector

import Entities.CustomerOrder
import java.sql.SQLException
import java.sql.Connection
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class CustomerOrderSQL {
  
  val dbConnection = new DBConnector()
      
  /**
   * @return : ObservableBuffer of Customer Orders
   * 
   * Finds all customer orders within the SQL database 
   * and return them as an ObservableBuffer
   */

  def findAllCustomerOrders() : ObservableBuffer[CustomerOrder] = 
  {
    val customerOrderArray : ObservableBuffer[CustomerOrder] = ObservableBuffer[CustomerOrder]()
    
    val resultSet = dbConnection.findAllSQL("SELECT customerorder_id, order_date, order_status, employee_id FROM customerorder")
    
    while(resultSet next)
    {
      customerOrderArray += new CustomerOrder(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4))
    }
    
    dbConnection closeConnection
    
    customerOrderArray
  }
  
   /**
   * @return : CustomerOrder with the given customer id
   * @param customerID, used to find selected orders with this customerID
   */
  
  def findByCustomerID(customerID : Int) : CustomerOrder = 
  {
    var customerOrder : CustomerOrder = new CustomerOrder(999999999, "", "", 0)
    
    val resultSet = dbConnection.runSQLQuery("SELECT * FROM customerorder WHERE customerorder_id = " , customerID)
    
    while(resultSet next)
    {
        customerOrder = new CustomerOrder(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4))
    }
    
    dbConnection closeConnection
    
    customerOrder
  }
  
  /**
   * @return an ObservableBuffer of CustomerOrder with the given employeeID
   * @param employeeID, used to find selected orders with this employeeID
   */
  
  def findByEmployeeID(employeeID : Int) : ObservableBuffer[CustomerOrder] = 
  {
    val customerOrderArray : ObservableBuffer[CustomerOrder] = ObservableBuffer[CustomerOrder]()
    
    val resultSet = dbConnection.runSQLQuery("SELECT * FROM customerorder WHERE customerorder_id = " , employeeID)
    
    while(resultSet next)
    {     
      customerOrderArray += new CustomerOrder(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4))
    }
    
    dbConnection closeConnection
    
    customerOrderArray
  }
  
 /**
   * Updates the customer order table with a new employee ID for the selected customer order id
   * @param employeeID will be the new employee id assigned to this order
   * @param customerOrderID is the order that will be updated by this method
   */
  
  def claimCustomerOrder(employeeID : Int, customerOrderID : Int) : Unit = 
  {
    dbConnection runSQLUpdateInt("UPDATE customerorder SET employee_id= '", "' WHERE customerorder_id= ", employeeID, customerOrderID)
    
    dbConnection closeConnection
  }
  
 /**
   * Updates the customer order table with a new employee ID for the selected customer order id
   * @param updatedStatus will be the new status assigned to this order
   * @param customerOrderID is the order that will be updated by this method
   */
  
  def updateOrderStatus(customerOrderID : Int, updatedStatus : String) : Unit = 
  {
    dbConnection runSQLUpdateString("Update customerorder SET order_status= '", "' WHERE customerorder_id= ", updatedStatus, customerOrderID)
    
    dbConnection closeConnection
  }
  
}