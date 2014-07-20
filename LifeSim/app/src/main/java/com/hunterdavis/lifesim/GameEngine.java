package com.hunterdavis.lifesim;

/**
 * Created by hunter on 7/19/14.
 */
public class GameEngine {

    public long simulatorAgeInTicks;

    public GameBoard currentGameBoard;

    public GameBoard preCalculateGameBoard;

    public GameEngine() {
        simulatorAgeInTicks = 0;

        currentGameBoard = new GameBoard();
        preCalculateGameBoard = new GameBoard();
    }

    public void tick() {
        // move the simulator forward one tick
        simulatorAgeInTicks++;
    }
}
