import scala.swing._
import scala.swing.Swing._
import scala.swing.event._
import java.awt.Graphics2D

//(capitalized-words-mode 't)

object FirstSwingApp extends SimpleSwingApplication {
  
  
  def top = new MainFrame {
    title = "First Swing App"
    val g = Grid.generate(5,5, "right")
    val r = g.rand
    View.init(r)
    Model.init(g, r)
    contents = View

  }

}
