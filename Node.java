package csci447.project4;

import java.util.Random;

/**
 * Represents a node in a neural network layer
 */
public class Node {

    public double[] weight;
    public double bias;
    public double output;
    public double error;
    public double[] deltaw;
    public double deltab;


    public Node(int incomingNodes) {
        weight = new double[incomingNodes];
        deltaw = new double[incomingNodes];
        initialize();
    }

    public void initialize() {
        // Used to generate random numbers
        Random random = new Random();
        // Bias gets a random number between -0.1 and 0.1 and delta is 0
        bias = 0.1 - random.nextDouble() * 0.2;
        deltab = 0;
        // Weights get a random number between -0.1 and 0.1 and deltas are 0
        for (int i = 0; i < weight.length; i++) {
            weight[i] = 0.01 - random.nextDouble() * 0.02;
            deltaw[i] = 0;
        }
    }

}