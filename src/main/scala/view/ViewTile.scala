package tetravex.view

import scala.swing._
import scala.swing.event._
import javax.swing.border.LineBorder

import tetravex.core.{Tile, AbsTile, EmptyTile}
import tetravex.controler.Controler


class ViewTile(pos: (Int, Int), grid: String) extends Panel {
  val postiion = pos
  val gridName = grid

  def select {
    border = new LineBorder(java.awt.Color.RED, 2)
  }

  def unselect {
    border = new LineBorder(java.awt.Color.BLACK, 2)
  }

}

object ViewTile {

  def numberToColor(i: Int) =  i match {
    case 0 => java.awt.Color.RED
    case 1 => java.awt.Color.BLUE
    case 2 => java.awt.Color.GREEN
    case 3 => java.awt.Color.YELLOW
    case 4 => java.awt.Color.WHITE
    case 5 => java.awt.Color.ORANGE
    case 6 => java.awt.Color.PINK
    case 7 => java.awt.Color.MAGENTA
    case 8 => java.awt.Color.CYAN
    case _ => java.awt.Color.DARK_GRAY
  }  

  def draw(t: AbsTile, p: (Int, Int), n: String) = {
    t match {
      case ti:Tile => drawTile(ti, p, n)
      case ti:EmptyTile => drawEmpty(ti, p, n)
    }
  }

  def drawTile(t: Tile, p: (Int, Int), n: String) = {
    new ViewTile(p, n) {
      preferredSize = new Dimension(50, 50)

      override def paint(g: Graphics2D) {
	val m = 50//for x
	val n = 50//for y
	val fm = g.getFontMetrics()
	g.setColor(numberToColor(t.left))
	g.fillPolygon(Array(0,m/2, 0, 0),Array(0,n/2, n, 0), 4)
	g.setColor(numberToColor(t.top))
	g.fillPolygon(Array(0, m/2, m, 0),Array(0, n/2, 0, 0), 4)
	g.setColor(numberToColor(t.right))
	g.fillPolygon(Array(m, m/2, m, m),Array(0, n/2, n, 0), 4)
	g.setColor(numberToColor(t.bottom))
	g.fillPolygon(Array(0, m/2, m, 0),Array(n, n/2, n, n), 4)

	g.setColor(java.awt.Color.BLACK)
	g.drawLine(0, 0, m, n)
	g.drawLine(0, n, m, 0)

	g.drawString(t.left.toString, m/6 - fm.stringWidth(t.left.toString)/2, n/2 + fm.getHeight()/2)
	g.drawString(t.top.toString, m/2 - fm.stringWidth(t.top.toString)/2, n/6 + fm.getHeight()/2)
	g.drawString(t.right.toString, (m/6) * 5 - fm.stringWidth(t.right.toString)/2, n/2 + fm.getHeight()/2)
	g.drawString(t.bottom.toString, m/2 - fm.stringWidth(t.bottom.toString)/2, (n/6) * 5 + fm.getHeight()/2)

	g.setColor(border match { case lb: LineBorder => lb.getLineColor(); case _ => java.awt.Color.BLACK})
	g.drawPolygon(Array(0, m, m, 0, 0), Array(0, 0, n, n, 0), 5)
	g.drawPolygon(Array(1, m-1, m-1, 1, 1), Array(1, 1, n-1, n-1, 1), 5)
      }

    
     
      border = new LineBorder(java.awt.Color.BLACK, 2)

      listenTo(mouse.clicks)

      name = t.id.toString

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
      name = t.id.toString

      listenTo(mouse.clicks)

      reactions += {
	case e: MouseClicked =>
	  Controler.click(p, gridName)
      }

    }
  }

}
