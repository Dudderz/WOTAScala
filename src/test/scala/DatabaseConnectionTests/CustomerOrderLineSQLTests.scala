package DatabaseConnectionTests

import com.qa.wota.UnitSpec

import com.qa.wota.UnitSpec
import com.qa.databaseconnector.{DBConnector, CustomerOrderLineSQL}
import java.sql.{DriverManager, SQLException}
import java.sql.ResultSet

import com.qa.entities.CustomerOrderLine
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class CustomerOrderLineSQLTests extends UnitSpec 
{
  def testFindAllCustomerOrderLine{
    "The findAllCustomerOrderLine method" should "not return null" in{
      
      val customerOrderLineSQL = new CustomerOrderLineSQL
      
      val results = customerOrderLineSQL findAllCustomerOrderLines
      
      assert(results != null)
    }
    
    it should "return all the order lines within the database" in{
      
      val customerOrderLineSQL = new CustomerOrderLineSQL
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.findAllSQL("SELECT * FROM customerorderline")
      
      val results = customerOrderLineSQL findAllCustomerOrderLines
      
      val customerOrderLineArray : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
      
      def getData(rs : ResultSet, custOrder : ObservableBuffer[CustomerOrderLine]) : Unit =
      {
        def getRSData()
        {
         if (rs next())
         {
          custOrder += new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
          getRSData
         }
        }
         getRSData
      }
      
      getData(resultSet, customerOrderLineArray)
      
      if(results.length == customerOrderLineArray.length) assert(true) else assert(false)

    }
    
    it should "return the correct data" in{
      val customerOrderLineSQL = new CustomerOrderLineSQL
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.findAllSQL("SELECT * FROM customerorderline")
      
      val results = customerOrderLineSQL findAllCustomerOrderLines
      
      val customerOrderLineArray : ObservableBuffer[CustomerOrderLine] = ObservableBuffer[CustomerOrderLine]()
      
      def getData(rs : ResultSet, custOrder : ObservableBuffer[CustomerOrderLine]) : Unit =
      {
        def getRSData()
        {
         if (rs next())
         {
          customerOrderLineArray += new CustomerOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
          getRSData
         }
        }
         getRSData
      }
      
      getData(resultSet, customerOrderLineArray)

      if(results(1).customerOrderID.value == customerOrderLineArray(1).customerOrderID.value
          && results(3).customerOrderID.value == customerOrderLineArray(3).customerOrderID.value)
        
        assert(true) else assert(false)

    }
  }
  
  def testFindByCustomerID{
    "The findByCustomerID" should "not return null with customerOrderID 1" in
    {
      val customerOrderLineSQL = new CustomerOrderLineSQL
      
      val results = customerOrderLineSQL findByCustomerID(1)
      
      assert(results != null)
    }
    it should "return the orders with the customerOrderID of 1" in{
      val customerOrderLineSQL = new CustomerOrderLineSQL
      
      val results = customerOrderLineSQL findByCustomerID(1)
      
      results(0).customerOrderID.value should be(1)
    }
    it should "return no customer orders with an employee id 999999999" in{
      
      val customerOrderLineSQL = new CustomerOrderLineSQL
      
      val customerOrders = customerOrderLineSQL.findByCustomerID(999999999)
      
      customerOrders.length should be (0)
    }
  }
  
  def testFindByProductID{
   "The findByProductID" should "not return null with productID 1" in
    {
      val customerOrderLineSQL = new CustomerOrderLineSQL
     
      val results = customerOrderLineSQL findByProductID(1)
      
      assert(results != null)
    }
    it should "return the orders with the productID of 1" in{
      val customerOrderLineSQL = new CustomerOrderLineSQL
     
      val results = customerOrderLineSQL findByProductID(1)

      results(0).productID.value should be(1)
    }
    it should "return no customer orders with a product id 999999999" in{
      
      val customerOrderLineSQL = new CustomerOrderLineSQL
     
      val customerOrders = customerOrderLineSQL.findByProductID(999999999)
      
      customerOrders.length should be (0)
    }
  }
    
  def testFindByCustomerOrderLineID{
   "The findByCustomerOrderLineID" should "not return null with customerOrderLineID of 1" in
    {
      val customerOrderLineSQL = new CustomerOrderLineSQL
     
      val results = customerOrderLineSQL findByCustomerOrderLineID(1)
      
      assert(results != null)
    }
    it should "return the orders with the productID of 1" in{
      val customerOrderLineSQL = new CustomerOrderLineSQL
     
      val results = customerOrderLineSQL findByCustomerOrderLineID(1)
      
      results.customerOrderLineID.value should be(1)
      
    }
    it should "return no customer orders with a product id 999999999" in{
      
      val customerOrderLineSQL = new CustomerOrderLineSQL
     
      val customerOrder = customerOrderLineSQL.findByCustomerOrderLineID(50)

      customerOrder.customerOrderLineID.value should be (99999999)
    }
  }
  
  testFindAllCustomerOrderLine
  testFindByCustomerID
  testFindByProductID
  testFindByCustomerOrderLineID
}