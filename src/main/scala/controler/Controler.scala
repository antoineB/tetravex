package tetravex.controler

import scala.swing._

import tetravex.model.Model

object Controler {

  def click(pos: (Int, Int), name: String) {
    Model.click(pos, name)
  }

  def buttonClick(nb: Int, range: String) {
    Model.newGame(nb, range)
  }

}
