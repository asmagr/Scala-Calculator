package backEnd
import scala.math.{cos, sin}

object Expr {

  sealed trait Expr
  case class Number(value: Double) extends Expr
  case object Variable extends Expr
  case class Operator(a: Expr, b: Expr, op: String) extends Expr
  case class Function (func: String, a: Expr) extends Expr

  def showExpr(e: Expr): String = {
    e match {
      case Number(n) => n.toString
      case Variable => "x"
      case Operator(a, b, "+") => showExpr(a) + "+" + showExpr(b)
      case Operator(a, b, "*") => showExpr(a) + "*" + showExpr(b)
      case Function("sin", a) => "sin(" + showExpr(a) + ")"
      case Function("cos", a) => "cos(" + showExpr(a) + ")"
    }
  }

  def eval(e: Expr)(x: Double): Double = {
    e match{
      case Number(n) => n
      case Variable => x
      case Operator(a, b, "+") => eval(a)(x) + eval(b)(x)
      case Operator(a, b, "*") => eval(a)(x) + eval(b)(x)
      case Function("sin", a) => sin(eval(a)(x))
      case Function("cos", a) => cos(eval(a)(x))
    }
  }

  def simplify(e: Expr): Expr = {
    e match{
      case Operator(a, Number(0), "+") => simplify(a)
      case Operator(Number(0), b, "+") => simplify(b)
      case Operator(_, Number(0), "*") | Operator(Number(0), _, "*") => Number(0)
      case Operator(a, Number(1), "*") => simplify(a)
      case Operator(Number(1), b, "*") => simplify(b)
      case Operator(Number(a), Number(b), "+") => Number(a + b)
      case Operator(Number(a), Number(b), "*") => Number(a * b)
      case Operator(a, b, op) if op == "+" || op == "*" =>
        val sa = simplify(a)
        val sb = simplify(b)
        sa match {
          case Number(1) if op == "*" => sb
          case Number(0) => if (op == "*") Number(0) else sb
          case Number(_) if sb.isInstanceOf[Number] => simplify(Operator(sa, sb, op))
          case _ =>
            sb match {
              case Number(1) if op == "*" => sa
              case Number(0) => if (op == "*") Number(0) else sa
              case _ => Operator(sa, sb, op)
            }
        }
      case Function("sin", a) => Function("sin", simplify(a))
      case Function("cos", a) => Function("cos", simplify(a))
      case _ => e
    }
  }

  def differentiate(e : Expr): Expr = {
    val res = e match{
      case Number(_) => Number(0)
      case Variable => Number(1)
      case Operator(a, b, "+") => Operator(differentiate(a), differentiate(b), "+")
      case Operator(a, b, "*") => Operator(Operator(a, differentiate(b), "*"), Operator(b, differentiate(a), "*"), "+")
      case Function("sin", a) => Operator(differentiate(a), Function("cos", a), "*")
      case Function("cos", a) => Operator(differentiate(a), Operator(Function("sin", a), Number(-1), "*"), "*")
    }
    simplify(res)
  }
}