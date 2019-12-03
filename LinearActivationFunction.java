package csci447.project4;

import org.ejml.simple.SimpleMatrix;

/**
 * Linear activation function for neural net nodes
 */
public class LinearActivationFunction implements ActivationFunction {

    // just return the input
    @Override
    public SimpleMatrix eval(SimpleMatrix inputs) {
        return inputs;
    }

    // return one
    @Override
    public SimpleMatrix derivative(SimpleMatrix outputs) {
        SimpleMatrix derivatives = new SimpleMatrix(outputs.numRows(), 1);
        derivatives.fill(1);
        return derivatives;
    }

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