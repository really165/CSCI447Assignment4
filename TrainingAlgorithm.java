package csci447.project4;

import java.util.ArrayList;

// interface for training a neural network to be generalized for ga, dep, and pso algorithms
interface TrainingAlgorithm {
	
    double[] train(ArrayList<Example> examples);

    String toString();

}