package tetravex.view

import scala.swing._
import scala.swing.Swing._
import scala.swing.event._
import java.awt.Graphics2D

import tetravex.model.Model
import tetravex.core.Grid

//(capitalized-words-mode 't)

object FirstSwingApp extends SimpleSwingApplication {
  
  
  def top = new MainFrame {
    title = "First Swing App"
    val g = Grid.generate(3,3, "right")
    val r = g.rand
    View.init(r)
    Model.init(g, r)
    contents = View

  }

}
