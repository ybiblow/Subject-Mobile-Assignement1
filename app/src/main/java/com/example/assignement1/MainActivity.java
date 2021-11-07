package com.example.assignement1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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

}


/*
package com.example.assignement1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private ImageView imageViewArrowLeft;
    private ImageView imageViewArrowRight;
    private ImageView imageViewCar;
    private ArrayList<ImageView> rocks;
    private Random random = new Random();
    private int randomNumber;
    private ArrayList<ImageView> hearts;
    private int numOfHearts = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        imageViewArrowLeft = findViewById(R.id.imageViewArrowLeft);
        imageViewArrowRight = findViewById(R.id.imageViewArrowRight);
        imageViewCar = findViewById(R.id.imageViewCar1);
        rocks = new ArrayList<ImageView>();
        hearts = new ArrayList<ImageView>();

        for (int i = 0; i < 15; i++) {
            rocks.add(findViewById(getResources().getIdentifier("imageViewRock" + i, "id", getPackageName())));
        }

        for (int i = 0; i < 3; i++) {
            hearts.add(findViewById(getResources().getIdentifier("imageViewHeart" + i, "id", getPackageName())));
        }

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("info", "Moving Rocks");
                dropRocks();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    public void moveCar(View v) {

        int carTag = Integer.parseInt(imageViewCar.getTag().toString());
        int arrowTag = Integer.parseInt(v.getTag().toString());

        if (arrowTag == 0) {
            //move left
            if (carTag != 0) {
                imageViewCar.setVisibility(View.INVISIBLE);
                if (carTag == 1) {
                    imageViewCar = findViewById(R.id.imageViewCar0);
                } else {
                    imageViewCar = findViewById(R.id.imageViewCar1);
                }
                imageViewCar.setVisibility(View.VISIBLE);
            } else {
                Log.i("Info", "Can't move left!");
            }
        } else {
            //move right
            if (carTag != 2) {
                imageViewCar.setVisibility(View.INVISIBLE);
                if (carTag == 0) {
                    imageViewCar = findViewById(R.id.imageViewCar1);
                } else {
                    imageViewCar = findViewById(R.id.imageViewCar2);
                }
                imageViewCar.setVisibility(View.VISIBLE);
            } else {
                Log.i("Info", "Can't move Right!");
            }
        }
        checkCrash();
    }

    public void dropRocks() {
        for (int i = 14; i > -1; i--) {
            if (i > 11) {
                rocks.get(i).setVisibility(View.INVISIBLE);
            } else {
                if (rocks.get(i).getVisibility() == View.VISIBLE) {
                    rocks.get(i).setVisibility(View.INVISIBLE);
                    rocks.get(i + 3).setVisibility(View.VISIBLE);
                }
            }
        }
        randomNumber = random.nextInt(1000) % 3;
        if (randomNumber == 0 || randomNumber == 1) {
            randomNumber = random.nextInt(1000) % 3;
            boolean rock4Visible = rocks.get(4).getVisibility() == View.VISIBLE;
            boolean rock6Visible = rocks.get(6).getVisibility() == View.VISIBLE;
            boolean rock8Visible = rocks.get(8).getVisibility() == View.VISIBLE;

            while ((rock4Visible && rock8Visible && randomNumber == 0) || (rock4Visible && rock6Visible && randomNumber == 2)) {
                randomNumber = random.nextInt(1000) % 3;
            }
            rocks.get(randomNumber).setVisibility(View.VISIBLE);
        }
        checkCrash();
    }

    public void checkCrash() {
        int carTag = Integer.parseInt(imageViewCar.getTag().toString());
        switch (carTag) {
            case 0:
                if (rocks.get(12).getVisibility() == View.VISIBLE) {
                    Toast.makeText(getApplicationContext(), "You have been hit!", Toast.LENGTH_SHORT).show();
                    reduceHeart();
                }
                break;
            case 1:
                if (rocks.get(13).getVisibility() == View.VISIBLE) {
                    Toast.makeText(getApplicationContext(), "You have been hit!", Toast.LENGTH_SHORT).show();
                    reduceHeart();
                }
                break;
            case 2:
                if (rocks.get(14).getVisibility() == View.VISIBLE) {
                    Toast.makeText(getApplicationContext(), "You have been hit!", Toast.LENGTH_SHORT).show();
                    reduceHeart();
                }
                break;
        }
    }

    private void reduceHeart() {
        switch (numOfHearts) {
            case 3:
                numOfHearts--;
                hearts.get(2).setVisibility(View.INVISIBLE);
                break;
            case 2:
                numOfHearts--;
                hearts.get(1).setVisibility(View.INVISIBLE);
                break;
            case 1:
            numOfHearts--;
            hearts.get(0).setVisibility(View.INVISIBLE);
            break;
            case 0:
                numOfHearts=3;
                hearts.get(2).setVisibility(View.VISIBLE);
                hearts.get(1).setVisibility(View.VISIBLE);
                hearts.get(0).setVisibility(View.VISIBLE);
        }
    }
}

*/
