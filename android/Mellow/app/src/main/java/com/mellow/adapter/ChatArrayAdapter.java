package com.mellow.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mellow.activity.ChatActivity;
import com.mellow.mellow.R;
import com.mellow.model.Chat;

import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter<Chat> {

    private List<Chat> chats;
    private TextView username;
    private TextView messageContent;
    private LinearLayout chatContainer;
    private Context context;

    public ChatArrayAdapter(Context context, List<Chat> chats) {
        super(context, R.layout.activity_chat_adapter, chats);
        this.chats = chats;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.activity_chat_adapter, parent, false);
        initializePalettes(customView);
        setChatContainerOnClick();
        Chat chat = chats.get(position);
        if (!chat.getMessages().isEmpty())
            messageContent.setText(chat.getMessages().get(chat.getMessages().size() - 1).getContent());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chat.getUsers().size(); i++) {
            stringBuilder.append(chat.getUsers().get(i).getUsername());
            if (chat.getUsers().size() > 1 && i != chat.getUsers().size()) {
                stringBuilder.append(", ");
            }
        }
        username.setText(stringBuilder);
        return customView;
    }

    private void initializePalettes(View view) {
        username = view.findViewById(R.id.firstname);
        messageContent = view.findViewById(R.id.message_text);
        chatContainer = view.findViewById(R.id.chat_container);
    }

    private void setChatContainerOnClick() {
        chatContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
}
