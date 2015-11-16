package Entities

import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty

/**
 * @author tdudley
 */
class CustomerOrder (val customerOrderID_ : Int, val customerOrderDate_ : String, 
    val customerOrderStatus_ : String, val employeeID_ : Int) {
    
  val customerOrderID = new ObjectProperty[Int](this, "customerOrderID", customerOrderID_)
  val employeeID = new ObjectProperty[Int](this, "employeeID", employeeID_)
  val customerOrderDate = new ObjectProperty[String](this, "customerOrderDate", customerOrderDate_)
  val customerOrderStatus = new ObjectProperty[String](this, "customerOrderStatus", customerOrderStatus_)
  
}