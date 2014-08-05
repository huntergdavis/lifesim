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
        //LoggingAndTime.logWithTiming(TAG, "About to instantiate DNA in microbe");

        dna = new DNA();
        health=100;
        age=0;

        //LoggingAndTime.logWithTiming(TAG,"Microbe DNA Instantiated");

    }

    public Microbe(Microbe parent) {
        dna = new DNA(parent.dna);
        health = 100;
        age = 0;
    }

    public void tick() {
        health--;
        age++;

        //LoggingAndTime.logWithTiming(TAG,"MICROBE TICK");

        // random mutation
        mutate(DNA.DEFAULT_MUTATION_RATE_PERCENTAGE);

        dna.tick();


        if(age > 100) {
            regenesis();
        }

    }

    public void regenesis() {

        //LoggingAndTime.logWithTiming(TAG,"EVOLUTION!!");

        health = 100;
        age = 0;

        for(int i = 0;i < DNA.PROTEIN_SIZE; i++) {
            for(int j = 0;j<DNA.PROTEIN_SIZE;j++) {
                // each protein in our DNA evolves
                dna.proteinMatrix[i][j].proteinType = calculateNewProteinTypeDuringEvolution(i,j);
            }
        }
    }

    public Protein.PROTEIN_TYPES calculateNewProteinTypeDuringEvolution(int x, int y) {
        Protein.PROTEIN_TYPES currentType = dna.proteinMatrix[x][y].proteinType;
        switch (currentType) {
            case AGGRESSIVE:
                // aggressive proteins do not lose thier DNA
                return Protein.PROTEIN_TYPES.AGGRESSIVE;
            case PASSIVE:
                // if passive is surrounded by 2 or more aggressive, becomes aggressive
                if(calculateNumberOfNeighboringProteinsOfType(x,y, Protein.PROTEIN_TYPES.AGGRESSIVE) > 1) {
                    return Protein.PROTEIN_TYPES.AGGRESSIVE;
                }

            default:

            }
                return currentType;
    }

    public int calculateNumberOfNeighboringProteinsOfType(int x, int y, Protein.PROTEIN_TYPES matchType) {
        int numberOfNeighborsWithThisType = 0;

        int offsetLargeValue = DNA.PROTEIN_SIZE - 1;

        // top left
        if((x > 0) && (y > 0)) {
            if (dna.proteinMatrix[x - 1][y - 1].proteinType == matchType) {
                numberOfNeighborsWithThisType++;
            }
        }

        // top center
        if(y > 0) {
            if (dna.proteinMatrix[x][y - 1].proteinType == matchType) {
                numberOfNeighborsWithThisType++;
            }
        }
        // top right
        if ((y > 0) && (x < offsetLargeValue)) {
            if (dna.proteinMatrix[x + 1][y - 1].proteinType == matchType) {
                numberOfNeighborsWithThisType++;
            }
        }
        // left
        if (x > 0) {
            if (dna.proteinMatrix[x - 1][y].proteinType == matchType) {
                numberOfNeighborsWithThisType++;
            }
        }
        // right
        if (x < offsetLargeValue) {
            if (dna.proteinMatrix[x + 1][y].proteinType == matchType) {
                numberOfNeighborsWithThisType++;
            }
        }

        // bottom left
        if ((y < offsetLargeValue) && (x > 0)) {
            if (dna.proteinMatrix[x - 1][y + 1].proteinType == matchType) {
                numberOfNeighborsWithThisType++;
            }
        }

        // bottom center
        if ((y < offsetLargeValue)) {
            if (dna.proteinMatrix[x][y + 1].proteinType == matchType) {
                numberOfNeighborsWithThisType++;
            }
        }

        // bottom right
        if ((x < offsetLargeValue) && (y < offsetLargeValue)) {
            if (dna.proteinMatrix[x + 1][y + 1].proteinType == matchType) {
                numberOfNeighborsWithThisType++;
            }
        }

        return numberOfNeighborsWithThisType;
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
