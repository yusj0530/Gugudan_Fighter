package com.example.gugudanfighter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gugudanfighter.R;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity  implements View.OnClickListener {
    private static final int TIME_LIMIT =30;
    private Timer timer = new Timer();

    private int correctAnswer;//정답
    private int correctAnswerCount;//정답수
    private int questionCount; //출제수
    private int ran1;
    private int ran2;
    private int exam;
    private int answerButtion;

    private TextView tvLastTime;
    private TextView tvScore;
    private TextView Lefttext;
    private TextView Righttext;
    private TextView Array;
    private int[] answerButtonids = {
            R.id.button_0_0,R.id.button_0_1,R.id.button_0_2,
            R.id.button_1_0,R.id.button_1_1,R.id.button_1_2,
            R.id.button_2_0,R.id.button_2_1,R.id.button_2_2
    };

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        if(b.getText().toString().equals(""+answerButtion)){
            correctAnswerCount++;
        }
        Question();
        example();
        updateScore();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findViewById(R.id.button_0_0).setOnClickListener(this);
        findViewById(R.id.button_0_1).setOnClickListener(this);
        findViewById(R.id.button_0_2).setOnClickListener(this);
        findViewById(R.id.button_1_0).setOnClickListener(this);
        findViewById(R.id.button_1_1).setOnClickListener(this);
        findViewById(R.id.button_1_2).setOnClickListener(this);
        findViewById(R.id.button_2_0).setOnClickListener(this);
        findViewById(R.id.button_2_1).setOnClickListener(this);
        findViewById(R.id.button_2_2).setOnClickListener(this);

        //View 찾기
        tvLastTime = findViewById(R.id.textViewLastTime);
        tvScore = findViewById(R.id.textViewScore);
        Lefttext = findViewById(R.id.textViewLeftOperand);
        Righttext = findViewById(R.id.textViewRightOperand);

        // UI 초기화
        updateLastTime(TIME_LIMIT);

        // 타이머 시작
        timer.schedule( new GamePlayTimerTask(), 1000, 1000);

        // 랜덤 문제

        Question();
        example();
        tvScore.setText(correctAnswerCount + "/" + questionCount);
        }

    private void Question() {
        ran1 = randaomize(2, 8);
        ran2 = randaomize(2, 8);
        Lefttext.setText(""+ran1);
        Righttext.setText(""+ran2);
        correctAnswer = ran1*ran2;

    }


    private void example(){
        int[] data = new int[9];
        for(int i=0; i<answerButtonids.length; i++){
                Array = findViewById(answerButtonids[i]);
                ran1 = randaomize(2, 8);
                ran2 = randaomize(2, 8);
                exam = ran1 * ran2;
                data[i]=exam;
                if (correctAnswer==exam){
                    i--;
                    continue;
                }
            for(int j=0; j<i;j++) {
                if (data[i] == data[j]) {
                    i--;
                    break;
                }
            }
            Array.setText("" + exam);
        }

        Button answer = findViewById(answerButtonids[randaomize(0,8)]);
        answer.setText(""+correctAnswer);
        answerButtion = Integer.parseInt(answer.getText().toString());


    }

    private int randaomize(int from, int to){
        return (int)(Math.random()* to)+from;
    }

    private void updateScore() {
        questionCount++;
        tvScore.setText(correctAnswerCount + "/" + questionCount);
    }
        private void updateLastTime(int lastTime){
        tvLastTime.setText(""+lastTime);
    }

    private class GamePlayTimerTask extends TimerTask {
        private int seconds = 0;

        @Override
        public void run() {
            if(seconds >= TIME_LIMIT) {
                /*게임 종료 */

                // 1. 타이머 스톱
                timer.cancel();

                // 2. Result Activity 이동
                Intent intent = new Intent( GameActivity.this, ResultActivity.class  );
                intent.putExtra("correctAnswerCount", correctAnswerCount);
                intent.putExtra("questionCount",questionCount);
                startActivity( intent );
                finish();
                return ;
            }
            seconds++;

            //UI 변경은 메인 쓰레드에서 해야함.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateLastTime(TIME_LIMIT - seconds);
                }
            });




        }
    }
}

