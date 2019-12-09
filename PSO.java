package csci447.project4;

import java.util.ArrayList;

// PSO implementation of a neural network training algorithm
public class PSO implements TrainingAlgorithm {
	
	private Swarm swarm;

	
	public PSO(NeuralNetwork network, int numParticles, double c1, double c2, double w) {
		this.swarm = new Swarm(network, numParticles, c1, c2, w);
	}

	// trains the swarm and sets the network weights to the gbest
	@Override
	public double[] train(ArrayList<Example> examples) {
		swarm.initialize();
		swarm.train(examples);
		return swarm.gbest;
	}

	// prints algorithm name
	@Override
	public String toString() {
		return "pso";
	}

}
