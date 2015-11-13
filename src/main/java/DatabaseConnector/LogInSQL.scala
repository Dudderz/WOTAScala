package DatabaseConnector

import Entities.Employee

import java.sql.Connection
import java.sql.SQLException
import scala.collection.mutable.ArrayBuffer


/**
 * @author tdudley
 * 
 * @param : username the employees username who will be logging into the system
 * @param : password the employees password who will be logging into the system
 * 
 * Class will hold all the SQL and scala code relevant for the employee logging
 * into the system
 */

class LogInSQL(val username : String, val password : String) {
  
  var employeeUsernames= new ArrayBuffer[String](10)
  var employeePasswords= new ArrayBuffer[String](10)
  
  val dbConnection = new DBConnector()
  
  /**
   * runs the SQL statements to get the arrays of usernames and passwords
   */
  def logIn() : Unit = 
  {
    try {
      
      val connection : Connection = dbConnection connect
      
      //Creates the statement and runs the select query
      val statement = connection createStatement
      val resultSet = statement executeQuery("SELECT username, password FROM employee")
    
      while(resultSet next)
      {
        employeeUsernames += resultSet getString("username")
        employeePasswords += resultSet getString("password")
      }
    } catch {
      case e : SQLException => e printStackTrace
    }

    dbConnection closeConnection

  }
  
  /**
   * @return : Boolean whether or not a log in has been successful
   * 
   * this runs a method simulating a for loop to loop through the 
   * user names and passwords within the array 
   */
  
  def verifyLogIn() : Boolean = 
  {   
     forLoop(0)
  }
  
  def findByEmployeeUserName(user : String) : Employee = 
  {
    var employee : Employee = new Employee(999999999, "", "", "")
    
    try{
      
      val connection : Connection = dbConnection connect
      
      val statement = connection createStatement
      val resultSet = statement executeQuery("SELECT * AS username FROM employee WHERE username= " + user)
      
      while(resultSet next)
      {
        employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4))
      }
      
    }
    catch {
      case e : SQLException => e printStackTrace
    }
    
    dbConnection closeConnection()
    employee
  }
  
  /**
   * @return : Boolean 
   * 
   * Recursive method to iterate through the employee usernames and passwords
   * Checks whether username matches any of the usernames within the array
   * and if true, checks the password at the same position in the password 
   * array to see if the password matches. Returns boolean dependant on the 
   * results
   */
  
  def forLoop(n : Int) : Boolean = 
  {
    if(n < employeeUsernames.length)
    {
      if(employeeUsernames(n) == username && employeePasswords(n) == password)
      {
        true
      }
      else
        forLoop(n + 1)
    }
    else
      false
  }
}