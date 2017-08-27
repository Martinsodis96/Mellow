package com.mellow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mellow.adapter.ChatArrayAdapter;
import com.mellow.mellow.R;
import com.mellow.model.Chat;
import com.mellow.model.Message;
import com.mellow.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    ListView contactListView;
    private ListAdapter listAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactListView = view.findViewById(R.id.contact_list);
        //TODO add ChatAdapter and ChatApi.
        List<Chat> chats = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Message> messages = new ArrayList<>();
        users.add(new User("Conversation 1"));
        chats.add(new Chat(users).setMessages(messages));
        listAdapter = new ChatArrayAdapter(getActivity().getApplicationContext(), chats);
        contactListView.setAdapter(listAdapter);
    }
}
