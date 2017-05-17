package com.example.ambiental.speechrecognition;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.ambiental.speechrecognition.ConnectionThread.bufferedW;
import static com.example.ambiental.speechrecognition.ConnectionThread.socket;
import static com.example.ambiental.speechrecognition.MainActivity.TAG;
import static com.example.ambiental.speechrecognition.MainActivity.thread;

public class TalkActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView txtSpeechInput;
    public Button talkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);

        talkButton = (Button) findViewById(R.id.talkButton);
        //talkButton.setClickable(true);

        //speechRecognition();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    if ( socket.isConnected() ) {
                        String message = (String) txtSpeechInput.getText();
//                        if (message == "Sayonara" ){
//                            talkButton.setClickable(false);
//                        }
                        try {
                            bufferedW.write((String)txtSpeechInput.getText());
                            bufferedW.newLine();
                            bufferedW.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(TAG, "Error sending message to server: " + e.getMessage());
                        }
                    }
                }
                break;
            }
        }
        //speechRecognition();
    }

    protected void speechRecognition() {
        Log.d(TAG,"Inicia el SpeechRecognition");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Function to activate speech recognition
    public void pushToTalk(View v){

        new Thread(){
            @Override
            public void run(){
                speechRecognition();
            }
        }.start();
    }

    // Function to back to main menu
    public void backMain(final View v){

        new Thread(){
            public void run(){
                thread.closeConnection();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent,0);
            }
        }.start();
    }

}
