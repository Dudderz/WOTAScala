package Entities

import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty

/**
 * @author tdudley
 */
class PurchaseOrder (val purchaseOrderID_ : Int, val purchaseOrderDate_ : String,
    val orderStatus_ : String, val employeeID_ : Int) {
  
  val purchaseOrderID = new ObjectProperty[Int](this, "purchaseOrderID", purchaseOrderID_)
  val purchaseOrderDate = new ObjectProperty[String](this, "purchaseOrderDate", purchaseOrderDate_)
  val orderStatus = new ObjectProperty[String](this, "orderStatus", orderStatus_)
  val employeeID = new ObjectProperty[Int](this, "employeeID", employeeID_)
  
}