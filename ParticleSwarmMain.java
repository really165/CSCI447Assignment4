import java.util.ArrayList;

import csci447.project3.CrossValidation;
import csci447.project4.DataPreprocessor;
import csci447.project4.Example;
import csci447.project4.NeuralNetwork;

public class ParticleSwarmMain {

    public static void main(String[] args) {

        ArrayList<Example> examples = DataPreprocessor.abalone();
        int[] hiddenLayers = new int[]{8,6};
        
        NeuralNetwork network = new NeuralNetwork(
            DataPreprocessor.ABALONE_NUM_FEATURES,
            DataPreprocessor.ABALONE_NUM_CLASSES,
            hiddenLayers
        );

        int c1 = 1;
        int c2 = 1;
        Swarm swarm = new Swarm(network, numParticles, c1, c2);

        ArrayList<Example>[][] folds = CrossValidation.getFolds(examples);

        ArrayList<Example> training;
        ArrayList<Example> validation;
        for (ArrayList<Example>[] fold : folds) {
            training = fold[0];
            validation = fold[1];

            // Run the pso algorithm
            swarm.update(training);
            
            // Set the network weights to the global best
            network.setWeights(swarm.gbest);

            // Run validation
            int actual = (int) e.c;
            int predicted;
            double loss = 0;
            int i;
            for (Example e : validation) {
                predicted = network.classify(e);
                if (actual != predicted) loss++;
            }
            loss = loss/validation.size();
        }
    }

}