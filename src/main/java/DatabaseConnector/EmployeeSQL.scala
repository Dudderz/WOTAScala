package DatabaseConnector

import Entities.Employee
import java.sql.SQLException
import java.sql.Connection
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 * 
 * @description Class that pulls information from the employee database
 * 
 */
class EmployeeSQL {

  val dbConnection = new DBConnector()
  
  /**
   * @return : ObservableBuffer of Employees
   * 
   * Finds all employees within the SQL database 
   * and return them as an ObservableBuffer
   */
  
  def findAllEmployee() : ObservableBuffer[Employee] = 
  {
    val employeeArray : ObservableBuffer[Employee] = ObservableBuffer[Employee]()
    
    val resultSet = dbConnection findAllSQL("SELECT * FROM employee")

    def getRSData()
    {
     if (resultSet next())
      {
        employeeArray += new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4))
        getRSData
      }
    }
   
    getRSData

    dbConnection closeConnection
    
    employeeArray
  }
  
  /**
   * @return : Employee 
   * @param : employeeUserName 
   * 
   * returns the employee with the given username
   */
  
  def findByEmployeeUsername(employeeUsername : String) : Employee = 
  {
    var employee = new Employee(99999999, "", "", "")
    
    try
    {
      val connection : Connection = dbConnection connect()
      
      val statement = connection createStatement
      val resultSet = statement executeQuery("SELECT * FROM employee WHERE username= '" + employeeUsername + "'")
    
      def getRSData()
      {
         if (resultSet next())
         {
            employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4))
            getRSData
         }
      }
   
      getRSData
    
    } catch {
      case e : SQLException => e printStackTrace
    }
    
    dbConnection closeConnection
    
    employee
  }
  
}