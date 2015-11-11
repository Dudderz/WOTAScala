package Entities

import scalafx.beans.property.ObjectProperty

/**
 * @author tdudley
 */
class PurchaseOrderLine (val purchaseOrderLineID_ : Int, val quantity_ : Int,
    val purchaseOrderID_ : Int, val productID_ : Int ){
  
  val purchaseOrderLineID = new ObjectProperty[Int](this, "purchaseOrderLineID", purchaseOrderLineID_)
  val quantity = new ObjectProperty[Int](this, "quantity", quantity_)
  val purchaseOrderID = new ObjectProperty[Int](this, "purchaseOrderID", purchaseOrderID_)
  val productID = new ObjectProperty[Int](this, "productID", productID_)
  
  
}