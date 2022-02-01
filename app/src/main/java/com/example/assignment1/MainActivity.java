package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.assignement1.R;

import java.util.Random;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Vibrator v;
    private ArrayList<ImageView> stones;
    private ArrayList<ImageView> imageViewSpaceships;
    private ArrayList<ImageView> imageViewHearts;
    private Random random = new Random();
    int randomNumber;
    int numOfHearts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        stones = new ArrayList<ImageView>();
        for (int i = 0; i < 12; i++) {
            stones.add(findViewById(getResources().getIdentifier("imageViewStone" + i, "id", getPackageName())));
        }
        imageViewSpaceships = new ArrayList<ImageView>();
        for (int i = 0; i < 3; i++) {
            imageViewSpaceships.add(findViewById(getResources().getIdentifier("imageViewSpaceship" + i, "id", getPackageName())));
        }
        imageViewHearts = new ArrayList<ImageView>();
        for (int i = 0; i < 3; i++) {
            imageViewHearts.add(findViewById(getResources().getIdentifier("imageViewHeart" + i, "id", getPackageName())));
        }
        numOfHearts = 3;
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dropRocks();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    public void dropRocks() {

        for (int i = 11; i >= 0; i--) {
            if (i > 8) {
                stones.get(i).setVisibility(View.INVISIBLE);
            } else if (stones.get(i).getVisibility() == View.VISIBLE) {
                stones.get(i).setVisibility(View.INVISIBLE);
                stones.get(i + 3).setVisibility(View.VISIBLE);
            }
        }

        randomNumber = random.nextInt(1000) % 3;
        stones.get(randomNumber).setVisibility(View.VISIBLE);
        checkCrash();
    }

    public void moveCar(View view) {
        int arrowTag = Integer.parseInt(view.getTag().toString());
        int spaceshipTag = getVisibleSpaceshipTag();
        if (arrowTag == 0) {
            //move left
            if (spaceshipTag != 0) {
                imageViewSpaceships.get(spaceshipTag).setVisibility(View.INVISIBLE);
                imageViewSpaceships.get(spaceshipTag - 1).setVisibility(View.VISIBLE);
            }
        } else {
            //move right
            if (spaceshipTag != 2) {
                imageViewSpaceships.get(spaceshipTag).setVisibility(View.INVISIBLE);
                imageViewSpaceships.get(spaceshipTag + 1).setVisibility(View.VISIBLE);
            }
        }
        checkCrash();
    }

    public int getVisibleSpaceshipTag() {
        for (ImageView spaceship : imageViewSpaceships) {
            if (spaceship.getVisibility() == View.VISIBLE)
                return Integer.parseInt(spaceship.getTag().toString());
        }
        return -1;
    }

    public void checkCrash() {
        int spaceshipTag = getVisibleSpaceshipTag();
        if (stones.get(spaceshipTag + 9).getVisibility() == View.VISIBLE) {
            Log.i("info", "You have been hit!");
            Toast.makeText(getApplicationContext(), "You have been hit!", Toast.LENGTH_SHORT).show();
            vibrate();
            reduceHeart();
        }
    }

    public void reduceHeart() {
        if (numOfHearts != 0) {
            for (ImageView heart : imageViewHearts) {
                if (heart.getVisibility() == View.VISIBLE) {
                    heart.setVisibility(View.INVISIBLE);
                    numOfHearts--;
                    break;
                }
            }
        } else {
            for (ImageView heart : imageViewHearts) {
                heart.setVisibility(View.VISIBLE);
                numOfHearts = 3;
            }
        }
    }

    public void vibrate() {
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

}