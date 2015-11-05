package DatabaseConnector

import java.sql.Connection
import java.sql.SQLException
import scala.collection.mutable.ArrayBuffer


/**
 * @author tdudley
 * 
 * Class will hold all the SQL and scala code relevant for the employee logging
 * into the system
 */

class LogInSQL(val username : String, val password : String) {

  
  var employeeUsernames= new ArrayBuffer[String](10)
  var employeePasswords= new ArrayBuffer[String](10)
  
  def logIn() : Unit = 
  {
   
    
    try {
      
      val dbConnection = new DBConnector()
      
      val connection : Connection = dbConnection connect()
      
      //Creates the statement and runs the select query
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT username FROM employee")
    
      while(resultSet next())
      {
        employeeUsernames += resultSet getString("username")
      }
    } catch {
      case e : SQLException => e printStackTrace
    }

    println(employeeUsernames length)

  }
  
  def verifyLogIn() : Boolean = 
  {   
    forLoop(employeeUsernames length)
    
    true
  }
  
  def forLoop(n : Int) : Boolean = 
  {
    if(n <= 1)
    {
      if(employeeUsernames(n) == username)
      {
        if(employeePasswords(n) == password)
        {
          true  
        }
        else
          false
      }
      
      else
      {
        false
      }
    }
    else
    {
      if(employeeUsernames(n) == username)
      {
        if(employeePasswords(n) == password)
        {
          true  
        }
        else
        {
          forLoop(n - 1)
        }
      }

      else
      {
         forLoop(n - 1)
      }
    }      
  }
}