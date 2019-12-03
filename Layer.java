package csci447.project4;

/**
 * Represents a layer in a mult-layer neural network
 */
public class Layer {

    public ActivationFunction activation;

    private double[] output;
    private double sum;

    public Node[] node;
    public double[] input;


    public Layer(int nodes, int previousNodes) {
        // Initialize node array
        node = new Node[nodes];
        // Initialize each node in the layer
        for (int i = 0; i < node.length; i++) {
            node[i] = new Node(previousNodes);
        }
        // Initialize input array
        input = new double[previousNodes];
    }

    public void feedForward() {
        // Calculate the output of each node in the layer
        for (int i = 0; i < node.length; i++) {
            // Add the bias
            sum = node[i].bias;
            // Add each of weighted inputs
            for (int j = 0; j < node[i].weight.length; j++) {
                sum = sum + input[j] * node[i].weight[j];
            }
            // Calculate the activation of the sum
            node[i].output = activation.eval(sum);
        }
    }

    public double[] getOutput() {
        // Initialize output array
        output = new double[node.length];
        // Get output of each node
        for (int i = 0; i < node.length; i++) {
            output[i] = node[i].output;
        }
        // Return output array
        return output;
    }

    public void setActivationFunction(ActivationFunction activation) {
        this.activation = activation;
    }

}