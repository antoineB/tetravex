import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Grid(nbRows: Int, nbColumns: Int, n: String) {
  val name = n
  val rows = nbRows
  val columns = nbColumns
  val list: ArrayBuffer[ArrayBuffer[AbsTile]] = ArrayBuffer.fill(rows){ArrayBuffer.fill(columns){Tile.generateEmpty}}
  
  //genere un nombre entre 0 - (rows * columns)
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
      for (c <- 0 until columns) 
	{
	  val e = real(next(lim))
	  val t = tuple(e)
	  used = insert(e)
	  lim = lim - 1
	  g.list(t._1)(t._2) = list(r)(c)
	}
    g
  }

  override def toString = {
    var str = ""
    for (r <- 0 until rows) {
      for (c <- 0 until columns)
	str = str + { if (list(r)(c) != null) list(r)(c) else "[null] " }
    str = str + "\n"
    }
    str
  }

  def goto(row: Int, colum: Int, t: Tile): Boolean =
    list(row)(colum) match {
      case Tile(_,_,_,_,_) => false
      case EmptyTile(id) => {
	println("empty")
	var res = true
	
	if (row > 0)
	  list(row -1)(colum) match {
	    case Tile(top,left,right,bottom,_) =>
	      if (top != t.bottom)
		res = res && false
	    case EmptyTile(_) => res = res && true
	  }

	if (row < rows - 1)
	  list(row +1)(colum) match {
	    case Tile(top,left,right,bottom,_) =>
	      if (bottom != t.top)
		res = res && false
	    case EmptyTile(_) => res = res && true
	  } 
       
	if (colum > 0)
	  list(row)(colum -1) match {
	    case Tile(top,left,right,bottom,_) =>
	      if (right != t.left)
		res = res && false
	    case EmptyTile(_) => res = res && true
	  } 

	if (colum < columns - 1)
	  list(row)(colum +1) match {
	    case Tile(top,left,right,bottom,_) =>
	      if (left != t.right)
		res = res && false
	    case EmptyTile(_) => res = res && true
	  }

	if (!res) return false

	list(row)(colum) = t
	println(this)

	true
      }
    }

  def gotoInc(row: Int, colum: Int, t: Tile): Boolean = {
    var b = list(row)(colum) match {
      case Tile(_,_,_,_,_) => false
      case EmptyTile(_) => true
    }
    
    if (!b) return false
    list(row)(colum) = t
    true
  }

  def drop(row: Int, colum: Int) {
    list(row)(colum) = Tile.generateEmpty
  }
}


object Grid {

  def generate(rows: Int, columns: Int, name: String) = {
    if (rows < 0 || columns < 0)
      new Grid(0,0, name)
    else {
      val g = new Grid(rows, columns, name)
    
      g.list(0)(0) = Tile.generate

      for (c <- 1 until columns)
	g.list(0)(c) = Tile.generate(g.list(0)(c-1) match {
	  case t @ Tile(_,_,_,_,_) => t.right
	})
     
      
    
      for (r <- 1 until rows) {
	g.list(r)(0) = Tile.generateTop(g.list(r-1)(0) match {
	  case t @ Tile(_,_,_,_,_) => t.bottom
	})

	for (c <- 1 until columns) {
	  g.list(r)(c) = Tile.generate(g.list(r-1)(c) match {
	    case t @ Tile(_,_,_,_,_) => t.bottom
	  }, g.list(r)(c-1) match { 
	    case t @ Tile(_,_,_,_,_) => t.right
	  })
	}
      }
      g
    }
  }

  def generateEmpty(rows: Int, columns: Int, name: String) = new Grid(rows, columns, name)

}
