package practicemodelingsystem;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProbabilityOfDefeat {
	private double proHit;
	private double proDefeat;
	private double proSave;
	private double probability;
	final static Logger log = Logger.getLogger("practicemodelingsystem");

	public ProbabilityOfDefeat(ProbabilityBuilder pb) {
		if(pb == null)
			throw new IllegalStateException("Can't find Probability Object");
		if(pb.proDefeat == 0) {
			throw new IllegalStateException("Install some Probability of Defeat");
		}
		this.proHit = pb.proHit;
		this.proDefeat = pb.proDefeat;
		this.proSave = pb.proSave;
		this.probability = pb.probability;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Общая вероятность: ").append(this.probability)
		.append(" \nВероятность попадания: ").append(this.proHit)
		.append(" \nВероятность поражения: ").append(this.proDefeat)
		.append(" \nВероятность сохранения: ").append(this.proSave);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		ProbabilityOfDefeat demo = new ProbabilityOfDefeat.ProbabilityBuilder()
				.setPro(5, 2, 10, 10)
				.setProDefeat(2)
				.setProSave(0.2)
				.calculation();
		System.out.println(demo);
	}
	
	public static class ProbabilityBuilder {
		private double proHit = 1.0;
		private double proDefeat = 1.0;
		private double proSave = 1.0;
		private double probability;
		
		public ProbabilityBuilder setPro(double l1, double l2, double sigmaOne, double sigmaTwo) {
			double proHit = (Math.pow(0.4769, 2) * (l1 * l2)) / (Math.PI * sigmaOne * sigmaTwo);
			this.proHit = proHit;
			return this;
		}
		
		public ProbabilityBuilder setProDefeat(int numberOfTarget) {
			this.proDefeat = (double) 1 / numberOfTarget;
			return this;
		}
		
		public ProbabilityBuilder setProSave(double proDefeatFromEnemy) {
			double proSave = (1 - proDefeatFromEnemy);
			this.proSave = proSave;
			return this;
		}
		
		public ProbabilityOfDefeat calculation() {
			ProbabilityOfDefeat pod = null;
			log.setLevel(Level.ALL);
			if(validateProbability()) {
				pod = new ProbabilityOfDefeat(this);
				pod.probability = pod.proDefeat * pod.proHit * pod.proSave;
			} else {
				log.info("Can't find ProbabilityOfDefeat");
			}
			return pod;
		}
		private boolean validateProbability() {
			return (this.proDefeat != 0);
		}
	}
}

