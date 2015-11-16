package com.qa.databaseconnectiontests

import com.qa.wota.UnitSpec

import com.qa.wota.UnitSpec
import com.qa.databaseconnector.{DBConnector, PurchaseOrderLineSQL}
import java.sql.{DriverManager, SQLException}
import java.sql.ResultSet

import com.qa.entities.PurchaseOrderLine
import scalafx.collections.ObservableBuffer

/**
 * @author Tom
 */
class PurchaseOrderLineSQLTests extends UnitSpec
{
  def testFindAllPurchaseOrderLine{
    "The findAllPurchaseOrderLine method" should "not return null" in{
      
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
      
      val results = purchaseOrderLineSQL findAllPurchaseOrderLines
      
      assert(results != null)
    }
    
    it should "return all the order lines within the database" in{
      
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.findAllSQL("SELECT * FROM purchaseorderline")
      
      val results = purchaseOrderLineSQL findAllPurchaseOrderLines
      
      val purchaseOrderLineArray : ObservableBuffer[PurchaseOrderLine] = ObservableBuffer[PurchaseOrderLine]()
      
      def getData(rs : ResultSet, purcOrder : ObservableBuffer[PurchaseOrderLine]) : Unit =
      {
        def getRSData()
        {
         if (rs next())
         {
          purcOrder += new PurchaseOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
          getRSData
         }
        }
         getRSData
      }
      
      getData(resultSet, purchaseOrderLineArray)

      if(results.length == purchaseOrderLineArray.length) assert(true) else assert(false)

    }
    
    it should "return the correct data" in{
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.findAllSQL("SELECT * FROM purchaseorderline")
      
      val results = purchaseOrderLineSQL findAllPurchaseOrderLines
      
      val purchaseOrderLineArray : ObservableBuffer[PurchaseOrderLine] = ObservableBuffer[PurchaseOrderLine]()
      
      def getData(rs : ResultSet, purcOrder : ObservableBuffer[PurchaseOrderLine]) : Unit =
      {
        def getRSData()
        {
         if (rs next())
         {
          purchaseOrderLineArray += new PurchaseOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
          getRSData
         }
        }
         getRSData
      }
      
      getData(resultSet, purchaseOrderLineArray)

      if(results(1).purchaseOrderID.value == purchaseOrderLineArray(1).purchaseOrderID.value
          && results(3).purchaseOrderID.value == purchaseOrderLineArray(3).purchaseOrderID.value)
        
        assert(true) else assert(false)
    }
  }
  
  def testFindByPurchaseOrderID{
    "The findByPurchaseOrderID" should "not return null with purchaseOrderID 1" in
    {
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
      
      val results = purchaseOrderLineSQL findByPurchaseOrderID(1)
      
      assert(results != null)
    }
    it should "return the orders with the purchaseOrderID of 1" in{
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
      
      val results = purchaseOrderLineSQL findByPurchaseOrderID(1)
      
      results(0).purchaseOrderID.value should be(1)
    }
    it should "return no purchase orders with an employee id 999999999" in{
      
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
      
      val purchaseOrders = purchaseOrderLineSQL.findByPurchaseOrderID(999999999)
      
      purchaseOrders.length should be (0)
    }
  }
  
  def testFindByProductID{
   "The findByProductID" should "not return null with productID 1" in
    {
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
     
      val results = purchaseOrderLineSQL findByProductID(1)
      
      assert(results != null)
    }
    it should "return the orders with the productID of 1" in{
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
     
      val results = purchaseOrderLineSQL findByProductID(1)

      results(0).productID.value should be(1)
    }
    it should "return no purchase orders with a product id 999999999" in{
      
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
     
      val purchaseOrders = purchaseOrderLineSQL.findByProductID(999999999)
      
      purchaseOrders.length should be (0)
    }
  }
    
  def testFindByPurchaseOrderLineID{
   "The findByPurchaseOrderLineID" should "not return null with purchaseOrderLineID of 1" in
    {
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
     
      val results = purchaseOrderLineSQL findByPurchaseOrderLineID(1)
      
      assert(results != null)
    }
    it should "return the orders with the productID of 1" in{
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
     
      val results = purchaseOrderLineSQL findByPurchaseOrderLineID(1)
      
      results.purchaseOrderLineID.value should be(1)
      
    }
    it should "return no purchase orders with a product id 999999999" in{
      
      val purchaseOrderLineSQL = new PurchaseOrderLineSQL
     
      val purchaseOrder = purchaseOrderLineSQL.findByPurchaseOrderLineID(50)

      purchaseOrder.purchaseOrderLineID.value should be (99999999)
    }
  }
  
  testFindAllPurchaseOrderLine
  testFindByPurchaseOrderID
  testFindByProductID
  testFindByPurchaseOrderLineID
}