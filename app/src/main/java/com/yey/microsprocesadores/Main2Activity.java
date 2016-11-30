package com.yey.microsprocesadores;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class Main2Activity extends AppCompatActivity {
    Button btnPaired;
    ListView devicelist;
    private BluetoothAdapter myBluetooth = null;
    //private Set pairedDevices;
    private Set<BluetoothDevice> pairedDevices;
;    public static final String EXTRA_ADDRESS = "address";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnPaired = (Button) findViewById(R.id.button);
        devicelist = (ListView) findViewById(R.id.listView);


        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if(myBluetooth == null){
            Toast.makeText(getApplicationContext(),"Dispositivos Bluetooth no disponibles ",Toast.LENGTH_SHORT).show();
        }
        else{
            if(myBluetooth.isEnabled()){}
            else{
                Intent turnBton = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBton,1);
            }
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList();
            }
        });


    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView av, View v, int arg2, long arg3){
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length()-17);
            Intent i = new Intent(Main2Activity.this, MainActivity.class);
            i.putExtra(EXTRA_ADDRESS, address);
            startActivity(i);
        }

    };

    private void pairedDevicesList(){
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();
        if(pairedDevices.size()>0){
            for(BluetoothDevice bt: pairedDevices){
                list.add(bt.getName()+"\n"+bt.getAddress());
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Sin dispostivos",Toast.LENGTH_SHORT).show();
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener);

    }




}
