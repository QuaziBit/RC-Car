package com.demo.rccar.rc_gui_demo_2;

import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private MyBluetooth myBluetooth = null;

    private Spinner bluetoothDevicesList = null;
    private List<String> bdNames = null;

    private Button go_button = null;
    private Button back_button = null;
    private Button stop_button = null;
    private Button left_button = null;
    private Button right_button = null;
    private Button setSpeed_button = null;
    private Button start_button = null;
    private Button end_button = null;
    private SeekBar seekBar = null;

    //Only for testing
    private TextView test_inputs = null;

    private boolean goPressed = false;
    private boolean backPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();
        initializeSeekBar();
        initializeSpinner();
        initializeTests();

        myBluetooth = new MyBluetooth();
        initializeBluetooth();
    }

    public void initializeBluetooth()
    {
        int option_1 = myBluetooth.findBluetoothAdapter();
        int option_2 = 0;

        if(option_1 == 1)
        {
            String msg = "No bluetooth adapter in your devise.";
            Log.d("[0]", msg);

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            //finish();
        }
        else if(option_1 == 2)
        {
            String msg = "Bluetooth adapter in turned OFF.";
            Log.d("[1]", msg);

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            finish();

            //If bluetooth adapter is of ask the user to turn it on
            Intent turnBluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBluetoothOn,1);
        }
        else if(option_1 == 3)
        {
            option_2 = myBluetooth.findPairedDevices();

            String msg = "Bluetooth adapter in turned ON.";
            Log.d("[2]", msg);

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            //finish();
        }


        if(option_2 == 1)
        {
            updateSpinner();

            String msg = "Paired Bluetooth Devices Found.";
            Log.d("[3]", msg);

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            //finish();
        }
        else
        {
            String msg = "No Paired Bluetooth Devices Found.";
            Log.d("[4]", msg);

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            //finish();
        }
    }

    //Add action ActionListener for the dropdown menu of the bluetooth devises
    //============================================================================//
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        String selected = parent.getItemAtPosition(pos).toString();
        Log.d("TEST", "Item Selected: " + selected);


        if(selected.equals("[HC-06]: [98:D3:32:30:EB:46]") || selected.equals("[HC-06]: [20:17:03:15:41:57]"))
        {
            //myBluetooth.connect();

            start_button.setEnabled(true);
        }
        else
        {
            // The bluetooth device will be disconnected
            //myBluetooth.connect();

            start_button.setEnabled(false);
            end_button.setEnabled(false);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        Log.d("TEST", "Item Uns-Selected");
    }
    //============================================================================//

    private void initializeButtons()
    {
        go_button = (Button) findViewById(R.id.go_button);
        go_button.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    goPressed = true;
                    go();
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    goPressed = false;
                    go();
                }
                return true;
            }
        });

        back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    backPressed = true;
                    back();
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    backPressed = false;
                    back();
                }
                return true;
            }
        });

        stop_button = (Button) findViewById(R.id.stop_button);

        left_button = (Button) findViewById(R.id.left_button);
        left_button.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    leftPressed = true;
                    left();
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    leftPressed = false;
                    left();
                }
                return true;
            }
        });

        right_button = (Button) findViewById(R.id.right_button);
        right_button.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    rightPressed = true;
                    right();
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    rightPressed = false;
                    right();
                }
                return true;
            }
        });

        setSpeed_button = (Button) findViewById(R.id.setSpeed_button);
        start_button = (Button) findViewById(R.id.start_button);
        end_button = (Button) findViewById(R.id.end_button);

        start_button.setEnabled(false);
        end_button.setEnabled(false);
    }

    private void initializeSeekBar()
    {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
    }

    private void initializeTests()
    {
        test_inputs = (TextView) findViewById(R.id.textView5);
    }

    private void initializeSpinner()
    {
        bluetoothDevicesList = (Spinner) findViewById(R.id.bluetooth_devices);
        bdNames = new ArrayList<String>();
        bdNames.add("Select bluetooth device");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bdNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bluetoothDevicesList.setAdapter(dataAdapter);
        bluetoothDevicesList.setOnItemSelectedListener(this);
    }

    private void updateSpinner()
    {
        String deviceName = myBluetooth.getBluetoothDevice().getName();
        String deviceAddress = myBluetooth.getBluetoothDevice().getAddress();
        bdNames.add("[" + deviceName + "]: " + "[" + deviceAddress + "]");

        //Reinitialize bluetoothDevicesList
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bdNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bluetoothDevicesList.setAdapter(dataAdapter);

    }

    private void sendData(String data)
    {
        String arg = "";

        if(data.equals("go"))
        {
            try
            {
                myBluetooth.write("g", arg);
            }
            catch (IOException e)
            {
                String msg = "Cannot send data via Bluetooth";
                Log.d("[0]:[ERROR-SEND_DATA]", msg, e);
            }
        }
        if(data.equals("back"))
        {
            try
            {
                myBluetooth.write("b", arg);
            }
            catch (IOException e)
            {
                String msg = "Cannot send data via Bluetooth";
                Log.d("[1]:[ERROR-SEND_DATA]", msg, e);
            }
        }
        if(data.equals("stop"))
        {
            try
            {
                myBluetooth.write("s", arg);
            }
            catch (IOException e)
            {
                String msg = "Cannot send data via Bluetooth";
                Log.d("[2]:[ERROR-SEND_DATA]", msg, e);
            }
        }
        if(data.equals("left"))
        {
            try
            {
                myBluetooth.write("l", arg);
            }
            catch (IOException e)
            {
                String msg = "Cannot send data via Bluetooth";
                Log.d("[3]:[ERROR-SEND_DATA]", msg, e);
            }
        }
        if(data.equals("right"))
        {
            try
            {
                myBluetooth.write("r", arg);
            }
            catch (IOException e)
            {
                String msg = "Cannot send data via Bluetooth";
                Log.d("[4]:[ERROR-SEND_DATA]", msg, e);
            }
        }

        if(data.equals("set_speed"))
        {
            try
            {
                arg = "set_speed";
                String speed = seekBar.getProgress()+ "";
                myBluetooth.write(speed, arg);
            }
            catch (IOException e)
            {
                String msg = "Cannot send data via Bluetooth";
                Log.d("[5]:[ERROR-SEND_DATA]", msg, e);
            }
        }
        if(data.equals("start"))
        {
            myBluetooth.connect();
            end_button.setEnabled(true);
        }
        if(data.equals("end"))
        {
            myBluetooth.connect();
            end_button.setEnabled(false);
            start_button.setEnabled(true);
        }
    }


    public void go(View view)
    {
        Log.d("[GO]", "Go");
        sendData("go");
    }
    public void go()
    {
        if(goPressed)
        {
            Log.d("[GO]", "Go");
            sendData("go");
        }
        else
        {
            Log.d("[BUTTON]", "go un-pressed");
            stop();
        }
    }

    public void back(View view)
    {
        Log.d("[BACK]", "Back");
        sendData("back");
    }
    public void back()
    {
        if(backPressed)
        {
            Log.d("[BACK]", "Back");
            sendData("back");
        }
        else
        {
            Log.d("[BUTTON]", "back un-pressed");
            stop();
        }
    }

    public void stop(View view)
    {
        Log.d("[STOP]", "Stop");
        sendData("stop");
    }
    public void stop()
    {
        Log.d("[STOP]", "Stop");
        sendData("stop");
    }

    public void left(View view)
    {
        Log.d("[LEFT]", "Left");
        sendData("left");
    }
    public void left()
    {
        if(leftPressed)
        {
            Log.d("[LEFT]", "Left");
            sendData("left");
        }
        else
        {
            Log.d("[BUTTON]", "left un-pressed");
            stop();
        }
    }

    public void right(View view)
    {
        Log.d("[RIGHT]", "Right");
        sendData("right");
    }
    public void right()
    {
        if(rightPressed)
        {
            Log.d("[RIGHT]", "Right");
            sendData("right");
        }
        else
        {
            Log.d("[BUTTON]", "right un-pressed");
            stop();
        }
    }

    public void setSpeed(View view)
    {
        test_inputs.setText(seekBar.getProgress() + "");
        Log.d("[SET-SPEED]", "Set Speed");
        sendData("set_speed");
    }

    public void start(View view)
    {
        Log.d("[START]", "Start");
        sendData("start");
    }

    public void end(View view)
    {
        Log.d("[END]", "End");
        sendData("end");
    }


    /*
    public void set_speed(View view)
    {
        test_inputs.setText(seekBar.getProgress());
    }
    */

    /*
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //unregisterReceiver();
    }
    */
}
