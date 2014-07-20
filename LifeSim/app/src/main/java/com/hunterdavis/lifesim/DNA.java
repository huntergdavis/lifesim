package com.hunterdavis.lifesim;

import java.util.Arrays;

/**
 * Created by hunter on 7/19/14.
 */
public class DNA implements java.io.Serializable {

    public Protein proteinMatrix[][];

    public static final int PROTEIN_SIZE = 16;

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
