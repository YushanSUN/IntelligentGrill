package INF380;
/**
 * Reference
 * at Telecom ParisTech, Paris, France in Summer 2015
 *
 * @author Yushan SUN
 *
 * 
 */
import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * This is the function which enables to draw the satisfaction of the district in different period.
 */

public class GTEST extends JFrame
{	
	static int x = 800;
	static int y = 600;
	 public GTEST(List<Double> sat)
	 {
	  add(new X2FunctionPanel(sat));
	 }
	
	 public static void main(String[] args)
	 {
		 List<Double> sat = new ArrayList<Double>();
	  GTEST frame=new GTEST(sat);
	  frame.setSize(x,y);
	  frame.setTitle("Satisfaction");
	  frame.setLocationRelativeTo(null);//center
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setVisible(true);
	 }
	}
	
	class X2FunctionPanel extends JPanel
	{
		List<Double> sat = new ArrayList<Double>();
	 public X2FunctionPanel(List<Double> sat){
		 this.sat = sat;
	 }
	 protected void paintComponent(Graphics g)
	 {
	  super.paintComponent(g);
	  int x = this.getWidth();
	  int y = this.getHeight();
	  int dx = x/80;
	  //draw X axis
	  g.drawLine((int)(x*0.1), (int)(y*0.8) , (int)(x*0.9) , (int)(y*0.8));
	  //draw X arrow
	  g.drawLine((int)(x*0.9) - dx , (int)(y*0.8) - dx ,(int)(x*0.9) , (int)(y*0.8));
	  g.drawLine((int)(x*0.9), (int)(y*0.8),(int)(x*0.9) - dx , (int)(y*0.8) + dx);
	  //draw ¡°x¡±
	  g.drawString("X, period",(int)(x*0.9) + dx, (int)(y*0.8) + dx/2);
	  //"0,0"
	  g.drawString("(0,0)", (int)(x*0.1) - dx*2, (int)(y*0.8) + dx*2);
	
	  //draw y axis
	  g.drawLine((int)(x*0.1), (int)(y*0.8) , (int)(x*0.1) , (int)(y*0.1));
	  g.drawLine((int)(x*0.1) - dx, (int)(y*0.1) + dx,(int)(x*0.1) , (int)(y*0.1));
	  g.drawLine((int)(x*0.1) , (int)(y*0.1),(int)(x*0.1) +dx, (int)(y*0.1) + dx);
	  g.drawString("Y, satisfaction",(int)(x*0.1) - dx*2, (int)(y*0.1) - 10);
	
	  // draw y = 1
	  g.drawLine((int)(x*0.1), (int)(y*0.4),(int)(x*0.9), (int)(y*0.4));
	  g.drawString("1.0", (int)(x*0.1) - dx*3, (int)(y*0.4) + dx/2);
	  for(int i=1;i<10;i++){
		  int tem = (int)(y*0.8)-(int)(y*0.4*0.1*i) + dx/2;
		  g.drawString("0."+i, (int)(x*0.1) - dx*3, tem);
		  g.drawLine((int)(x*0.1), tem, (int)(x*0.1)+dx/2, tem);
	  }
	  for(int i=1;i<6;i++){
		  int tem = (int)(y*0.8)-(int)(y*0.4*(i+10)/10) + dx/2;
		  g.drawString("1."+i, (int)(x*0.1) - dx*3, tem);
		  g.drawLine((int)(x*0.1), tem, (int)(x*0.1)+dx/2, tem);
	  }
	  
	  //draw the graph
	  int n = sat.size()+1;
	  int dn = (int) (x*0.8/(n-1));
	  Polygon p=new Polygon();
	  for(int i=0 ; i < n-1 ; i++ )
	  {
		  int temx = (int)(x*0.1) + (int)(dn*(i+0.5)) -dx/2;
	      p.addPoint((int)(dn*(i+0.5))+(int)(x*0.1), (int)(y*0.8) - (int)(sat.get(i)*y*0.4));
	      g.drawString(""+(i+1), temx, (int)(y*0.8) + dx*2);
	      g.drawLine(temx, (int)(y*0.8), temx, (int)(y*0.8) -dx/2);
	  }
	  
	  g.drawPolyline(p.xpoints,p.ypoints,p.npoints);
	  
	 }
	
}