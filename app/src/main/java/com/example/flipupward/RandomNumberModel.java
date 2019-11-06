package com.example.flipupward;

import java.io.Serializable;

public class RandomNumberModel implements Serializable {
    private int num;
    private boolean isSelecter;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isSelecter() {
        return isSelecter;
    }

    public void setSelecter(boolean selecter) {
        isSelecter = selecter;
    }
}
