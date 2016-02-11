package com.example.mateusz.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> listAdapter ;
    Button connectNew ;
    ListView listView ;
    BluetoothAdapter btAdapter ;
    Set<BluetoothDevice> devicesArray ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (btAdapter == null) {
            Toast.makeText(getApplicationContext(), "No bluetooth detected", 0).show();
            finish();
        }
        else if(!btAdapter.isEnabled()) {
            Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE) ;
            startActivityForResult(intent,1);
        }
        getPairedDevices () ;
    }
    private void getPairedDevices(){
        devicesArray=btAdapter.getBondedDevices() ;
        if (devicesArray.size() > 0) {
            for(BluetoothDevice device:devicesArray){
                listAdapter.add(device.getName()+"\n"+device.getAddress());
                
            }
        }
    }
    private void  init(){
        connectNew=(Button)findViewById(R.id.button);
        listView=(ListView)findViewById(R.id.listView) ;
        listAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,0) ;

        listView.setAdapter(listAdapter);
        btAdapter=BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_CANCELED){
            Toast.makeText(getApplicationContext(),"Bluetooth must be enabled to continue",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
