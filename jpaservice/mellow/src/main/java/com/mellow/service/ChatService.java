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

    public ChatDao getChatById(Long chatId) {
        ChatDao chat = execute(chatRepository1 ->
                chatRepository1.findOne(chatId),
                "Failed to get chat with id: " + chatId);
        if (chat != null) {
            return chat;
        } else {
            throw new NoSearchResultException("Could not find chat with id: " + chatId);
        }
    }

    public Iterable<ChatDao> getAllChats(){
        Iterable<ChatDao> chat;
        try{
             chat = chatRepository.findAll();
        }catch (DataAccessException e){
            throw new DatabaseException("Failed to get all chats");
        }

        if (chat != null) {
            return chat;
        } else {
            throw new NoSearchResultException("No chats were founds.");
        }
    }

    public ChatDao createChat(List<UserDao> users){
        return execute(chatRepository1 ->
                chatRepository1.save(new ChatDao(users)),
                "Failed To Create Chat");
    }

    public ChatDao addUserToChat(Long chatId, Long userId){
        ChatDao chat;
        UserDao user;
        try{
            chat = chatRepository.findOne(chatId);
            user = userRepository.findOne(userId);
        }catch (DataAccessException e){
            throw new DatabaseException("Failed to add user to chat.");
        }

        if(chat != null && user != null){
            chat.addUser(user);
            return chatRepository.save(chat);
        }else {
            throw new NoSearchResultException("Could not find user user with id: "
                    + userId + " or chat with id: " + chatId);
        }
    }

    public ChatDao removeUserFromChat(Long chatId, Long userId){
        ChatDao chat;
        UserDao user;
        try{
            chat = chatRepository.findOne(chatId);
            user = userRepository.findOne(userId);
        }catch (DataAccessException e){
            throw new DatabaseException("Failed to remove user from chat.");
        }

        if(chat != null && user != null){
            chat.removeUser(user);
            return chatRepository.save(chat);
        }else {
            throw new NoSearchResultException("Could not find user user with id: "
                    + userId + " or chat with id: " + chatId);
        }
    }

    public List<UserDao> getAllUsersFromChat(Long chatId){
        ChatDao chat;
        try{
            chat = chatRepository.findOne(chatId);
        }catch (DataAccessException e){
            throw new DatabaseException("Failed to add user to chat.");
        }
        if(chat != null){
            return chat.getUsers();
        }else {
            throw new NoSearchResultException("Could not find chat with id: " + chatId);
        }
    }

    private ChatDao execute(Function<ChatRepository, ChatDao> operation, String dbExMsg){
        try{
            return operation.apply(chatRepository);
        }catch (DataAccessException e){
            throw new DatabaseException(dbExMsg);
        }
    }
}