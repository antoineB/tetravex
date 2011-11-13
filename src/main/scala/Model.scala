object Model {
  var current: Tile = null
  var currentPos: (Int, Int) = null
  var currentGrid: String = ""

  var numberPlaced = 0
  var goal: Grid = null
  var left: Grid = null
  var right: Grid = null

  def init(g: Grid, r: Grid) {
    goal = g
    right = r
    left = Grid.generateEmpty(g.rows, g.columns, "left")
    numberPlaced = g.rows * g.columns
  }

  def get(pos: (Int, Int), name: String): Tile = {
    if (name == "left")
      left.list(pos._1)(pos._2) match { 
	case t: Tile => t
	case _ => null
      }
    else
      right.list(pos._1)(pos._2) match { 
	case t: Tile => t
	case _ => null
      }
  }

  def select(pos: (Int, Int), name: String) {
    println("select " + pos._1 + " " + pos._2)
    currentGrid = name
    currentPos = pos
    current = get(pos, name)

    //view inform
    View.select(pos, name)
  }

  def unselect(pos: (Int, Int), name: String) {
    currentGrid = null
    currentPos = null
    current = null


    //view inform
    View.unselect(pos, name)
  }

  def move(pos: (Int, Int), name: String) {
    println("move")
    if (name == "left") {
      println("move left to " + pos._1 + " " + pos._2)
      println(left)
      if (left.goto(pos._1, pos._2, current)) {
	if (currentGrid == "left")
	  left.drop(currentPos._1, currentPos._2)
	else {
	  right.drop(currentPos._1, currentPos._2)
	  numberPlaced = numberPlaced - 1
	}
	View.move((currentPos, currentGrid), (pos, name), current)
	currentGrid = null
	currentPos = null
	current = null

	if (numberPlaced == 0)
	  println("game is win") //quit game
	println(right)
      }
    }
    else {
      if (right.gotoInc(pos._1, pos._2, current)) {
	if (currentGrid == "left") {
	  left.drop(currentPos._1, currentPos._2)
  	  numberPlaced = numberPlaced + 1
	}
	else
	  right.drop(currentPos._1, currentPos._2)
	View.move((currentPos, currentGrid), (pos, name), current)
	currentGrid = null
	currentPos = null
	current = null
      }
    }
  }

  def click(pos: (Int, Int), name: String) {
    if (current == null) {
      if (get(pos, name) != null)
	select(pos, name)
    }
    else {
      if (get(pos, name) != null && pos == currentPos && currentGrid == name)
	unselect(pos, name)
      if (get(pos, name) == null)
	move(pos, name)
    }
  }
}