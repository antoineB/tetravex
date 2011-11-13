import scala.swing._
import scala.swing.event._

object ViewGrid {
  def apply(g: Grid) = new ViewGrid(g)
}

class ViewGrid(g: Grid) extends GridPanel(g.rows, g.columns) {
  name = g.name

  for (r <- 0 until g.rows)
    for (c <- 0 until g.columns)
      contents += ViewTile.draw(g.list(r)(c), (r,c), g.name)

  def clear(row: Int, column: Int) {
    contents(row * columns + column) = ViewTile.drawEmpty(Tile.generateEmpty, (row,column), g.name)
  }

  def get(row: Int, column: Int): ViewTile = contents(row * columns + column) match {
    case v: ViewTile => v
  }

  def set(row: Int, column: Int, tile: ViewTile) = contents(row * columns + column) =  tile

  def reDraw(row: Int, column: Int, t: Tile) {
    contents(row * columns + column) = ViewTile.draw(t, (row,column), g.name)
  }

  def select(row: Int, column: Int) {
    get(row, column).select
  }

  def unselect(row: Int, column: Int) {
    get(row, column).unselect
  }
}
