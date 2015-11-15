package DatabaseConnector

import Entities.Employee

import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import scala.collection.mutable.ArrayBuffer
import scalafx.collections.ObservableBuffer


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
    
  /**
   * runs the SQL statements to get the arrays of usernames and passwords
   * 
   * @return : Boolean 
   * 
   * Recursive method to iterate through the employee usernames and passwords
   * Checks whether username matches any of the usernames within the array
   * and if true, checks the password at the same position in the password 
   * array to see if the password matches. Returns boolean dependant on the 
   * results
   */

  def logIn() : Boolean = 
  {
    val dbConnection = new DBConnector
    
    val employeeSQL = new EmployeeSQL
    
    val employees = employeeSQL.findAllEmployee()

    dbConnection closeConnection
    
    def forLoop(n : Int) : Boolean = 
    {
    if(n < employees.length)
    {
      if(employees(n).username.value == username && employees(n).password.value == password)
      {
        true
      }
      else
        forLoop(n + 1)
    }
    else
      false
    }
    
    val bool = forLoop(0)
    
    bool
    
  }

}