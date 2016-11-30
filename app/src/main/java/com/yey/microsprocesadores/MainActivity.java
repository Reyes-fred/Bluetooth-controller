package com.yey.microsprocesadores;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
Button atras, adelante, izquierda, derecha, detener,baja,media,alta;
    String address = null;
    BluetoothSocket btsocket=null;
    BluetoothAdapter myBluetooth = null;
    private boolean isBtConnected = false;

    static final UUID myUUID = UUID.fromString("00002A00-0000-1000-8000-00805F9B34FB");

    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent nuevo = getIntent();
        address = nuevo.getStringExtra(Main2Activity.EXTRA_ADDRESS);
        Log.e("Address",""+address);
        ConnectBT bt = new ConnectBT();
        bt.execute();

        atras = (Button) findViewById(R.id.atras);
        adelante = (Button) findViewById(R.id.adelante);
        izquierda = (Button) findViewById(R.id.izquierda);
        derecha = (Button) findViewById(R.id.derecha);
        detener = (Button) findViewById(R.id.detener);
        baja = (Button) findViewById(R.id.lenta);
        media = (Button) findViewById(R.id.media);
        alta = (Button) findViewById(R.id.alta);

        atras.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moverse("2");
            }
        });
        adelante.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moverse("1");
            }
        });
        izquierda.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moverse("3");
            }
        });
        derecha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moverse("4");
            }
        });

        detener.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moverse("5");
            }
        });

        baja.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moverse("6");
            }
        });

        media.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moverse("7");
            }
        });

        alta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moverse("8");
            }
        });


    }



    private  void moverse(String valor){
        if(btsocket!=null){
            try{
                btsocket.getOutputStream().write(valor.toString().getBytes());

            }
            catch (IOException e){
                msg("Error");
            }
        }
    }

private void msg(String s){
    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
}


    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private  boolean ConnectSuccess = true;

        @Override
        protected Void doInBackground(Void... devices) {
            try{
                if(btsocket == null || !isBtConnected){
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btsocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter();
                    btsocket.connect();
                }
            }
            catch (IOException e){
                ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected  void onPreExecute(){
            progress = ProgressDialog.show(MainActivity.this,"Conectando","");
        }

        protected  void onPostExecute(Void result){
            super.onPostExecute(result);
            if(!ConnectSuccess){
                msg("Conexion fallida");
                finish();
            }
            else{
                msg("Conectado");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }


}

