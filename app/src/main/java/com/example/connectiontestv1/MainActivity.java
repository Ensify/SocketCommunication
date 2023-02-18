package com.example.connectiontestv1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText etIP, etPort;
    EditText etMessage;
    Button btnSend;
    TextView connectionStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        Button btnConnect = findViewById(R.id.btnConnect);
        connectionStatus = findViewById(R.id.ctnStatus);
        btnConnect.setOnClickListener(view -> {
            String SERVER_IP = etIP.getText().toString().trim();
            int SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());
            new ConnectionThread(SERVER_IP,SERVER_PORT).start();

        });
        btnSend.setOnClickListener(view -> {
            String message = etMessage.getText().toString().trim();
            new SendMessageThread(message).start();
        });
    }
    private PrintWriter socket_out;
    class ConnectionThread extends Thread{
        private final String SERVER_IP;
        private final int SERVER_PORT;
        ConnectionThread(String ip,int port){
            this.SERVER_IP = ip;
            this.SERVER_PORT = port;
        }
        @Override
        public void run(){
            try{
                Socket s = new Socket(SERVER_IP,SERVER_PORT);
                socket_out = new PrintWriter(s.getOutputStream());
                String msg = "Connected to" + SERVER_IP + ":" + SERVER_PORT;
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    connectionStatus.setText(msg);
                });
            }
            catch(Exception e){
                String msg = "Not Connected";
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Not valid details.", Toast.LENGTH_SHORT).show();
                    connectionStatus.setText(msg);
                });
                e.printStackTrace();
            }
        }
    }
    class SendMessageThread extends Thread{
        private final String msg;
        SendMessageThread(String msg){
            this.msg = msg;
        }
        @Override
        public void run(){
            socket_out.write(msg);
            socket_out.flush();
        }
    }
}