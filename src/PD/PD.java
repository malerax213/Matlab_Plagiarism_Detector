package PD;

import java.io.IOException;
import java.util.List;

public class PD {

    public double getScore(String inputFileName1, String inputFileName2, int tupleSize) throws IOException {
        Service service = new Service();
        System.out.println("");

        List<NTuple<String>> nTuples1 = NTuple.loadTuplesFromFile(inputFileName1, tupleSize);
        System.out.println("");
        List<NTuple<String>> nTuples2 = NTuple.loadTuplesFromFile(inputFileName2, tupleSize);
        System.out.println("");

        int count = service.countMatches(nTuples1, nTuples2);
        System.out.println("Num matches " + count);

        double percentMatch = 0.0;
        if (nTuples1.size() > 0) {
            percentMatch = (100 * count) / nTuples1.size();
        }
        System.out.println("% of tuples that match: " + percentMatch);

        return percentMatch;
    }

    public static void main(String[] args) throws IOException {
        PD p = new PD();
        String comparisonFileName;
        String baseFileName;
        int numTuples = 3;

        if (args.length > 0) {
            if (args.length < 2 || args.length > 3) {
                System.err.println("USAGE: <comparison_file_name> <base_file_name> [<num_tuples>]");
                System.exit(-1);
            }

            comparisonFileName = args[0];
            baseFileName = args[1];
            if (args.length == 3) {
                String s = args[2];
                numTuples = Integer.valueOf(s);
            }

            p.getScore(comparisonFileName,
                    baseFileName, numTuples);

        } else { // In case user sets no args lets just run the test
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
