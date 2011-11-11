import scala.swing._
import scala.swing.event._

object View extends BorderPanel{
  var left: ViewGrid = null
  var right: ViewGrid = null

  layout(new Panel{preferredSize = new Dimension(20, 20)}) = BorderPanel.Position.Center

  def init(g: Grid) {
    left = ViewGrid(Grid.generateEmpty(g.rows, g.columns, "left"))
    right = ViewGrid(g)

    layout(left) = BorderPanel.Position.West
    layout(right) = BorderPanel.Position.East
  }

  def move(from: ((Int, Int), String), to: ((Int, Int), String), t:Tile) {
    var tile: ViewTile = ViewTile.drawTile(t, to._1, to._2)
    if (from._2 == "left")
      left.clear(from._1._1, from._1._2)
    else
      right.clear(from._1._1, from._1._2)
    
    if (to._2 == "left")
      left.set(to._1._1, to._1._2, tile)
    else
      right.set(to._1._1, to._1._2, tile)

    revalidate
    repaint
  }

  def select(pos: (Int, Int), gridName: String) {
    if (gridName == "left")
      left.select(pos._1, pos._2)
    else
      right.select(pos._1, pos._2)
  }
   
  def unselect(pos: (Int, Int), gridName: String) {
    if (gridName == "left")
      left.unselect(pos._1, pos._2)
    else
      right.unselect(pos._1, pos._2)
  }
}
