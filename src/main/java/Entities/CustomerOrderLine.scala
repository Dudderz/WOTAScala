package Entities

import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty

/**
 * @author tdudley
 */
class CustomerOrderLine (val customerOrderLineID_ : Int, val productID_ : Int, 
    val quantity_ : Int, val customerOrderID_ : Int)
{
  val customerOrderLineID = new ObjectProperty[Int](this, "customerOrderLineID", customerOrderLineID_)
  val productID = new ObjectProperty[Int](this, "productID", productID_)
  val quantity = new ObjectProperty[Int](this, "quantity", quantity_)
  val customerOrderID = new ObjectProperty[Int](this, "customerOrderID", customerOrderID_)
  
}