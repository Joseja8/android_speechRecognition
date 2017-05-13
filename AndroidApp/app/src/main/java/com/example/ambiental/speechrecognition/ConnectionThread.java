package com.example.ambiental.speechrecognition;

import android.renderscript.Int2;
import android.util.Log;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static com.example.ambiental.speechrecognition.MainActivity.TAG;

/**
 * @autor: Joaquín Ramírez Guerra
 * @description: Class with parametres to create a socket and connect it with server
 */

public class ConnectionThread {

    public static String host;
    public static int port;
    public static Socket socket;
    public static BufferedWriter bufferedW;

    // Default constructor
    public ConnectionThread() {

    }

    //Parametrized constructor
    public ConnectionThread(String h, String p ) {
        host = h;
        port = Integer.parseInt(p);
        Log.d(TAG,"En el constructor de ConnectionThread.");
    }

    // Function to connect with server
    public void connect(){
        try {
            socket = new Socket(host, port); //connect to server
            bufferedW = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error to connect: " + e.getMessage() );
        }
        Log.d(TAG, "Connection succesful!");
    }

    // Function to shut down socket and close connection with server
    public void closeConnection(){
        try {
            socket.close();   //closing the connection
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Connection closed. :( ");
        }
    }

    // Function to check socket connection
    public boolean isConnect(){

        if (socket.isConnected()){
            return true;
        }
        return false;
    }
}
