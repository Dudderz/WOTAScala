package DatabaseConnector

import Entities.CustomerOrder
import java.sql.SQLException
import java.sql.Connection

/**
 * @author tdudley
 */
class CustomerOrderSQL {
  
  val dbConnection = new DBConnector()
      
  
  
  def findByCustomerID(customerID : Int) : CustomerOrder = 
  {
    var customerOrder : CustomerOrder = new CustomerOrder(999999999, 0, "", "")
    
    try{
      
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT customerOrderID, employeeID, customerOrderDate, customerOrderStatus  FROM customerorder WHERE customerOrderID = " + customerID)
      
      while(resultSet next())
      {
        customerOrder = new CustomerOrder(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4))
      }
      
     
      
    } catch {
      case e : SQLException => e printStackTrace
      
    }
    
     customerOrder
  }
  
}