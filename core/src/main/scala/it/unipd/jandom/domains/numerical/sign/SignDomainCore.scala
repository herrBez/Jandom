package it.unipd.jandom.domains.numerical.sign

import it.unipd.jandom.domains.{Abstraction, CompleteLatticeOperator, IntOperator}
import Sign._

/**
  * Operations on the (basic) sign domain.
  *
  * @author Mirko Bez <mirko.bez@studenti.unipd.it>
  * @author Stefano Munari <stefano.munari@studenti.unipd.it>
  * @author Sebastiano Valle <sebastiano.valle@studenti.unipd.it>
  */
object SignDomainCore extends CompleteLatticeOperator[Sign] with IntOperator[Sign] with Abstraction[Int, Sign] {

  /**
    * @inheritdoc
    */
  override def alpha(n : Int) : Sign =
    if(n < 0)
      Minus
    else if(n == 0)
      Zero
    else
      Plus

  /**
    * Factory method for signs.
    *
    * @param num number that has to be converted to sign
    * @return sign of `num`
    */
  // TODO: Stale - keep or dispose?
  def toSign(num : Double): Sign =
    if(num > 0)
      Plus
    else if(num < 0)
      Minus
    else
      Zero

  /**
    * @inheritdoc
    */
  def sum(s: Sign, t: Sign) : Sign =
    (s,t) match {
      case (SignBottom, _) => SignBottom
      case (_, SignBottom) => SignBottom
      case (SignTop, _) => SignTop
      case (_, SignTop) => SignTop
      case (a, Zero) => a
      case (Zero, a) => a
      case (Plus, Plus) => Plus
      case (Minus, Minus) => Minus
      case _ => SignTop
    }

  /**
    * @inheritdoc
    */
  def mult(s: Sign, t : Sign) : Sign =
    (s,t) match {
      case (SignBottom, _) => SignBottom
      case (_, SignBottom) => SignBottom
      case (_, Zero) => Zero
      case (Zero, _) => Zero
      case (SignTop, _) => SignTop
      case (_, SignTop) => SignTop
      case (a, b) => if(a == b) Plus else Minus
    }

  /**
    * @inheritdoc
    */
  def inverse(s: Sign) : Sign =
    s match {
      case Plus => Minus
      case Minus => Plus
      case a => a
    }

  /**
    * @inheritdoc
    */
  def division(s : Sign, t : Sign) : Sign =
    (s, t) match {
      case (SignBottom, _) => SignBottom
      case (_, SignBottom) => SignBottom
      case (_, Zero) => SignBottom
      case (SignTop, _) => SignTop
      case (Zero, _) => Zero
      case (_, SignTop) => SignTop
      case _ => SignTop
    }

  /**
    * @inheritdoc
    */
  def remainder(s : Sign, t : Sign) : Sign =
    (s,t) match {
      case (SignBottom, _) => SignBottom
      case (_, SignBottom) => SignBottom
      case (_, Zero) => SignBottom
      case (Zero, _) => Zero
      case (_, _) => SignTop
    }

  /**
    * @inheritdoc
    */
  def lub(s : Sign, t : Sign) : Sign =
    (s, t) match {
      case (SignTop, _) => SignTop
      case (_, SignTop) => SignTop
      case (SignBottom, a) => a
      case (a, SignBottom) => a
      case (a, b) => if (a == b) a else SignTop
    }

  /**
    * @inheritdoc
    */
  def glb(s : Sign, t : Sign) : Sign =
      (s,t) match {
        case (SignTop, a) => a
        case (a, SignTop) => a
        case (_, SignBottom) => SignBottom
        case (SignBottom, _) => SignBottom
        case (a, b) => if(a == b) a else SignBottom
      }

  /**
    * @inheritdoc
    */
  def compare(s: Sign, t:Sign): Option[Int] =
    (s, t) match {
      case (SignTop, SignTop) => Option(0)
      case (SignTop, _) => Option(1)
      case (_, SignTop) => Option(-1)
      case (SignBottom, SignBottom) => Option(0)
      case (SignBottom, _) => Option(-1)
      case (_, SignBottom) => Option(1)
      case (a, b) => if (a.equals(b)) Option(0) else Option.empty
    }

  /**
    * @inheritdoc
    */
  override def top: Sign = SignTop

  /**
    * @inheritdoc
    */
  override def bottom: Sign = SignBottom

} // end object SignDomainCore
