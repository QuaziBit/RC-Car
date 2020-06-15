package com.demo.rccar.rc_gui_demo_2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyBluetooth
{
    private boolean isConnected = false;
    private BluetoothSocket socket_tmp = null;

    private BluetoothDevice bluetoothDevice = null;
    private BluetoothAdapter bluetoothAdapter = null;
    private List<BluetoothDevice> bluetoothDevices = new ArrayList<BluetoothDevice>();

    private OutputStream outputStream = null;
    private InputStream inStream = null;

    public MyBluetooth()
    {

    }

    public int findBluetoothAdapter()
    {
        int option = 0;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null)
        {
            option = 1;
        }
        if(!bluetoothAdapter.isEnabled())
        {
            option = 2;
        }
        if(bluetoothAdapter.isEnabled())
        {
            option = 3;
        }

        return option;
    }

    public int findPairedDevices()
    {
        int option = 0;

        Set<BluetoothDevice> pairedDevices;
        pairedDevices = bluetoothAdapter.getBondedDevices();

        //Find paired bluetooth devises
        if (pairedDevices.size() > 0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                //bdNames.add(bt.getName() + ": " + bt.getAddress());

                if(bt.getName().equals("HC-06") || bt.getAddress().equals("98:D3:32:30:EB:46") || bt.getAddress().equals("20:17:03:15:41:57"))
                {
                    bluetoothDevice = bt;
                    bluetoothDevices.add(bt);

                    Log.d("[?]", "Bluetooth device found.");

                    option = 1;
                }
            }
        }
        else
        {
            //No Paired Bluetooth Devices Found.
            option = 2;
        }

        return option;
    }

    public void connect()
    {
        try
        {
            if(!isConnected)
            {
                init();
            }
            else if(socket_tmp != null && isConnected)
            {
                socket_tmp.close();
                socket_tmp = null;

                isConnected = false;
                String msg = "Connection closed.";
                Log.d("[CONNECTION-TERMINATED]", msg);
            }

            /*
            String msg = "Bluetooth devise is successfully connected.";
            Log.d("[0]:[CONNECT]", msg);
            */
        }
        catch (IOException e)
        {
            String msg = "Cannot connect to the bluetooth devise.";
            Log.d("[1]:[ERROR-CONNECT]", msg, e);
        }
    }

    private void init() throws IOException
    {
        BluetoothDevice device = bluetoothDevice;
        ParcelUuid[] uuids = device.getUuids();

        BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
        socket.connect();

        if(socket.isConnected())
        {
            socket_tmp = socket;
            outputStream = socket.getOutputStream();
            inStream = socket.getInputStream();

            isConnected = true;
            String msg = "Bluetooth devise is successfully connected.";
            Log.d("[0]:[CONNECT]", msg);
        }
    }


    public void write(String val, String arg) throws IOException
    {
        String str = null;
        if(val != null && !arg.equals("set_speed"))
        {
            str = new String(val.getBytes());
        }
        else if(val != null && arg.equals("set_speed"))
        {
            str = new String(val.getBytes());
        }

        try
        {
            if(str != null)
            {
                outputStream.write(val.getBytes());
                Log.d("[WRITE-BYTES]", "DATA: [" + str + "] ARG: [" + arg + "]");
            }
        }
        catch (Exception e)
        {
            Log.d("[ERROR-WRITE-BYTES]", "[" + str + "]");
        }
    }

    public BluetoothDevice getBluetoothDevice()
    {
        return bluetoothDevice;
    }
}
