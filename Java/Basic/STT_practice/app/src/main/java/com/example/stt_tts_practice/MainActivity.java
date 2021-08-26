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
                TTS.setPitch(1f); // 톤설정
                TTS.setSpeechRate(1f);   // 말 속도 설정;
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
                    // 퍼미션 체크
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO},1); }

            }
        });




        recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                tg.startTone(ToneGenerator.TONE_PROP_BEEP,400);
                Toast.makeText(getApplicationContext(),"음성인식 시작.",Toast.LENGTH_SHORT).show();

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
                        message = "오디오 에러";
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        message = "클라이언트 에러";
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        message = "퍼미션 없음";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        message = "네트워크 에러";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        message = "네트웍 타임아웃";
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        message = "찾을 수 없음";
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        message = "RECOGNIZER 가 바쁨";
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        message = "서버가 이상함";
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        message = "말하는 시간초과";
                        break;
                    default:
                        message = "알 수 없는 오류임";
                        break;
                }
                Toast.makeText(getApplicationContext(), "에러 : " + message,Toast.LENGTH_SHORT).show();
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