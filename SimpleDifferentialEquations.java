package practicemodelingsystem;

import org.jfree.ui.*;
import java.util.logging.*;

import java.util.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;

public class SimpleDifferentialEquations extends ApplicationFrame {
	
	final static Logger log = Logger.getLogger("practicemodelingsystem");
	
	public static final double throughput = 10;
	public static final double numberOfService = 100;
	public static final double initialNumber = 100;
	public static final double tFinite = 1;	
	public static final double tForSystem = 0.7;
	public static final double alfa = 80;
	public static final double beta = 79;
//  System consts 2 x 2 
	public static final double alfaOne = 5;
	public static final double betaOne = 15;
	public static final double alfaTwo = 10;
	public static final double betaTwo = 5;
//  System initial conditions
	public static final double N0 = 100;
	public static final double M0 = 100;
	
	public SimpleDifferentialEquations(String title) {
		super(title);
		XYSeriesCollection data = new XYSeriesCollection();
		XYSeries seriesOnlyNumber = new XYSeries("N(t)");
		XYSeries seriesWitnService = new XYSeries("N(t) + Nservice");
		XYSeries seriesTwoParameters = new XYSeries("N(t), alfa, beta");
		
		SimpleDifferentialEquations.rungeOnlyNumber(initialNumber)
					.forEach((k, v) -> seriesOnlyNumber.add(k, v));
		
		SimpleDifferentialEquations.rungeWithService(initialNumber)
					.forEach((k, v) -> seriesWitnService.add(k,v));
		
		SimpleDifferentialEquations.rungeTwoParameters(initialNumber)
					.forEach((k, v) -> seriesTwoParameters.add(k,v));
		
		data.addSeries(seriesOnlyNumber);
		data.addSeries(seriesWitnService);
		data.addSeries(seriesTwoParameters);
		
		JFreeChart chart = ChartFactory.createXYLineChart("Solve equation",
				"t", "N(t)", data, PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(900, 540));
		this.setContentPane(chartPanel);
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}
	
	public SimpleDifferentialEquations(String title, XYSeriesCollection data) {
		super(title);
		
		JFreeChart chart = ChartFactory
				.createXYLineChart("Solve System", "t", "N(t) & M(t)", data, 
						PlotOrientation.VERTICAL, true, true, false);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(900, 540));
		this.setContentPane(chartPanel);
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}
	
	public static double functionOfNumber(double n) {
		return (- throughput * n);
	}
	public static double functionService(double n) {
		return (- throughput * n + numberOfService);
	}
	public static double functionOfTwoParameters(double n) {
		double delta = alfa - beta;
		return (delta * n);
	}
//	System equations
	public static double functionSystemOne(double n, double m) {
		return alfaOne * n - betaOne * m * n;
	}
	public static double functionSystemTwo(double n, double m) {
		return  - alfaTwo * n + betaTwo * m * n;
	}
	
	public static Map<Double, Double> rungeOnlyNumber(double N0) {
		log.setLevel(Level.ALL);
		double h = 0.002;
		double k1, k2, k3, k4;
		Map<Double, Double> NtoT = new HashMap<>();
		for(double t = 0; t < tFinite; t += h) {
			k1 = h * functionOfNumber(N0);
			k2 = h * functionOfNumber(N0 + k1/2.0);
			k3 = h * functionOfNumber(N0 + k2/2.0);
			k4 = h * functionOfNumber(N0 + k3);
			N0 = N0 + (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0;
			NtoT.put(t, N0);
			String str = Double.toString(N0);
			log.info(str);
		}
		return NtoT;
	}
	public static Map<Double, Double> rungeWithService(double N0) {
		log.setLevel(Level.ALL);
		double h = 0.002;
		double k1, k2, k3, k4;
		Map<Double, Double> NtoTwS = new HashMap<>();
		for(double t = 0; t < tFinite; t += h) {
			k1 = h * functionService(N0);
			k2 = h * functionService(N0 + k1/2.0);
			k3 = h * functionService(N0 + k2/2.0);
			k4 = h * functionService(N0 + k3);
			N0 = N0 + (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0;
			NtoTwS.put(t, N0);
			String str = Double.toString(N0);
			log.info(str);
		}
		return NtoTwS;
	}	
	public static Map<Double, Double> rungeTwoParameters(double N0) {
		log.setLevel(Level.ALL);
		double h = 0.002;
		double k1, k2, k3, k4;
		Map<Double, Double> TwoParam = new HashMap<>();
		for(double t = 0; t < tFinite; t += h) {
			k1 = h * functionOfTwoParameters(N0);
			k2 = h * functionOfTwoParameters(N0 + k1/2.0);
			k3 = h * functionOfTwoParameters(N0 + k2/2.0);
			k4 = h * functionOfTwoParameters(N0 + k3);
			N0 = N0 + (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0;
			TwoParam.put(t, N0);
			String str = Double.toString(N0);
			log.info(str);
		}
		return TwoParam;
	}	
	// Equation Lotki-Volterra
	public static XYSeriesCollection rungeSystemTwoEquation(double N0, double M0, double t0) {
		
		double h = 0.002;
		double k11, k12, k41, k42,
			   k21, k22, k31, k32;
		
		Map<Double, Double> NtoT = new HashMap<>();
		Map<Double, Double> MtoT = new HashMap<>();
		
		for(double t = 0; t < t0; t += h) {
			k11 = h * functionSystemOne(N0, M0);
			k12 = h * functionSystemTwo(N0, M0);
			
			k21 = h * functionSystemOne(N0 + k11/2.0, M0 + k12/2.0);
			k22 = h * functionSystemTwo(N0 + k11/2.0, M0 + k12/2.0);
			
			k31 = h * functionSystemOne(N0 + k21/2.0, M0 + k22/2.0);
			k32 = h * functionSystemTwo(N0 + k21/2.0, M0 + k22/2.0);
			
			k41 = h * functionSystemOne(N0 + k31, M0 + k32);
			k42 = h * functionSystemTwo(N0 + k31, M0 + k32);
			
			N0 = N0 + (k11 + 2.0 * k21 + 2.0 * k31 + k41) / 6.0;
			M0 = M0 + (k12 + 2.0 * k22 + 2.0 * k32 + k42) / 6.0;
			
			NtoT.put(t, N0);
			MtoT.put(t, M0);
		}
		
		XYSeriesCollection data = new XYSeriesCollection();
		XYSeries seriesN = new XYSeries("N(t)");
		XYSeries seriesM = new XYSeries("M(t)");		
		
		NtoT.forEach((k, v) -> seriesN.add(k, v));
		MtoT.forEach((k, v) -> seriesM.add(k,v));
		
		data.addSeries(seriesN);
		data.addSeries(seriesM);
		
		return data;
	}	
	
	public static void main(String[] args) {
		new SimpleDifferentialEquations("Solve Equation #1,2,3");
		
		XYSeriesCollection dataSystem = 
				SimpleDifferentialEquations
					.rungeSystemTwoEquation(N0, M0, tForSystem);
		
		new SimpleDifferentialEquations("Solve of System Eq", dataSystem);
	}
}
