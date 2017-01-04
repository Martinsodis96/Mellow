package com.mellow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mellow.mellow.R;
import com.mellow.model.Chat;
import com.mellow.model.Post;
import com.mellow.model.User;

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
        messageContent.setText(chat.getMessages().get(chat.getMessages().size()-1).getContent());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chat.getUsers().size(); i++) {
            stringBuilder.append(chat.getUsers().get(i).getUsername());
            if(chat.getUsers().size() > 1 && i != chat.getUsers().size()){
                stringBuilder.append(", ");
            }
        }
        username.setText(stringBuilder);
        return customView;
    }

    private void initializePalettes(View view){
        username = (TextView) view.findViewById(R.id.firstname);
        messageContent = (TextView) view.findViewById(R.id.message_text);
    }
}
