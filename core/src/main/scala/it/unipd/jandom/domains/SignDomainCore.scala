package sign.main
import scala.io._


object SignFunctions extends App {

trait Sign;
case object SignPlus extends Sign;
case object SignMinus extends Sign;
case object SignZero extends Sign;
case object SignTop extends Sign;
case object SignBottom extends Sign;

class main  {
  def toSign(n : Int) : Sign = {
    if(n < 0)
      SignMinus
    else if(n == 0)
      SignZero
    else
      SignPlus
  }
  
  def sum(s: Sign, t : Sign) : Sign = {
    (s,t) match {
      case (SignBottom, _) => SignBottom
      case (_, SignBottom) => SignBottom
      case (SignTop, _) => SignTop
      case (_, SignTop) => SignTop
      case (a, SignZero) => a
      case (SignZero, a) => a
      case (SignPlus, SignPlus) => SignPlus
      case (SignMinus, SignMinus) => SignMinus
      case _ => SignTop
      
    }
  }
  
  def mult(s: Sign, t : Sign) : Sign = {
    (s,t) match {
      case (SignBottom, _) => SignBottom
      case (_, SignBottom) => SignBottom
      case (_, SignZero) => SignZero
      case (SignZero, _) => SignZero
      case (SignTop, _) => SignTop
      case (_, SignTop) => SignTop
      case (a, b) => if(a == b) SignPlus else SignMinus
      
    }
  }
  
  def inverse(s: Sign) : Sign = {
    s match {
      case SignPlus => SignMinus
      case SignMinus => SignPlus
      case a => a 
    }
  }
  
  def division(s : Sign, t : Sign) : Sign = {
    (s, t) match {
      case (SignBottom, _) => SignBottom
      case (_, SignBottom) => SignBottom
      case (_, SignZero) => SignBottom
      case (SignTop, _) => SignTop
      case (SignZero, SignTop) => SignZero
      case (_, SignTop) => SignTop
      case (a, b) => if (a == b) SignPlus else SignMinus
    }
  }

  /** JAVA CONVENTION for mod/remainder sign is to take as result
    * the sign  of the dividend
    * @param s
    * @param t
    * @return
    */
  def remainder(s : Sign, t : Sign) : Sign = {
    (s,t) match {
      case (SignBottom, _) => SignBottom
      case (_, SignBottom) => SignBottom
      case (a, _) => a
    }
  }

  /*** XOR DISCLAIMER: If we do not distinguish between >= 0, and > 0 then
    * we take top because
    * 10 ^ 10 = 0
    * 10 ^ 11 = 1
    * therefore with + + we obtain top
    */

  

  
}
}