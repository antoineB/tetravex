package tetravex.core

sealed abstract class AbsTile

case class Tile(top: Char, left: Char, right: Char, bottom: Char) extends AbsTile{
  def tEq(t: Tile) = top == t.top
  def lEq(t: Tile) = left == t.left
  def rEq(t: Tile) = right == t.right
  def bEq(t: Tile) = bottom == t.bottom

  override def toString = "[<" + left + " ^" + top + " v" + bottom + " " + right + ">] "
}

case class EmptyTile() extends AbsTile

object Tile {

  def generate = Tile(Generator.next, Generator.next, Generator.next, Generator.next)

  def generate(left: Char) = Tile(Generator.next, left, Generator.next, Generator.next)

  def generateTop(top: Char) = Tile(top, Generator.next, Generator.next, Generator.next)

  def generate(top:Char, left: Char) = Tile(top, left, Generator.next, Generator.next)

  def generateEmpty = EmptyTile()
}

