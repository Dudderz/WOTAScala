package com.qa.databaseconnectiontests

import com.qa.wota.UnitSpec

import com.qa.databaseconnector.{DBConnector, ProductSQL}
import java.sql.{DriverManager, SQLException}
import java.sql.ResultSet
import com.qa.entities.Product
import scalafx.collections.ObservableBuffer


/**
 * @author tdudley
 */
class ProductSQLTests extends UnitSpec
{
 def testFindAllProducts{
   "The findAllProducts" should "not return null" in{
     
     val productSQL = new ProductSQL
     
     val results = productSQL findAllProducts
     
     assert(results != null)
   }
   
   it should "return all the products within the database" in{
     
     val productSQL = new ProductSQL
     
     val dbConnection = new DBConnector
     
     val resultSet = dbConnection.findAllSQL("SELECT * FROM product")
     
     val results = productSQL findAllProducts
     
     val productArray : ObservableBuffer[Product] = ObservableBuffer[Product]()
    
     def getData(rs : ResultSet, product : ObservableBuffer[Product]) : Unit =
      {
        def getRSData()
        {
         if (rs next())
         {
           //TODO fix this line
          product += new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7))
          getRSData
         }
        }
         getRSData
      }
     
     getData(resultSet, productArray)
     
     var bool : Boolean = false
     
     if(results.length == productArray.length)
       bool = true
     else
       bool = false
     
       assert(bool)
   }
   
   it should "return the correct data" in{
     val productSQL = new ProductSQL
     
     val dbConnection = new DBConnector
     
     val resultSet = dbConnection.findAllSQL("SELECT * FROM product")
     
     val results = productSQL findAllProducts
     
     val productArray : ObservableBuffer[Product] = ObservableBuffer[Product]()
    
     def getData(rs : ResultSet, product : ObservableBuffer[Product]) : Unit =
      {
        def getRSData()
        {
         if (rs next())
         {
          product += new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7))
          getRSData
         }
        }
         getRSData
      }
     
     getData(resultSet, productArray)
     
     var bool : Boolean = false
     
     if(results(1).productID.value == productArray(1).productID.value
         && results(4).productID.value == productArray(4).productID.value)
       bool = true
     else
       bool = false
     
       assert(bool)
   }
   
   
 }
 
 def testFindByProductID{
   
   "The findByProductID " should "not return a null value" in{
   
    val productSQL = new ProductSQL
   
    productSQL.findByProductID(1).productID should not be (null)
   }
   it should "return the product with product id 2" in{
     val productSQL = new ProductSQL
     
     productSQL.findByProductID(2).productID.value should be (2)
   }
   it should "return a product with product id 999999999" in{
     
     val productSQL = new ProductSQL
     
     productSQL.findByProductID(20002).productID.value should be (99999999)
   } 
 }
 
   testFindAllProducts
   testFindByProductID
   
}