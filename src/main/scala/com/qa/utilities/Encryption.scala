package com.qa.utilities

import java.security.MessageDigest
import java.util.Formatter

/**
 * @author tdudley
 */

class Encryption 
{

  def hasher(password : String) : String = {
    
    //choose what type of encryption you require
    val sha256 : MessageDigest = MessageDigest.getInstance("SHA-512")
    
    val passBytes : Array[Byte] = password.getBytes()
    val passHash : Array[Byte] = sha256.digest(passBytes)   
    
    var res : String = ""
    
    def forLoop(n : Int) : String =
    {
      if(n >= passHash.length - 1)
      {
        var b : Byte = passHash(n)
        res ++= "%02x".format(b).toString()
       
        res
      }
      else
      {
        var b : Byte = passHash(n)
        res ++= "%02x".format(b).toString()
          forLoop(n + 1)
     
      }       
    }
   
    forLoop(0)
   
    res
    
  }
}