package com.diora.core.utils.maths;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class RandomFromList {
    ArrayList<Integer> randomNum;

    public RandomFromList(int capacety) {
        randomNum = new ArrayList<>(capacety);
        for (int i = 0; i < capacety; i++) {
            randomNum.add(i);
        }
    }

    public int getRandomId(){
        if (randomNum.size()<=0) return randomNum.get(0);
        int index = MathUtils.random(0, randomNum.size()-1);
        int i = randomNum.get(index);
        randomNum.remove(index);
        return i;
    }

}
