# Scala-Calculator

We implemented a program/ calculator that is able to : 

  1/ Read Strings representing mathematical functions of a single variable x.
  2/ Convert them to an internal tree-like data representation.
  3/ Compute a value based on different values of x
  4/ Display a set of points (x, f(x)) that will be connected with lines to make the whole picture look like a plot that will represent the mathematical        function.
 
All you have to do is open the project folder with your code editor and click run., it's that easy !
Don't forget to check if the .jar exists within the scala-2.12 folder if not generate it using 'assembly'
in the sbt shell

Examples of Functions to test our Calculator :

    3*(7+1) (scaling problem for the first derivative)
    3*x+17.3
    sin(x)+cos(x)
    sin(2*x+3.2)+3*x+5.7 (scaling problem for the first derivative)
    x*x+x+10
    12+13+14+x*2+x*0+0 (scaling problem for the first derivative)
