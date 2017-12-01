package PD;

import java.util.List;

public class Service {

    public static int countMatches(List<NTuple<String>> nTuples1,
            List<NTuple<String>> nTuples2) {

        int count = 0;
        for (NTuple<String> tuple1 : nTuples1) {
            for (NTuple<String> tuple2 : nTuples2) {
                // Count each match just once
                if (isMatch(tuple1, tuple2)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    private static boolean isMatch(NTuple<String> tuple1, NTuple<String> tuple2) {

        if (tuple1.size() != tuple2.size()) {
            throw new IllegalArgumentException("Your tuple sizes do not match");
        }

        for (int i = 0; i < tuple1.size(); i++) {
            String word1 = tuple1.get(i);
            String word2 = tuple2.get(i);

            if (!word1.equals(word2)) {
                return false;
            }

        }
        return true;
    }
}
