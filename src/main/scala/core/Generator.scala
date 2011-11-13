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

  def color(c: Char) = new Color((16777216 / range.size) * (range.indexOf(c) + 1))

}
