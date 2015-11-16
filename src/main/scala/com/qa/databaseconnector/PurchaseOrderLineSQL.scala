
package com.qa.databaseconnector

import com.qa.entities.PurchaseOrderLine
import java.sql.SQLException
import java.sql.Connection
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class PurchaseOrderLineSQL {
  
  val dbConnection = new DBConnector()
      
  /**
   * @return : ObservableBuffer[PurchaseOrderLine]
   * 
   * Finds all customer orders within the SQL database 
   * and returns them as an ObservableBuffer
   */
  
  def findAllPurchaseOrderLines() : ObservableBuffer[PurchaseOrderLine] = 
  {
    val purchaseOrderLineArray : ObservableBuffer[PurchaseOrderLine] = ObservableBuffer[PurchaseOrderLine]()
    
    val resultSet = dbConnection findAllSQL("SELECT * FROM purchaseorderline")
   
    def getRSData()
    {
     if (resultSet next())
     {
       purchaseOrderLineArray += new PurchaseOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
       getRSData
     }
    }

    getRSData
    
    dbConnection closeConnection
    
    purchaseOrderLineArray
  }
  
   /**
   * @Return : PurchaseOrder with the given purchase id
   * @Param  : purchaseID, used to find selected orders with this purchaseID
   */
  
  def findByPurchaseOrderID(purchaseID : Int) : ObservableBuffer[PurchaseOrderLine] = 
  {
    var purchaseOrderLineArray : ObservableBuffer[PurchaseOrderLine] = ObservableBuffer[PurchaseOrderLine]()
    
    val resultSet = dbConnection runSQLQuery("SELECT * FROM purchaseorderline WHERE purchase_order_id = ", purchaseID)
    
    def getRSData()
    {
     if (resultSet next())
     {
       purchaseOrderLineArray += new PurchaseOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
       getRSData
     }
    }
    
    getRSData

    dbConnection closeConnection() 
    purchaseOrderLineArray
  }
  
  /**
   * @Return : ObservableBuffer of PurchaseOrder with the given productID
   * @Param : productID, used to find selected orders with this productID
   */
  
  def findByProductID(productID : Int) : ObservableBuffer[PurchaseOrderLine] = 
  {
    val purchaseOrderLineArray : ObservableBuffer[PurchaseOrderLine] = ObservableBuffer[PurchaseOrderLine]()
    
    val resultSet = dbConnection runSQLQuery("SELECT * FROM purchaseorderline WHERE product_id =  ", productID)
    
    def getRSData()
    {
     if (resultSet next())
     {
       purchaseOrderLineArray += new PurchaseOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
       getRSData
     }
    }

    getRSData
    
    dbConnection closeConnection
    
    purchaseOrderLineArray
  }
  
  /**
   * @Return : PurchaseOrderLine with the given orderLineID
   * @Param : orderLineID, used to find selected orders with this orderLineID
   */
  
  def findByPurchaseOrderLineID(orderLineID : Int) : PurchaseOrderLine = 
  {
    var purchaseOrderLine : PurchaseOrderLine = new PurchaseOrderLine(99999999, 0, 0, 0)
    
    val resultSet = dbConnection runSQLQuery("SELECT * FROM purchaseorderline WHERE purchase_orderline_id =", orderLineID)
    
    def getRSData()
    {
     if (resultSet next())
     {
       purchaseOrderLine = new PurchaseOrderLine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4))
       getRSData
     }
    }

    getRSData

    dbConnection closeConnection
    
    purchaseOrderLine
  }
  
  /**
   * @Param : purchaseOrder, that is going to be added to the database
   */
  
  def addOrderLine(purchaseOrder : PurchaseOrderLine) : Unit = 
  {
    try
    {
      val connection : Connection = dbConnection connect
      val statement = connection createStatement 
      val resultSet = statement executeUpdate("INSERT INTO `purchaseorderline` (`purchase_orderline_id`, `quantity`, `purchase_order_id`, `product_id`) VALUES ('" + purchaseOrder.purchaseOrderLineID.value.toInt + "', '" + purchaseOrder.quantity.value.toInt + "', '" + purchaseOrder.purchaseOrderID.value.toInt + "', '" + purchaseOrder.productID.value.toInt + "')")
    }
    catch
    {
      case e : SQLException => e printStackTrace
    }
    
    dbConnection closeConnection
  }

  
}