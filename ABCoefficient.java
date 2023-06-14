package practicemodelingsystem;

// This class need for matrix transform. 
// If we have game 3x3 we need a, b coefficient
// for calculate in Theory Game to Kramer's Method

public class ABCoefficient {
	
	public double[][] a;
	public double[][] b;
	
	public ABCoefficient(double[][] a, double[][] b) {
		this.a = a;
		this.b = b;
	}
	
	public static ABCoefficient matrixTransform(double[][] g) {
		double[][] a = new double[3][3];
		double[][] b = new double[3][1];
		for(int i = 0; i < 3; i++) {
			a[i][0] = g[i][0] - g[i][1];
			a[i][1] = g[i][0] - g[i][2];
			a[i][2] = 1;
			b[i][0] = g[i][0];
		}
//			{g[1][1] - g[1][2], g[1][1] - g[1][3], 1}, 
//			{g[2][1] - g[2][2], g[2][1] - g[2][3], 1}, 
//			{g[3][1] - g[3][2], g[3][1] - g[3][3], 1};
		return new ABCoefficient(a, b);
	}
}
