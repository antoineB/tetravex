import scala.swing._

object Controler {

  def click(pos: (Int, Int), name: String) {
    Model.click(pos, name)
  }

}
