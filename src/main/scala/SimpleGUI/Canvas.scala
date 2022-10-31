package SimpleGUI
import scala.swing.Panel
import java.awt.{ Graphics2D, Color }
import backEnd.Expr._

class Canvas extends Panel {

  var computeFunction:Expr =  null

  def setFunction(fun:Expr)={
    this.computeFunction=fun
    repaint()
  }

  // Setting up the x & y axis
  def xToPixel( x:Double ):Int =  ( size.width * (x + Math.PI)/(2*Math.PI) ).toInt
  def yToPixel( y:Double ):Int =  ( size.height * (1 - (y + 1)/2.0 ) ).toInt

  override def paintComponent(g: Graphics2D) = {
    // Putting a Light Gray background
    g.setColor( Color.LIGHT_GRAY);
    g.fillRect( 0, 0, size.width, size.height)

    // Drawing the axis
    g.setColor( Color.BLUE );
    g.drawLine( 0, size.height/2, size.width, size.height/2 ); //size.width
    g.drawLine( size.width/2, 0, size.width/2,size.height );

    // Drawing the values
    g.setColor( Color.BLUE );
    g.drawString( "0,0", (size.width*0.51).toInt, (size.height*0.54).toInt);
    g.drawString( "-\u03c0", ((size.width*0.5)/1.5).toInt, ((size.height*0.54)).toInt);
    g.drawString( "-2\u03c0", ((size.width*0.5)/3).toInt, ((size.height*0.54)).toInt);
    g.drawString( "-3\u03c0", ((size.width*0.02)).toInt, ((size.height*0.54)).toInt);
    g.drawString( "\u03c0", ((size.width*1.33)/2).toInt, (size.height*0.54).toInt);
    g.drawString( "2\u03c0", ((size.width*1.6)/2).toInt, (size.height*0.54).toInt);
    g.drawString( "3\u03c0", (size.width*0.96).toInt, (size.height*0.54).toInt);

    // Drawing curves
    if (computeFunction != null) {
      val step:Double = 0.001;
      g.setColor( new Color( 255, 0, 255 ) );
      var oldX:Int = xToPixel( -Math.PI );
      val env = Math.PI
      var oldY:Int = yToPixel( eval(computeFunction)(env) );
      var lxn = (-Math.PI+step)
      for( a <- 1 to 1000000) {
        val x:Int = xToPixel( lxn )
        val env2 = (lxn*6)
        val y:Int = yToPixel( 0.25*eval(computeFunction)(env2))
        g.drawLine( x, y, oldX, oldY )
        oldX = x
        oldY = y
        lxn=lxn+step
      }
    }
  }
}