package practicemodelingsystem;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class MarksProcessing extends ApplicationFrame{

	private final double t0 = 7.0;
	private final double h = 0.02;
	
	private final double lam12 = 1.0;
	private final double lam13 = 1.0;
	private final double lam21 = 1.5;
	private final double lam34 = 0.5;
	private final double lam35 = 0.75;
	private final double lam42 = 1.0;
	private final double lam43 = 0.5;
	private final double lam54 = 1.0;
	
	private MarksProcessing(String title) {
		super(title);
	}
	
	public static void main(String[] args) {
		MarksProcessing demo = new MarksProcessing("Kolmogorov's equations");
		demo.methodRK();
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
	//Equation
	double f1(double p1, double p2)
	{
	    return p2*lam21 - p1*(lam12 + lam13);
	}
	double f2(double p1, double p2, double p4)
	{
	    return p1*lam12 + p4*lam42 - p2*lam21;
	}
	double f3(double p1, double p3, double p4)
	{
	    return p1*lam13 + p4*lam43 - p3*(lam34 + lam35);
	}
	double f4(double p3, double p4, double p5)
	{
	    return p3*lam34 + p5*lam54 - p4*(lam43 + lam42);
	}
	double f5(double p3, double p5)
	{
	    return p3*lam35 - p5*lam54;
	}
	
	private void methodRK() {
		double t = 0;
		double p1 = 0.2;
		double p2 = 0.2;
		double p3 = 0.2;
		double p4 = 0.2;
		double p5 = 0.2;
		double k11, k12, k13, k14, k15,
			   k21, k22, k23, k24, k25,
			   k31, k32, k33, k34, k35,
			   k41, k42, k43, k44, k45;
		
		XYSeriesCollection data = new XYSeriesCollection();
		XYSeries seriesP1 = new XYSeries("P1");
		XYSeries seriesP2 = new XYSeries("P2");
		XYSeries seriesP3 = new XYSeries("P3");
		XYSeries seriesP4 = new XYSeries("P4");	
		XYSeries seriesP5 = new XYSeries("P5");
		
		for(;t < t0; t += h) {
			k11 = h*f1(p1, p2);
			k12 = h*f2(p1, p2, p4);
			k13 = h*f3(p1, p3, p4);
			k14 = h*f4(p3, p4, p5);
			k15 = h*f5(p3, p5);
			
			k21 = h*f1(p1+k11/2, p2+k12/2);
			k22 = h*f2(p1+k11/2, p2+k12/2, p4+k14/2);
			k23 = h*f3(p1+k11/2, p3+k13/2, p4+k14/2);
			k24 = h*f4(p3+k13/2, p4+k14/2, p5+k15/2);
			k25 = h*f5(p3+k13/2, p5+k15/2);
			
			k31 = h*f1(p1+k21/2, p2+k22/2);
			k32 = h*f2(p1+k21/2, p2+k22/2, p4+k24/2);
			k33 = h*f3(p1+k21/2, p3+k23/2, p4+k24/2);
			k34 = h*f4(p3+k23/2, p4+k24/2, p5+k25/2);
			k35 = h*f5(p3+k23/2, p5+k25/2);
			
			k41 = h*f1(p1+k31, p2+k32);
			k42 = h*f2(p1+k31, p2+k32, p4+k34);
			k43 = h*f3(p1+k31, p3+k33, p4+k34);
			k44 = h*f4(p3+k33, p4+k34, p5+k35);
			k45 = h*f5(p3+k33, p5+k35);
			
			p1 = p1 + (k11 + 2*k21 + 2*k31 + k41)/6;
			p2 = p2 + (k12 + 2*k22 + 2*k32 + k42)/6;
			p3 = p3 + (k13 + 2*k23 + 2*k33 + k43)/6;
			p4 = p4 + (k14 + 2*k24 + 2*k34 + k44)/6;
			p5 = p5 + (k15 + 2*k25 + 2*k35 + k45)/6;
			System.out.println("Sum of p: " + (p1 + p2 + p3 + p4 + p5));
	        seriesP1.add(t+h, p1);
	        seriesP2.add(t+h, p2);
	        seriesP3.add(t+h, p3);
	        seriesP4.add(t+h, p4);
	        seriesP5.add(t+h, p5);
		}
		data.addSeries(seriesP1);
		data.addSeries(seriesP2);
		data.addSeries(seriesP3);
		data.addSeries(seriesP4);
		data.addSeries(seriesP5);
		
		JFreeChart chart = ChartFactory.createXYLineChart("Kolmogorov's Equation", "X", "P1-P5", data, PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
		this.setContentPane(chartPanel);
	} 
}
