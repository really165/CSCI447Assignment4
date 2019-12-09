package csci447.project4;

import java.util.ArrayList;
import java.util.Random;
import csci447.project4.NeuralNetwork;

public class DEP implements TrainingAlgorithm {

	public NeuralNetwork network;
	
	int weightArrayLength;
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
		
		ArrayList<Double> fitnessScores = new ArrayList<Double>();
		ArrayList<double[]> population = new ArrayList<double[]>();
		ArrayList<ArrayList<double[]>> populationSet = new ArrayList<ArrayList<double[]>>();
		ArrayList<ArrayList<Double>> fitnessSet = new ArrayList<ArrayList<Double>>();
		double bestFitness=0;
		int position=0;
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
 		populationSet.add(population);// Initial population
 		fitnessSet.add(fitnessScores);
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
			ArrayList<double[]> tempPopulation = new ArrayList<double[]>();
			ArrayList<Double> tempFitness = new ArrayList<Double>();
			for (int j=0; j<populationSet.get(t).size(); j++) {
				
				// select three non-repeated random vectors
				ArrayList<Integer> repeated = new ArrayList<Integer>();
				repeated.add(j);
				int randNumber=repeatedCheck(repeated);
				x1=populationSet.get(t).get(randNumber);
				repeated.add(randNumber);
				randNumber=repeatedCheck(repeated);
				x2=populationSet.get(t).get(randNumber);
				repeated.add(randNumber);
				randNumber=repeatedCheck(repeated);
				x3=populationSet.get(t).get(randNumber);

				// Trial Vector
				trialVector=x1[0]+beta*(x2[0]-x3[0]);
				// Offpring-Crossover
				double fitnessOffpring;
				
				for(int i = 0; i < weightArrayLength; i++) {
					double randDouble=rand.nextDouble();
					if(randDouble<=pr) {
						Xo[i]=populationSet.get(t).get(j)[i]; 
					}
					else {
						Xo[i]=trialVector;
					}
	 			}
				
				//Set fitness of the new offpring
				fitnessOffpring = getFitness(examples, Xo);
				
				//Selection
				
				if(fitnessOffpring>fitnessScores.get(j)) {//if fitness of offpring is better
					tempPopulation.add(Xo); //First vector have the best fitness
					tempFitness.add(fitnessOffpring);
				}
				else {
					tempPopulation.add(populationSet.get(t).get(j));
					tempFitness.add(fitnessSet.get(t).get(j));
				}
				if(tempFitness.get(j)>bestFitness) {//Determine best fitness of the population
					bestFitness=tempFitness.get(j);
					position=j;
				}
			}
			populationSet.add(tempPopulation);
			fitnessSet.add(tempFitness);
			System.out.println("Fitness Population: " +t +" - "+ bestFitness);
			t++;
		}
		System.out.println("Final Fitness: " + bestFitness);
		return populationSet.get(populationSet.size()-1).get(position);
	}
	public int repeatedCheck(ArrayList<Integer> num) {
		Random rand=new Random();
		int randInt=rand.nextInt(populationSize);
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

