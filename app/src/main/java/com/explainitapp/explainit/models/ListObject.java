package com.explainitapp.explainit.models;

/**
 * Created by rikmen00@gmail.com on 28.05.2019.
 */


public class ListObject {

    public static final int TYPE_MESSAGE = 0;

    private String mObligatoryIds;

    private ListObject() {
    }

    public String getMessage() {
        return mObligatoryIds;
    }

    public static class Builder {
        private String mObligatoryIds;



        public ListObject.Builder username(String username) {
            mObligatoryIds = username;
            return this;
        }

        public ListObject build() {
            ListObject message = new ListObject();
            message.mObligatoryIds = mObligatoryIds;
            return message;
        }
    }
}