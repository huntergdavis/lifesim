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

    public LifeSimFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_life_screen, container, false);
        return rootView;
    }
}