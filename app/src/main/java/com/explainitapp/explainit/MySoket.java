//package com.explainitapp.explainit;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
//import com.explainitapp.explainit.models.ListObject;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.List;
//
//import io.socket.client.IO;
//import io.socket.client.Socket;
//
//public class MySoket extends AppCompatActivity {
//    private Socket mSocket;
//    private List<ListObject> mMessages = new ArrayList<ListObject>();
//    {
//        try {
//            mSocket = IO.socket("https://admin.explainitapp.com/dev");
//        } catch (URISyntaxException e) {
//        }
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        mSocket.on("appStarted", onNewMessage);
//        mSocket.on("getObjectList", onNewMessage);
//        mSocket.connect();
//    }
//    @Override
//    public void call(final Object... args) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                JSONObject data = (JSONObject) args[0];
//                String username;
//                String message;
//                try {
//                    username = data.getString("username");
//                    message = data.getString("message");
//                } catch (JSONException e) {
//                    return;
//                }
//
//                // add the message to view
//                addMessage(username, message);
//            }
//        });
//    }
//}
