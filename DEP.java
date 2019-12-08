package csci447.project4;

import java.util.ArrayList;
import java.util.Random;
public class DEP implements TrainingAlgorithm {

	ArrayList<Double> fitnessScores = new ArrayList<Double>();
	ArrayList<double[]> population = new ArrayList<double[]>();
	int weightArrayLength = 50;
	int populationSize;
	double beta;
	double pr;
	
	public DEP(int population, double beta, double pr) {
		this.populationSize=population;
		this.beta=beta;
		this.pr=pr;
	}
	
	@Override
	public double[] train(ArrayList<Example> examples) {
 		int t=0; //generation counter
 		double trialVector;
 		
 		for(int i = 0; i < populationSize; i++) {
			//generate random weights
 			//for length of weight array
 			//declare new weight array
 			double[] newWeightArray = new double[weightArrayLength];
 			for(int j = 0; j < weightArrayLength; j++) {
 				//generate random number within a certain range(-0.1 to 0.1)
 				Random weight = new Random();
 				double minWeight = -0.1;
 			    double maxWeight = 0.1;
 			    double randomWeight = minWeight + (maxWeight - minWeight) * weight.nextDouble();
 				//put that number in the weight array at index
 			    newWeightArray[j] = randomWeight;
 			}
 			Random fitnessRand = new Random();
 			double fitness = fitnessRand.nextDouble();
 			//add new member to the population members variable
 			population.add(newWeightArray);
 			//add fitness value to the fitness score variable
 			fitnessScores.add(fitness);
 		}
 		
 		// Offpring
 		double[] Xo=new double[1];
 		Random rand=new Random();
 		//Random examples
 		double[] x1;
 		double[] x2;
 		double[] x3;
 		
		int iterations=0;
		while (iterations<100) {
			iterations++;
			for (int j=0; j<populationSize; j++) {
				
				// select three non-repeated random vectors
				ArrayList<Integer> repeated = new ArrayList<Integer>();
				repeated.add(j);
				int randNumber=repeatedCheck(repeated);
				x1=population.get(randNumber);
				repeated.add(randNumber);
				randNumber=repeatedCheck(repeated);
				x2=population.get(randNumber);
				repeated.add(randNumber);
				randNumber=repeatedCheck(repeated);
				x3=population.get(randNumber);

				// Trial Vector
				trialVector=x1[0]+beta*(x2[0]-x3[0]);
				// Offpring-Crossover
				double fitnessOffpring;
				double randDouble=rand.nextDouble();
				if(randDouble<=pr) {
					Xo=population.get(j); 
				}
				else {
					Xo[0]=trialVector;
				}
				//Set fitness of the new offpring
				Random fitnessRand = new Random();
				fitnessOffpring = fitnessRand.nextDouble();
				
				//Selection
				if(fitnessOffpring>fitnessScores.get(j)) {//if fitness of offpring is better
					population.set(j+1, Xo);
					fitnessScores.set(j+1, fitnessOffpring);
				}
				else {
					population.set(j+1, population.get(j));
					fitnessScores.set(j+1, fitnessScores.get(j));
				}
				
			}
		}
		return null;
	}
	public int repeatedCheck(ArrayList<Integer> num) {
		Random rand=new Random();
		int randInt=rand.nextInt(population.size());
		if(num.contains(randInt)) {
			return repeatedCheck(num);
		}
		else {
			return randInt;
		}
	}

}

