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

                    screenCapture.capture(180, 60, 360, 360, FileUtils.getAbsolutePathOnExternalStorage(LifeScreen.this, "Screen_" + System.currentTimeMillis() + ".png"), new IScreenCaptureCallback() {
                        @Override
                        public void onScreenCaptured(final String pFilePath) {
                            LifeScreen.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(LifeScreen.this, "Screenshot: " + pFilePath + " taken!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onScreenCaptureFailed(final String pFilePath, final Exception pException) {
                            LifeScreen.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LifeScreen.this, "FAILED capturing Screenshot: " + pFilePath + " !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                return true;
            }
        });

        scene.setBackground(new Background(0, 0, 0));

		/* Create the rectangles. */
        final Rectangle rect1 = this.makeColoredRectangle(-180, -180, 1, 0, 0);
        final Rectangle rect2 = this.makeColoredRectangle(0, -180, 0, 1, 0);
        final Rectangle rect3 = this.makeColoredRectangle(0, 0, 0, 0, 1);
        final Rectangle rect4 = this.makeColoredRectangle(-180, 0, 1, 1, 0);

        final Entity rectangleGroup = new Entity(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2);

        rectangleGroup.attachChild(rect1);
        rectangleGroup.attachChild(rect2);
        rectangleGroup.attachChild(rect3);
        rectangleGroup.attachChild(rect4);

        scene.attachChild(rectangleGroup);

        return scene;
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