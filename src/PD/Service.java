/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author amartinas
 */
public class Service {

    public Service() throws IOException {

    }

    public int countMatches(List<NTuple<String>> nTuples1, List<NTuple<String>> nTuples2) {

        int count = 0;
        for (NTuple<String> tuple1 : nTuples1) {
            for (NTuple<String> tuple2 : nTuples2) {
                //Design decision count each match just once
                if (isMatch(tuple1, tuple2)) {
                    count++;
                    break;
                }
            }

        }
        return count;
    }

    private boolean isMatch(NTuple<String> tuple1, NTuple<String> tuple2) {

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
