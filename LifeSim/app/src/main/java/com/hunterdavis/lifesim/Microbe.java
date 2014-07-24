package com.hunterdavis.lifesim;

import com.hunterdavis.lifesim.util.LoggingAndTime;

/**
 * Created by hunter on 7/19/14.
 */
public class Microbe implements java.io.Serializable {

    public static final String TAG = "Microbe";
    public DNA dna;
    public int health;
    public int age;

    public Microbe() {
        LoggingAndTime.logWithTiming(TAG, "About to instantiate DNA in microbe");

        dna = new DNA();
        health=100;
        age=0;

        LoggingAndTime.logWithTiming(TAG,"Microbe DNA Instantiated");

    }

    public Microbe(Microbe parent) {
        dna = new DNA(parent.dna);
        health = 100;
        age = 0;
    }

    public void tick() {
        health--;
        age++;

        dna.tick();
    }

    public void mutate(float mutationRate) {
        dna.mutate(mutationRate);
    }

    @Override
    public String toString() {
        return "Microbe{" +
                "dna=" + dna +
                ", health=" + health +
                ", age=" + age +
                '}';
    }
}
