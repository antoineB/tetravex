package tetravex.core

sealed abstract class AbsTile

case class Tile(top: Int, left: Int, right: Int, bottom: Int) extends AbsTile{
  def tEq(t: Tile) = top == t.top
  def lEq(t: Tile) = left == t.left
  def rEq(t: Tile) = right == t.right
  def bEq(t: Tile) = bottom == t.bottom

  override def toString = "[<" + left + " ^" + top + " v" + bottom + " " + right + ">] "
}

case class EmptyTile() extends AbsTile

object Tile {

  def generate = Tile(Generator.next, Generator.next, Generator.next, Generator.next)

  def generate(left: Int) = Tile(Generator.next, left, Generator.next, Generator.next)

  def generateTop(top: Int) = Tile(top, Generator.next, Generator.next, Generator.next)

  def generate(top:Int, left: Int) = Tile(top, left, Generator.next, Generator.next)

  def generateEmpty = EmptyTile()
}

