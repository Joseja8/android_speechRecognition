package com.example.ambiental.speechrecognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Debug";
    public static ConnectionThread thread;

    public Button connectButton;
    public EditText ipEditText;
    public EditText portEditText;

    public String host = "";
    public String port = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = (Button) findViewById(R.id.connectButton);
        ipEditText = (EditText) findViewById(R.id.ipEditText);
        portEditText = (EditText) findViewById(R.id.portEditText);

        thread = new ConnectionThread();
        //host += "192.168.1.7";
        //port = 4444;
        Log.d(TAG,"Waiting to connect to: "+host);
    }

    // Function to connect with server socket after enter ip and port values and push connectButton
    public void connect(final View v) {

        new Thread() {
            @Override
            public void run() {
                host = ipEditText.getText().toString();
                port = portEditText.getText().toString();
                if( !host.isEmpty() && !port.isEmpty() ){
                    thread = new ConnectionThread(host, port);
                    Log.d(TAG,"Thread iniciado.");
                    thread.connect();
                    Log.d(TAG,"Thread conectado");

                    if (thread.isConnect()) {
                        Intent intent = new Intent(v.getContext(), TalkActivity.class);
                        startActivityForResult(intent,0);
                    }else{
                        Log.d(TAG,"Without connection.");
                    }
                }else{
                    //Pendiente
                }
            }

//        new Thread() {
//            @Override
//            public void run() {
//                Socket client = null;
//                Log.d(TAG, "Try to connect with server.");
//                try {
//                    client = new Socket(host, 4444); //connect to server
//                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//                    bw.write("Hello server!!");
//                    bw.newLine();
//                    bw.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.d(TAG, "Error to connect: " + e.getMessage() );
//                }
//                Log.d(TAG, "Connection succesful! =D");
//
//                try {
//                    client.close();   //closing the connection
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.d(TAG, "Connection closed. :( ");
//                }
//            }
//        }.start();
        }.start();
    }

    /*
    protected void connectedToBlueetoth(BluetoothDevice connectedDevice, String msg) throws IOException {
        BluetoothSocket socket = connectedDevice.createRfcommSocketToServiceRecord(device_UUID);
        socket.connect();
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        dos.writeBytes(msg);
        //dos.write(msg);
        socket.close();

    }
    */
}
