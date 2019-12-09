package csci447.project4;

import java.util.ArrayList;
import java.util.Random;

import csci447.project4.Example;
import csci447.project4.NeuralNetwork;

public class Swarm {

    public NeuralNetwork network;
    public ArrayList<Particle> particles;
    public Random random;
    public Particle[] particle;
    public double[] gbest;
    public double bestf;
    public double c1;
    public double c2;
    public double w;


    public Swarm(NeuralNetwork network, int numParticles, double c1, double c2, double w) {
        this.network = network;
        this.c1 = c1;
        this.c2 = c2;
        this.w = w;
        this.particle = new Particle[numParticles];
        this.random = new Random();
    }

    public void initialize() {
        int particleSize = network.getNumWeights();
        this.gbest = new double[particleSize];
        for (int i = 0; i < particle.length; i++) {
            particle[i] = new Particle(particleSize);
        }
    }

    public void update(ArrayList<Example> examples) {

        // Calculate fitness of each particle and update pbest and gbest
        calculateFitness(examples);

        Particle p;
        int i, j;
        double r1, r2, cognitive, social;
        for (i = 0; i < particle.length; i++) {
            p = particle[i];
            r1 = random.nextDouble();
            r2 = random.nextDouble();
            // Update velocity and position
            for (j = 0; j < p.velocity.length; j++) {
                // Update velocity
                p.velocity[j] = p.velocity[j] * w;
                cognitive = c1 * r1 * (p.pbest[j] - p.position[j]);
                social = c2 * r2 * (gbest[j] - p.position[j]);
                p.velocity[j] = p.velocity[j] + cognitive + social;
                // Update position
                p.position[j] = p.position[j] + p.velocity[j];
            }
        }
    }

    public void train(ArrayList<Example> examples) {
        int i = 0;
        while (i < 1000) {
            update(examples);
            i++;
            System.out.println("iteration: " + i);
        }
        calculateFitness(examples);
    }

    public void calculateFitness(ArrayList<Example> examples) {
        Particle p;
        double fitness;
        int i;
        for (i = 0; i < particle.length; i++) {
            p = particle[i];
            fitness = p.getFitness(network, examples);
            if (fitness > p.bestf) {
                p.pbest = p.position;
                p.bestf = fitness;
            }
            if (fitness > bestf) {
                gbest = p.position;
                bestf = fitness;
            }
        }
    }

}