package com.hunterdavis.lifesim;

import java.util.Random;

/**
 * Created by hunter on 7/19/14.
 */
public class Protein implements java.io.Serializable {

    public static enum PROTEIN_TYPES {
        AGGRESSIVE,PASSIVE,STABLE,UNSTABLE,MOVEMENT,DEFENCE,NOURISHMENT,TRANSCRIPTION,CONTROLLER,EFFICIENCY,STORAGE;
    }

    public int health;
    public int age;

    PROTEIN_TYPES proteinType;

    public Protein() {
        proteinType = randomProteinType();
        health = 100;
        age = 0;
    }

    public Protein(Protein parent) {
        proteinType = parent.proteinType;
        health = parent.health;
        age = parent.age;

    }

    public Protein (PROTEIN_TYPES proteinTypeFromSingleParent) {
        proteinType = proteinTypeFromSingleParent;
        health = 100;
        age = 0;
    }

    public void tick() {
        health--;
        age++;
    }


    // returns 1 if things have changed, 0 if not. -1 if things went very badly?
    public int mutate(float percentage) {

        // mutation requires some degree of randomness
        if(new Random().nextFloat() > percentage) {
            return 0;
        }

        PROTEIN_TYPES mutationProtein = randomProteinType();
        if(mutationProtein.equals(proteinType)) {
            return 0;
        }

        proteinType = mutationProtein;
        return 1;
    };

    private PROTEIN_TYPES randomProteinType() {
        int pick = new Random().nextInt(PROTEIN_TYPES.values().length);
        return PROTEIN_TYPES.values()[pick];
    }

    public static int getNumberOfProteinTypes() {
        return PROTEIN_TYPES.values().length;
    }

    public int getCurrentProteinTypeAsInt() {
        return proteinType.ordinal();
    }


    @Override
    public String toString() {
        return "Protein{" +
                "health=" + health +
                ", age=" + age +
                ", proteinType=" + proteinType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Protein)) return false;

        Protein protein = (Protein) o;

        if (age != protein.age) return false;
        if (health != protein.health) return false;
        if (proteinType != protein.proteinType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = health;
        result = 31 * result + age;
        result = 31 * result + proteinType.hashCode();
        return result;
    }

}