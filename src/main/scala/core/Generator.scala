package tetravex.core

import scala.util.Random

object Generator {
  val r = Random
  
  def next() = (r.nextInt.abs % 10)
}
