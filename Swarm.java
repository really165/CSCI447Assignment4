import csci447.project3.CrossValidation;
import csci447.project4.NeuralNetwork;

public class Swarm {

    public NeuralNetwork network;
    public ArrayList<Particle> particles;
    public Random random;
    public Particle[] particle;
    public double[] gbest;
    public double bestFitness;
    public double c1;
    public double c2;


    public Swarm(NeuralNetwork network, int numParticles, double c1, double c2) {
        this.network = network;
        this.c1 = c1;
        this.c2 = c2;
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
        Particle p;
        double fitness;
        int i;
        for (i = 0; i < particle.length; i++) {
            p = particle[i];
            fitness = p.getFitness(network, examples);
            if (fitness > p.bestFitness) {
                p.pbest = p.postion;
                p.bestFitness = fitness;
            }
            if (fitness > bestFitness) {
                gbest = p.position;
                bestFitness = fitness;
            }
        }

        for (i = 0; i < particle.length; i++) {
            p = particle[i];
            // Update velocity here
            // Update position here
        }

    }

}