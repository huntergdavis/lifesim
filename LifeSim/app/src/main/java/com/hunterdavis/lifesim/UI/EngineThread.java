package com.hunterdavis.lifesim.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.hunterdavis.lifesim.DNA;
import com.hunterdavis.lifesim.GameEngine;

/**
 * Created by hunter on 7/23/14.
 */

class EngineThread extends Thread {

    private static final String TAG = "EngineThread";
    private static boolean run = false;
    private GameEngine testEngine;
    SurfaceHolder sh;
    Context ctx;
    Handler localHandler;
    private int bitmapWidth = 1000;
    private int bitmapHeight = 600;
    private int canvasWidth = 1000;
    private int canvasHeight = 600;
    private Bitmap backingBitmap;

    private int xAccumulator = 0;

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

    }

    public void run() {

        while (run) {
            if (!sh.getSurface().isValid())
                continue;
            testEngine.tick();

            Canvas c = null;
            synchronized (sh) {
                c = sh.lockCanvas(null);
                doDraw(c);
                if (c != null) {
                    sh.unlockCanvasAndPost(c);
                }
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
    }

    private void refreshMicrobeViewOnBackingBitmap(int lowx, int highx) {
        for (int i = lowx; i < highx; i++) {
            for (int j = 0; j < 600; j++) {
                backingBitmap.setPixel(i, j, getColorForPixelBasedOnCurrentGameBoard(i, j));
            }
        }

    }

    private void refreshMicrobeViewOnBackingBitmap() {

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

        // more intelligent coloring for known types:
        switch (proteinTypeAsInt) {
            case 0:
                // aggressive proteins
                return Color.RED;
            case 1:
                // passive proteins
                return Color.BLUE;
            case 2:
                // stable proteins
                return Color.GRAY;
            case 3:
                // unstable proteins
                return Color.DKGRAY;
            case 4:
                // movement proteins
                return Color.YELLOW;
            case 5:
                // defence proteins
                return Color.LTGRAY;
            case 6:
                // nourishment proteins
                return Color.MAGENTA;
            case 7:
                // transcription proteins
                return Color.WHITE;
            case 8:
                // controller proteins
                return Color.CYAN;
            case 9:
                // efficiency proteins
                return Color.TRANSPARENT;
            case 10:
                // storage proteins
                return Color.BLACK;

            default:
                return Color.BLACK;
        }
    }
}

