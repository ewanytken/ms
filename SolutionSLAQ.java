package practicemodelingsystem;

public class SolutionSLAQ {
	
	public static double[] methodOfKramera3x3(double[][] a, double[][] b) {
		// 00 01 02
		// 10 11 12
		// 20 21 22
		double[] definers = new double[4];
		for(int i = 0; i < 4; i++) {
			if(i == 0) {
				definers[i] = (a[0][0] * a[1][1] * a[2][2]) + (a[1][0] * a[2][1] * a[0][2]) 
							+ (a[2][0] * a[0][1] * a[1][2]) - (a[0][0] * a[2][1] * a[1][2])
							- (a[2][0] * a[1][1] * a[0][2]) - (a[1][0] * a[0][1] * a[2][2]);
			}
			if(i == 1) {
				definers[i] = (b[0][0] * a[1][1] * a[2][2]) + (b[1][0] * a[2][1] * a[0][2]) 
							+ (b[2][0] * a[0][1] * a[1][2]) - (b[0][0] * a[2][1] * a[1][2])
							- (b[2][0] * a[1][1] * a[0][2]) - (b[1][0] * a[0][1] * a[2][2]);
			}
			if(i == 2) {
				definers[i] = (a[0][0] * b[1][0] * a[2][2]) + (a[1][0] * b[2][0] * a[0][2]) 
							+ (a[2][0] * b[0][0] * a[1][2]) - (a[1][0] * b[0][0] * a[2][2])
							- (a[0][0] * b[2][0] * a[1][2]) - (a[2][0] * b[1][0] * a[0][2]);
			}
			if(i == 3) {
				definers[i] = (a[0][0] * a[1][1] * b[2][0]) + (a[1][0] * a[2][1] * b[0][0]) 
							+ (a[2][0] * a[0][1] * b[1][0]) - (a[0][0] * a[2][1] * b[1][0])
							- (a[2][0] * a[1][1] * b[0][0]) - (a[1][0] * a[0][1] * b[2][0]);
			}
		}
		for(int i = 1; i <= 3; i++) {
			definers[i] = definers[i] / definers[0];
			if(i == 3) {
				definers[0] = 1 - definers[1] - definers[2];
			}
		}
		return definers;
	}
}