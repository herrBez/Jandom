/**
 * Copyright 2014 Gianluca Amato, Francesca Scozzari, Simone Di Nardo Di Maio
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

package it.unich.jandom.domains.numerical

/**
 * This is the class which implements the sum of two generic numerical domains,
 * without any particular herustics.
 * @author Gianluca Amato
 * @author Francesca Scozzari
 * @author Simone Di Nardo Di Maio
 */

class SumGenericDomain[D1 <: NumericalDomain, D2 <: NumericalDomain](val dom1: D1, val dom2: D2) extends SumDomain[D1,D2] {
  type Property = SumGeneric
  
  class SumGeneric(val p1: dom1.Property, val p2: dom2.Property) extends Sum
  
  def apply(p1: dom1.Property, p2: dom2.Property) = new SumGeneric(p1, p2)
}