 package com.gi.programing_quiz.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.gi.programing_quiz.R;
import com.gi.programing_quiz.SharedPrefrence.SharedPre;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


 public class MyFireBaseMessagingService extends FirebaseMessagingService {

     String title, message,mobile;
     public String TAG = "mylog";
     String CHANNEL_ID = "my_notification";
     SharedPre sharedPre;

     @Override
     public void onNewToken(String token) {
         Log.d(TAG, "Refreshed token: " + token);

         sharedPre = new SharedPre(this);
         sharedPre.writeData("token",token);
         /*APIService apiService1;
         apiService1 = Client_wamp.getClient("https://gisurat.co.in/Water/AndroidNotification/").create(APIService.class);

         mobile = "9586033791";

         apiService1.insertKey(token,mobile).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d(TAG, response.toString());
             }

             @Override
             public void onFailure(Call<String> call, Throwable t) {
                 Log.d(TAG, t.toString());
             }
         });*/
     }
     @Override
     public void onMessageReceived(RemoteMessage remoteMessage) {
         Log.d(TAG,"message received");
         Log.d(TAG,remoteMessage.getData().toString());
         title = remoteMessage.getData().get("Title");
         message = remoteMessage.getData().get("Message");
         notification();
     }

     void notification() {
         createNotificationChannel();


         NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                 .setSmallIcon(R.drawable.noti)
                 .setContentTitle(title)
                 .setContentText(message)
                 .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                 .setAutoCancel(true);

         NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
         notificationManager.notify(404, builder.build());
     }

     private void createNotificationChannel() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             CharSequence name = "my notification";
             String description = "general notifications";
             int importance = NotificationManager.IMPORTANCE_DEFAULT;
             NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
             channel.setDescription(description);
             NotificationManager notificationManager = getSystemService(NotificationManager.class);
             notificationManager.createNotificationChannel(channel);
         }
     }
 }