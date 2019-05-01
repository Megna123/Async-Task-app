/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.lifecycle.util.StatusTracker;
import com.example.android.lifecycle.util.Utils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;

import org.w3c.dom.Text;

import java.util.Random;

/**
 * Example Activity to demonstrate the Async Task.
 */
public class ActivityA extends Activity {

    private String mActivityName;
    private TextView mCounterView;
    TextView temp,humid,activity,number;
    TextView output;
    Button generate;
    Integer temperatureValue, humidValue, activityValue, param;
    String outputEntry = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        mActivityName = getString(R.string.activity_a);
        temp = (TextView) findViewById(R.id.edittemp);
        humid = (TextView) findViewById(R.id.humidityurl);
        activity = (TextView) findViewById(R.id.activityurl);
        number = (EditText) findViewById(R.id.editnumber);
        output = (TextView) findViewById(R.id.output);
        generate = (Button)  findViewById(R.id.generatebtn);
        generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                param = Integer.parseInt(number.getText().toString());
                outputEntry="";
                new testAsyncTask().execute(param);
            }
        });

    }

    private class testAsyncTask extends AsyncTask<Integer, Integer, Void> {
        Integer count = 1;
        Random random=new Random();
        @Override
        protected Void doInBackground(Integer... param) {
            while(count <= param[0]){
                temperatureValue = random.nextInt(100-25+1) + 25;
                humidValue = random.nextInt(100-40+1) + 40;
                activityValue = random.nextInt(500) + 1;
                publishProgress(count);
                count++;
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("InterruptedException", "doInBackground: "+e);
                }
            }
            return null;
        }
        protected void onProgressUpdate(Integer... param) {
            super.onProgressUpdate();
            String result = "Output " + param[0].toString()+ ":\n" +"    Temperature:" + temperatureValue.toString() + "F\n" +
                    "    Humidity:" + humidValue.toString() + "%\n" + "    Activity:"+ activityValue.toString()+ "\n" ;
            outputEntry=outputEntry+result;
            Log.d("Output", "onProgressUpdate: "+outputEntry);
            temp.setText(String.valueOf(temperatureValue)+"F");
            humid.setText(String.valueOf(humidValue)+"%");
            activity.setText(String.valueOf(activityValue));
            output.setMovementMethod(new ScrollingMovementMethod());
            output.setText(outputEntry);
        }
    }


    public void startDialog(View v) {
        ActivityA.this.finish();
    }

}
