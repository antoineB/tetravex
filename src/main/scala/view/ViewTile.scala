package tetravex.view

import scala.swing._
import scala.swing.event._
import javax.swing.border.LineBorder
import java.awt.RenderingHints
import java.awt.Font
import java.awt.GraphicsEnvironment

import tetravex.core.{Tile, AbsTile, EmptyTile, Generator}
import tetravex.controler.Controler


class ViewTile(pos: (Int, Int), grid: String) extends Panel {
  private val postiion = pos
  private val gridName = grid

  def select {
    border = new LineBorder(java.awt.Color.RED, 2)
  }

  def unselect {
    border = new LineBorder(java.awt.Color.BLACK, 2)
  }

}

object ViewTile {

  def draw(t: AbsTile, p: (Int, Int), n: String) = {
    t match {
      case ti:Tile => drawTile(ti, p, n)
      case ti:EmptyTile => drawEmpty(ti, p, n)
    }
  }

  def drawTile(t: Tile, p: (Int, Int), n: String) = {
    new ViewTile(p, n) {
      preferredSize = new Dimension(50, 50)

      font = new Font("Monospaced", Font.PLAIN, 22)

      override def paint(g: Graphics2D) {
	g.setFont(font)

	val m = size.getWidth().toInt//for x
	val n = size.getHeight().toInt//for y
	val fm = g.getFontMetrics()

	g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
	g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

	g.setColor(Generator.color(t.left))
	g.fillPolygon(Array(0,m/2, 0, 0),Array(0,n/2, n, 0), 4)
	g.setColor(Generator.color(t.top))
	g.fillPolygon(Array(0, m/2, m, 0),Array(0, n/2, 0, 0), 4)
	g.setColor(Generator.color(t.right))
	g.fillPolygon(Array(m, m/2, m, m),Array(0, n/2, n, 0), 4)
	g.setColor(Generator.color(t.bottom))
	g.fillPolygon(Array(0, m/2, m, 0),Array(n, n/2, n, n), 4)

	g.setColor(java.awt.Color.BLACK)
	g.drawLine(0, 0, m, n)
	g.drawLine(0, n, m, 0)

	g.drawString(t.left.toString, m/6 - fm.stringWidth(t.left.toString)/2, n/2 + fm.getHeight()/2)
	g.drawString(t.top.toString, m/2 - fm.stringWidth(t.top.toString)/2, n/6 + fm.getHeight()/2)
	g.drawString(t.right.toString, (m/6) * 5 - fm.stringWidth(t.right.toString)/3, n/2 + fm.getHeight()/2)
	g.drawString(t.bottom.toString, m/2 - fm.stringWidth(t.bottom.toString)/2, (n/4) * 3 + fm.getHeight()/2)

	g.setColor(border match { case lb: LineBorder => lb.getLineColor(); case _ => java.awt.Color.BLACK})
	g.drawPolygon(Array(0, m, m, 0, 0), Array(0, 0, n, n, 0), 5)
	g.drawPolygon(Array(1, m-1, m-1, 1, 1), Array(1, 1, n-1, n-1, 1), 5)
      }

      border = new LineBorder(java.awt.Color.BLACK, 2)

      listenTo(mouse.clicks)

      reactions += {
	case e: MouseClicked =>
	  Controler.click(p, gridName)
      }
    }
  }

  def drawEmpty(t: EmptyTile, p: (Int, Int), n: String) = {
    new ViewTile(p, n) {
      preferredSize = new Dimension(50, 50)
      border = new LineBorder(java.awt.Color.BLACK, 2)

      listenTo(mouse.clicks)

      reactions += {
	case e: MouseClicked =>
	  Controler.click(p, gridName)
      }
    }
  }

}
