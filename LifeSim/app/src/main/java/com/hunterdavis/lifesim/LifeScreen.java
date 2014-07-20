package com.hunterdavis.lifesim;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;


public class LifeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_screen);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new LifeSimFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.life_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class LifeSimFragment extends Fragment {

        public LifeSimFragment() {

            // create a game simulation
            GameEngine testEngine = new GameEngine();
            //testEngine.tick();
            //testEngine.tick();

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_life_screen, container, false);
            return rootView;
        }
    }
}
