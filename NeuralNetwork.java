package csci447.project4;

import java.util.ArrayList;

/**
 * Class to represent a multi-layer neural network
 */
public final class NeuralNetwork {

    private final int numOutputs;
    private final int layers;

    private Layer[] layer;

    public double eta;
    public double momentum;

    public TrainingAlgorithm trainingAlgorithm;


    /**
     * Creates a multi-layer neural network
     * @param numInputs The number of input features
     * @param numOutputs The number of classes to predict or 1 for regression
     * @param hiddenNodes An int array with the number of hidden nodes in each hidden layer
     */
    public NeuralNetwork(int numInputs, int numOutputs, int[] hiddenNodes) {
        // Store the number of outputs and layers
        this.numOutputs = numOutputs;
        this.layers = hiddenNodes.length+2;
        // Initialize layer array
        this.layer = new Layer[layers];
        // Number of nodes in previous layer
        int p = numInputs;
        // Create input layer
        this.layer[0] = new Layer(p, p);
        // Create hidden layers
        for (int i = 1; i < layers-1; i++) {
            this.layer[i] = new Layer(hiddenNodes[i-1], p);
            p = hiddenNodes[i-1];
        }
        // Create output layer
        this.layer[layers-1] = new Layer(numOutputs, p);
    }

    /**
     * Feeds the set input of the network through each of the layers
     */
    public void feedForward() {
        int i;
        // Output of input layer is the input
        for (i = 0; i < layer[0].node.length; i++) {
            layer[0].node[i].output = layer[0].input[i];
        }
        // Input of second layer is the input of the first
        layer[1].input = layer[0].getOutput();
        // Feed the inputs forward
        for (i = 1; i < layers; i++) {
            layer[i].feedForward();
            // Set the input of the next layer to the output of the current
            if (i != layers - 1) {
                layer[i + 1].input = layer[i].getOutput();
            }
        }
    }

    /**
     * Feeds each example through the network and backpropagates the error to update the weights and biases on-line
     * @param examples The set of examples to use for training
     */
    public void train(ArrayList<Example> examples) {
        System.out.print("[backpropagation][eta=" + eta + "][momentum=" + momentum + "][iterations=");
        int i;
        int iter = 0;
        while (iter < 1000) {
            // Iterate over the examples
            for (Example e : examples) {
                // Sets the network input to the example's input
                for (i = 0; i < layer[0].node.length; i++) {
                    layer[0].input[i] = e.input[i];
                }
                // Feed the input through the network
                feedForward();
                // Update the weights and biases by backpropagating the error
                updateWeights(e.target);
            }
            iter++;
        }
        System.out.println(iter + "]");
    }

    public void trainWithAlgorithm(ArrayList<Example> examples) {
        System.out.println("[" + trainingAlgorithm.toString() + "]");
        setWeights(trainingAlgorithm.train(examples));
    }

    /**
     * Calculates the error given a target value and uses backpropagation to update the weights
     * @param target The target values used to calculate the error
     */
    public void updateWeights(double[] target) {
        // calculate the errors at each node
        calculateErrors(target);
        // backpropagate the error
        backPropagation();
    }

    /**
     * Calculates the errors at each node
     * @param target The target values used to calculate the error
     */
    public void calculateErrors(double[] target) {
        int i, j, k;
        double output, sum;
        int outputLayer = layers-1;
        Layer l = layer[outputLayer];
        Layer nl;

        for (i = 0; i < l.node.length; i++) {
            output = l.node[i].output;
            l.node[i].error = (target[i] - output) * l.activation.derivative(output);
        }

        for (i = layers - 2; i > 0; i--) {
            l = layer[i];
            nl = layer[i+1];
            for (j = 0; j < l.node.length; j++) {
                sum = 0;
                output = l.node[j].output;
                for (k = 0; k < nl.node.length; k++) {
                    sum = sum + nl.node[k].weight[j] * nl.node[k].error;
                }
                l.node[j].error = l.activation.derivative(output) * sum;
            }
        }
    }

    /**
     * Backpropagates the errors the calculate the gradients of the weights and biases at each node
     */
    public void backPropagation() {
        int i, j, k;
        Layer l, pl;
        Node n, pn;

        for (i = layers - 1; i > 0; i--) {
            l = layer[i];
            pl = layer[i-1];
            for (j = 0; j < l.node.length; j++) {
                n = l.node[j];
                n.deltab = eta * n.error + momentum * n.deltab;
                n.bias = n.bias + n.deltab;
                for (k = 0; k < l.input.length; k++) {
                    pn = pl.node[k];
                    n.deltaw[k] = eta * n.error * pn.output + momentum * n.deltaw[k];
                    n.weight[k] = n.weight[k] + n.deltaw[k];
                    if (Double.isNaN(n.weight[k])) {
                        System.out.println("weight diverged");
                        throw new Error("NaN");
                    }
                }
            }
        }
    }


    public int classify(Example e) {
        int i;
        double[] output;
        // Set the network input to the example's input
        for (i = 0; i < layer[0].node.length; i++) {
            layer[0].input[i] = e.input[i];
        }
        // Feed the input through the network
        feedForward();
        // Get the output of the last layer
        output = layer[layers-1].getOutput();
        // The class is the index of the largest output node of the output layer
        int c = -1;
        double max = 0;
        for (i = 0; i < output.length; i++) {
            if (output[i] > max) {
                c = i;
                max = output[i];
            }
        }
        return c;
    }


    public double[] classifyOutput(Example e) {
        for (int i = 0; i < layer[0].node.length; i++) {
            layer[0].input[i] = e.input[i];
        }
        // Feed the input through the network
        feedForward();
        // Return the output of the last layer
        return layer[layers-1].getOutput();
    }  

    
    public double regress(Example e) {
        int i;
        double[] output;
        // Set the network input to the example's input
        for (i = 0; i < layer[0].node.length; i++) {
            layer[0].input[i] = e.input[i];
        }
        // Feed the input through the network
        feedForward();
        // Get the output of the last layer
        output = layer[layers-1].getOutput();
        // Since it's regression there's only one node so return it's value
        return output[0];
    }

    
    public int getNumOutputs() {
        return this.numOutputs;
    }

    public int getNumWeights() {
        int numWeights = 0;
        for (int i = 0; i < layers-1; i++) {
            numWeights += (layer[i].node.length + 1) * layer[i+1].node.length;
        }
        return numWeights;
    }

    /**
     * Set's the activation function of every non-input layer in the network
     * @param activation The activation function to use
     */
    public void setActivationFunction(ActivationFunction activation) {
        for (int i = 1; i < layers; i++) {
            layer[i].setActivationFunction(activation);
        }
    }

    /**
     * Set's the activation function of the specified layer
     * @param i The layer index
     * @param activation The activation function to use
     */
    public void setActivationFunction(int i, ActivationFunction activation) {
        layer[i].setActivationFunction(activation);
    }

    /**
     * Sets the learning rate of the network
     * @param eta
     */
    public void setLearningRate(double eta) {
        this.eta = eta;
    }

    /**
     * Sets the momentum of the network
     * @param momentum
     */
    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }

    /**
     * Sets the learning rate and momentum of the network
     * @param eta
     * @param momentum
     */
    public void setParameters(double eta, double momentum) {
        this.eta = eta;
        this.momentum = momentum;
    }

    public void setTrainingAlgorithm(TrainingAlgorithm algorithm) {
        this.trainingAlgorithm = algorithm;
    }

    /**
     * Sets the weights of each node in the network given a vector of all the weights
     * @param weights A vector of weights representing all the weights in the network
     */
    public void setWeights(double[] weights) {
        Layer curLayer;
        Node curNode;
        int i,j,k;
        int w = 0;
        for (i = 1; i < layers; i++) {
            curLayer = layer[i];
            for (j = 0; j < curLayer.node.length; j++) {
                curNode = curLayer.node[j];
                for (k = 0; k < curNode.weight.length; k++) {
                    curNode.weight[k] = weights[w++];
                }
                curNode.bias = weights[w++];
            }
        }
    }
}
