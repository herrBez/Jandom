/**
 * Copyright 2013 Gianluca Amato <gamato@unich.it>
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

package it.unich.sci.jandom.domains

/**
 * A `DimensionFiberedProperty` is an element of a `DimensionFiberedDomain`. Each fiber is characterized by
 * a natural number called `size`, which is the number of dimensions in the object. There are many methods
 * to add and remove dimensions.
 * @author Gianluca Amato <gamato@unich.it>
 */
trait DimensionFiberedProperty[Property <: DimensionFiberedProperty[Property]] <: AbstractProperty[Property] {
  this: Property =>

  /**
   * Returns the dimension of the current object
   */
  def dimension: Int

  /**
   * Add a new variable
   */
  def addVariable(): Property

  /**
   * Add m new variables
   * @note `m` should be positive
   */
  def addVariables(m: Int): Property = {
    require (m >= 0)
    (0 until m).foldLeft(this) ( (p,i) => p.addVariable() )
  }

  /**
   * Remove variable `v`
   * @param v variable to remove
   * @note `v` should be between 0 and `dimension`-1
   */
  def delVariable(v: Int = dimension - 1): Property

  /**
   * Remove variables in `vs`
   * @note `vs` should contain integers between 0 and `dimension`-1 without repetitions.
   */
  def delVariables(vs: Seq[Int]): Property = {
    vs.sortWith( _ > _ ).foldLeft(this) ( (p,i) => p.delVariable(i) )
  }
}
