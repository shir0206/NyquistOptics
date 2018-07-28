package com.example.shir.nyquistoptics;

import java.util.HashMap;

public class TargetSizeHolder {

    private static TargetSizeHolder instance;

    private TargetSizeHolder() {
    }

    public static TargetSizeHolder getInstance() {
        if (instance == null){
            instance = new TargetSizeHolder();
        }
        return instance;
    }

    public static void setInstance(TargetSizeHolder instance) {
        TargetSizeHolder.instance = instance;
    }


    private HashMap<String, TargetSize> targetSizeCollection = new HashMap<>();

    void addTargetSize(String key, TargetSize value) {
        targetSizeCollection.put(key, value);
    }

    boolean hasTargetSize(String key) {
        return targetSizeCollection.containsKey(key);
    }

    TargetSize getTargetSize(String key) {
        return targetSizeCollection.get(key);
    }


}
