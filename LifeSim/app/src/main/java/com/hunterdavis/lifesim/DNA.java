package com.hunterdavis.lifesim;

/**
 * Created by hunter on 7/19/14.
 */
public class DNA {

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
}
