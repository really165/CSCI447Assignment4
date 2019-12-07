import java.util.Random;

class Particle {

    public static Random random;

    public static double[] gbest;
    public static double c1;
    public static double c2;

    public double velocity;
    public double[] position;
    public double[] pbest;

    public Particle(int size) {
        this.position = new double[size];
        for (int i = 0; i < size; i++) {
            position[i] = pbest[i] = 0.01 - random.nextDouble() * 0.02;
        }
    }

}