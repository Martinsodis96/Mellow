package com.mellow.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mellow.mellow.R;
import com.mellow.model.Message;

import java.util.List;
import java.util.Objects;

public class MessageArrayAdapter extends RecyclerView.Adapter<MessageArrayAdapter.ViewHolder> {

    private List<Message> messages;
    private Context context;
    private SharedPreferences sharedPreferences;

    public MessageArrayAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View message = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_adapter, parent, false);
        return new ViewHolder(message);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView messageTextView = holder.message;
        LinearLayout messageContainer = holder.messageContainer;
        if (messages != null && !messages.isEmpty()) {
            Message message = messages.get(position);
            messageTextView.setText(message.getContent());
            messageTextView.setPadding(10, 10, 10, 10);
            if (Objects.equals(message.getUsername(), sharedPreferences.getString("username", ""))) {
                styleMessageContainer(messageContainer, 20, R.color.ownMessageColor, Gravity.END);
            } else{
                styleMessageContainer(messageContainer, 20, R.color.messageColor, Gravity.START);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (messages != null && !messages.isEmpty())
            return messages.size();
        else
            return 0;
    }

    private void styleMessageContainer(LinearLayout messageContainer, Integer margin, Integer color, Integer gravity){
        messageContainer.setBackgroundColor(ContextCompat.getColor(context, color));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = gravity;
        params.setMargins(margin, margin, margin, margin);
        messageContainer.setLayoutParams(params);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private LinearLayout messageContainer;

        ViewHolder(View view) {
            super(view);
            this.message = view.findViewById(R.id.message_content);
            this.messageContainer = view.findViewById(R.id.message_content_container);
        }
    }
}
