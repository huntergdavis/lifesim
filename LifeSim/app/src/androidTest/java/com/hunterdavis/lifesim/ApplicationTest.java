package com.hunterdavis.lifesim;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.io.IOException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        serializeTest();
    }

    public void serializeTest() {

        // create a game simulation
        GameEngine testEngine = new GameEngine();
        testEngine.tick();
        testEngine.tick();

        String outputString = "nothing happened";

        try {
            outputString = SavingAndLoading.toString(testEngine);
        } catch (IOException e) {
            Log.e("LifeScreen", e.getMessage());
        }

        Log.e("LifeScreen", "Game Engine as Serialized String is: " + outputString);


        Log.e("LifeScreen", "Game Engine regular ole toString is: " + testEngine.toString());

        GameEngine loadedEngine = null;

        try {
            loadedEngine = (GameEngine) SavingAndLoading.fromString(outputString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String loadedOutputString = "nothing happened either";

        try {
            loadedOutputString = SavingAndLoading.toString(loadedEngine);
        } catch (IOException e) {
            Log.e("LifeScreen", e.getMessage());
        }

        Log.e("LifeScreen", "Loaded Engine as Serialized String is: " + loadedOutputString);


        Log.e("LifeScreen", "Loaded Engine regular ole toString is: " + loadedEngine.toString());


        if(loadedOutputString.equals(outputString)) {
            Log.e("LifeScreen", "Serialized Strings Are Equal!!");
        }else {
            Log.e("LifeScreen", "Serialized Strings Are NOT Equal!!");
        }

        if(testEngine.toString().equals(loadedEngine.toString())) {
            Log.e("LifeScreen", "Regular ToStrings Are Equal");
        }else {
            Log.e("LifeScreen", "Regular ToStrings Are NOT Equal");
        }
    }
}