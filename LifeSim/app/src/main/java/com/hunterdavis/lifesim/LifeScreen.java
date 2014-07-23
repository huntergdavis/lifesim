package com.hunterdavis.lifesim;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.entity.util.ScreenCapture;
import org.andengine.entity.util.ScreenCapture.IScreenCaptureCallback;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.FileUtils;
import org.andengine.util.color.Color;

import android.widget.Toast;

/*
    Now using AndEngine for displaying life
 */
public class LifeScreen extends SimpleBaseGameActivity {
    // ===========================================================
    // Constants
    // ===========================================================

    private final static String TAG = "LifeScreen";

    private static final int CAMERA_WIDTH = 1000;
    private static final int CAMERA_HEIGHT = 600;

    private GameEngine testEngine;

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    public void onCreateResources() {

    }

    @Override
    public Scene onCreateScene() {

        // game engine initialization.  is this a good place?

        // create a game simulation
        // a big one
        LoggingAndTime.logWithTiming(TAG, "Initializing Engine");
        testEngine = new GameEngine();
        LoggingAndTime.logWithTiming(TAG, "Finished Initializing Engine");


        this.mEngine.registerUpdateHandler(new FPSLogger());

        final Scene scene = new Scene();
        final ScreenCapture screenCapture = new ScreenCapture();
        scene.attachChild(screenCapture);
        scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
                if(pSceneTouchEvent.isActionDown()) {
                    LoggingAndTime.logWithTiming(TAG, "About to tick game engine 1000 times");
                    for(int i = 0; i < 1000; i++) {
                        testEngine.tick();
                    }
                    LoggingAndTime.logWithTiming(TAG, "Just ticked game engine 1000 times");

                }
                return true;
            }
        });

        //scene.setBackground(new Background(0, 0, 0));

		/* Create the rectangles. */
        //final Rectangle rect1 = this.makeColoredRectangle(-180, -180, 1, 0, 0);
        //final Rectangle rect2 = this.makeColoredRectangle(0, -180, 0, 1, 0);
        //final Rectangle rect3 = this.makeColoredRectangle(0, 0, 0, 0, 1);
        //final Rectangle rect4 = this.makeColoredRectangle(-180, 0, 1, 1, 0);

        //final Entity rectangleGroup = new Entity(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2);

        //rectangleGroup.attachChild(rect1);
        //rectangleGroup.attachChild(rect2);
        //rectangleGroup.attachChild(rect3);
        //rectangleGroup.attachChild(rect4);

        //scene.attachChild(rectangleGroup);




        return scene;
    }

    private void refreshMicrobeView() {

        for(int i = 0;i<CAMERA_WIDTH;i++) {
            for (int j = 0; j < CAMERA_HEIGHT;j++) {
                //TODO - hunter, figure out pixel vs rectangle in andengine and what's the what 
                Color color = getColorForPixelBasedOnCurrentGameBoard(i,j);
            }
        }


        for (int i = 0; i < testEngine.height; i++) {
            for( int j = 0; j < testEngine.width; j++) {
                for (int k = 0;k < DNA.PROTEIN_SIZE;k++) {
                    for (int l = 0; l < DNA.PROTEIN_SIZE;l++) {
                        testEngine.currentGameBoard.lifeMatrix[i][j].dna.proteinMatrix[k][l]
                    }
                }
            }
        }
    }

    public Color getColorForPixelBasedOnCurrentGameBoard(int x, int y) {

        // first, find the bug number for X,Y
        // a bug is the size of it's DNA in terms of visual representation
        int bugNumberX = x / DNA.PROTEIN_SIZE;
        int bugNumberY = y / DNA.PROTEIN_SIZE;
        int innerOffsetX = x % DNA.PROTEIN_SIZE;
        int innerOffsetY = y % DNA.PROTEIN_SIZE;

        int proteinTypeAsInt = testEngine.currentGameBoard.lifeMatrix[bugNumberX][bugNumberY].dna.proteinMatrix[innerOffsetX][innerOffsetY].getCurrentProteinTypeAsInt();
        float colorPercentage = proteinTypeAsInt / Protein.getNumberOfProteinTypes();

        // red, green,blue as percentages.
        if (proteinTypeAsInt < colorPercentage/3) {
            // red set, with some variation
            return new Color(colorPercentage,colorPercentage/3,colorPercentage/3);
        }else if (proteinTypeAsInt < 2*colorPercentage/3) {
            // blue set, with some variation
            return new Color(colorPercentage/3,colorPercentage/3,colorPercentage);
        }else {
            // green set
            return new Color(colorPercentage/3,colorPercentage,colorPercentage/3);
        }
    }

    private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
        final Rectangle coloredRect = new Rectangle(pX, pY, 180, 180, this.getVertexBufferObjectManager());
        coloredRect.setColor(pRed, pGreen, pBlue);
        return coloredRect;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}