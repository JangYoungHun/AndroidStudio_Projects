package com.example.stt_tts_practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecognitionListener recognitionListener;
    SpeechRecognizer speechRecognizer;
    TextView STT;
    EditText TTS_Text;
    Button STT_Btn;
    Button TTS_Btn;

    Intent intent;
    TextToSpeech TTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TTS = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != -1)
                    TTS.setLanguage(Locale.KOREA);
            }
        });


        STT = findViewById(R.id.STT);
        TTS_Text = findViewById(R.id.TTS_Text);
        STT_Btn = findViewById(R.id.STT_Btn);
        TTS_Btn = findViewById(R.id.TTS_Btn);

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");


        TTS_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = TTS_Text.getText().toString().trim();
                TTS.setPitch(1f); // ?????????
                TTS.setSpeechRate(1f);   // ??? ?????? ??????;
                TTS.speak(str,TextToSpeech.QUEUE_FLUSH,null,"TTS1");
            }
        });



        STT_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
                speechRecognizer.setRecognitionListener(recognitionListener);
                speechRecognizer.startListening(intent);

                if ( Build.VERSION.SDK_INT >= 23 ){
                    // ????????? ??????
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO},1); }

            }
        });




        recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                tg.startTone(ToneGenerator.TONE_PROP_BEEP,400);
                Toast.makeText(getApplicationContext(),"???????????? ??????.",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override public void onError(int error) {
                String message;
                switch (error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        message = "????????? ??????";
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        message = "??????????????? ??????";
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        message = "????????? ??????";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        message = "???????????? ??????";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        message = "????????? ????????????";
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        message = "?????? ??? ??????";
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        message = "RECOGNIZER ??? ??????";
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        message = "????????? ?????????";
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        message = "????????? ????????????";
                        break;
                    default:
                        message = "??? ??? ?????? ?????????";
                        break;
                }
                Toast.makeText(getApplicationContext(), "?????? : " + message,Toast.LENGTH_SHORT).show();
            }

            @Override public void onResults(Bundle results) {

                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                for(int i = 0; i < matches.size() ; i++){
                    STT.setText(matches.get(i)); }
            }


            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        };

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(TTS != null) {
            TTS.stop();
            TTS.shutdown();
            TTS = null;
        }
    }
}