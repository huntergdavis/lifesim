package com.hunterdavis.lifesim.UI;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunterdavis.lifesim.DNA;
import com.hunterdavis.lifesim.GameEngine;
import com.hunterdavis.lifesim.Protein;
import com.hunterdavis.lifesim.R;
import com.hunterdavis.lifesim.util.LoggingAndTime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hunter on 7/23/14.
 */
public class LifeSimFragment extends Fragment {

    public static final String TAG = "LifeSimFragment";

    // for writing a bmp to sd card
    private GameEngine testEngine;
    private Bitmap backingBitmap;

    public LifeSimFragment() {
        // create a backing Bitmap
        Bitmap.Config bitmapConf = Bitmap.Config.RGB_565; // see other conf types if switch to alpha
        backingBitmap = Bitmap.createBitmap(1000, 600, bitmapConf);

        // create a game simulation
        testEngine = new GameEngine();
        refreshMicrobeViewOnBackingBitmap();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        backingBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String fileName =Environment.getExternalStorageDirectory()
                + File.separator + "LifeTest.jpg";

        LoggingAndTime.logWithTiming(TAG,"Filename is: " + fileName);

        //you can create a new file name "test.jpg" in sdcard folder.
        File f = new File(fileName);
        try {
            f.createNewFile();
            //write the bytes in file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            // remember close de FileOutput
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_life_screen, container, false);
        return rootView;
    }

    private void refreshMicrobeViewOnBackingBitmap() {

        // subtract 1 for zero indexing on arrays vs visual xml items
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 600; j++) {
                backingBitmap.setPixel(i, j, getColorForPixelBasedOnCurrentGameBoard(i, j));
            }
        }
    }


    public int getColorForPixelBasedOnCurrentGameBoard(int x, int y) {

        // first, find the bug number for X,Y
        // a bug is the size of it's DNA in terms of visual representation
        int bugNumberX = (x / DNA.PROTEIN_SIZE);
        int bugNumberY = (y / DNA.PROTEIN_SIZE);
        int innerOffsetX = x % DNA.PROTEIN_SIZE;
        int innerOffsetY = y % DNA.PROTEIN_SIZE;

        int proteinTypeAsInt = testEngine.currentGameBoard.lifeMatrix[bugNumberX][bugNumberY].dna.proteinMatrix[innerOffsetX][innerOffsetY].getCurrentProteinTypeAsInt();

        double percentageOfProtein = proteinTypeAsInt*255 / Protein.getNumberOfProteinTypes();
        int colorPercentage = (int) percentageOfProtein;

        int lowThreshold = colorPercentage / 3;

        // red, green,blue as percentages.
        if (colorPercentage < 85) {
            // red set, with some variation
            return Color.rgb(colorPercentage, lowThreshold,lowThreshold);
        } else if (colorPercentage < 170) {
            // blue set, with some variation
            return Color.rgb(lowThreshold, lowThreshold, colorPercentage);
        } else {
            // green set
            return Color.rgb(lowThreshold, colorPercentage, lowThreshold);
        }
    }
}