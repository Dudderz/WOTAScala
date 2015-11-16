package com.qa.databaseconnectiontests

import com.qa.wota.UnitSpec
import com.qa.databaseconnector.DBConnector
import java.sql.{DriverManager, SQLException}
import java.sql.ResultSet

import com.qa.entities.CustomerOrder
import scalafx.collections.ObservableBuffer


/**
 * @author tdudley
 */
class DBConnectionTests extends UnitSpec 
{
  def testGetConnection{
    "The connect method" should "return a connection to the sql database" in{
      
      val driver = "com.mysql.jdbc.Driver"
      val url = "jdbc:mysql://localhost:3306/mydb"
      val username = "root"
      val password = "user"
      
      val connection = DriverManager.getConnection(url, username, password)
      
      val dbConnector = new DBConnector
            
      dbConnector.connect.getMetaData.getURL should be (connection.getMetaData.getURL)
      
    } 
  }
  
  def testCloseConnection{
    "The closeConnection method" should "close the connection the sql database" in{
      
      val dbConnector = new DBConnector
      
      dbConnector.connect
      dbConnector closeConnection
      
      assert(dbConnector.connection.isClosed)

    }
  }
  
  def testSQLFindAll{
    "The findAllSQL method" should "execute the given query and the return not be null" in{
      
      val dbConnector = new DBConnector
      
      assert(dbConnector.findAllSQL("SELECT * FROM customerorder") != null)
            
    }
    
    it should "return the correct data" in{
      
      val dbConnector = new DBConnector
      
      val driver = "com.mysql.jdbc.Driver"
      val url = "jdbc:mysql://localhost:3306/mydb"
      val username = "root"
      val password = "user"
     
      val connection = DriverManager.getConnection(url, username, password)
      val statement = connection createStatement
      val resultSet = statement executeQuery("SELECT * FROM customerorder")
          
      var customerOrderArray : ObservableBuffer[CustomerOrder] = ObservableBuffer[CustomerOrder]()
      var customerOrderArray2 : ObservableBuffer[CustomerOrder] = ObservableBuffer[CustomerOrder]()
      
      val resultSetTwo = dbConnector.findAllSQL("SELECT * FROM customerorder")
      
      def getData(rs : ResultSet, custOrder : ObservableBuffer[CustomerOrder]) : Unit =
      {
        def getRSData()
        {
         if (rs next())
         {
          custOrder += new CustomerOrder(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4))
          getRSData
         }
        }
         getRSData
      }
      
      getData(resultSet, customerOrderArray)         
      getData(resultSetTwo, customerOrderArray2)

      def checkArraysAtPos(n : Int) : Boolean = 
      {
        if(customerOrderArray(n).customerOrderID.value == customerOrderArray2(n).customerOrderID.value 
            && customerOrderArray(n).customerOrderDate.value == customerOrderArray2(n).customerOrderDate.value
            && customerOrderArray(n).customerOrderStatus.value == customerOrderArray2(n).customerOrderStatus.value
            && customerOrderArray(n).employeeID.value == customerOrderArray2(n).employeeID.value)
          true
        else
          false
      }
      
      if (checkArraysAtPos(2) && checkArraysAtPos(5)) assert(true) else assert(false)

    }
  }
  
  def testRunSQLQuery{
    "The runSQLQuery method" should "not return null" in{
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.runSQLQuery("SELECT * FROM customerorder WHERE customerorder_id = ", 1)
      
      assert(resultSet != null)
    }
    
    it should "return not null with invalid data" in{
      
      val dbConnection =  new DBConnector
      
      val resultSet  = dbConnection.runSQLQuery("SELECT * FROM customerorder WHERE customerorder_id = ", 100)
    
      assert(resultSet != null)
    }
    
    it should "return null with an invalid statement" in{
      
      val dbConnection =  new DBConnector
      
      val resultSet  = dbConnection.runSQLQuery("SELECT * FROM customerder WHERE customerorder_id = ", 1)
    
      assert(resultSet == null)
    }
    
   it should "return the correct value within the database" in{
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.runSQLQuery("SELECT * FROM customerorder WHERE customerorder_id = ", 1)
      
      var customerOrder : CustomerOrder = new CustomerOrder(999999999, "", "", 0)
      
      def getRSData()
      {
       if(resultSet next())
        {
         customerOrder = new CustomerOrder(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4))
         getRSData
        }
      }
    
      getRSData

      customerOrder.customerOrderID.value should be (1)

    }
  }
  
  def testRunSQLUpdateInt
  {
    "The runSQLUpdateInt method" should "update the correct item within the table" in
    {
      val dbConnector = new DBConnector
            
      dbConnector runSQLUpdateInt("UPDATE customerorder SET employee_id= '", "' WHERE customerorder_id= ", 3, 1)
      
      val resultSet = dbConnector runSQLQuery("SELECT * FROM customerorder WHERE customerorder_id = ", 1)
      
      var customerOrder : CustomerOrder = new CustomerOrder(999999999, "", "", 0)
      
      def getRSData()
      {
       if(resultSet next())
        {
         customerOrder = new CustomerOrder(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4))
         getRSData
        }
      }
      
      getRSData()

      customerOrder.employeeID.value should be (3)

    }
  }
  
  def testRunSQLUpdateString
  {
    "The runSQLUpdateString method" should "update the correct item within the table" in
    {
      val dbConnector = new DBConnector
            
      dbConnector runSQLUpdateString("UPDATE customerorder SET order_status= '", "' WHERE customerorder_id= ", "Processing", 1)
      
      val resultSet = dbConnector runSQLQuery("SELECT * FROM customerorder WHERE customerorder_id = ", 1)
      
      var customerOrder : CustomerOrder = new CustomerOrder(999999999, "", "", 0)
      
      def getRSData()
      {
       if(resultSet next())
        {
         customerOrder = new CustomerOrder(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4))
         getRSData
        }
      }
      
      getRSData()

      customerOrder.customerOrderStatus.value should be ("Processing")

    }
  }
  
  testGetConnection
  testCloseConnection
  testSQLFindAll
  testRunSQLQuery
  testRunSQLUpdateInt
  testRunSQLUpdateString
}