package csci447.project4;

import java.util.ArrayList;
import java.util.Random;

import csci447.project4.Example;
import csci447.project4.NeuralNetwork;

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

    public double getFitness(NeuralNetwork network, ArrayList<Example> examples) {
        network.setWeights(position);
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
        return 1.0/((double)loss/examples.size());
    }

}