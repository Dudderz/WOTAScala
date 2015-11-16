package com.qa.entities

import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty

/**
 * @author tdudley
 */
class Product (val productID_ : Int, val productName_ : String, val productDescription_ : String, 
    val productPrice_ : Float, val productPicture_ : String, val productType_ : String, val productQuantity_ : Int)
{
  val productID = new ObjectProperty[Int](this, "productID", productID_)
  val productName = new ObjectProperty[String](this, "productName", productName_)
  val productDescription = new ObjectProperty[String](this, "productDescription", productDescription_)
  val productPrice = new ObjectProperty[Float](this, "productPrice", productPrice_)
  val productPicture = new ObjectProperty[String](this, "productPicture", productPicture_)
  val productType = new ObjectProperty[String](this, "productType", productType_)
  val productQuantity = new ObjectProperty[Int](this, "productQuantity", productQuantity_)
  
}