package practicemodelingsystem;

import static java.lang.Math.*;

public class QueuingSystem {
	
	private static double lambda = 0.75;
	private static double mu = 0.25;
	private static double N = 3;
	
	public static double factorial(double n) {
		if(n == 0) return 1;
		return factorial(n - 1) * n;
	}
	public static double P0(double lambda, double mu, double N) {
		double alfa = lambda / mu;
		double sum = 1;
		for(int i = 1; i <= N; i++) {
			sum += (pow(alfa, i) / (QueuingSystem.factorial(i)));
		}
		return 1/sum;
	}
	public static double Pn(double lambda, double mu, double N) {
		return pow(N, N) / factorial(N) * QueuingSystem.P0(lambda, mu, N);
	}
	
	public static double pRefusal(double lambda, double mu, double N) {
		return QueuingSystem.Pn(lambda, mu, N);
	}
	
	public static double relatedThroughput(double lambda, double mu, double N) {
		return 1- pow(N, N) / factorial(N) * QueuingSystem.P0(lambda, mu, N);
	}
	
	public static double absoluteThroughput(double lambda, double mu, double N) {
		return lambda - QueuingSystem.relatedThroughput(lambda, mu, N);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Probobality P0: " + P0(lambda, mu, N));
		System.out.println("Probobality PN: " + Pn(lambda, mu, N));
		System.out.println("Probobality Refusal: " + pRefusal(lambda, mu, N));
		System.out.println("Related Throughput: " + relatedThroughput(lambda, mu, N));
		System.out.println("Absolute Throughput: " + absoluteThroughput(lambda, mu, N));
	}

}
