package tetravex.model

import javax.swing.Timer
import java.awt.event.{ActionListener, ActionEvent}

import tetravex.core._
import tetravex.view.View


object Model {
  private var current: Tile = null
  private var currentPos: (Int, Int) = null
  private var currentGrid: String = ""

  private var timeCount = 0
  private var timer: Timer = null
  private var numberPlaced = 0
  private var goal: Grid = null
  private var left: Grid = null
  private var right: Grid = null

  def close() {
    timer.stop()
  }

  def newGame(n: Int, range: String) {
    try { Generator.setRange(range) }
    catch {
      case e: Exception =>
	View.errorMsg(e.getMessage())
      return;
    }

    val g = Grid.generate(n,n, "right")
    val r = g.rand
    View.init(r)
    Model.init(g, r)
   
    val taskPerformer: ActionListener = new ActionListener() {
      def actionPerformed(evt: ActionEvent) {
	timeCount = timeCount + 1
	View.updateTime(timeString)
      }
    }
    if (timer != null) timer.stop()
    timer = new Timer(1000, taskPerformer)
    timeCount = 0
    timer.start();
  }

  private def timeString = "%02d:%02d"  format(timeCount / 60, timeCount % 60)

  private def init(g: Grid, r: Grid) {
    goal = g
    right = r
    left = Grid.generateEmpty(g.rows, g.columns, "left")
    numberPlaced = g.rows * g.columns
    
    currentGrid = ""
    currentPos = null
    current = null
  }

  def get(pos: (Int, Int), name: String): Tile = {
    if (name == "left")
      left.matrix(pos._1)(pos._2) match { 
	case t: Tile => t
	case _ => null
      }
    else
      right.matrix(pos._1)(pos._2) match { 
	case t: Tile => t
	case _ => null
      }
  }

  def select(pos: (Int, Int), name: String) {
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
    if (name == "left") {
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

	if (numberPlaced == 0) {
	  println("game is win " + timeString)
	  newGame(left.rows, Generator.getRange)
	}
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
