package csci447.project4;

import java.util.ArrayList;
import java.util.Random;
import csci447.project4.NeuralNetwork;

public class DEP implements TrainingAlgorithm {

	public NeuralNetwork network;
	ArrayList<Double> fitnessScores = new ArrayList<Double>();
	ArrayList<double[]> population = new ArrayList<double[]>();
	int weightArrayLength = 50;
	int populationSize;
	double beta;
	double pr;
	
	public DEP(NeuralNetwork network, int population, double beta, double pr) {
		this.network=network;
		this.populationSize=population;
		this.beta=beta;
		this.pr=pr;
	}
	
	@Override
	public double[] train(ArrayList<Example> examples) {
		weightArrayLength = network.getNumWeights();
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
 			
 			double fitness = getFitness(examples, newWeightArray);
 			//add new member to the population members variable
 			population.add(newWeightArray);
 			//add fitness value to the fitness score variable
 			fitnessScores.add(fitness);
 		}
 		
 		// Offpring
 		double[] Xo=new double[weightArrayLength];
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
				
				for(int i = 0; i < weightArrayLength; i++) {
					if(randDouble<=pr) {
						Xo[i]=population.get(j)[i]; 
					}
					else {
						Xo[i]=trialVector;
					}
	 			}
				
				//Set fitness of the new offpring
				fitnessOffpring = getFitness(examples, Xo);
				
				//Selection
				if(j==population.size()-1) {//Last vector
					if(fitnessOffpring>fitnessScores.get(j)) {//if fitness of offpring is better
						population.add(Xo);
						fitnessScores.add(fitnessOffpring);
					}
					else {
						population.add(population.get(j));
						fitnessScores.add(fitnessScores.get(j));
					}
				}
				else {
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
		}
		System.out.println("Final Fitness: " + fitnessScores.get(fitnessScores.size()-1));
		return population.get(population.size()-1);
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
	
	public double getFitness(ArrayList<Example> examples, double[] weights) {
        network.setWeights(weights);
        int actual;
        int predicted;
        int loss = 0;
        for (Example e : examples) {
            network.classify(e);
            actual = (int) e.c;
            predicted = network.classify(e);
            if (actual != predicted) {
                loss++;
            }
        }
        double lossTotal = (double)loss;
        double examplesSize = (double)examples.size();
        if(lossTotal/examplesSize == 0.0) {
        	return examplesSize;
        }
        return 1/(lossTotal/examplesSize);
    }
	

}

