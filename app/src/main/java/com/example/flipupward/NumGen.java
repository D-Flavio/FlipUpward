package com.example.flipupward;

import java.util.ArrayList;
import java.util.Collections;

public class NumGen {

    private static ArrayList<Integer> allNums = new ArrayList<>();

    public static int generate() {
        if (allNums.size()<1){
            for (int i=1; i<100; i++) {
                allNums.add(i);
            }
        }

        Collections.shuffle(allNums);
        int i = allNums.get(1);
        allNums.remove(1);
        return i;
    }
}
