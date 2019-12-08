package csci447.project4;

/**
 * Linear activation function for neural net nodes
 */
public class LinearActivationFunction implements ActivationFunction {

    // return input
    @Override
    public double eval(double input) {
        return input;
    }

    // return one
    @Override
    public double derivative(double output) {
        return 1;
    }
    
}