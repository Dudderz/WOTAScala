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
  
  def connect()
  {
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/mydb"
    val username = "root"
    val password = "user"
  
    var connection:Connection = null
  
    try {
    //Makes the initial connection
    Class forName(driver)
    connection = DriverManager getConnection(url, username, password)
    
    
    //Creates the statement and runs the select query
    val statement = connection createStatement()
    val resultSet = statement executeQuery("SELECT employee_name FROM employee")
    
    while(resultSet next()){
      val employee = resultSet getString("employee_name")
      println("Employee name =  " + employee)
    }
   } catch {
     case e => e printStackTrace
   }
    
   connection close()
  }

}