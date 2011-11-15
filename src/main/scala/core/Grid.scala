package tetravex.core

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


class Grid(nbRows: Int, nbColumns: Int, n: String) {
  val name = n
  val rows = nbRows
  val columns = nbColumns
  val matrix: ArrayBuffer[ArrayBuffer[AbsTile]] = ArrayBuffer.fill(rows){ArrayBuffer.fill(columns){Tile.generateEmpty}}

  //randomize the current grid
  def rand = {
    val g = new Grid(rows, columns, name)
    val ra = Random    

    var lim = rows * columns
    var used: List[Int] = List()

    def next(limit: Int) = ra.nextInt.abs % limit
    def tuple(nb: Int) = ((nb / columns).toInt, nb % columns)
    def real(nb: Int) = nb + used.foldLeft(0)((sum, n) => if (n <= (nb + sum)) sum + 1 else sum)
    def insert(nb: Int) = (nb +: used).sortWith((a, b) => a < b)
    
    for (r <- 0 until rows) 
      for (c <- 0 until columns) {
	val e = real(next(lim))
	val t = tuple(e)
	used = insert(e)
	lim = lim - 1
	g.matrix(t._1)(t._2) = matrix(r)(c)
      }
    g
  }

  override def toString = {
    var str = ""
    for (r <- 0 until rows) {
      for (c <- 0 until columns)
	str = str + { if (matrix(r)(c) != null) matrix(r)(c) else "[null] " }
      str = str + "\n"
    }
    str
  }

  def goto(row: Int, colum: Int, t: Tile): Boolean =
    matrix(row)(colum) match {
      case Tile(_,_,_,_) => false
      case EmptyTile() => {
	var res = true
	
	def operate(t: AbsTile, f: Tile => Boolean) {
	  t match {
	    case tu @ Tile(_,_,_,_) =>
	      if (f(tu))
		res = res && false
	    case EmptyTile() => res = res && true
	  }
	}

	if (row > 0) 
	  if (matrix(row -1)(colum) ne t)
	    operate(matrix(row -1)(colum), (a => t.top != a.bottom))
	
	if (row < rows - 1)
	  if (matrix(row +1)(colum) ne t)
	    operate(matrix(row +1)(colum), (a => t.bottom != a.top))
       
	if (colum > 0)
	  if (matrix(row)(colum -1) ne t)
	    operate(matrix(row)(colum -1), (a => a.right != t.left))

	if (colum < columns - 1)
	  if (matrix(row)(colum +1) ne t)
	    operate(matrix(row)(colum +1), (a => a.left != t.right))

	if (!res) return false

	matrix(row)(colum) = t
	true
      }
    }

  def gotoInc(row: Int, colum: Int, t: Tile): Boolean = {
    var b = matrix(row)(colum) match {
      case Tile(_,_,_,_) => false
      case EmptyTile() => true
    }
    
    if (!b) return false
    matrix(row)(colum) = t
    true
  }

  def drop(row: Int, colum: Int) {
    matrix(row)(colum) = Tile.generateEmpty
  }
}


object Grid {

  def generate(rows: Int, columns: Int, name: String) = {
    if (rows < 0 || columns < 0)
      new Grid(0,0, name)
    else {
      val g = new Grid(rows, columns, name)
    
      g.matrix(0)(0) = Tile.generate

      for (c <- 1 until columns)
	g.matrix(0)(c) = Tile.generate(g.matrix(0)(c-1) match {
	  case t @ Tile(_,_,_,_) => t.right
	  case _ => throw new Exception("not empty tile should be find")
	})
     
      for (r <- 1 until rows) {
	g.matrix(r)(0) = Tile.generateTop(g.matrix(r-1)(0) match {
	  case t @ Tile(_,_,_,_) => t.bottom
  	  case _ => throw new Exception("not empty tile should be find")
	})

	for (c <- 1 until columns) {
	  g.matrix(r)(c) = Tile.generate(g.matrix(r-1)(c) match {
	    case t @ Tile(_,_,_,_) => t.bottom
	    case _ => throw new Exception("not empty tile should be find")
	  }, g.matrix(r)(c-1) match { 
	    case t @ Tile(_,_,_,_) => t.right
    	    case _ => throw new Exception("not empty tile should be find")
	  })
	}
      }
      g
    }
  }

  def generateEmpty(rows: Int, columns: Int, name: String) = new Grid(rows, columns, name)

}
