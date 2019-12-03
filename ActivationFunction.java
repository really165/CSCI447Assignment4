package csci447.project4;

import org.ejml.simple.SimpleMatrix;

/**
 * An interface allowing the realization of different activation functions in
 * the MLP neural network.
 */

interface ActivationFunction {

    /**
     * @param inputs The weighted sums going into a node.
     *
     */
    SimpleMatrix eval(SimpleMatrix inputs);

    /**
     * @param outputs The outputs of a node.
     *
     */
    SimpleMatrix derivative(SimpleMatrix outputs);

    double eval(double input);

    double derivative(double output);
}