package DatabaseConnectionTests

import com.qa.wota.UnitSpec

import com.qa.wota.UnitSpec
import com.qa.databaseconnector.{DBConnector, EmployeeSQL}
import java.sql.{DriverManager, SQLException}
import java.sql.ResultSet

import com.qa.entities.Employee
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class EmployeeSQLTests extends UnitSpec 
{
  def testFindAllEmployee{
    "The findAllEmployee method" should "not return null" in{
      
      val employeeSQL = new EmployeeSQL
      
      val results = employeeSQL findAllEmployee
      
      assert(results != null)
    }
    
    it should "return all employees within the database" in {
      val employeeSQL = new EmployeeSQL
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.findAllSQL("SELECT * FROM employee")
      
      val results = employeeSQL findAllEmployee
      
      val employeeArray : ObservableBuffer[Employee] = ObservableBuffer[Employee]()
      
      def getData(rs : ResultSet, employee : ObservableBuffer[Employee]) : Unit =
      {
        def getRSData()
        {
         if (rs next())
         {
          employee += new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4))
          getRSData
         }
        }
         getRSData
      }
      
      getData(resultSet, employeeArray)

      if(results.length == employeeArray.length ) assert(true) else assert(false)

    }
    
    it should "return the correct data" in{
      val employeeSQL = new EmployeeSQL
      
      val dbConnection = new DBConnector
      
      val resultSet = dbConnection.findAllSQL("SELECT * FROM employee")
      
      val results = employeeSQL findAllEmployee
      
      val employeeArray : ObservableBuffer[Employee] = ObservableBuffer[Employee]()
      
      def getData(rs : ResultSet, employee : ObservableBuffer[Employee]) : Unit =
      {
        def getRSData()
        {
         if (resultSet next())
         {
          employeeArray += new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4))
          getRSData
         }
        }
   
        getRSData
      }
      
      getData(resultSet, employeeArray)

      if(results(1).employeeID.value == employeeArray(1).employeeID.value
          && results(3).employeeID.value == employeeArray(3).employeeID.value)

        assert(true) else assert(false)

    }
  }
  
  def testFindByEmployeeUsername{
    "The findByEmployeeUsername method" should "not return null with employee username benback" in{
      val employeeSQL = new EmployeeSQL
      
      val results = employeeSQL findByEmployeeUsername("benback")
      
      assert(results != null)
    }
    it should "return the employee with username benback" in{
      val employeeSQL = new EmployeeSQL
      
      val results = employeeSQL findByEmployeeUsername("benback")
      
      results.username.value should be("benback")
    }
    it should "return no employee with a username of test" in{
      
      val employeeSQL = new EmployeeSQL
      
      val results = employeeSQL findByEmployeeUsername("test")
      
      results.employeeID.value should be (99999999)
    }
  }
  
  testFindByEmployeeUsername
  testFindAllEmployee
  
}