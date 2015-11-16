package DatabaseConnectionTests

import com.qa.wota.UnitSpec
import DatabaseConnector.{DBConnector, PurchaseOrderSQL}
import java.sql.{DriverManager, SQLException}
import java.sql.ResultSet

import Entities.PurchaseOrder
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class PurchaseOrderSQLTests extends UnitSpec {
 
    def testFindAllPurchaseOrders{
    "The findAllPurchaseOrders method" should "not return null" in{
     
     val purchaseOrderSQL = new PurchaseOrderSQL
     
     val results = purchaseOrderSQL findAllPurchaseOrders
     
     assert(results != null)
    }
    
    it should "return all the orders within the database" in{
      
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.findAllSQL("SELECT * FROM purchaseorder")
      
      val results = purchaseOrderSQL findAllPurchaseOrders
      
      val purchaseOrderArray : ObservableBuffer[PurchaseOrder] = ObservableBuffer[PurchaseOrder]()
      
      def getData(rs : ResultSet, purcOrder : ObservableBuffer[PurchaseOrder]) : Unit =
      {
        def getRSData()
        {
         if (rs next())
         {
          purcOrder += new PurchaseOrder(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4))
          getRSData
         }
        }
         getRSData
      }
      
      getData(resultSet, purchaseOrderArray)

      var bool : Boolean = false
      
      if(results.length == purchaseOrderArray.length )
        bool = true
      else
        bool = false
        
      assert(bool)
     }
    
    it should  "return the correct data" in{
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.findAllSQL("SELECT * FROM purchaseorder")
      
      val results = purchaseOrderSQL findAllPurchaseOrders
      
      val purchaseOrderArray : ObservableBuffer[PurchaseOrder] = ObservableBuffer[PurchaseOrder]()
      
      def getData(rs : ResultSet, purcOrder : ObservableBuffer[PurchaseOrder]) : Unit =
      {
        def getRSData()
        {
         if (rs next())
         {
          purcOrder += new PurchaseOrder(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4))
          getRSData
         }
        }
         getRSData
      }
      
      getData(resultSet, purchaseOrderArray)

      var bool : Boolean = false
      
      if(results(1).purchaseOrderID.value == purchaseOrderArray(1).purchaseOrderID.value
          && results(3).purchaseOrderID.value == purchaseOrderArray(3).purchaseOrderID.value)
        bool = true
      else
        bool = false
    }
  }
  
  def testFindByPurchaseOrderID{
    
    "The findByPurchaseOrderID method" should "not return a null value" in{
      
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      purchaseOrderSQL.findByPurchaseOrderID(1) should not be (null)
    }
    it should "return the purchase order with purchase id 2" in{
      
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      purchaseOrderSQL.findByPurchaseOrderID(2).purchaseOrderID.value should be(2)
    }
    it should "return a purchase order with purchase id 999999999" in{
      
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      purchaseOrderSQL.findByPurchaseOrderID(1002).purchaseOrderID.value should be(999999999)
    }
  }
  
  def testFindByEmployeeID{
    "The findByEmployeeID method" should "not return a null value" in{
      
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      purchaseOrderSQL.findByEmployeeID(1) should not be (null)
    }
    it should "return the purchase orders with employee id 2" in{
          
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      val purchaseOrders = purchaseOrderSQL.findByEmployeeID(2)
      
      purchaseOrders(0).employeeID.value should be (2)
    }
    it should "return no purchase orders with an employee id 999999999" in{
      
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      val purchaseOrders = purchaseOrderSQL.findByEmployeeID(999999999)
      
      purchaseOrders.length should be (0)
    }
  }
  
  def testClaimPurchaseOrder{
    "The claimPurchaseOrder method" should "update the correct item within the table" in{
      
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      purchaseOrderSQL.claimPurchaseOrder(1, 1)
      
      purchaseOrderSQL.findByPurchaseOrderID(1).employeeID.value should be(1)
      
    }
    it should "update the employeeID to 3" in{
       val purchaseOrderSQL = new PurchaseOrderSQL
      
      purchaseOrderSQL.claimPurchaseOrder(3, 1)
      
      purchaseOrderSQL.findByPurchaseOrderID(1).employeeID.value should be(3)
    }   
  }
  
  def testUpdateOrderStatus{
    "The updateOrderStatus method" should "update the correct item within the table" in{
      
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      purchaseOrderSQL.updateOrderStatus(1, "Testing")
      
      purchaseOrderSQL.findByPurchaseOrderID(1).orderStatus.value should be ("Testing")
    }
    it should "update the order status to Processing" in{
      
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      purchaseOrderSQL.updateOrderStatus(1, "Processing")
      
      purchaseOrderSQL.findByPurchaseOrderID(1).orderStatus.value should be ("Processing")
    }
  }
  
  def testAddOrder{
    "The addOrder method" should "insert into the correct table" in{
      
      val purchaseOrderSQL = new PurchaseOrderSQL
      
      val purchaseOrder = new PurchaseOrder(100, "16/11/2016", "Order Sent", 4)
      
      purchaseOrderSQL.addOrder(purchaseOrder)
      
      purchaseOrderSQL.findByPurchaseOrderID(100).purchaseOrderID.value should be (100)      
      
    }

  }
  
  testFindAllPurchaseOrders
  testFindByPurchaseOrderID
  testFindByEmployeeID
  testClaimPurchaseOrder
  testUpdateOrderStatus
  testAddOrder 
}