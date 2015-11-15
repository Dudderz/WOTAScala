package DatabaseConnectionTests

import com.qa.wota.UnitSpec
import DatabaseConnector.DBConnector
import java.sql.{DriverManager, SQLException}


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
      
      /*intercept[SQLException]
      {
        val dbConnector = new DBConnector
        dbConnector.connect
        cancel("Test cannot run - cannot create an SQL connection")
      }*/
      
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
  
  testGetConnection
  testCloseConnection
  
}