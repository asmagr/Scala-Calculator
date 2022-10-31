import backEnd.Expr
import backEnd.Parser
import SimpleGUI.Canvas
import scala.swing._
import scala.swing.event._

class UI extends MainFrame {
  def restrictHeight(s: Component) {
    s.maximumSize = new Dimension(Short.MaxValue, s.preferredSize.height)
  }
  // Setting up the GUI
  title = "Calculator"

  val canvasFunc = new Canvas{
    border = Swing.EmptyBorder(350, 350, 350, 350)
  }

  //Setting up the buttons
  val amount = new TextField
  val answerSpace = new Label("")
  val drawGraph = new Button("DrawGraph")
  val simplify = new Button("Simplify")
  listenTo(simplify)
  val derivate = new Button("derivate")
  listenTo(derivate)
  restrictHeight(amount)
  listenTo(drawGraph)

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new BoxPanel(Orientation.Vertical) {
      contents += canvasFunc
    }

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new Label("f(x)=")
      contents += amount
    }
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += Swing.HGlue
      contents += Swing.HStrut(10)
      contents += drawGraph
      contents += Swing.HStrut(10)
      contents += simplify
      contents += Swing.HStrut(10)
      contents += derivate
    }
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += Swing.HStrut(5)
      contents += answerSpace
      contents += Swing.HStrut(10)
    }
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += Swing.HGlue
      contents += Swing.HStrut(50)
      contents += Swing.HStrut(50)
    }
    for (e <- contents)
      e.xLayoutAlignment = 0.0
  }

  //Reacting to button clicks
  reactions += {
    case ButtonClicked(`simplify`) => {
      amount.text = Expr.showExpr(Expr.simplify(new Parser().apply(amount.text).get))
    }
    case ButtonClicked(`derivate`) => {
      val a = Expr.differentiate(new Parser().apply(amount.text).get)
      amount.text = Expr.showExpr(a)
      canvasFunc.setFunction(a)
    }
    case ButtonClicked(`drawGraph`) => {
      val a = Expr.simplify(new Parser().apply(amount.text).get)
      val shexp = Expr.showExpr(a)
      amount.text = shexp
      canvasFunc.setFunction(a)
    }
  }
}

object finalGUI {
  def main(args: Array[String]) {
    val ui = new UI
    ui.visible = true
  }
}






