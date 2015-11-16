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
  
  /**
   * @return : connection - connection to the mySQL database
   * 
   * Creates a connection to the sql database and 
   * once connected, returns the connection
   * 
   */
  
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
  
  /**
   * @param : query the sql query that the will find all of the items
   * within the given table
   * @result : ResultSet this contains the results from the sql query
   */
  
  def findAllSQL(query : String) : ResultSet = 
  {
    try{
      
      val connection : Connection = connect
      
      val statement = connection createStatement
      val resultSet = statement executeQuery(query)

      resultSet

    }
    catch 
    {
      case e : SQLException => println("SQL Exception")
      null
    }
  }
  
  /**
   * @param : query the sql query that the will find all of the items
   * within the given table
   * @param : id the given id that will be searched for within the database
   * @result : ResultSet this contains the results from the sql query
   */
  
  def runSQLQuery(query : String, id : Int) : ResultSet = 
  {
      
    try{
      
      val connection : Connection = connect
      
      val statement = connection createStatement
      val resultSet = statement executeQuery(query + id)
     
      resultSet

    }
    catch 
    {
      case e : SQLException => println("SQL Exception")
      null
    }

  }
  
  /**
   * @param : update part of the sql query that will make up the update query for 
   * within the given table
   * @param : where part of the sql query that will make up the update query for 
   * within the given table
   * @param : id1 the id that will be updated
   * @param : id2 the id that will be used to search for the item in question
   * @result : ResultSet this contains the results from the sql query
   */
  
  def runSQLUpdateInt(update : String, where : String,  id1 : Int, id2 : Int) : Unit = 
  {
      
    try{
      
      val connection : Connection = connect
      
      val statement = connection createStatement
      val resultSet = statement executeUpdate(update + id1 + where + id2)

    }
    catch 
    {
      case e : SQLException => println("SQL Exception")
    }

  }
  
 /**
   * @param : update part of the sql query that will make up the update query for 
   * within the given table
   * @param : where part of the sql query that will make up the update query for 
   * within the given table
   * @param : id1 the id that will be updated
   * @param : id2 the id that will be used to search for the item in question
   * @result : ResultSet this contains the results from the sql query
   */
  
  def runSQLUpdateString(update : String, where : String, status : String, id : Int) : Unit = 
  {
    try{
      
      val connection : Connection = connect
      
      val statement = connection createStatement
      val resultSet = statement executeUpdate(update + status + where + id)

    }
    catch 
    {
      case e : SQLException => println("SQL Exception")
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