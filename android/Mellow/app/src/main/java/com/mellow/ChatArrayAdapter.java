package com.mellow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mellow.mellow.R;
import com.mellow.model.Chat;
import com.mellow.model.Post;

import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter<Chat> {

    private List<Chat> chats;
    private TextView username;
    private TextView messageContent;

    public ChatArrayAdapter(Context context, List<Chat> chats) {
        super(context, R.layout.activity_chat_adapter, chats);
        this.chats=chats;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.activity_chat_adapter, parent, false);
        initializePalettes(customView);

        Chat chat = chats.get(position);
        messageContent.setText(chat.getMessages().get(position).getContent());
        username.setText(chat.getUsers().get(position).getUsername());
        return customView;
    }

    private void initializePalettes(View view){
        username = (TextView) view.findViewById(R.id.firstname);
        messageContent = (TextView) view.findViewById(R.id.message_text);
    }
}
