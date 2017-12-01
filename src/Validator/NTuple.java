package PD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NTuple<T> {

    List<T> wordsInTuple = new ArrayList<>();

    public void addWord(T string) {
        wordsInTuple.add(string);
    }

    public T get(int i) {
        return wordsInTuple.get(i);
    }

    public int size() {
        return wordsInTuple.size();
    }

    public static List<NTuple<String>> loadTuplesFromFile(String fileName,
            int tupleSize) throws IOException {
        BufferedReader br = null;
        List<NTuple<String>> tuples = new ArrayList<>();
        try {
            FileReader in = new FileReader(fileName);
            br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                tuples.addAll(lineToTuples(line, tupleSize));
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return tuples;
    }

    private static List<NTuple<String>> lineToTuples(String line, int tupleSize) {
        List<NTuple<String>> tuplesInLine = new ArrayList<>();

        // Lower case processing
        String[] splitwords = line.toLowerCase().split(" ");

        // Some more checking
        if (splitwords.length < tupleSize) {
            // Empty list
            return tuplesInLine;
        }

        for (int i = 0; i < splitwords.length - (tupleSize - 1); i++) {

            NTuple<String> n = new NTuple<>();
            for (int j = i; j < i + tupleSize; j++) {
                n.addWord(splitwords[j]);
            }

            tuplesInLine.add(n);
        }
        return tuplesInLine;
    }
}
