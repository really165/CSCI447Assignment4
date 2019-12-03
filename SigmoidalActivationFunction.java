package csci447.project4;

import org.ejml.simple.SimpleMatrix;

/**
 * Sigmoidal activation function using logistic equation
 */
public final class SigmoidalActivationFunction implements ActivationFunction {

    /**
     * @param val The inputs going into a node.
     * @return The activation of the node.
     */
    public SimpleMatrix eval(SimpleMatrix inputs) {
        SimpleMatrix activations = inputs.copy();
        for (int i = 0; i < activations.numRows(); i++) {
            activations.set(i, 1 / (1 + Math.exp(-activations.get(i))));
        }
        return activations;
    }

    /**
     * @param outputs The activation of a node.
     * @return The weighted sum going into the node.
     */
    public SimpleMatrix derivative(SimpleMatrix outputs) {
        SimpleMatrix derivatives = outputs.copy();
        SimpleMatrix ones = new SimpleMatrix(derivatives.numRows(), 1);
        ones.fill(1);
        derivatives.elementMult(ones.minus(derivatives));
        return derivatives;
    }

    public double eval(double input) {
        return 1 / (1 + Math.exp(-input));
    }

    public double derivative(double output) {
        return output * (1 - output);
    }
}