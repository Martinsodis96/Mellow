package com.mellow.service;

import com.mellow.model.ChatDao;
import com.mellow.model.UserDao;
import com.mellow.repository.ChatRepository;
import com.mellow.repository.UserRepository;
import com.mellow.service.exception.DatabaseException;
import com.mellow.service.exception.NoSearchResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class ChatService {

    private ChatRepository chatRepository;
    private UserRepository userRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public ChatDao getChatById(Long id) {
        ChatDao chat = execute(chatRepository1 ->
                chatRepository1.findOne(id),
                "Failed to get chat with id: " + id);
        if (chat != null) {
            return chat;
        } else {
            throw new NoSearchResultException("Could not find chat with id: " + id);
        }
    }

    public Iterable<ChatDao> getAllChats(){
        try{
            Iterable<ChatDao> chat = chatRepository.findAll();
            if (chat != null) {
                return chat;
            } else {
                throw new NoSearchResultException("No chats were founds.");
            }
        }catch (DataAccessException e){
            throw new DatabaseException("Failed to get all chats");
        }
    }

    public ChatDao createChat(List<UserDao> users){
        return execute(chatRepository1 ->
                chatRepository1.save(new ChatDao(users)),
                "Failed To Create Chat");
    }

    public ChatDao addUserToChat(Long chatId, Long userId){
        return execute(chatRepository1 -> {
            ChatDao chat = chatRepository1.findOne(chatId);
            chat.addUser(userRepository.findOne(userId));
            return chatRepository1.save(chat);
        },  "Failed to add user to chat.");
    }

    public ChatDao removeUserFromChat(Long chatId, Long userId){
        return execute(chatRepository1 -> {
            ChatDao chat = chatRepository1.findOne(chatId);
            chat.removeUser(userRepository.findOne(userId));
            return chatRepository1.save(chat);
        },  "Failed to remove user from chat.");
    }

    public List<UserDao> getAllUsersFromChat(Long chatId){
        try{
            ChatDao chat = chatRepository.findOne(chatId);
            return chat.getUsers();
        }catch (DataAccessException e){
            throw new DatabaseException("Failed to add user to chat.");
        }
    }

    public ChatDao execute(Function<ChatRepository, ChatDao> operation, String dbExMsg){
        try{
            return operation.apply(chatRepository);
        }catch (DataAccessException e){
            throw new DatabaseException(dbExMsg);
        }
    }
}