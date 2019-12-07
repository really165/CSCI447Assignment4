package csci447.project4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GA implements TrainingAlgorithm {

	int populationSize;
	ArrayList<Double> fitnessScores = new ArrayList<Double>();
	int weightArrayLength = 50;//TODO: determine length of weight arrays
	ArrayList<double[]> members = new ArrayList<double[]>();
	double maxMutation;
	double mutationProbability;
	double fitnessAverage;
	
	public GA(int populationSize, double mutationProbability, double maxMutation) {
		this.populationSize = populationSize;
		this.mutationProbability = mutationProbability;
		this.maxMutation = maxMutation;
	}
	
	@Override
	public double[] train(ArrayList<Example> examples) {
		System.out.println("what the fuck");
		//construct initial population(generate random weights)
		//declare variable for fitness total(used for average fitness)
		double fitnessTotal = 0;
		//for population size iterations
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
			//compute fitness for each member of the population
			//declare NN and assign weight array to it
			//compute loss of the NN using the training set
			Random fitnessRand = new Random();//TODO: determine fitness of initial population
			double fitness = fitnessRand.nextDouble();
			//add new member to the population members variable
			members.add(newWeightArray);
			//add fitness value to the fitness score variable
			fitnessScores.add(fitness);
			//add fitness score to the fitness total
			fitnessTotal += fitness;
		}
		//determine fitness average using fitness total/population size
		fitnessAverage = fitnessTotal/populationSize;
		
		//declare variable for iteration count
		int iterations = 0;
		//while population has not converged(average fitness has not changed enough) or a set number of iterations
		while(iterations < 100) {
			//selection(find the two members with the highest fitness scores)
			//declare parent1, parent2 variables(index of max weight array)
			int parent1 = 0, parent2 = 0;
			//declare max1, max2 variables(score)
			double max1 = 0, max2 = 0;
			//for the length of the fitness scores variable
			for(int i = 0; i < fitnessScores.size(); i++) {
				//get current score at index
				double currentScore = fitnessScores.get(i);
				//if current score is greater than max1
				if(currentScore > max1) {
					//max2 = max1
					max2 = max1;
					//parent2 = parent1
					parent2 = parent1;
					//max1 = current score at index
					max1 = currentScore;
					//parent1 = current index
					parent1 = i;
				}
				//else if current score is greater than max2
				else if(currentScore > max2) {
					//max2 = current score at index
					max2 = currentScore;
					//parent2 = current index
					parent2 = i;
				}
				else {
					//do nothing
				}
			}
			//get the weight arrays of parent1 and parent2
			double[] parentArray1 = members.get(parent1);
			double[] parentArray2 = members.get(parent2);		
			
			//crossover(select random crossover point, swap values of parents, this creates two offspring)
			//declare two children weight arrays
			//child1 = parent1
			double[] child1 = parentArray1.clone();
			//child2 = parent2
			double[] child2 = parentArray2.clone();
			//generate random int between 1 and size of weight array-2
			int minPoint = 1;
			int maxPoint = weightArrayLength-2;
			Random point = new Random();
			int crossoverPoint = point.nextInt((maxPoint - minPoint) + 1) + minPoint;
			//for i = random int to size of weight array
			for(int i = crossoverPoint; i < weightArrayLength; i++) {
				//swap the values at index
				//temp = child1 value at index
				double temp = child1[i];
				//child1 value at index = child2 value at index
				child1[i] = child2[i];
				//child2 value at index = temp
				child2[i] = temp;
			}

			//mutation(slightly modify the values of the offspring, +/- rand on a couple of the weights)
			//for length of weight array
			for(int i = 0; i < weightArrayLength; i++) {
				//the first offspring
				//determine if weight will be mutated
				//generate random number between 0 and 100
				Random randomPercentage1 = new Random();
				int percentage1 =  randomPercentage1.nextInt(100+1);
				//if number is less than mutation probability
				if(percentage1 < mutationProbability) {
					//determine how much to mutate by
					//generate random number between negative max mutation and positive max mutation
					Random mutation = new Random();
					double minMutate = -maxMutation;
				    double maxMutate = maxMutation;
				    double weightChange = minMutate + (maxMutate - minMutate) * mutation.nextDouble();
					//add this number to the weight
				    child1[i] += weightChange;
				}
				//the second offspring
				//determine if weight will be mutated
				//generate random number between 0 and 100
				Random randomPercentage2 = new Random();
				int percentage2 =  randomPercentage1.nextInt(100+1);
				//if number is less than mutation probability
				if(percentage2 < mutationProbability) {
					//determine how much to mutate by
					//generate random number between negative max mutation and positive max mutation
					Random mutation = new Random();
					double minMutate = -maxMutation;
				    double maxMutate = maxMutation;
				    double weightChange = minMutate + (maxMutate - minMutate) * mutation.nextDouble();
					//add this number to the weight
				    child2[i] += weightChange;
				}
			}

			//remove the two least fit members from the population members variable(along with their fitness scores)
			//declare index1, index2(keep track of min index)
			int index1 = 0, index2 = 0;
			//declare min1, min2 variables(min score)(min1 is the lowest)
			double min1 = Double.MAX_VALUE, min2 = Double.MAX_VALUE;
			//for the length of the fitness scores variable
			for(int i = 0; i < fitnessScores.size(); i++) {
				//get current score
				double currentScore = fitnessScores.get(i);
				//if current score is less than min1
				if(currentScore < min1) {
					//min2 = min1
					min2 = min1;
					//index2 = index1
					index2 = index1;
					//min1 = current score
					min1 = currentScore;
					//index1 = current weight array index
					index1 = i;
				}
				//else if current score is less than max2
				else if(currentScore < min2) {
					//min2 = current score
					min2 = currentScore;
					//index2 = current weight array index
					index2 = i;
				}
				else {
					//do nothing
				}
			}
			//remove the weight arrays and scores at index1 and index2
			members.remove(index1);
			fitnessScores.remove(index1);
			members.remove(index2);
			fitnessScores.remove(index2);

			//add the new offspring and their fitness scores to the population
			//determine the fitness of the offspring
			//first offspring
			//declare NN and assign first offspring's weight array to it
			//compute loss of the NN using the training set
			Random fitnessRand1 = new Random();//TODO: determine fitness of first child
			double fitness1 = fitnessRand1.nextDouble();
			//add the first offspring to the population members variable
			members.add(child1);
			//add fitness value of the first offspring to the fitness score variable
			fitnessScores.add(fitness1);
			//second offspring
			//declare NN and assign second offspring's weight array to it
			//compute loss of the NN using the training set
			Random fitnessRand2 = new Random();//TODO: determine fitness of second child
			double fitness2 = fitnessRand2.nextDouble();
			//add the second offspring to the population members variable
			members.add(child2);
			//add fitness value of the second offspring to the fitness score variable
			fitnessScores.add(fitness2);
			
			//compute average fitness for the population
			//delcare variable for new fitness total
			double newFitnessTotal = 0;
			//for length of fitness scores variable
			for(int i = 0; i < fitnessScores.size(); i++) {
				//add fitness score to fitness total variable
				newFitnessTotal += fitnessScores.get(i);
			}
			//new fitness average is new fitness total/length of fitness scores variable
			double newFitnessAverage = newFitnessTotal/fitnessScores.size();
			System.out.println("New fitness average is: " + newFitnessAverage);

			//determine if fitness has changed enough
			//if new fitness average > old fitness average
			if(newFitnessAverage > fitnessAverage) {
				//update fitness average
				//old fitness average = new fitness average
				fitnessAverage = newFitnessAverage;
				//increment iteration count
				iterations++;
			}
			else {
				break;
			}
		}
		//find the most fit member of the population
		//declare a variable of maxFitnessScore
		double maxFitnessScore = 0;
		//declare a variable to store index of the best score
		int maxFitnessIndex = 0;
		//for length of the population size
		for(int i = 0; i < fitnessScores.size(); i++) {
			//get the current score
			double currentScore = fitnessScores.get(i);
			//if current score is greater than max
			if(currentScore > maxFitnessScore) {
				//maxFitnessScore is equal to the current score
				maxFitnessScore = currentScore;
				//index of best score is the current index
				maxFitnessIndex = i;
			}
		}
		//return the array of the most fit member of the population
		System.out.println("Most fit array: " + Arrays.toString(members.get(maxFitnessIndex)) + "\nwith a score of " + fitnessScores.get(maxFitnessIndex));
		return members.get(maxFitnessIndex);
	}
}
