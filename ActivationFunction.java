package csci447.project4;

/**
 * An interface allowing the realization of different activation functions in
 * the MLP neural network.
 */

interface ActivationFunction {

    double eval(double input);

    double derivative(double output);
}