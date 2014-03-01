package com.example.helloandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	EditText msg;
	TextView registrationView;  
	ListView msgList;
	List<String> regMessages ;
	ArrayAdapter<String> adapter;
	GCMBroadcastReceiver gcmr;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   
        setContentView(R.layout.form);
        msg = (EditText)findViewById(R.id.editText1);
        regMessages = new ArrayList<String>();
        msgList = (ListView) findViewById(R.id.listView1);
     
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //on tweet button pressed
   public void send(View v){
    	//display message on display board and clear the text input area.
    	regMessages.add(msg.getText().toString());
    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,regMessages);
    	msgList.setAdapter(adapter);
    	msg.setText("");
    	Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");

    	registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));

    	//Sender id = 941079637736 this is prject id receieved after creating project on google console cloud.google.com/console/project
    	registrationIntent.putExtra("sender", "941079637736"); 
    	startService(registrationIntent);
  	    	
    }
}
