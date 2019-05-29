package com.explainitapp.explainit;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
/**
 * Created by rikmen00@gmail.com on 28.05.2019.
 */


public class MyApplication  extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constants.EXPLAINITAPP_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
