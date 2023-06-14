package practicemodelingsystem;

import org.jfree.ui.*;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.*;

public class Distribution extends ApplicationFrame {

	private static double[] row = {40, 0, 8, 66, 7, 18, 
								   22, 10, 13, 5, 10, 2, 
								   22, 0, 22, 0, 0, 16,
								   88, 46, 10, 14, 7, 50, 
								   21, 2, 10, 21, 46, 9, 6};
	
	private static double NbyJornal = 0;
	public Distribution(String title) {
		super(title);
		double[] diplacedRow = Arrays.stream(row)
				.map(v -> Math.abs((v + NbyJornal))).toArray();
		
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.RELATIVE_FREQUENCY);
		dataset.addSeries("Flights", diplacedRow, 7);
		JFreeChart hist = ChartFactory.createHistogram("Distribution",
				"x", "P(x)", dataset, PlotOrientation.VERTICAL, true, true, false);
		hist.setBackgroundPaint(Color.white);
		ChartPanel chartPanel = new ChartPanel(hist);
		chartPanel.setPreferredSize(new java.awt.Dimension(900, 540));
		this.setContentPane(chartPanel);
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}
	public static Map<Double, Double> dencity(double[] values) {
		double lambda = 1 / (Arrays.stream(values).average().getAsDouble());
		double max = Arrays.stream(values).max().getAsDouble();
		double min = Arrays.stream(values).min().getAsDouble();
//		Stream<Double> intervals = 
		Arrays.stream(values).flatMap(value -> DoubleStream.of(value + 2, value%2, value -2)).forEach(System.out::print);

//		Arrays.stream(values).map(val -> lambda * Math.exp(-lambda * )));
		return null;
	}
	
	public static void main(String[] args) {
//		new Distribution("Flights");
		double lambda = 1 / (Arrays.stream(row).average().getAsDouble());
		System.out.println(lambda);
		double max = Arrays.stream(row).max().getAsDouble();
		double min = Arrays.stream(row).min().getAsDouble();
		double interval = (max - min) / 7;
		Stream<Double> l = DoubleStream
				.iterate(min, val -> lambda * Math.exp(- lambda * (val + interval)))
				.limit(8).boxed();
		l.forEach(System.out::println);
		Stream<Double> t = DoubleStream
				.iterate(min, val -> (val + interval))
				.limit(8).boxed();
		t.forEach(System.out::println);	
		Map<Double, Double> lambdaTot = l.collect(Collectors.toMap(l1-> l1, l1 -> l1));
	}
}
