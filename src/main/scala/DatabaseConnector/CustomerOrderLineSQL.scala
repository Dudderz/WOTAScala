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
  
  /**
   * @Return : ObservableBuffer of CustomerOrder
   *  
   * Creates a connection to the database and runs the required statement
   * then assigns the results to an observable buffer
   * the method then closes the connection and returns the observable buffer 
   * containing the results from the statement 
   */
  
  def findAllCustomerOrderLines() : ObservableBuffer[CustomerOrderLine] = 
  {
    val customerOrderLineArray : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
    
    val resultSet = dbConnection findAllSQL("SELECT * FROM customerorderline")
    
    def getRSData()
    {
     if (resultSet next())
      {
       customerOrderLineArray += new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
       getRSData
      }
    }
    
    getRSData

    dbConnection closeConnection()
    
    customerOrderLineArray
  }
  
   /**
   * @Return : ObservableBuffer of CustomerOrder
   * @Param : customerID used to find selected orders with this customerID
   * 
   * Creates a connection to the database and runs the required statement
   * then assigns the results to an observable buffer
   * the method then closes the connection and returns the observable buffer 
   * containing the results from the statement 
   */
  
  def findByCustomerID(customerID : Int) : ObservableBuffer[CustomerOrderLine] = 
  {
    var customerOrderLineArray : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
    
    val resultSet = dbConnection runSQLQuery("SELECT * FROM customerorderline WHERE customerorder_id =", customerID)
 
    def getRSData()
    {
     if(resultSet next())
      {
       customerOrderLineArray += new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
       getRSData
      }
    }

    getRSData

    dbConnection closeConnection() 
    
    customerOrderLineArray
  }
  
  /**
   * @Return : ObservableBuffer of CustomerOrder
   * @Param : productID used to find selected orders with this productID
   * 
   * Creates a connection to the database and runs the required statement
   * then assigns the results to an observable buffer
   * the method then closes the connection and returns the observable buffer 
   * containing the results from the statement 
   */
  
  def findByProductID(productID : Int) : ObservableBuffer[CustomerOrderLine] = 
  {
    val customerOrderArray : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
    
    val resultSet = dbConnection runSQLQuery("SELECT * FROM customerorderline WHERE product_id =", productID)
    
    def getRSData()
    {
     if(resultSet next())
      {
       customerOrderArray += new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
       getRSData
      }
    }

    getRSData
    dbConnection closeConnection()
    
    customerOrderArray
  }
  
  /**
   * @Return : ObservableBuffer of CustomerOrder
   * @Param : orderlineID used to find selected orders with this orderlineID
   * 
   * Creates a connection to the database and runs the required statement
   * then assigns the results to an observable buffer
   * the method then closes the connection and returns the observable buffer 
   * containing the results from the statement 
   */
  
  def findByCustomerOrderLineID(orderLineID : Int) : CustomerOrderLine = 
  {
    var customerOrderLine : CustomerOrderLine = new CustomerOrderLine(99999999, 0, 0, 0)
    
    val resultSet = dbConnection runSQLQuery("SELECT * FROM customerorderline WHERE customer_orderline_id =", orderLineID)
    
    def getRSData()
    {
     if(resultSet next())
      {
       customerOrderLine = new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
       getRSData
      }
    }

    getRSData

    dbConnection closeConnection()
    
    customerOrderLine
  }
  
}