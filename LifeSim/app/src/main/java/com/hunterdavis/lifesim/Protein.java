package com.hunterdavis.lifesim;

import java.util.Random;

/**
 * Created by hunter on 7/19/14.
 */
public class Protein {

    public static enum PROTEIN_TYPES {
        AGGRESSIVE,PASSIVE,STABLE,UNSTABLE,MOVEMENT,DEFENCE,NOURISHMENT,TRANSCRIPTION,CONTROLLER,EFFICIENCY,STORAGE;
    }

    PROTEIN_TYPES proteinType;

    public Protein() {
        proteinType = randomProteinType();
    }

    public Protein (PROTEIN_TYPES proteinTypeFromSingleParent) {
        proteinType = proteinTypeFromSingleParent;
    }

    private PROTEIN_TYPES randomProteinType() {
        int pick = new Random().nextInt(PROTEIN_TYPES.values().length);
        return PROTEIN_TYPES.values()[pick];
    }
}
