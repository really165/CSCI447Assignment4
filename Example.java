package csci447.project4;

 /**
  * Stores the data for each dataset
  */
 public final class Example {

    public double c;
    public double[] input;
    public double[] target;
    public double distance;

    /**
     * Classification
     * @param inputs
     * @param target
     * @param numClasses
     */
    public Example(double[] input, int target, int numClasses) {
        this.input = input;
        this.target = new double[numClasses];
        this.target[target] = 1;
        this.c = target;
    }

    /**
     * Regression
     * @param inputs
     * @param target
     */
    public Example(double[] input, double target) {
        this.input = input;
        this.target = new double[1];
        this.target[0] = target;
        this.c = target;
    }

 }