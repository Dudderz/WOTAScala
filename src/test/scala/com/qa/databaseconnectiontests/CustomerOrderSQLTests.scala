package com.qa.databaseconnectiontests

import com.qa.wota.UnitSpec
import com.qa.databaseconnector.{DBConnector, CustomerOrderSQL}
import java.sql.{DriverManager, SQLException}
import java.sql.ResultSet

import com.qa.entities.CustomerOrder
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class CustomerOrderSQLTests extends UnitSpec 
{
  def testFindAllCustomerOrders{
    "The findAllCustomerOrders" should "not return null" in{
     
     val customerOrderSQL = new CustomerOrderSQL
     
     val results = customerOrderSQL findAllCustomerOrders
     
     assert(results != null)
    }
    
    it should "return all the orders within the database" in{
      
      val customerOrderSQL = new CustomerOrderSQL
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.findAllSQL("SELECT * FROM customerorder")
      
      val results = customerOrderSQL findAllCustomerOrders
      
      val customerOrderArray : ObservableBuffer[CustomerOrder] = ObservableBuffer[CustomerOrder]()
      
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

      if(results.length == customerOrderArray.length ) assert(true) else assert(false)

     }
    
    it should  "return the correct data" in{
      val customerOrderSQL = new CustomerOrderSQL
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.findAllSQL("SELECT * FROM customerorder")
      
      val results = customerOrderSQL findAllCustomerOrders
      
      val customerOrderArray : ObservableBuffer[CustomerOrder] = ObservableBuffer[CustomerOrder]()
      
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
      
      if(results(1).customerOrderID.value == customerOrderArray(1).customerOrderID.value
          && results(3).customerOrderID.value == customerOrderArray(3).customerOrderID.value)

        assert(true) else assert(false)
    }
  }
  
  def testFindByCustomerID{
    
    "The findByCustomerID " should "not return a null value" in{
      
      val customerOrderSQL = new CustomerOrderSQL
      
      customerOrderSQL.findByCustomerID(1) should not be (null)
    }
    it should "return the customer order with customer id 2" in{
      
      val customerOrderSQL = new CustomerOrderSQL
      
      customerOrderSQL.findByCustomerID(2).customerOrderID.value should be(2)
    }
    it should "return a customer order with customer id 999999999" in{
      
      val customerOrderSQL = new CustomerOrderSQL
      
      customerOrderSQL.findByCustomerID(1002).customerOrderID.value should be(999999999)
    }
  }
  
  def testFindByEmployeeID{
    "The findByEmployeeID " should "not return a null value" in{
      
      val customerOrderSQL = new CustomerOrderSQL
      
      customerOrderSQL.findByEmployeeID(1) should not be (null)
    }
    it should "return the customer orders with employee id 2" in{
          
      val customerOrderSQL = new CustomerOrderSQL
      
      val customerOrders = customerOrderSQL.findByEmployeeID(2)
      
      customerOrders(0).employeeID.value should be (2)
    }
    it should "return no customer orders with an employee id 999999999" in{
      
      val customerOrderSQL = new CustomerOrderSQL
      
      val customerOrders = customerOrderSQL.findByEmployeeID(999999999)
      
      customerOrders.length should be (0)
    }
  }
  
  def testClaimCustomerOrder{
    "The claimCustomerOrder" should "update the correct item within the table" in{
      
      val customerOrderSQL = new CustomerOrderSQL
      
      customerOrderSQL.claimCustomerOrder(1, 1)
      
      customerOrderSQL.findByCustomerID(1).employeeID.value should be(1)
      
    }
    it should "update the employeeID to 3" in{
       val customerOrderSQL = new CustomerOrderSQL
      
      customerOrderSQL.claimCustomerOrder(3, 1)
      
      customerOrderSQL.findByCustomerID(1).employeeID.value should be(3)
    }   
  }
  
  def testUpdateOrderStatus{
    "The updateOrderStatus" should "update the correct item within the table" in{
      
      val customerOrderSQL = new CustomerOrderSQL
      
      customerOrderSQL.updateOrderStatus(1, "Testing")
      
      customerOrderSQL.findByCustomerID(1).customerOrderStatus.value should be ("Testing")
    }
    it should "update the order status to Processing" in{
      
      val customerOrderSQL = new CustomerOrderSQL
      
      customerOrderSQL.updateOrderStatus(1, "Processing")
      
      customerOrderSQL.findByCustomerID(1).customerOrderStatus.value should be ("Processing")
    }
  }
  
  testFindAllCustomerOrders
  testFindByCustomerID
  testFindByEmployeeID
  testClaimCustomerOrder
  testUpdateOrderStatus
}