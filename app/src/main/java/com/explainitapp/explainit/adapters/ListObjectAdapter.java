//package com.explainitapp.explainit.adapters;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.explainitapp.explainit.R;
//import com.explainitapp.explainit.models.ListObject;
//
//import java.util.List;
//
//
//public class ListObjectAdapter extends RecyclerView.Adapter<ListObjectAdapter.ViewHolder> {
//
//    private List<ListObject> mMessages;
//    private int[] mUsernameColors;
//
//    public ListObjectAdapter(Context context, List<ListObject> messages) {
//        mMessages = messages;
//
//        mUsernameColors = context.getResources().getIntArray(R.array.username_colors);
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        int layout = -1;
//        switch (viewType) {
//        case ListObject.TYPE_MESSAGE:
//            layout = R.layout.item_message;
//            break;
////        case ListObject.TYPE_LOG:
////            layout = R.layout.item_log;
////            break;
////        case ListObject.TYPE_ACTION:
////            layout = R.layout.item_action;
////            break;
//        }
//        View v = LayoutInflater
//                .from(parent.getContext())
//                .inflate(layout, parent, false);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        ListObject message = mMessages.get(position);
//        viewHolder.setMessage(message.getMessage());
//        viewHolder.setUsername(message.getUsername());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mMessages.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return mMessages.get(position).getType();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView mUsernameView;
//        private TextView mMessageView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            mUsernameView = (TextView) itemView.findViewById(R.id.username);
//            mMessageView = (TextView) itemView.findViewById(R.id.message);
//        }
//
//        public void setUsername(String username) {
//            if (null == mUsernameView) return;
//            mUsernameView.setText(username);
//            mUsernameView.setTextColor(mUsernameColors[0]);
//        }
//
//        public void setMessage(String message) {
//            if (null == mMessageView) return;
//            mMessageView.setText(message);
//        }
//
//    }
//}
