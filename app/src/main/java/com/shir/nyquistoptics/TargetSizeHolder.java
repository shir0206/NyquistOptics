package com.shir.nyquistoptics;

import java.util.HashMap;

/**
 * This class is a holder for target size class (Singletone).
 */
public class TargetSizeHolder {

    //------------------------------------- Parameters -------------------------------------

    /**
     * instance of the singletone, available access to the class
     */
    protected static TargetSizeHolder instance;

    /**
     * HashMap of target sizes.
     * Every target type initialized with his own unique W*H.
     * key = Name of target type [nato/human/object].
     * value = Target type size [each target type can have different size].
     */
    private HashMap<String, TargetSize> targetSizeCollection = new HashMap<>();

    //------------------------------------- Constructors -------------------------------------

    /**
     * TargetSizeHolder empty constructor.
     */
    private TargetSizeHolder() {
    }

    //------------------------------------- Functional Methods -------------------------------------

    /**
     * Method that add target size to the HashMap of target sizes.
     * @param key = Name of target type [nato/human/object].
     * @param value = Target size [each target type can have different size].
     */
    void addTargetSize(String key, TargetSize value) {
        targetSizeCollection.put(key, value);
    }

    /**
     * Method that flags if HashMap of target sizes contains a certain target type (certain key).
     * @param key = Name of target type [nato/human/object].
     * @return True = If key exists; False = If key not exists.
     */
    boolean hasTargetSize(String key) {
        return targetSizeCollection.containsKey(key);
    }

    //------------------------------------- Getters & Setters -------------------------------------

    /**
     * Get the instance of the target size holder. If the instance is null pointer, create new.
     * @return = instance of the singletone (available access to the class).
     */
    public static TargetSizeHolder getInstance() {
        if (instance == null){
            instance = new TargetSizeHolder();
        }
        return instance;
    }

    /**
     * Set the instance of the target size holder.
     * @param instance = Instance of the singletone (available access to the class).
     */
    public static void setInstance(TargetSizeHolder instance) {
        TargetSizeHolder.instance = instance;
    }

    /**
     * Method that returns a target size from the HashMap of target sizes, by given a certain target type (certain key).
     * @param key = Name of target type [nato/human/object].
     * @return = Target size of a certain target type (certain key).
     */
    TargetSize getTargetSize(String key) {
        return targetSizeCollection.get(key);
    }


}
