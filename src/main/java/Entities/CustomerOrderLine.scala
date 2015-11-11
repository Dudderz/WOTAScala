package Entities

import scalafx.beans.property.ObjectProperty

/**
 * @author tdudley
 */
class CustomerOrderLine (val customerOrderLineID_ : Int, 
    val quantity_ : Int, val productID_ : Int, val customerOrderID_ : Int)
{
  val customerOrderLineID = new ObjectProperty[Int](this, "customerOrderLineID", customerOrderLineID_)
  val quantity = new ObjectProperty[Int](this, "quantity", quantity_)
  val productID = new ObjectProperty[Int](this, "productID", productID_)
  val customerOrderID = new ObjectProperty[Int](this, "customerOrderID", customerOrderID_)
  
}