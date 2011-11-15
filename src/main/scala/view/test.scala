package tetravex.view

import scala.swing._
import scala.swing.Swing._
import scala.swing.event._
import java.awt.Graphics2D
import java.awt.event.{KeyEvent, KeyListener}

import tetravex.model.Model
import tetravex.core.{Grid, Generator}


object Tetravex extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "tetravex"
    
    Model.newGame(3, Generator.defaultRange)

    contents = View

    override def closeOperation {
      Model.close
      sys.exit(0)
    }
  }

}
