package practicemodelingsystem;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MonteCarlo {
	
	final static int MIN_RANGE = 5;
	final static int MAX_RANGE = 75;
	final static int N = 1000;
	final static int MISSILE = 6;
	final static int AIR_DEFENSE_ROCKET = 8;
	final static int THRESHOLD = 4;
	final static double P_1 = 0.4; //Probability from 0.4 to 0.6
	final static double P_2 = 0.5;
	final static double P_3 = 0.6;
	final static double CHANCE_MISSILE = 0.8;
	
	public static void main(String[] args) {
		double array[] = new double[N];
		for(int i = 0; i < N; i++) {
			array[i] = (double)MonteCarloMethod(AIR_DEFENSE_ROCKET, MISSILE, P_1, P_2, P_3, CHANCE_MISSILE)/N;
		}		
		System.out.println(Arrays.toString(array));
		double avr = Arrays.stream(array).sum();
		double chance = (double)Arrays.stream(array).filter(x -> x >= (double)THRESHOLD/N).count()/N;
		System.out.println("Average is: " + avr + " Probobility is: " + chance*100 + "%");
		//Double[] arr = Stream.generate(() -> (double)MonteCarloMethod(AIR_DEFENCE_ROCKET, MISSILE, P_1, P_2, P_3, CHANCE_MISSILE))
		//		.limit(N).map(x -> x.)
	}
	
	public static int randomMethod(int min, int max) {
		return (int)(Math.random()*(max - min)+1) + min;
	}
	
	public static int randomAirFieldHit(double p) {
		if((int)(Math.random()*10) < p*10) //0-9 => 10
			return 1;
		return 0;
	}	
	
	public static int randomMissileHit(double p1, double p2, double p3) {
		int temp = (int)(Math.random()*3+1); // [1 2 3]
		System.out.print(temp + " ");
		if(temp == 1)
		{
			if((int)(Math.random())*10 < p1*10) // 0-9 => 10
				return 0;
		}
		else if(temp == 2) {
			if((int)(Math.random())*10 < p2*10)
				return 0;
		}
		else if(temp == 3) {
			if((int)(Math.random()*10) < p3*10)
				return 0;
		}
		return 1;
	}
	
	public static int MonteCarloMethod(int rocketAirdefence, int missile, double p1, double p2, double p3, double pMissile) {
		int hitRocket = 0;
		int currentRange[] = new int[missile];
		for(int i = 0; i < missile && rocketAirdefence > 0; i++)
		{
			rocketAirdefence--;
			currentRange[i] = randomMethod(MIN_RANGE, MAX_RANGE);
			if(currentRange[i] <= MIN_RANGE){
				hitRocket++;
				System.out.println(hitRocket);
			}
			else {
				if(randomMissileHit(p1, p2, p3) == 1) {
					if(randomAirFieldHit(pMissile) == 1) {
						hitRocket++;
					}
				}
				else {
					if(rocketAirdefence > 0) {
						rocketAirdefence--;
						if(randomMissileHit(p1, p2, p3) == 1) {
							if(randomAirFieldHit(pMissile) == 1) {
								hitRocket++;
							}
						}
					}
				}
			}
		}
		int addMissingRocket = (int) IntStream.of(currentRange).filter(x -> x == 0).count();
		System.out.println("choose case; random distance: " + Arrays.toString(currentRange) + " Missile attack: " + (hitRocket) + " " + addMissingRocket);
		return hitRocket + addMissingRocket;
	}
}
