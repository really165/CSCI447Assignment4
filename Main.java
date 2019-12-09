package csci447.project4;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<Example> abaloneSet = DataPreprocessor.abalone();
        ArrayList<Example> carSet = DataPreprocessor.car();
        ArrayList<Example> segmentationSet = DataPreprocessor.segmentation();
        ArrayList<Example> forestSet = DataPreprocessor.forest();
        ArrayList<Example> machineSet = DataPreprocessor.machine();
		ArrayList<Example> wineSet = DataPreprocessor.wine();
		
		int[] hidden0 = new int[]{};
		int[] hidden1 = new int[]{10};
		int[] hidden2 = new int[]{10,10};

		// O
		NeuralNetwork[] network = new NeuralNetwork[3];
		network[0] = new NeuralNetwork(DataPreprocessor.WINE_NUM_FEATURES, 1, hidden0, false);
		network[1] = new NeuralNetwork(DataPreprocessor.WINE_NUM_FEATURES, 1, hidden1, false);
		network[2] = new NeuralNetwork(DataPreprocessor.WINE_NUM_FEATURES, 1, hidden2, false);

		for (NeuralNetwork n : network) n.setActivationFunction(new SigmoidalActivationFunction());

		int populationSize = 100;
		double mr = 80;
		double maxm = 0.1;
		double beta = 1.5;
		double pr = 0.5;
		int swarmSize = 10;
		double c1 = 2.05;
		double c2 = 2.05;
		double w = 0.729;
		double loss;

		for (NeuralNetwork n : network) {
			// genetic algorithm
			n.setTrainingAlgorithm(new GA(n, populationSize, mr, maxm));
			loss = CrossValidation.run(n, wineSet, true);
			System.out.println("Loss: " + loss);
			// differential evolution
			n.setTrainingAlgorithm(new DEP(n, populationSize, beta, pr));
			loss = CrossValidation.run(n, wineSet, true);
			System.out.println("Loss: " + loss);
			// particle swarm
			n.setTrainingAlgorithm(new PSO(n, swarmSize, c1, c2, w));
			loss = CrossValidation.run(n, wineSet, true);
			System.out.println("Loss: " + loss);
		}
	}


}
