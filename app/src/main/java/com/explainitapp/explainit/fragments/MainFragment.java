//package com.explainitapp.explainit.fragments;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.EditorInfo;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.explainitapp.explainit.MyApplication;
//import com.explainitapp.explainit.R;
//import com.explainitapp.explainit.StartActivity;
//import com.explainitapp.explainit.adapters.ListObjectAdapter;
//import com.explainitapp.explainit.models.ListObject;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;
//
//
///**
// * A chat fragment containing messages view and input form.
// */
//public class MainFragment extends Fragment {
//
//    private static final String TAG = "MainFragment";
//
//    private static final int REQUEST_LOGIN = 0;
//
//    private static final int TYPING_TIMER_LENGTH = 600;
//
//    private RecyclerView mMessagesView;
//    private EditText mInputMessageView;
//    private List<ListObject> mMessages = new ArrayList<ListObject>();
//    private RecyclerView.Adapter mAdapter;
//    private boolean mTyping = false;
//    private String mUsername;
//    private Socket mSocket;
//
//    private Boolean isConnected = true;
//
//    public MainFragment() {
//        super();
//    }
//
//
//    // This event fires 1st, before creation of fragment or any views
//    // The onAttach method is called when the Fragment instance is associated with an Activity.
//    // This does not mean the Activity is fully initialized.
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mAdapter = new ListObjectAdapter(context, mMessages);
//        if (context instanceof Activity){
//            //this.listener = (MainActivity) context;
//        }
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setHasOptionsMenu(true);
//
//        MyApplication app = (MyApplication) getActivity().getApplication();
//        mSocket = app.getSocket();
//        mSocket.on(Socket.EVENT_CONNECT,onConnect);
//        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
//        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.on("new message", onNewMessage);
//
//        mSocket.connect();
//
//        startSignIn();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_main, container, false);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        mSocket.disconnect();
//
//        mSocket.off(Socket.EVENT_CONNECT, onConnect);
//        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
//        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.off("new message", onNewMessage);
//
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        mMessagesView = (RecyclerView) view.findViewById(R.id.messages);
//        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mMessagesView.setAdapter(mAdapter);
//
//        mInputMessageView = (EditText) view.findViewById(R.id.message_input);
//        mInputMessageView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
//                if (id == R.id.send || id == EditorInfo.IME_NULL) {
//                    attemptSend();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        ImageButton sendButton = (ImageButton) view.findViewById(R.id.send_button);
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                attemptSend();
//            }
//        });
//
//        //attemptSend();
//    }
//    private Emitter.Listener onNewMessage = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    String username;
//                    String message;
//                    try {
//                        username = data.getString("username");
//                        message = data.getString("message");
//                    } catch (JSONException e) {
//                        Log.e(TAG, e.getMessage());
//                        return;
//                    }
//                    Toast.makeText(getActivity(), "Name - " + username + "Message - " + message, Toast.LENGTH_SHORT).show();
//                    addMessage(username, message);
//
//                }
//            });
//        }
//    };
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (Activity.RESULT_OK != resultCode) {
//            getActivity().finish();
//            return;
//        }
//
//        mUsername = data.getStringExtra("username");
//        int numUsers = data.getIntExtra("numUsers", 1);
//
//
//
//    }
//
////    private void addMessage(String username, String message) {
////        mMessages.add(new ListObject.Builder(ListObject.TYPE_MESSAGE).username(username).message(message).build()
////        );
////        mAdapter.notifyItemInserted(mMessages.size() - 1);
////        scrollToBottom();
////    }
//
//    private void attemptSend() {
//        mUsername = "nameMy";
//      //  if (null == mUsername) return;
//        if (!mSocket.connected()) return;
//
//        mTyping = false;
//
//        String message = mInputMessageView.getText().toString().trim();
//        message = "my test message";
//        if (TextUtils.isEmpty(message)) {
//            mInputMessageView.requestFocus();
//            return;
//        }
//
//        mInputMessageView.setText("");
//        addMessage(mUsername, message);
//
//        // perform the sending message attempt.
//        mSocket.emit("new message", message);
//    }
//
//    private void startSignIn() {
//        mUsername = null;
//        Intent intent = new Intent(getActivity(), StartActivity.class);
//        startActivityForResult(intent, REQUEST_LOGIN);
//    }
//
//    private void scrollToBottom() {
//        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
//    }
//
//    private Emitter.Listener onConnect = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if(!isConnected) {
//                        if(null!=mUsername)
//                            mSocket.emit("add user", mUsername);
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                R.string.connect, Toast.LENGTH_LONG).show();
//                        isConnected = true;
//                    }
//                }
//            });
//        }
//    };
//
//    private Emitter.Listener onDisconnect = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Log.i(TAG, "diconnected");
//                    isConnected = false;
//                    Toast.makeText(getActivity().getApplicationContext(),
//                            R.string.disconnect, Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    };
//
//    private Emitter.Listener onConnectError = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Log.e(TAG, "Error connecting");
//                    Toast.makeText(getActivity().getApplicationContext(),
//                            R.string.error_connect, Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    };
//}
//
