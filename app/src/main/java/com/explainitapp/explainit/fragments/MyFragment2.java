package com.explainitapp.explainit.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.explainitapp.explainit.MyApplication;
import com.explainitapp.explainit.R;
import com.explainitapp.explainit.StartActivity;
import com.explainitapp.explainit.models.ObjectList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * A chat fragment containing messages view and input form.
 */
public class MyFragment2 extends Fragment {

    private static final String TAG = "MainFragment";

    private static final int REQUEST_LOGIN = 0;

    private String mUsername;
    private Socket mSocket;

    private Boolean isConnected = true;
    private TextView mTextview;

    public MyFragment2() {
        super();
    }


    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      //  mAdapter = new ListObjectAdapter(context, mMessages);
      //  if (context instanceof Activity){
            //this.listener = (MainActivity) context;
     //   }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            setHasOptionsMenu(true);

            MyApplication app = (MyApplication) getActivity().getApplication();
            mSocket = app.getSocket();
            mSocket.on(Socket.EVENT_CONNECT,onConnect);
            mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

            mSocket.connect();

            startSignIn();
            test();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.my_layout, container, false);

    }
    private void test() {
        String[] obligatoryIds = {"test0", "test1", "test2"};
        JSONObject createUser = new JSONObject();
        JSONObject createUser2 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("s4");
        jsonArray.put("ss2");
        jsonArray.put("ss");

        try {
            createUser.put("name3", jsonArray);
            createUser.put("username", 1);
            createUser.put("email", "test2");
            createUser.put("birthdate", "datanasc");
            createUser2 = createUser;
            createUser.put("createUser", createUser2);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (!mSocket.connected()) return;
        mSocket.emit("echo", createUser, new Ack() {
            @Override
            public void call(final Object... args) {
                        JSONObject data = (JSONObject) args[0];
                try {
                    String s = data.getString("email");

                Toast.makeText(getActivity().getApplicationContext(),
                                "args = " + data, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    }
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
        mSocket.disconnect();

        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      //  attemptSend();
    }


    private void attemptSend() {
        String obligatoryIds = "obligatoryIds";
        if (null == obligatoryIds) return;
        if (!mSocket.connected()) return;

      //  addMessage(obligatoryIds, "");
        ObjectList objectList = new ObjectList();
        String[] myPar = new String[1];
        myPar[0] = "DB0B2725-608C-4511-BE99-752083C5BF62";
        objectList.getObligatoryIds().add(0, myPar);
        // perform the sending message attempt.
        mSocket.emit("getObjectList",objectList);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK != resultCode) {
            getActivity().finish();
            return;
        }

        mUsername = data.getStringExtra("username");
        int numUsers = data.getIntExtra("numUsers", 1);



    }

    private void startSignIn() {
        mUsername = null;
        Intent intent = new Intent(getActivity(), StartActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isConnected) {
                        if(null!=mUsername)
                            mSocket.emit("add user", mUsername);
                        Toast.makeText(getActivity().getApplicationContext(),
                                R.string.connect, Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "diconnected");
                    isConnected = false;
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.disconnect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Error connecting");
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };
}

