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
  
  /*
   * runs the SQL statements to get the arrays of usernames and passwords
   */
  def logIn() : Unit = 
  {
    try {
      
      val dbConnection = new DBConnector()
      
      val connection : Connection = dbConnection connect()
      
      //Creates the statement and runs the select query
      val statement = connection createStatement()
      val resultSet = statement executeQuery("SELECT username, password FROM employee")
    
      while(resultSet next())
      {
        employeeUsernames += resultSet getString("username")
        employeePasswords += resultSet getString("password")
      }
    } catch {
      case e : SQLException => e printStackTrace
    }

    println(employeeUsernames length)

  }
  
  def verifyLogIn() : Boolean = 
  {   
    forLoop((employeeUsernames length) - 1)

  }
  
  /*
   * Recursive method to iterate through the employee usernames and passwords
   * Checks whether username matches any of the usernames within the array
   * and if true, checks the password at the same position in the password 
   * array to see if the password matches. Returns boolean dependant on the 
   * results
   */
  
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