package tetravex.view

import scala.swing._
import scala.swing.event._

import tetravex.core.{Grid, Tile}


object View extends BorderPanel{
  var board: Board = null
  var menu: Menu = new Menu()

  layout(menu) = BorderPanel.Position.North

  def init(g: Grid) {
    board = new Board(g)
    layout(board) = BorderPanel.Position.Center
    
    revalidate
  }

  def errorMsg(s: String) {
    layout(new Label(s)) = BorderPanel.Position.Center
    
    revalidate
  }

  def select(pos: (Int, Int), gridName: String) {
    board.select(pos, gridName)
  }

  def unselect(pos: (Int, Int), gridName: String) {
    board.unselect(pos, gridName)
  }

  def move(from: ((Int, Int), String), to: ((Int, Int), String), t: Tile) {
    board.move(from, to, t)
  }

  def updateTime(s: String) {
    board.updateTime(s)
  }
}
