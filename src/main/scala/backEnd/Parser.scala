package backEnd
import backEnd.Expr._

import scala.util.parsing.combinator._

class Parser extends RegexParsers {

  def expr: Parser[Expr] = multi ~ rep("+" ~ multi) ^^ {
    case number ~list => list.foldLeft(number){
      case (a, "+" ~ b) => Operator(a, b, "+")
    }
  }

  def multi: Parser[Expr] = operator ~ rep( "*" ~ operator) ^^ {
    case number ~ list => list.foldLeft(number){
      case (a, "*" ~ b) => Operator(a, b, "*")
    }
  }

  def number: Parser[Expr] = """\d+(\.\d*)?""".r ^^ { num => Number(num.toDouble) }
  def variable: Parser[Expr] = """x""".r ^^ {_ => Variable}
  def cos: Parser[Expr] = "cos("~>expr<~")" ^^ {exp =>Function("cos", exp)}
  def sin: Parser[Expr] = "sin("~>expr<~")" ^^ {exp =>Function("sin", exp)}
  def operator: Parser[Expr] = number | variable | cos | sin | "(" ~> expr <~ ")"


  def apply(input: String): Option[Expr] = parseAll(expr, input) match {
    case Success(result, _) => Some(result)
    case failure : NoSuccess => scala.sys.error(failure.msg)
    case _ => None
  }
}
