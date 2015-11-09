package DatabaseConnector

import Entities.Employee
import java.sql.SQLException
import java.sql.Connection
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class EmployeeSQL {

  val dbConnection = new DBConnector()
  
  def findAllEmployee() : ObservableBuffer[Employee] = 
  {
    val employeeArray : ObservableBuffer[Employee] = ObservableBuffer[Employee]()
    
    try
    {
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement
      val resultSet = statement executeQuery("SELECT * FROM employee")
      
      while(resultSet next())
      {
        employeeArray += new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4))
      }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    dbConnection closeConnection
    
    employeeArray
    
  }
  
  def findByEmployeeUsername(employeeUsername : String) : Employee = 
  {
    var employee = new Employee(99999999, "", "", "")
    
    try
    {
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement
      val resultSet = statement executeQuery("SELECT * FROM employee WHERE username= '" + employeeUsername + "'")
    
      while(resultSet next())
      {
        employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4))
      }
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    dbConnection closeConnection
    
    employee
  }
  
}