package csci447.project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class containing static methods for preprocessing each dataset.
 */

public final class DataPreprocessor {
    
    public static final int ABALONE_NUM_FEATURES = 10;
    public static final int ABALONE_NUM_CLASSES = 29;

    public static final int CAR_NUM_FEATURES = 6;
    public static final int CAR_NUM_CLASSES = 4;

    public static final int SEGMENTATION_NUM_FEATURES = 19;
    public static final int SEGMENTATION_NUM_CLASSES = 7;

    public static final int FOREST_NUM_FEATURES = 12;

    public static final int MACHINE_NUM_FEATURES = 6;

    public static final int WINE_NUM_FEATURES = 11;
    
    /**
     * 
     * @return An ArrayList of Example Objects
     * @throws FileNotFoundException
     */
    public static ArrayList<Example> abalone() throws FileNotFoundException {
    	Example e;
        double[] inputs;
        int c;

        Scanner sc = new Scanner(new File("datasets/abalone.data"));
        ArrayList<Example> examples = new ArrayList<Example>();
        String line;
        String[] parts;

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            inputs = new double[ABALONE_NUM_FEATURES];
            parts = line.split(",");
            
            if (parts.length < ABALONE_NUM_FEATURES-2) continue;
            
            // One hot encoding for gender
            if(parts[0].equals("M")) {
            	inputs[0] = 1;
            	inputs[1] = 0;
            	inputs[2] = 0;
            }
            else if(parts[0].equals("F")) {
            	inputs[0] = 0;
            	inputs[1] = 1;
            	inputs[2] = 0;
            }
            else {
            	inputs[0] = 0;
            	inputs[1] = 0;
            	inputs[2] = 1;
            }
            
            for (int i = 1; i < parts.length-1; i++) {
                inputs[i+2] = Double.parseDouble(parts[i]);
            }
            
            c = Integer.parseInt(parts[parts.length-1])-1;
            e = new Example(inputs, c, ABALONE_NUM_CLASSES);
            examples.add(e);

        } sc.close();

        return examples;
    }
    
    /**
     * 
     * @return An ArrayList of Example Objects
     * @throws FileNotFoundException
     */
    public static ArrayList<Example> car() throws FileNotFoundException {
    	Example e;
        double[] inputs;
        int c;

        Scanner sc = new Scanner(new File("datasets/car.data"));
        ArrayList<Example> examples = new ArrayList<Example>();
        String line;
        String[] parts;

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            inputs = new double[CAR_NUM_FEATURES];
            parts = line.split(",");
            
            if (parts.length < CAR_NUM_FEATURES+1) continue;
            
            // Ordinal assignment for buying price
            if (parts[0].equals("vhigh")) inputs[0] = 4;
            else if (parts[0].equals("high")) inputs[0] = 3;
            else if (parts[0].equals("med")) inputs[0] = 2;
            else if (parts[0].equals("low")) inputs[0] = 1;
            else inputs[0] = 0;
            
            // Ordinal assignment for maintenance price
            if (parts[1].equals("vhigh")) inputs[1] = 4;
            else if (parts[1].equals("high")) inputs[1] = 3;
            else if (parts[1].equals("med")) inputs[1] = 2;
            else if (parts[1].equals("low")) inputs[1] = 1;
            else inputs[1] = 0;
            
            // Ordinal assignment for number of doors
            if (parts[2].equals("2")) inputs[2] = 1;
            else if (parts[2].equals("3")) inputs[2] = 2;
            else if (parts[2].equals("4")) inputs[2] = 3;
            else if (parts[2].equals("5more")) inputs[2] = 4;
            else inputs[2] = 0;
            
            // Ordianl assignment for capacity
            if (parts[3].equals("2")) inputs[3] = 1;
            else if (parts[3].equals("4")) inputs[3] = 2;
            else if (parts[3].equals("more")) inputs[3] = 3;
            else inputs[3] = 0;
            
            // Ordianl assignment for trunk size
            if (parts[4].equals("small")) inputs[4] = 1;
            else if (parts[4].equals("med")) inputs[4] = 2;
            else if (parts[4].equals("big")) inputs[4] = 3;
            else inputs[4] = 0;
            
            // Ordinal assignment for safety
            if (parts[5].equals("low")) inputs[5] = 1;
            else if (parts[5].equals("med")) inputs[5] = 2;
            else if (parts[5].equals("high")) inputs[5] = 3;
            else inputs[5] = 0;
            
            //class
            switch (parts[6]) {
                case "unacc": c = 0; break;
                case "acc": c = 1; break;
                case "good": c = 2; break;
                case "vgood": c = 3; break;
                default: c = 0;
            }
            
            e = new Example(inputs, c, CAR_NUM_CLASSES);
            examples.add(e);

        } sc.close();

        return examples;
    }

    public static ArrayList<Example> segmentation() throws FileNotFoundException {
    	Example e;
        double[] inputs;
        int c;

        Scanner sc = new Scanner(new File("datasets/segmentation.data"));
        ArrayList<Example> examples = new ArrayList<Example>();
        String line;
        String[] parts;

        for (int j = 0; j < 5; j++) line = sc.nextLine();
        
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            inputs = new double[SEGMENTATION_NUM_FEATURES];
            parts = line.split(",");
            
            if (parts.length < SEGMENTATION_NUM_FEATURES+1) continue;
            
            switch (parts[0]) {
                case "BRICKFACE": c = 0; break;
                case "SKY": c = 1; break;
                case "FOLIAGE": c = 2; break;
                case "CEMENT": c = 3; break;
                case "WINDOW": c = 4; break;
                case "PATH": c = 5; break;
                default: c = 6;
            }

            for (int i = 1; i < parts.length; i++) {
            	inputs[i-1] = Double.parseDouble(parts[i]);
            }
            
            e = new Example(inputs, c, SEGMENTATION_NUM_CLASSES);
            examples.add(e);

        } sc.close();

        return examples;
    }
    
    /**
     * 
     * @return An ArrayList of Example Objects
     * @throws FileNotFoundException
     */
    public static ArrayList<Example> forest() throws FileNotFoundException {
    	Example e;
        double[] inputs;
        double c;

        Scanner sc = new Scanner(new File("datasets/forestfires.data"));
        ArrayList<Example> examples = new ArrayList<Example>();
        String line;
        String[] parts;

        line = sc.nextLine();
        
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            inputs = new double[FOREST_NUM_FEATURES];
            parts = line.split(",");
            
            if (parts.length < FOREST_NUM_FEATURES+1) continue;
            
            // first two columns
            for (int i = 0; i < 2; i++) {
            	inputs[i] = Double.parseDouble(parts[i]);
            }
            
            // third column
            if (parts[2].equals("jan")) inputs[2] = 1;
            else if (parts[2].equals("feb")) inputs[2] = 2;
            else if (parts[2].equals("mar")) inputs[2] = 3;
            else if (parts[2].equals("apr")) inputs[2] = 4;
            else if (parts[2].equals("may")) inputs[2] = 5;
            else if (parts[2].equals("jun")) inputs[2] = 6;
            else if (parts[2].equals("jul")) inputs[2] = 7;
            else if (parts[2].equals("aug")) inputs[2] = 8;
            else if (parts[2].equals("sep")) inputs[2] = 9;
            else if (parts[2].equals("oct")) inputs[2] = 10;
            else if (parts[2].equals("nov")) inputs[2] = 11;
            else if (parts[2].equals("dec")) inputs[2] = 12;
            else inputs[2] = 0;
            
            // fourth column
            if (parts[3].equals("sat")) inputs[3] = 1;
            else if (parts[3].equals("sun")) inputs[3] = 2;
            else if (parts[3].equals("mon")) inputs[3] = 3;
            else if (parts[3].equals("tue")) inputs[3] = 4;
            else if (parts[3].equals("wed")) inputs[3] = 5;
            else if (parts[3].equals("thu")) inputs[3] = 6;
            else if (parts[3].equals("fri")) inputs[3] = 7;
            else inputs[3] = 0;
            
            // fifth through twelfth columns
            for (int j = 4; j < 12; j++) {
            	inputs[j] = Double.parseDouble(parts[j]);;
            }
            
            c = Double.parseDouble(parts[parts.length-1]);
            e = new Example(inputs, c);
            examples.add(e);

        } sc.close();

        return examples;
    }
    
    public static ArrayList<Example> machine() throws FileNotFoundException {
    	Example e;
        double[] inputs;
        double c;

        Scanner sc = new Scanner(new File("datasets/machine.data"));
        ArrayList<Example> examples = new ArrayList<Example>();
        String line;
        String[] parts;

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            inputs = new double[MACHINE_NUM_FEATURES];
            parts = line.split(",");
            
            if (parts.length < MACHINE_NUM_FEATURES+1) continue;
            
            // columns 3-8
            for (int i = 2; i < 8; i++) {
            	inputs[i-2] = Double.parseDouble(parts[i]);
            }
            
            c = Double.parseDouble(parts[parts.length-2]);
            e = new Example(inputs, c);
            examples.add(e);

        } sc.close();

        return examples;
    }

    public static ArrayList<Example> wine() throws FileNotFoundException {
    	Example e;
        double[] inputs;
        double c;

        Scanner sc = new Scanner(new File("datasets/winequality-red.csv"));
        ArrayList<Example> examples = new ArrayList<Example>();
        String line;
        String[] parts;

        line = sc.nextLine();
        
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            inputs = new double[WINE_NUM_FEATURES];
            parts = line.split(";");
            
            if (parts.length < WINE_NUM_FEATURES+1) continue;
            
            for (int i = 0; i < parts.length-1; i++) {
            	inputs[i] = Double.parseDouble(parts[i]);
            }
            
            c = Double.parseDouble(parts[parts.length-1]);
            
            e = new Example(inputs, c);
            examples.add(e);

        } sc.close();
        
        // scan the white wine data
        sc = new Scanner(new File("datasets/winequality-white.csv"));
        
        line = sc.nextLine();
        
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            inputs = new double[WINE_NUM_FEATURES];
            parts = line.split(";");
            
            if (parts.length < WINE_NUM_FEATURES+1) continue;
            
            for (int i = 0; i < parts.length-1; i++) {
            	inputs[i] = Double.parseDouble(parts[i]);
            }
            
            c = Double.parseDouble(parts[parts.length-1]);
            
            e = new Example(inputs, c);
            examples.add(e);

        } sc.close();

        return examples;
    }

}