package tetravex.view

import scala.swing._
import scala.swing.event._

import tetravex.controler.Controler
import tetravex.core.Generator

class Menu extends BoxPanel(Orientation.Horizontal) {

  contents += new Button { text = "3x3" }
  contents += new Button { text = "4x4" }
  contents += new Button { text = "5x5" }
  contents += new Button { text = "6x6" }
  contents += new Button { text = "7x7" }
  contents += new Button { text = "8x8" }
  contents += new TextField { text = Generator.defaultRange }

  contents.foreach((i => listenTo(i)))

  reactions += {
    case ButtonClicked(b) => Controler.buttonClick(b.text.head.toString.toInt, contents.last match { case t: TextField => t.text; case _ => Generator.defaultRange})
  }
}
