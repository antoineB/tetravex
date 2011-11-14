package tetravex.core

import java.awt.Color
import scala.util.Random

object Generator {
  val r = Random
  private var range: String = null
  val defaultRange = "0123456789"
  
  def getRange = range

  def setRange(s: String) {
    if (s.size < 1)
      throw new Exception("the letters set must be > 0")

    var start = 0
    var res = false
    s.foreach((c => {start = start + 1; if (s.indexOf(c, start) != -1) res = true}))
    
    if (res == false)
      range = s
    else
      throw new Exception("2 or more occurences of a letter")
  }

  def next() = range(r.nextInt.abs % range.size)

//  def color(c: Char) = new Color((16777216 / range.size) * (range.indexOf(c) + 1))

  def color(c: Char) = {
    val v: Int = 1785 / range.size * (range.indexOf(c) + 1)

    v match {
      case i: Int if (i <= 255) => new Color(255, 0, v)
      case i: Int if (i <= 510) => new Color(255 - (v % 256), 0, 255)
      case i: Int if (i <= 765) => new Color(0, v % 256, 255)
      case i: Int if (i <= 1020) => new Color(0, 255, 255 - (v % 256))
      case i: Int if (i <= 1275) => new Color(v % 256, 255, 0)
      case i: Int if (i <= 1530) => new Color(255, 255 - (v % 256), 0)
      case _ => new Color(v % 256, v % 256, v % 256)
      //      case v <= 1785 =>
    }
  }

}
