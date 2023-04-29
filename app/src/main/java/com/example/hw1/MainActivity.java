package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public final int DELAY = 1000;
    public final int HEIGHT = 6;
    public final int NUMOFCHICKENS = 3;
    private Gamemanager gamemanager;
    private ShapeableImageView[] hearts;
    private ShapeableImageView[][] eggs;
    private ShapeableImageView[] brokenEggs;
    private ShapeableImageView ship1;
    private ShapeableImageView ship2;
    private ShapeableImageView ship3;
    private  int counter=1;

    private MaterialButton[] mainLeftRightBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //gamemanager=new Gamemanager()
        findViewForAllGameBoard();
        setVisibility();
        buttonLeftRightClick();
        startGame();

    }
    //move to game manager
    private void startGame() {
          final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                handler.postDelayed(this, 800);
                dropEggsDownView();
                eggsCrash();
            }
        }, DELAY);

        runOnUiThread(new Runnable() {
            public void run() {
                handler.postDelayed(this,1700);
                startFallingEggs();
                initBrokenEggs();


            }
        });



    }


    //move to game manager
private void buttonLeftRightClick(){
    mainLeftRightBTN[0].setOnClickListener(v->moveLeft());
    mainLeftRightBTN[1].setOnClickListener(v->moveRight());
}
    //move to game manager
    private void findViewForAllGameBoard() {
        mainLeftRightBTN = new MaterialButton[]{
                findViewById(R.id.Main_button_left),
                findViewById(R.id.Main_button_right)};
        hearts = new ShapeableImageView[]{
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3)};
        ship1 = findViewById(R.id.ship1);
        ship2 = findViewById(R.id.ship2);
        ship3 = findViewById(R.id.ship3);
        brokenEggs=new ShapeableImageView[]
                {findViewById(R.id.broken_egg1),
                findViewById(R.id.broken_egg2),
                findViewById(R.id.broken_egg3)};

        setEggView() ;

    }

    //move to game manager
    private void setEggView() {

        eggs = new ShapeableImageView[HEIGHT][NUMOFCHICKENS];
        int num = 1;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < NUMOFCHICKENS; j++) {
                String numOfEgg = "egg" + num;
                num++;
                int resID = getResources().getIdentifier(numOfEgg, "id", getPackageName());
                eggs[i][j] = ((ShapeableImageView) findViewById(resID));
            }
        }

    }
    //move to game manager
    private void setVisibility(){
        initShip();
        initEggs();
    }
    private void heartManager(int heartsNumber){
        hearts[hearts.length - heartsNumber].setVisibility(View.INVISIBLE);
        if(heartsNumber == 3){
            for (int i = 1; i <= 3 ; i++) {
                hearts[hearts.length - i].setVisibility(View.VISIBLE);
            }
        }

    }
    private void eggsCrash() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (counter == 4)
            counter = 1;

        if (eggs[4][0].getVisibility() == View.VISIBLE && ship1.getVisibility() == View.VISIBLE) {
            swichEggs(eggs[4][0], brokenEggs[0]);
            heartManager(counter);
            toastVibrator(getApplicationContext(), v, counter);
            counter++;

        }
        if (eggs[4][1].getVisibility() == View.VISIBLE && ship2.getVisibility() == View.VISIBLE) {

            swichEggs(eggs[4][1], brokenEggs[1]);
            heartManager(counter);
            toastVibrator(getApplicationContext(), v, counter);
            counter++;
        }
        if (eggs[4][2].getVisibility() == View.VISIBLE && ship3.getVisibility() == View.VISIBLE) {

            swichEggs(eggs[4][2], brokenEggs[2]);
            heartManager(counter);
            toastVibrator(getApplicationContext(), v, counter);
            counter++;
        }

    }
    private void toastVibrator(Context context,Vibrator v,int heartsNumber){

        if(heartsNumber == 3)
            heartsNumber = 0;
        Toast.makeText(context," The number of attempts left:" + (hearts.length - heartsNumber)  ,Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }
    public void initShip(){//move the ship to the middle
        ship1.setVisibility(View.INVISIBLE);
        ship3.setVisibility(View.INVISIBLE);

    }

    public void moveLeft(){
        if(ship2.getVisibility()==View.VISIBLE){
            ship1.setVisibility(View.VISIBLE);
            ship2.setVisibility(View.INVISIBLE);
        }else if(ship3.getVisibility()==View.VISIBLE){
            ship2.setVisibility(View.VISIBLE);
            ship3.setVisibility(View.INVISIBLE);

        }
    }

    public void moveRight(){
        if(ship2.getVisibility()==View.VISIBLE){
            ship3.setVisibility(View.VISIBLE);
            ship2.setVisibility(View.INVISIBLE);
        }else if(ship1.getVisibility()==View.VISIBLE){
            ship2.setVisibility(View.VISIBLE);
            ship1.setVisibility(View.INVISIBLE);
        }
    }
    public void initEggs(){
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < NUMOFCHICKENS; j++) {
                this.eggs[i][j].setVisibility(View.INVISIBLE);
            }
        }
        initBrokenEggs();
    }

    public void swichEggs(ShapeableImageView egg, ShapeableImageView brokenEgg) {
        egg.setVisibility(View.INVISIBLE);
        brokenEgg.setVisibility(View.VISIBLE);

    }

    public void startFallingEggs() {
        Random rand = new Random();
        int randEgg = rand.nextInt(3);
        eggs[0][randEgg].setVisibility(View.VISIBLE);
    }

    public void dropEggsDownView() {
        for(int i = HEIGHT-1;i>=0;i--){
            for (int j=NUMOFCHICKENS-1;j>=0;j--){
                if(i==HEIGHT-1){
                    eggs[i][j].setVisibility(View.INVISIBLE);
                }
                else{
                    if(eggs[i][j].getVisibility()==View.VISIBLE){
                        eggs[i+1][j].setVisibility(View.VISIBLE);
                        eggs[i][j].setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

    //move to main activity
    public void initBrokenEggs() {
        for(int i=0;i<NUMOFCHICKENS;i++){
            this.brokenEggs[i].setVisibility(View.INVISIBLE);
        }
    }

}