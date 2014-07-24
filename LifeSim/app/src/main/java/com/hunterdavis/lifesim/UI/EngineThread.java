package com.hunterdavis.lifesim.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.hunterdavis.lifesim.DNA;
import com.hunterdavis.lifesim.GameEngine;
import com.hunterdavis.lifesim.Protein;
import com.hunterdavis.lifesim.util.LoggingAndTime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hunter on 7/23/14.
 */

class EngineThread extends Thread {

    private static final String TAG = "EngineThread";
    private int bitmapWidth = 1000;
    private int bitmapHeight = 600;

    private int canvasWidth = 1000;
    private int canvasHeight = 600;

    private static final int SPEED = 2;
    private boolean run = false;
    SurfaceHolder sh;
    Context ctx;
    Handler localHandler;

    private GameEngine testEngine;
    private Bitmap backingBitmap;

    public EngineThread(SurfaceHolder surfaceHolder, Context context,
                        Handler handler) {
        sh = surfaceHolder;
        localHandler = handler;
        ctx = context;

        // create a backing Bitmap
        Bitmap.Config bitmapConf = Bitmap.Config.RGB_565; // see other conf types if switch to alpha
        backingBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, bitmapConf);

        // create a game simulation
        testEngine = new GameEngine();
        refreshMicrobeViewOnBackingBitmap();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        backingBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

        //you can create a new file name "test.jpg" in sdcard folder.
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + "LifeTest.jpg");
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

    public void run() {
        while (run) {
            if (!sh.getSurface().isValid())
                continue;

            Canvas c = null;
                // 1000 ticks
                for (int i = 0; i < 1000; i++) {
                    testEngine.tick();
                }

                    synchronized (sh) {
                        c = sh.lockCanvas(null);
                        doDraw(c);
                    }
                if (c != null) {
                    sh.unlockCanvasAndPost(c);
                }

        }
    }

    public void setRunning(boolean b) {
        run = b;
    }

    public void setSurfaceSize(int width, int height) {
            canvasWidth = width;
            canvasHeight = height;
    }

    private void doDraw(Canvas canvas) {
        // the world has turned, what's up?
        refreshMicrobeViewOnBackingBitmap();
        canvas.drawBitmap(backingBitmap, 0, 0, null);
        Paint paint = new Paint();
        paint.setARGB(3,35,123,124);
        canvas.drawRect(0, 0, 300, 300, paint);


    }

    private void refreshMicrobeViewOnBackingBitmap() {

        // subtract 1 for zero indexing on arrays vs visual xml items
        for (int i = 0; i < bitmapWidth; i++) {
            for (int j = 0; j < bitmapHeight; j++) {
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
        int colorPercentage = ((proteinTypeAsInt / Protein.getNumberOfProteinTypes()) * 255);

        int lowThreshold = colorPercentage / 3;

        // red, green,blue as percentages.
        if (proteinTypeAsInt < lowThreshold) {
            // red set, with some variation
            return Color.rgb(colorPercentage, lowThreshold,lowThreshold);
        } else if (proteinTypeAsInt < 2 * lowThreshold) {
            // blue set, with some variation
            return Color.rgb(lowThreshold, lowThreshold, colorPercentage);
        } else {
            // green set
            return Color.rgb(lowThreshold, colorPercentage, lowThreshold);
        }
    }
}

