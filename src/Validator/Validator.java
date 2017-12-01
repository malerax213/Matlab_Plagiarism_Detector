package Validator;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {

    public static double getMatch(String inputFileName1, String inputFileName2,
            int tupleSize) throws IOException {
        List<NTuple<String>> nTuples1 = NTuple.loadTuplesFromFile(inputFileName1,
                tupleSize);
        List<NTuple<String>> nTuples2 = NTuple.loadTuplesFromFile(inputFileName2,
                tupleSize);

        int count = Service.countMatches(nTuples1, nTuples2);

        double percentMatch = 0.0;
        if (nTuples1.size() > 0) {
            percentMatch = (100 * count) / nTuples1.size();
        }
        return percentMatch;
    }

    public static double getScore(String inputFileName1, String inputFileName2,
            int tupleSize) throws IOException {

        List<NTuple<String>> nTuples1 = NTuple.loadTuplesFromFile(inputFileName1,
                tupleSize);
        List<NTuple<String>> nTuples2 = NTuple.loadTuplesFromFile(inputFileName2,
                tupleSize);

        int count = Service.countMatches(nTuples1, nTuples2);

        double percentMatch = 0.0;
        if (nTuples1.size() > 0) {
            percentMatch = (100 * count) / nTuples1.size();
        }

        // If the percentage of similarities is 80% or more
        // it will be considered plagiarism
        if (percentMatch > 80) {
            System.out.println("Plagiarism detected. Got " + count
                    + " matches and " + percentMatch + "% of tuples that match\n");
        } else {
            System.out.println("Plagiarism NOT detected. Got " + count
                    + " matches and " + percentMatch + "% of tuples that match\n");
        }
        return percentMatch;
    }

    public static void compareFilesFromDirectory(File[] files, int numTuples,
            String[] args) throws IOException {
        String comparisonFileName;
        String baseFileName;
        ArrayList<String> comparedFiles;
        Map<String, String> usedAsBaseFile = new HashMap<>(); // TODO

        for (File f : files) {
            baseFileName = f.toString();
            comparedFiles = new ArrayList();

            for (File file : files) {
                if (!comparedFiles.contains(f.getName())
                        && !f.getName().equals(file.getName())) {
                    if (file.canRead() && file.isFile()) {
                        comparisonFileName = file.toString();

                        if (!usedAsBaseFile.containsKey(comparisonFileName)) {
                            System.out.println("Comparing " + f.getName()
                                    + " with " + file.getName());
                            getScore(comparisonFileName,
                                    baseFileName, numTuples);
                            comparedFiles.add(file.getName());
                            usedAsBaseFile.put(baseFileName, comparisonFileName);
                        }
                    }
                }
            }
        }
    }

    public static void compareFilesWithModel(File[] files, int numTuples, 
            File model) throws IOException {
        String comparisonFileName;

        for (File file : files) {
            if (file.canRead() && file.isFile()) {
                System.out.println("Comparing " + model.getName()
                        + " with " + file.getName());
            }
            comparisonFileName = file.toString();
            System.out.println("Percentage of match: "
                    + getMatch(comparisonFileName, model.toString(),
                            numTuples) + "%");
        }
    }
    
    // TODO: Check the style guide lines and return true or false depending 
    // on the format
    public static double fileStyle(File f){
        
        return 0.0;
    }
    
    public static void styleLines(File[] files) {
        
        for (File file : files) {
            System.out.println("Checking if the file "+file.getName()+
                    " passes the style tests");
            System.out.println("Got a "+fileStyle(file)+"% of correctness");
        }
    }

    public static void main(String[] args) throws IOException {
        int numTuples;
        if (args.length > 0) {

            // Solution validator: Checks if the files got the right output 
            // (the model's one)
            if ("1".equals(String.valueOf(args[0]))) {
                numTuples = 1;
                if (args.length != 3) {
                    System.err.println("USAGE: <function to be executed> "
                            + "<directory of files> <model file to be compared>");
                }
                File[] files = new File(String.valueOf(args[1])).listFiles();
                File model = new File(String.valueOf(args[2]));
                compareFilesWithModel(files, numTuples, model);

            // Style lines: Checks if the files are passing the style tests
            } else if ("2".equals(String.valueOf(args[0]))) {
                if (args.length != 2) {
                    System.err.println("USAGE: <function to be executed> "
                            + "<directory of files to be checked>");
                }
                File[] files = new File(String.valueOf(args[1])).listFiles();
                styleLines(files);
                
            // Plagiarism detector: Checks if there's any sing of Plagiarism 
            // giving the file directory    
            } else if ("3".equals(String.valueOf(args[0]))) {
                numTuples = 3;
                if (args.length != 2) {
                    System.err.println("USAGE: <function to be executed> "
                            + "<directory of files>");
                }
                File[] files = new File(String.valueOf(args[1])).listFiles();
                compareFilesFromDirectory(files, numTuples, args);
            }
        } else {
            System.err.println("USAGE: <function to be executed> "
                    + "<directory of files>");
            System.exit(-1);
        }

    }
}
