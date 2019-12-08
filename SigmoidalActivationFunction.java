package csci447.project4;

/**
 * Sigmoidal activation function using logistic equation
 */
public final class SigmoidalActivationFunction implements ActivationFunction {


    public double eval(double input) {
        return 1 / (1 + Math.exp(-input));
    }

    public double derivative(double output) {
        return output * (1 - output);
    }
}