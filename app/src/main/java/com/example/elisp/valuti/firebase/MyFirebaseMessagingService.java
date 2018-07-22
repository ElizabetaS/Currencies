package com.example.elisp.valuti.firebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG ="MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String notificationBody="body";
        String notificationText="";
        if(remoteMessage.getData().containsKey("vesti"))
        {
            notificationText = remoteMessage.getData().get("vesti");
        }
        Log.d("Notification", notificationText);
        if(remoteMessage.getNotification() != null)
        {
            notificationBody = remoteMessage.getNotification().getBody();
        }
        Intent intent = new Intent ("android.intent.action.FIREBASE").putExtra("notification",notificationText).
                putExtra("notificationBody",notificationBody);
        this.sendBroadcast(intent);
        Log.d("remotemessage", notificationBody);
    }
}
