package com.example.unityplg;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;



import com.unity3d.player.UnityPlayer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class UnityPluginTest extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    String STT;
    Intent intent;
    RecognitionListener recognitionListener;
    SpeechRecognizer speechRecognizer;

    TextToSpeech TTS;

    Activity UnityActivity;


    BluetoothAdapter bluetoothAdapter;
    private static UnityPluginTest m_instance;
    Set<BluetoothDevice> bluetoothDevices;
    List<String> list;
    BluetoothDevice pairedBluetoothDevice;
    BluetoothSocket bluetoothSocket;
    String readMessage;
    OutputStream BluetoothOutput;

    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    final static int BT_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int STT_START = 3;
    ConnectThread connectThread;
    private Context context;


    Boolean testLamp_On = false;



    private void bluetoothIntent() {

        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        context.startActivity(intent);

    }

    private void sendBluetoothData(String data) {
        ShowToast("데이터 전송중");
        if (bluetoothAdapter.isEnabled() && bluetoothSocket.isConnected())
            connectThread.write(data);
        else
            ShowToast("Bluetooth 연결을 확인해 주세요");
    }


    private void setBluetoothAdapter() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null)
            ShowToast("Bluetooth를 지원하지 않는 장치입니다");
    }

    private boolean checkBluetooth() {
        return bluetoothAdapter.isEnabled();
    }

    private void socketDisconnect() {
        //아두이노 블루투스 연결 끊기 신호 전달 (초기화를 위해)

        if (bluetoothSocket.isConnected()) {
            connectThread.disconnectSocket();
        }
    }

    void pairing() {
        if (bluetoothAdapter.isEnabled()) {
            bluetoothDevices = bluetoothAdapter.getBondedDevices();
            if (bluetoothDevices.size() > 0) {  // 페어링 목록이 1개 이상이라면
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("장치 목록");
                list = new ArrayList<String>();

                for (BluetoothDevice device : bluetoothDevices) {
                    list.add(device.getName());
                }

                final CharSequence[] nameList = list.toArray(new CharSequence[bluetoothDevices.size()]);

                builder.setItems(nameList, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(nameList[item].toString());

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            } else { // 페어링 가능 기기가 0개다
                Toast.makeText(context, "페어링 가능 기기가 없습니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            // 블루투스가 off  라면
            Toast.makeText(context, "블루투스가 꺼져있습니다.", Toast.LENGTH_LONG).show();
        }

    }


    private void connectSelectedDevice(String deviceName) {
        for (BluetoothDevice devices : bluetoothDevices) {
            if (deviceName.equals(devices.getName())) {
                pairedBluetoothDevice = devices;
            }
        }
        try {
            bluetoothSocket = pairedBluetoothDevice.createInsecureRfcommSocketToServiceRecord(BT_UUID);

            bluetoothSocket.connect();
            if (bluetoothSocket.isConnected()) {
                Toast.makeText(context, "연결 완료.", Toast.LENGTH_LONG).show();
            }
            // Thread 실행 부분

            connectThread = new ConnectThread(bluetoothSocket);
            connectThread.setDaemon(true);
            connectThread.start();
        } catch (Exception e) {
            Toast.makeText(context, "페어링 실패.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private class ConnectThread extends Thread {

        private BluetoothSocket socket;
        private InputStream input;
        private OutputStream output;

        public ConnectThread(BluetoothSocket socket) {

            try {
                this.socket = socket;
                input = socket.getInputStream();
                output = socket.getOutputStream();
                BluetoothOutput = output;
                write("S"); // 아두이노에 블루투스 통신 시작알림
            } catch (Exception e) {
                Toast.makeText(context, "소켓연결중 오류 발생", Toast.LENGTH_LONG).show();
            }

        }


        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            while (true) {
                try {
                    if (input.available() > 0) {
                        Thread.sleep(100);
                        bytes = input.available(); // 읽을 수 있는 바이트수

                        buffer = new byte[bytes];
                        bytes = input.read(buffer, 0, bytes); // buffer에 저장하고 읽은 바이트수 반환
                        String recv = new String(buffer);


                    }
                } catch (Exception e) {
                    Toast.makeText(context, "데이터 수신 중 오류발생", Toast.LENGTH_LONG).show();
                }
            }

        }

        public void write(String data) {
            if (socket != null) {
                byte[] buffer = new byte[1024];
                try {
                    Thread.sleep(100);
                    buffer = data.getBytes();
                    output.write(buffer);
                    output.flush();
                } catch (Exception e) {
                    Toast.makeText(context, "데이터 전송 중 오류 발생", Toast.LENGTH_LONG).show();
                }
            }
        }

        public void disconnectSocket() {
            try {
                socket.close();
                BluetoothOutput.close();
            } catch (Exception e) {
                Toast.makeText(context, "소켓 해제 중 오류 발생", Toast.LENGTH_LONG).show();
            }

        }
    }


    public boolean getSocketConnect() {
        return bluetoothSocket.isConnected();
    }

    public static UnityPluginTest instance() {
        if (m_instance == null) {
            m_instance = new UnityPluginTest();
        }
        return m_instance;
    }

    private void setContext(Context context) {
        this.context = context;
    }

    private void setActivity(Activity activity) {
        UnityActivity = activity;
    }

    private String getReadMessage() {
        return readMessage;
    }

    private void ShowToast(String toastStr) {
        Toast.makeText(context, toastStr, Toast.LENGTH_SHORT).show();
    }

    private void AndroidVersionCheck(String objName, String objMethod) {
        UnityPlayer.UnitySendMessage(objName, objMethod, "My Android Version: " + Build.VERSION.RELEASE);
    }

    private void makeAlertDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context)
                .setTitle("확인")
                .setMessage(str)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void BluetoothDialog(String str) {
        if (!bluetoothAdapter.isEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.context)
                    .setTitle("확인")
                    .setMessage(str)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bluetoothIntent();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else
            ShowToast("블루투스가 켜져있습니다");
    }

    private void BluetoothWrite(String str) {
        if (bluetoothSocket.isConnected()) {
            try {
                BluetoothOutput.write(str.getBytes());
            } catch (Exception e) {
                Toast.makeText(context, "데이터 전송 중 오류 발생", Toast.LENGTH_LONG).show();
            }
        }

    }


    void TTST_Init() {

    }

    void deleteTTS() {
        TTS.stop();
        TTS.shutdown();
        TTS = null;
    }

    void makeTTS(String str) {
        if (TTS == null) {
            TTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != -1)
                        TTS.setLanguage(Locale.KOREA);
                }
            });
        }
        TTS.setPitch(1f); // 톤설정
        TTS.setSpeechRate(1f);   // 말 속도 설정;
        TTS.speak(str, TextToSpeech.QUEUE_FLUSH, null);

    }

    void makeSTT() {

        STT = "";
        if (recognitionListener == null) {
            recognitionListener = new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP, 400);
                    Toast.makeText(context, "음성인식 시작.", Toast.LENGTH_SHORT).show();

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

                @Override
                public void onError(int error) {
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
                    Toast.makeText(context, "에러 : " + message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResults(Bundle results) {

                    ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    for (int i = 0; i < matches.size(); i++) {
                        STT = (matches.get(i));
                    }
                }


                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            };
        }


        if (intent == null) {
            intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        }
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
            speechRecognizer.setRecognitionListener(recognitionListener);
        }

        speechRecognizer.startListening(intent);


    }

    String getSTT() {
        return STT;
    }
}


