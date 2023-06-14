package practicemodelingsystem;

import java.util.Arrays;

public class AlgorithmFloyd {
	
	public int[][] MATRIX;
	public int[][] S_MATRIX;
	public static int inf = 1000000;
	public AlgorithmFloyd(int[][] matrix, int[][] sMatrix) {
		this.MATRIX = matrix;
		this.S_MATRIX = sMatrix;
	}
	
	public int[][] getMatrix() {
		return this.MATRIX;
	}
	
	public int[][] getSmatrix() {
		return this.S_MATRIX;
	}
	
	public String toString() {
		return Arrays.toString(this.MATRIX);
	}
	
	public static AlgorithmFloyd shortWayCalculator(int[][] matrix, int[][] sMatrix) {
		for(int k = 0; k < matrix.length; k++)
			for(int i = 0; i < matrix.length; i++)
				for(int j = 0; j < matrix.length; j++)
				{
					if(matrix[i][j] > matrix[i][k] + matrix[k][j]) {
						matrix[i][j] = matrix[i][k] + matrix[k][j];
						sMatrix[i][j] = k+1;
					}
				}
		return new AlgorithmFloyd(matrix, sMatrix);
	}
	
	public static void main(String[] args) {
		int[][] tempMatrix = {{0, 1, 5, inf,inf, inf,inf},
							  {inf, 0, inf, 1, 5, inf, inf},
							  {inf, 4, 0, inf, 1, inf, inf},
							  {inf, inf, 1, 0, inf, 2, inf},
							  {inf, inf, inf, 3, 0, 1, 3},
							  {inf, 1, inf, inf, inf, 0, 4},
							  {inf, inf, inf, inf, inf, inf, 0}}; 
		
		int[][] tempSmatrix = {{0, 2, 3, 4, 5, 6, 7},
							   {1, 0, 3, 4, 5, 6, 7}, 
							   {1, 2, 0, 4, 5, 6, 7},
							   {1, 2, 3, 0, 5, 6, 7},
							   {1, 2, 3, 4, 0, 6, 7},
							   {1, 2, 3, 4, 5, 0, 7},
							   {1, 2, 3, 4, 5, 6, 0}};
		
		System.out.println("INITIAL CONDITIONS:\nMatrix:");
		for(int i = 0; i < tempMatrix.length; i++) {
			System.out.println((i+1) + "-> " + Arrays.toString(tempMatrix[i]));
		}
		
		System.out.println("\nMatrix: S0");
		for(int i = 0; i < tempSmatrix.length; i++) {
			System.out.println((i+1) + "-> " + Arrays.toString(tempSmatrix[i]));
		}	
		
		int[][] tempOne = AlgorithmFloyd.shortWayCalculator(tempMatrix, tempSmatrix).getMatrix();
		System.out.println("\nSolved matrix:\nMatrix(6's step):");
		for(int i = 0; i < tempMatrix.length; i++) {
			System.out.println((i+1) + "-> " + Arrays.toString(tempOne[i]));
		}
		
		int[][] tempTwo = AlgorithmFloyd.shortWayCalculator(tempMatrix, tempSmatrix).getSmatrix();		
		System.out.println("\nS6:\nMatrix:");
		for(int i = 0; i < tempMatrix.length; i++) {
			System.out.println((i+1) + "-> " + Arrays.toString(tempTwo[i]));
		}
	}
}
