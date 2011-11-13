package tetravex.core

sealed abstract class AbsTile

case class Tile(top: Int, left: Int, right: Int, bottom: Int, id: Int) extends AbsTile{
  def tEq(t: Tile) = top == t.top
  def lEq(t: Tile) = left == t.left
  def rEq(t: Tile) = right == t.right
  def bEq(t: Tile) = bottom == t.bottom

  override def toString = "[<" + left + " ^" + top + " v" + bottom + " " + right + ">] "
}

case class EmptyTile(id: Int) extends AbsTile

object Tile {
  var id = 0

  def generate = {
    id = id + 1
    Tile(Generator.next, Generator.next, Generator.next, Generator.next, id)
  }
  def generate(left: Int) = {
    id = id + 1
    Tile(Generator.next, left, Generator.next, Generator.next, id)
  }
  def generateTop(top: Int) = {
    id = id + 1
    Tile(top, Generator.next, Generator.next, Generator.next, id)
  }
  def generate(top:Int, left: Int) = {
    id = id + 1
    Tile(top, left, Generator.next, Generator.next, id)
  }
  def generateEmpty = {
    id = id + 1
    EmptyTile(id)
  }
}
