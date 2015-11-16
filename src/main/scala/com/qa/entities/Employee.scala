package com.qa.entities

import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty

/**
 * @author tdudley
 */
class Employee(val employeeID_ : Int, val employeeName_ : String, 
    val username_ : String, val password_ : String) 
{
  val employeeID = new ObjectProperty[Int](this, "employeeID", employeeID_)
  val employeeName = new ObjectProperty[String](this, "employeeName", employeeName_)
  val username = new ObjectProperty[String](this, "username", username_)
  val password = new ObjectProperty[String](this, "password", password_)
}