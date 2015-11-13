package DatabaseConnector

import java.sql.DriverManager
import java.sql.Connection
import java.sql.SQLException
import java.sql.ResultSet

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
  
  def findAllSQL(query : String) : ResultSet = 
  {
    try{
      
      val connection : Connection = connect
      
      val statement = connection createStatement
      val resultSet = statement executeQuery(query)
      
      //closeConnection
      
      resultSet

    }
    catch 
    {
      case e : SQLException => e.printStackTrace
      null
    }
  }
  
  def runSQLQuery(query : String, id : Int) : ResultSet = 
  {
      
    try{
      
      val connection : Connection = connect
      
      val statement = connection createStatement
      val resultSet = statement executeQuery(query + id)
      
      closeConnection
      
      resultSet

    }
    catch 
    {
      case e : SQLException => e.printStackTrace
      null
    }

  }
  
  def runSQLUpdateInt(update : String, where : String,  id1 : Int, id2 : Int) : Unit = 
  {
      
    try{
      
      val connection : Connection = connect
      
      val statement = connection createStatement
      val resultSet = statement executeUpdate(update + id1 + where + id2)

    }
    catch 
    {
      case e : SQLException => e.printStackTrace
    }

  }
  
  def runSQLUpdateString(update : String, where : String, status : String, id : Int) : Unit = 
  {
    try{
      
      val connection : Connection = connect
      
      val statement = connection createStatement
      val resultSet = statement executeUpdate(update + status + where + id)

    }
    catch 
    {
      case e : SQLException => e.printStackTrace
    }
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