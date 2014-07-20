package com.hunterdavis.lifesim;

/**
 * Created by hunter on 7/19/14.
 */
public class Microbe implements java.io.Serializable {

    public DNA dna;
    public int health;
    public int age;

    public Microbe() {
        dna = new DNA();
        health=100;
        age=0;
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

    @Override
    public String toString() {
        return "Microbe{" +
                "dna=" + dna +
                ", health=" + health +
                ", age=" + age +
                '}';
    }
}
