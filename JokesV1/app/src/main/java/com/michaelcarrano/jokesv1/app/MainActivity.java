package com.michaelcarrano.jokesv1.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends Activity {

    private int mJoke = 0;

    private String[] mJokes;

    private String[] mAnswers;

    private TextView mJokeTextView;

    private Button mAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get our jokes and answers
        mJokes = getResources().getStringArray(R.array.jokes);
        mAnswers = getResources().getStringArray(R.array.answers);

        // Get our views
        mJokeTextView = (TextView) findViewById(R.id.text_joke);
        mAnswerButton = (Button) findViewById(R.id.btn_answer);

        // Setup button listener
        mAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), mAnswers[mJoke], Toast.LENGTH_LONG).show();
            }
        });

        displayJoke();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_rand_joke) {
            displayJoke();
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayJoke() {
        mJokeTextView.setText(mJokes[rand()]);
    }

    private int rand() {
        return mJoke = new Random().nextInt(mJokes.length);
    }
}
