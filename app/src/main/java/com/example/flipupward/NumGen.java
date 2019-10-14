package com.example.flipupward;

import java.util.ArrayList;
import java.util.Collections;

public class NumGen {
    //TODO simplify

    private static ArrayList<Integer> allNums = new ArrayList<>();

    public static int generate() {
        if (allNums.size()<1){
            for (int i=1; i<101; i++) {
                allNums.add(i);
            }
        }

        Collections.shuffle(allNums);
        return allNums.get(1);
    }
}
