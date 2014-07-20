package com.hunterdavis.lifesim;

/**
 * Created by hunter on 7/19/14.
 */
public class GameEngine implements java.io.Serializable {

    public final String TAG = "GameEngine";

    public long simulatorAgeInTicks;

    public GameBoard currentGameBoard;

    public int width;
    public int height;

    public GameEngine() {
        simulatorAgeInTicks = 0;

        width = 24;
        height = 24;

        LoggingAndTime.logWithTiming(TAG,"About to instantiate game board");
        currentGameBoard = new GameBoard(width,height);
        LoggingAndTime.logWithTiming(TAG,"Finished instantiation of game board");

    }

    public GameEngine(GameEngine loadGame) {
        simulatorAgeInTicks = loadGame.simulatorAgeInTicks;
        width = loadGame.width;
        height = loadGame.height;
        currentGameBoard = loadGame.currentGameBoard;
    }

    public void tick() {
        // move the simulator forward one tick
        simulatorAgeInTicks++;

        // set the preCalculatedGameBoard to the current game board
        GameBoard preCalculateGameBoard = new GameBoard(currentGameBoard);

        // do everything we think we should do
        preCalculateGameBoard.tick();

        // here's where we'll actually do some fonky simulator business

        // now actually do it, after some transformation
        currentGameBoard.tick();

    }

    @Override
    public String toString() {
        return "GameEngine{" +
                "simulatorAgeInTicks=" + simulatorAgeInTicks +
                ", currentGameBoard=" + currentGameBoard +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
