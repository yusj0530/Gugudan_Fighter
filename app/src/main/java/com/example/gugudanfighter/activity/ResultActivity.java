package com.example.gugudanfighter.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gugudanfighter.R;


public class ResultActivity extends AppCompatActivity {

    private int correctCounter;
    private int question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        correctCounter = intent.getIntExtra("correctAnswerCount",0);
        question = intent.getIntExtra("questionCount",0);

        TextView Correct = findViewById(R.id.textViewResultCorrectAnswerCount);
        TextView Question = findViewById(R.id.textViewResultQuestionCount);

        Correct.setText(""+correctCounter);
        Question.setText(""+question);

        findViewById(R.id.buttonResultYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (ResultActivity.this, GameActivity.class));
                finish();

            }
        });

        findViewById(R.id.buttonResultNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
