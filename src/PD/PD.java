package PD;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PD {

    public double getScore(String inputFileName1, String inputFileName2,
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
            String[] args, PD p) throws IOException {
        String comparisonFileName;
        String baseFileName;
        ArrayList<String> comparedFiles;
        Map<String,String> usedAsBaseFile = new HashMap<>(); // TODO
        
        for (File f : files) {
            baseFileName = f.toString();
            comparedFiles = new ArrayList();

            for (File file : files) {
                if (!comparedFiles.contains(f.getName())
                        && !f.getName().equals(file.getName())) {
                    if (file.canRead() && file.isFile()) {
                        comparisonFileName = file.toString();
                        if (args.length == 2) {
                            numTuples = Integer.valueOf(args[1]);
                        }
                        
                        if (!usedAsBaseFile.containsKey(comparisonFileName)) {
                            System.out.println("Comparing " + f.getName()
                                + " with " + file.getName());
                            p.getScore(comparisonFileName,
                                    baseFileName, numTuples);
                            comparedFiles.add(file.getName());
                            usedAsBaseFile.put(baseFileName,comparisonFileName);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        PD p = new PD();
        String comparisonFileName;
        String baseFileName;
        int numTuples = 3;

        if (args.length > 0) {
            System.err.println("Press 1 for directory or 2 for files");
            Scanner sc = new Scanner(System.in);

            // Comparing files from a directory
            if ("1".equals(String.valueOf(sc.next()))) {
                if (args.length < 1 || args.length > 2) {
                    System.err.println("USAGE: <directory of files to be "
                            + "compared> [<num_tuples>]");
                }
                System.err.println("Enter directory: ");
                File[] files = new File(String.valueOf(sc.next())).listFiles();
                compareFilesFromDirectory(files, numTuples, args, p);

                // Comparing 2 files
            } else {
                if (args.length < 2 || args.length > 3) {
                    System.err.println("USAGE: <comparison_file_name> "
                            + "<base_file_name> [<num_tuples>]");
                    System.exit(-1);
                }

                comparisonFileName = args[0];
                baseFileName = args[1];
                if (args.length == 3) {
                    numTuples = Integer.valueOf(args[2]);
                }

                p.getScore(comparisonFileName,
                        baseFileName, numTuples);
            }

            // Only for testing purpose if the user didn't enter any argument
        } else {
            System.out.println("Running Tests");
            System.out.println("=== TEST 1 OF 2===");
            comparisonFileName = "file1.txt";
            baseFileName = "file2.txt";

            double rate = p.getScore(comparisonFileName,
                    baseFileName, numTuples);
            if (rate != 100.0) {
                System.err
                        .println("Test failed expected 100.0 but got " + rate);
                System.exit(-1);
            }

            System.out.println("\n=== TEST 2 OF 2===");

            baseFileName = "file3.txt";
            rate = p.getScore(comparisonFileName, baseFileName, numTuples);
            if (rate != 50.0) {
                System.err
                        .println("Test failed expected 100.0 but got " + rate);
                System.exit(-1);
            }

            System.out.println("=== SUCCESS: All tests passed ===");
        }
    }
}
