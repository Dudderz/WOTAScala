package DatabaseConnector

import java.sql.DriverManager
import java.sql.Connection

/**
 * @author tdudley
 * 
 * Scala JDBC Connector
 *  
 * */
class DBConnector {
  
  var connection:Connection = null
  
  def connect() : Connection =
  {
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/mydb"
    val username = "root"
    val password = "user"
  
    try {
    
      //Makes the initial connection
      Class forName(driver)
      connection = DriverManager getConnection(url, username, password)
    
    
    } catch {
     case e : Throwable => e printStackTrace
    }
    
   connection
  }

  /*
   * closes the connection to the database
   */
  def closeConnection() : Unit =
  {
    if(connection != null)
    {
      connection close
    }
  }
  
}