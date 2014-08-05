package com.hunterdavis.lifesim;

import com.hunterdavis.lifesim.util.LoggingAndTime;

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

        width = 40;
        height = 24;

        LoggingAndTime.logWithTiming(TAG, "About to instantiate game board");
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

        //LoggingAndTime.logWithTiming(TAG,"1 ticks have occurred");


        // set the preCalculatedGameBoard to the current game board
        //GameBoard preCalculateGameBoard = new GameBoard(currentGameBoard);

        // do everything we think we should do
        //preCalculateGameBoard.tick();

        // We can look into the future when we need to...

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
