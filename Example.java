package csci447.project4;

import org.ejml.simple.SimpleMatrix;

 /**
  * Stores the data for each dataset
  */
 public final class Example implements Comparable<Example> {

    public SimpleMatrix inputs;
    public SimpleMatrix target;

    public double c;
    public double[] input;
    public double[] targeta;
    public double distance;

    /**
     * Classification
     * @param inputs
     * @param target
     * @param numClasses
     */
    public Example(double[] inputs, int target, int numClasses) {
        this.input = inputs;
        this.targeta = new double[numClasses];
        this.targeta[target] = 1;
        this.inputs = new SimpleMatrix(inputs.length, 1, true, inputs);
        this.target = new SimpleMatrix(numClasses, 1);
        this.target.set(target, 1);
        this.c = target;
    }

    /**
     * Regression
     * @param inputs
     * @param target
     */
    public Example(double[] inputs, double target) {
        this.input = inputs;
        this.targeta = new double[1];
        this.targeta[0] = target;
        this.inputs = new SimpleMatrix(inputs.length, 1, true, inputs);
        this.target = new SimpleMatrix(1, 1);
        this.target.set(0, target);
        this.c = target;
    }

    /**
     * Creating a cluster from existing example
     * @param inputs
     */
    public Example(SimpleMatrix inputs) {
        this.inputs = inputs;
    }

    /**
     * Calculates the distance from another example e
     * @param e
     */
    public void distanceFrom(Example e) {
        distance = this.inputs.minus(e.inputs).elementPower(2).elementSum();
    }

    @Override
    public int compareTo(Example e) {
        if (this.distance < e.distance) return -1;
        else if (this.distance > e.distance) return 1;
        else return 0;
    }
    
    @Override
    public String toString() {
        return this.inputs.toString();
    }

 }