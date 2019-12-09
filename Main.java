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
		network[0] = new NeuralNetwork(DataPreprocessor.ABALONE_NUM_FEATURES, DataPreprocessor.ABALONE_NUM_CLASSES, hidden0);
		network[1] = new NeuralNetwork(DataPreprocessor.ABALONE_NUM_FEATURES, DataPreprocessor.ABALONE_NUM_CLASSES, hidden1);
		network[2] = new NeuralNetwork(DataPreprocessor.ABALONE_NUM_FEATURES, DataPreprocessor.ABALONE_NUM_CLASSES, hidden2);

		for (NeuralNetwork n : network) n.setActivationFunction(new SigmoidalActivationFunction());

		int swarmSize = 10;
		double c1 = 2.05;
		double c2 = 2.05;
		double w = 0.729;
		double loss;

		for (NeuralNetwork n : network) {
			n.setTrainingAlgorithm(new PSO(n, swarmSize, c1, c2, w));
			loss = CrossValidation.run(n, abaloneSet, false);
			System.out.println("Loss: " + loss);
		}
	}


}
