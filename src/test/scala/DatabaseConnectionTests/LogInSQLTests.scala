package DatabaseConnectionTests

import com.qa.wota.UnitSpec

import com.qa.wota.UnitSpec
import com.qa.databaseconnector.{DBConnector, LogInSQL}
import java.sql.{DriverManager, SQLException}
import java.sql.ResultSet

import com.qa.entities.Employee
import scalafx.collections.ObservableBuffer

/**
 * @author tdudley
 */
class LogInSQLTests extends UnitSpec {
  
  def testLogIn{
    "The logIn method" should "return true with the given user details" in{
      val logInSQL = new LogInSQL("benback", "e019fcd0baab1639bc7c078872d0752adcec990c350a5230441d871ca9e2be867bdbbabbac4f833679bf31f9b95d7d1090e46d38cf43df609a3d90532a90c02b")
    
      assert(logInSQL.logIn())
    } 
    
    it should "fail with the given log in details" in{
      val logInSQL = new LogInSQL("benback", "password")
    
      assert(!logInSQL.logIn())
    }
  }
  
  testLogIn
}