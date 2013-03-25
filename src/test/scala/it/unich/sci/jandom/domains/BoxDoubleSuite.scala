/**
 * Copyright 2013 Gianluca Amato
 * 
 * This file is part of JANDOM: JVM-based Analyzer for Numerical DOMains
 * JANDOM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JANDOM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty ofa
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JANDOM.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.unich.sci.jandom
package domains

import org.scalatest.FunSuite

/**
 * This is a unit test for the BoxDouble numerical domain.
 * @author Gianluca Amato <amato@sci.unich.it>
 *
 */
class BoxDoubleSuite extends FunSuite {
    
    test("constructors should only work with normalized bounds")  {
      intercept[IllegalArgumentException] { BoxDouble(Array(0,2),Array(0,2,3)) }
      intercept[IllegalArgumentException] { BoxDouble(Array(Double.PositiveInfinity,2),Array(0,2,3)) }
    }
           
    test("operations on boxes") {
    	val i = BoxDouble(Array(1,2),Array(5,4))
        val j = BoxDouble(Array(0,3),Array(3,4))        
        expectResult(BoxDouble(Array(0,2),Array(5,4))) { i union j }     
    	expectResult( BoxDouble(Array(1,3),Array(3,4))) { i intersection j }    	
    } 
            
    test("empty boxes") {
      val i = BoxDouble(Array(-1,-2),Array(-4,3))
      val j = BoxDouble(Array(0,0),Array(5,5))
      expectResult(BoxDouble.empty(2)) { i }
      expectResult(j) { i union j }
      expectResult(i) { i intersection j }
      expectResult(i) { i.linearAssignment(1,Array(1,1),1) }
      expectResult(i) { i.linearAssignment(1,Array(0,0),0) }
      intercept[IllegalArgumentException] { i.linearAssignment(-1,Array(1,1),1) }
      intercept[IllegalArgumentException] { i.linearAssignment(2,Array(1,1),1) }
    }
    
    test("linear inequations") {
      val i = BoxDouble.full(2).linearInequality(Array(1,0),-3)
      val j = BoxDouble(Array(0,0),Array(5,5)).linearInequality(Array(1,1),-4)
      expectResult(BoxDouble(Array(Double.NegativeInfinity,Double.NegativeInfinity), Array(3,Double.PositiveInfinity))) { i }
      expectResult(BoxDouble(Array(0,0),Array(4,4))) { j }
    }
    
    test("non deterministic assignment") {
      val i = BoxDouble(Array(0,0),Array(5,5))
      val j = BoxDouble(Array(0,Double.NegativeInfinity), Array(5, Double.PositiveInfinity))
      val l = BoxDouble(Array(Double.NegativeInfinity,0), Array(Double.PositiveInfinity,5))
      expectResult (j) { i.nonDeterministicAssignment(1) }
      expectResult (l) { i.nonDeterministicAssignment(0) }
      intercept[IllegalArgumentException] { i.nonDeterministicAssignment(-1) }
      intercept[IllegalArgumentException] { i.nonDeterministicAssignment(2) }
    }
    
    test("dimensional variation") {
      val i = BoxDouble(Array(0,0),Array(1,2))
      val j = BoxDouble(Array(0,0,Double.NegativeInfinity),Array(1,2,Double.PositiveInfinity))
      val h = BoxDouble(Array(0,Double.NegativeInfinity),Array(1,Double.PositiveInfinity))
      expectResult (j) ( i.addDimension )
      expectResult (h) ( j.delDimension(1) )
      expectResult (i) ( j.delDimension(2) )
      intercept[IllegalArgumentException] { i.delDimension(-1) }
      intercept[IllegalArgumentException] { i.delDimension(2) }
    }
    
    
    test("string conversion") {
      val i = BoxDouble(Array(0,-1), Array(2,3))
      expectResult(Seq("0.0 <= x <= 2.0","-1.0 <= y <= 3.0")) { i.mkString(IndexedSeq("x","y")) }
      expectResult("[ 0.0 <= v0 <= 2.0 , -1.0 <= v1 <= 3.0 ]") { i.toString }
    }       
}