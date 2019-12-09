package csci447.project4;

import java.util.ArrayList;
import java.util.Collections;

import csci447.project4.Example;

/**
 * Generalizes the 10-fold cross validation procedure for both types of networks
 */
public class CrossValidation {

    public static double run(NeuralNetwork nn, ArrayList<Example> examples, boolean regression) {
        // Creates 10 folds from the examples
        ArrayList<Example>[][] folds = getFolds(examples);
        
        int i = 0;
        int j = 1;
        double[][] table;
        
        if (regression) table = new double[folds.length*folds[0][1].size()][2];
        else table = new double[nn.getNumOutputs()][nn.getNumOutputs()];
        
        // For every fold
        for (ArrayList<Example>[] fold : folds) {
            System.out.println("[cross-validation][fold=" + j + "]");
            j++;
            // Train the network with the training set using the training algorithm
            nn.trainWithAlgorithm(fold[0]);
            // Classify all the examples in the test set
            for (Example e : fold[1]) {
                if (regression) {
                    double actual = e.c;
                    double predicted = nn.regress(e);
                    table[i][0] = actual;
                    table[i][1] = predicted;
                    i++;
                }
                else {
                    int actualClass = (int)e.c;
                    int predictedClass = nn.classify(e);
                    table[actualClass][predictedClass]++;
                }
            }
        }
        
        // Return the 0-1 loss for classification and MSE for regression
        if (regression) { // Calculate Mean Squared Error
            double sum = 0;
            for (double[] row : table) sum += Math.pow((row[0] - row[1]), 2);
            return sum/table.length;
        } else { // Calculate 0-1 Loss
            double miss = 0;
            int total = 0;
            for (i = 0; i < table.length; i++) {
                for (j = 0; j < table[i].length; j++) {
                    if (i != j) miss += table[i][j];
                    total += table[i][j];
                }
            }
            return miss/total;
        }

    }

    // Generates 10 folds from a set of examples
    public static ArrayList<Example>[][] getFolds(ArrayList<Example> examples) {
        Collections.shuffle(examples);
        int partitionSize = (int)((double)examples.size() / 10);
        ArrayList<Example>[][] folds = new ArrayList[10][2];
        
        for (ArrayList<Example>[] fold : folds) {
            for (int i = 0; i < fold.length; i++) {
                fold[i] = new ArrayList<Example>();
            }
        }
        
        for (int fold = 0; fold < 10; fold++) {
            int end = partitionSize * (fold+1);
            int start = end - partitionSize;
            ArrayList<Example> trainingSet = new ArrayList<Example>(examples.subList(0, start));
            trainingSet.addAll(examples.subList(end, examples.size()));
            ArrayList<Example> testSet = new ArrayList<Example>(examples.subList(start, end));
            folds[fold][0] = trainingSet;
            folds[fold][1] = testSet;
        }
        
        return folds;
    }

}