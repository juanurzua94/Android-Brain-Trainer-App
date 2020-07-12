package com.example.braintrainer;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // TextViews
    private TextView timerBox;
    private TextView equationBox;
    private TextView scoreBox;
    private TextView answer1Box;
    private TextView answer2Box;
    private TextView answer3Box;
    private TextView answer4Box;
    private TextView resultBox;
    // TextViews


    private CountDownTimer timer;
    private Random rand1;
    private Random rand2;
    private Random threeRandomNum;
    private int num1;
    private int num2;
    private int result;
    private int attempts;
    private int correct;
    private boolean started;

    public void chosenAnswer(View view){
        TextView counter = (TextView) view;
        int tappedTag = Integer.parseInt(counter.getTag().toString());
        attempts++;
        int choice;
        if(tappedTag == 0) {
            choice = Integer.parseInt(answer1Box.getText().toString());
        }
        else if(tappedTag == 1){
            choice = Integer.parseInt(answer2Box.getText().toString());
        }
        else if(tappedTag == 2){
            choice = Integer.parseInt(answer3Box.getText().toString());
        }
        else{
            choice = Integer.parseInt(answer4Box.getText().toString());
        }

        if(choice == result){
            correct++;
            resultBox.setTextColor(Color.GREEN);
            resultBox.setText("CORRECT!");
        }
        else {
            resultBox.setTextColor(Color.RED);
            resultBox.setText("WRONG");
        }

        resultBox.setVisibility(View.VISIBLE);
        triggerNewDisplay();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerBox = (TextView) findViewById(R.id.timer);
        equationBox = (TextView) findViewById(R.id.equation);
        scoreBox = (TextView) findViewById(R.id.score);
        answer1Box = (TextView) findViewById(R.id.answer1);
        answer2Box = (TextView) findViewById(R.id.answer2);
        answer3Box = (TextView) findViewById(R.id.answer3);
        answer4Box = (TextView) findViewById(R.id.answer4);
        resultBox = (TextView) findViewById(R.id.result);


        started = false;

        rand1 = new Random();
        rand2 = new Random();
        threeRandomNum = new Random();

        attempts = 0;
        correct = 0;

        timerBox.setText(Integer.toString(30));
        triggerNewDisplay();

        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                    timerBox.setText(Long.toString(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                    endOfChallenge();
            }
        }.start();

    }

    private void createEquationDisplay(){
        num1 = rand1.nextInt(15) + 1;
        num2 = rand1.nextInt(15) + 1;
        equationBox.setText(Integer.toString(num1) + " x " + Integer.toString(num2));
    }

    private void generateResult(){
        result = num1 * num2;
    }

    private void createChoicesDisplay(){
        int[] displayNumbers = {0, 0, 0, 0};
        int correctIndex = new Random().nextInt(4);
        displayNumbers[correctIndex] = result;
        for(int i = 0; i < 4; ++i){
            if(i != correctIndex){
                int randNum = threeRandomNum.nextInt(225) + 1;
                while(randNum == result){
                    randNum = threeRandomNum.nextInt(225) + 1;
                }
                displayNumbers[i] = randNum;
            }
        }

        answer1Box.setText(Integer.toString(displayNumbers[0]));
        answer2Box.setText(Integer.toString(displayNumbers[1]));
        answer3Box.setText(Integer.toString(displayNumbers[2]));
        answer4Box.setText(Integer.toString(displayNumbers[3]));


    }

    private void createScoreDisplay(){
        scoreBox.setText(Integer.toString(correct) + "/" + Integer.toString(attempts));
    }

    private void triggerNewDisplay(){
        createEquationDisplay();
        generateResult();
        createChoicesDisplay();
        createScoreDisplay();
    }

    private void endOfChallenge(){
        double grade = ( (double) correct/attempts) * 100;
        if(grade >= 70){
            resultBox.setText("Congrats! You scored a " + Integer.toString((int) grade) + "%!");
        }
        else{
            resultBox.setText("Sorry.. Your score was a " + Integer.toString((int) grade) + "%");
        }
    }

}
