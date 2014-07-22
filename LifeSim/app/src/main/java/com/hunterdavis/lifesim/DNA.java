package com.hunterdavis.lifesim;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by hunter on 7/19/14.
 */
public class DNA implements java.io.Serializable {

    public Protein proteinMatrix[][];

    public static final int PROTEIN_SIZE = 16;
    public static int DEFAULT_MUTATION_RATE_PERCENTAGE = 25;


    public DNA() {
        proteinMatrix = new Protein[PROTEIN_SIZE][PROTEIN_SIZE];
        for(int i = 0; i < PROTEIN_SIZE; i++) {
            for (int j = 0; j < PROTEIN_SIZE; j++) {
                proteinMatrix[i][j] = new Protein();
            }
        }
    };

    public DNA(DNA parent) {
        for(int i = 0; i < PROTEIN_SIZE; i++) {
            for (int j = 0; j < PROTEIN_SIZE; j++) {
                proteinMatrix[i][j] = new Protein(parent.proteinMatrix[i][j]);
            }
        }
    }

    // mutate the DNA based on pre-defined parameters
    public void autoMutate() {
        mutate(DEFAULT_MUTATION_RATE_PERCENTAGE);
    }

    // some percentage of the DNA has a chance of mutation,
    // return what percentage actually did
    public float mutate(float percentage) {
        // an accumulator for the number of mutations which actually occurred
        int totalNumberOfMutations = 0;

        for(int i = 0; i < PROTEIN_SIZE; i++) {
            for (int j = 0; j < PROTEIN_SIZE; j++) {
                totalNumberOfMutations += proteinMatrix[i][j].mutate(percentage);
            }
        }

        return (totalNumberOfMutations / (PROTEIN_SIZE * PROTEIN_SIZE));
    }

    public void tick() {
        for(int i = 0; i < PROTEIN_SIZE; i++) {
            for (int j = 0; j < PROTEIN_SIZE; j++) {
                proteinMatrix[i][j].tick();
            }
        }
    }

    @Override
    public String toString() {
        return "DNA{" +
                "proteinMatrix=" + Arrays.toString(proteinMatrix) +
                '}';
    }
}
