package practicemodelingsystem;

import static java.lang.Math.cosh;
import static java.lang.Math.sinh;
import static java.lang.Math.sqrt;
import static java.lang.Math.exp;

import java.util.Arrays;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class LanchesterEquation {
	
	public static final int N_ONE = 25;
	public static final int N_TWO = 40;
	public static final double PRO_ONE = 0.95;
	public static final double PRO_TWO = 0.25;
	public static final double LAM_ONE = 1.0; //per hour
	public static final double LAM_TWO = 0.2; //per hour
	public static final int NUMBER_OF_ITERATION = 6;
	static double[] muOneA = new double[NUMBER_OF_ITERATION]; 
	static double[] muTwoA = new double[NUMBER_OF_ITERATION];
	static double[] muOneB = new double[NUMBER_OF_ITERATION];
	static double[] muTwoB = new double[NUMBER_OF_ITERATION];
	
	public static void modelA(int N1, int N2, double proOne, double proTwo, double lamOne, double lamTwo){
		double A1 = proOne * lamOne;
		double A2 = proTwo * lamTwo;
		double U1 = (double)(A1 * N1)/N2;
		double U2 = (double)(A2 * N2)/N1;
		double tPr = (double)(U1 + U2) / 2;
		double beta = (double)(U1 - U2) / (U1 + U2);
		System.out.println(U1);
		for(int i = 0; i < NUMBER_OF_ITERATION; i++) {
			muOneA[i] = cosh(sqrt(U1 * U2) * (double)i) - sqrt(U2/U1) * sinh(sqrt(U1 * U2) * (double)i);
			muTwoA[i] = cosh(sqrt(U1 * U2) * (double)i) - sqrt(U1/U2) * sinh(sqrt(U1 * U2) * (double)i);
			muOneB[i] = (2 * beta) / ((1 + beta) - (1- beta) * exp(-2 * beta * tPr * (double)i));
			muTwoB[i] = (2 * beta) / ((1 + beta) * exp(2 * beta * tPr * (double)i) - (1 - beta));
		}
	}
	
	public static void main(String[] args) {
		modelA(N_ONE, N_TWO, PRO_ONE, PRO_TWO, LAM_ONE, LAM_TWO);
		System.out.println(Arrays.toString(muOneA));
		System.out.println(Arrays.toString(muTwoA));
		ChartOfFight demo = new ChartOfFight("Chart Demo");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
	static class ChartOfFight extends ApplicationFrame {

		public ChartOfFight(String title) {
			super(title);
			XYSeriesCollection data = new XYSeriesCollection();
			XYSeries seriesMuOneA = new XYSeries("MuOneA");
			XYSeries seriesMuTwoA = new XYSeries("MuTwoA");
			XYSeries seriesZero = new XYSeries("Zero");
			XYSeries seriesMuOneB = new XYSeries("MuOneB");
			XYSeries seriesMuTwoB = new XYSeries("MuOneB");
			for(int j = 0; j < NUMBER_OF_ITERATION; j++) {
				seriesMuOneA.add((double)j, muOneA[j]);
				seriesMuTwoA.add((double)j, muTwoA[j]);
				seriesMuOneB.add((double)j, muOneB[j]);
				seriesMuTwoB.add((double)j, muTwoB[j]);
				seriesZero.add((double)j, 0);
			}
			data.addSeries(seriesMuTwoA);
			data.addSeries(seriesMuOneA);
			data.addSeries(seriesZero);
			data.addSeries(seriesMuTwoB);
			data.addSeries(seriesMuOneB);
			JFreeChart chart = ChartFactory.createXYLineChart("Lanchester equation", "X", "Y", data, PlotOrientation.VERTICAL, true, true, false);
	        ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
	        this.setContentPane(chartPanel);
		}
	}
}
