package com.hunterdavis.lifesim;

import com.hunterdavis.lifesim.util.LoggingAndTime;

import java.util.Arrays;

/**
 * Created by hunter on 7/19/14.
 */
public class GameBoard implements java.io.Serializable {

    public final String TAG = "GameBoard";

    public Microbe[][] lifeMatrix;

    public int currentWidth;
    public int currentHeight;

    public int accumulateX = 0;
    public int accumulateY = 0;

    public GameBoard(int width, int height) {

        currentWidth = width;
        currentHeight = height;

        LoggingAndTime.logWithTiming(TAG, "About to instantiate life matrix");
        lifeMatrix = new Microbe[width][height];
        LoggingAndTime.logWithTiming(TAG,"Life Matrix Instantiated");

        LoggingAndTime.logWithTiming(TAG,"About to iterate through life Matrix");
        for(int i = 0; i < width; i++) {
            for (int j=0;j<height;j++) {
                lifeMatrix[i][j] = new Microbe();
            }
        }
        LoggingAndTime.logWithTiming(TAG,"Finished Iterating Through Life Matrix");

    }

    public GameBoard(GameBoard loadGame) {
        currentHeight = loadGame.currentHeight;
        currentWidth = loadGame.currentWidth;

        lifeMatrix = new Microbe[currentWidth][currentHeight];

        for(int i = 0; i < loadGame.currentWidth; i++) {
            for (int j=0;j<loadGame.currentHeight;j++) {
                lifeMatrix[i][j] = new Microbe(loadGame.lifeMatrix[i][j]);
            }
        }
    }

    public void tick() {

        //LoggingAndTime.logWithTiming(TAG, "About to tick life matrix");
        for(int i = 0; i < currentWidth; i++) {
            for (int j=0;j<currentHeight;j++) {
                lifeMatrix[i][j].tick();
            }
        }
        //LoggingAndTime.logWithTiming(TAG, "Finished ticking life matrix");

    }

    @Override
    public String toString() {
        return "GameBoard{" +
                "lifeMatrix=" + Arrays.toString(lifeMatrix) +
                ", currentWidth=" + currentWidth +
                ", currentHeight=" + currentHeight +
                '}';
    }
}
