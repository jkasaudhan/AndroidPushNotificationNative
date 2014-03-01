package com.example.helloandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.MailTo;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class GCMBroadcastReceiver extends BroadcastReceiver{
	private int notificationID = 20;
	private String urlToSaveUserInfo = "";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		//check if received messege is registration related information or message for users
		if("com.google.android.c2dm.intent.REGISTRATION".equals(action)){
			
			Log.w("C2DM","Receieved registration_id ");
			final String registrationID = intent.getStringExtra("registration_id");
			String error = intent.getStringExtra("error");
			Log.d("C2DM","registration_id =" + registrationID +",error =" + error);
			//sendRegistrationIDTOServer("", registrationID);
	    	//code to register user's data and registration ID to our server
	    	 CustomHttpClient task = new CustomHttpClient();
	    	 String url = "http://192.168.1.157/gcmserver/register.php?name=abcd&email=a@a.de&regID=" + registrationID;
	         task.execute(new String[] { url });
			
		}else if("com.google.android.c2dm.intent.RECEIVE".equals(action)){
			
			Log.w("C2DM","Received message");
			String message = intent.getStringExtra("message");
			Log.w("C2DM",message);
			createNotification("Notification Title",message,context);
		
			
		}
		
	}
	
	   public void createNotification(String title,String message,Context con)
	   {
		   NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(con)
			        .setSmallIcon(R.drawable.ic_launcher)
			        .setContentTitle(title)
			        .setContentText(message)
			        .setAutoCancel(true); // use setAutoCancel(true) here rather than declaring at last  because it don't work at all.
			// Creates an explicit intent for an Activity in your app
			Intent resultIntent = new Intent(con,MainActivity.class);

			// The stack builder object will contain an artificial back stack for the
			// started Activity.
			// This ensures that navigating backward from the Activity leads out of
			// your application to the Home screen.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(con);
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(MainActivity.class);
			// Adds the Intent that starts the Activity to the top of the stack
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent =
			        stackBuilder.getPendingIntent(
			            0,
			            PendingIntent.FLAG_UPDATE_CURRENT  //PendingIntent.FLAG_UPDATE_CURRENT updates the current message of notification if it already exist.
			        );
			mBuilder.setContentIntent(resultPendingIntent);
			NotificationManager mNotificationManager =(NotificationManager) con.getSystemService(android.content.Context.NOTIFICATION_SERVICE.toString());
			// mId allows you to update the notification later on.
			mNotificationManager.notify(notificationID, mBuilder.build());
	   }
	   
	   //below code has not been used yet but can be used further to cancel notification from notification bar
	   public void clearNotification(Context con) {
		    NotificationManager notificationManager = (NotificationManager) con
		            .getSystemService(Context.NOTIFICATION_SERVICE);
		    notificationManager.cancel(notificationID);
		}

}
