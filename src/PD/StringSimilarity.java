package PD;

import java.util.List;

public interface StringSimilarity {

    public int countMatches(List<NTuple<String>> nTuples1, List<NTuple<String>> nTuples2);
}
