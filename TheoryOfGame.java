package practicemodelingsystem;

import java.util.Arrays;

public class TheoryOfGame {
	
	public static double[] solveofGame2x2(double[][] matrix) {
		
		double[] solution = new double[5];
		
		solution[0] = (matrix[1][1] - matrix[1][0]) 
				/ (matrix[0][0] + matrix[1][1] - matrix[0][1] - matrix[1][0]); //p1
		solution[1] = 1 - solution[0];										   //p2
		solution[2] = solution[0] * matrix[0][0] + solution[1] * matrix[1][0]; //v
		
		solution[3] = (solution[2] - matrix[0][1]) / (matrix[0][0] - matrix[0][1]);//q1
		solution[4] = 1 - solution[3];											   //q2
		
		return solution;
	}
	
	public static double[] solveOfGame3x3(double[][] matrix) {
		
		double a1112 = matrix[0][0] - matrix[0][1];
		double a1113 = matrix[0][0] - matrix[0][2];
		double a2122 = matrix[1][0] - matrix[1][1];
		double a2123 = matrix[1][0] - matrix[1][2];
		double a3132 = matrix[2][0] - matrix[2][1];
		double a3133 = matrix[2][0] - matrix[2][2];
		
		double mainDefiner = (a1112 * a2123) + (a1113 * a3132) + (a2122 * a3133)
							- (a3132 * a2123) - (a1112 * a3133) - (a2122 * a1113);
		double p2Definer = (matrix[0][0] * a2123) + (matrix[2][0] * a1113) 
						  + (matrix[1][0] * a3133) - (matrix[2][0] * a2123)
						  - (matrix[0][0] * a3133) - (matrix[1][0] * a1113);
		
		double p3Definer = (matrix[0][1] * a1112) + (matrix[0][0] * a3132) 
							+ (matrix[0][2] * a2122) - (matrix[0][1] * a3132)
							- (matrix[0][2] * a1112) - (matrix[0][0] * a2122);
		
		double viDefiner = (matrix[2][0] * a1112 * a2123) + (matrix[1][0] * a3132 * a1113) 
				  	+ (matrix[0][0] * a2122 * a3133) - (matrix[0][0] * a3132 * a2123)
				  	- (matrix[1][0] * a1112 * a3133) - (matrix[2][0] * a2122 * a1113);
		
		double[] solution = new double[4];
		
		solution[1] = p2Definer / mainDefiner;
		solution[2] = p3Definer / mainDefiner;
		solution[3] = viDefiner / mainDefiner;
		solution[0] = 1 - p2Definer / mainDefiner - p3Definer / mainDefiner;
		
		return solution;
	} 

//===========================================================================
	public static void main(String[] args) {
		double[][] matrixOfGame3x3 =   {{3.0, 6.0, 10.0},
										{4.0, 7.0, 5.0},
										{8.0, 2.0, 1.0}};
		
		double[][] matrixOfGame2x2 =   {{0.2, 0.8},
										{0.7, 0.3}};	
//===========================================================================
		ABCoefficient ab = ABCoefficient.matrixTransform(matrixOfGame3x3);

		
		System.out.println("Game 3 x 3 - Kramer: " + Arrays.toString(
				SolutionSLAQ.methodOfKramera3x3(ab.a, ab.b)));
		
		System.out.println("Game 3 x 3: " + Arrays.toString(
				TheoryOfGame.solveOfGame3x3(matrixOfGame3x3)));
		
		System.out.println("Game 2 x 2: " + Arrays.toString(
				TheoryOfGame.solveofGame2x2(matrixOfGame2x2)));
	}
}
