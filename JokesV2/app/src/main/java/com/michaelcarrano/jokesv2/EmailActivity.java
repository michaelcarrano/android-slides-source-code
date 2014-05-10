package com.michaelcarrano.jokesv2;

import com.github.sendgrid.SendGrid;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Hashtable;


public class EmailActivity extends Activity {

    private String mJoke;

    private String mAnswer;

    private EditText mEmailTo;

    private EditText mEmailMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        // Get the joke and answer
        Intent intent = getIntent();
        if (intent != null) {
            mJoke = intent.getStringExtra("Joke");
            mAnswer = intent.getStringExtra("Answer");

            // Print to the LogCat
            Log.i("JokesV2", mJoke);
            Log.i("JokesV2", mAnswer);
        }

        // Get our views (note: we don't need to find the button due to the onClick attribute)
        mEmailTo = (EditText) findViewById(R.id.edit_email_to);
        mEmailMsg = (EditText) findViewById(R.id.edit_email_message);

        // Display the joke and answer in the mEmailMsg viw
        mEmailMsg.append("Check out this funny joke!\n\n");
        mEmailMsg.append(mJoke);
        mEmailMsg.append("\n\n\n");
        mEmailMsg.append(mAnswer);

    }

    // Called when the user clicks the Send button
    public void sendJoke(View view) {
        Hashtable<String, String> params = new Hashtable<String, String>();

        // Get our values from the views
        String to = mEmailTo.getText().toString();
        params.put("to", to);

        params.put("from", Constants.MY_EMAIL);

        params.put("subject", Constants.SUBJECT);

        String msg = mEmailMsg.getText().toString();
        params.put("msg", msg);

        // Send the Email
        SendEmailWithSendGrid email = new SendEmailWithSendGrid();
        email.execute(params);
    }

    // Send an email with SendGrid's Web API, using the SendGrid Java Library
    // This needs to happen off the UI thread so we will use an AsyncTask
    private class SendEmailWithSendGrid extends AsyncTask<Hashtable<String, String>, Void, String> {

        @Override
        protected String doInBackground(Hashtable<String, String>... params) {
            Hashtable<String, String> h = params[0];
            SendGrid sendgrid = new SendGrid(Constants.SENDGRID_NAME, Constants.SENDGRID_PW);
            sendgrid.addTo(h.get("to"));
            sendgrid.setFrom(h.get("from"));
            sendgrid.setSubject(h.get("subject"));
            sendgrid.setText(h.get("msg"));
            String response = sendgrid.send();
            return response;
        }

        protected void onPostExecute(String response) {
            Log.i("JokesV2", response);
            Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
        }
    }
}
