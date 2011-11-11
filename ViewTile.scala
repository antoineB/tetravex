import scala.swing._
import scala.swing.event._
import javax.swing.border.LineBorder

class ViewTile(pos: (Int, Int), grid: String) extends BorderPanel {
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

      val to = new Label(t.top.toString) {
	opaque = true
	background = numberToColor(t.top)
      }
      val b = new Label(t.bottom.toString) {
	opaque = true
	background = numberToColor(t.bottom)
      }
      val r = new Label(t.right.toString) {
	opaque = true
	background = numberToColor(t.right)
      }
      val l = new Label(t.left.toString) {
	opaque = true
	background = numberToColor(t.left)
      }
      
      layout(to) = BorderPanel.Position.North
      layout(b) = BorderPanel.Position.South
      layout(r) = BorderPanel.Position.East
      layout(l) = BorderPanel.Position.West
      
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
