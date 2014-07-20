package com.hunterdavis.lifesim;

/**
 * Created by hunter on 7/19/14.
 */
public class GameBoard {

    public int[][] positionMatrix;
    public Microbe[][] lifeMatrix;



    public GameBoard(int width, int height) {
        positionMatrix = new int[width][height];
        lifeMatrix = new Microbe[width][height];

        for(int i = 0; i < width; i++) {
            for (int j=0;j<height;j++) {
                lifeMatrix[i][j] = new Microbe();
            }
        }
    }


}
