package csci447.project4;

import java.util.ArrayList;
import java.util.Random;

import csci447.project4.Example;
import csci447.project4.NeuralNetwork;

// represents a particle for the pso algorithm
class Particle {

    public static Random random = new Random();

    public double[] velocity;
    public double[] position;
    public double[] pbest;
    public double bestf;


    public Particle(int size) {
        this.velocity = new double[size];
        this.position = new double[size];
        this.pbest = new double[size];
        this.bestf = 0;
        for (int i = 0; i < size; i++) {
            position[i] = pbest[i] = 0.01 - random.nextDouble() * 0.02;
        }
    }

    // gets the fitness of the current position
    public double getFitness(NeuralNetwork network, ArrayList<Example> examples) {
        // set network weights to current position and calcualte 0-1 loss or mse
        network.setWeights(position);
        if (network.isClassification()) {
            int actual;
            int predicted;
            int loss = 0;
            for (Example e : examples) {
                actual = (int) e.c;
                predicted = network.classify(e);
                if (actual != predicted) {
                    loss++;
                }
            }
            return 1.0/((double)loss/examples.size());
        } else {
            double actual, predicted;
            double mse = 0;
            for (Example e : examples) {
                actual = e.c;
                predicted = network.regress(e);
                mse += Math.pow(predicted-actual, 2);
            }
            return 1.0/mse;
        }        
        // return 1/loss or mse
    }

}