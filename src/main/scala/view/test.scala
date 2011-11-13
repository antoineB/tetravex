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
    
    Model.newGame(3)

    contents = View

  }

}
