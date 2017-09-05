package com.mellow.application.jpaservice.service.implementation;

import com.mellow.application.jpaservice.entity.Chat;
import com.mellow.application.jpaservice.entity.User;
import com.mellow.application.jpaservice.repository.ChatRepository;
import com.mellow.application.jpaservice.repository.UserRepository;
import com.mellow.application.jpaservice.service.exception.DatabaseException;
import com.mellow.application.jpaservice.service.exception.NoSearchResultException;
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

    public Chat getChatById(Long chatId) {
        return execute(chatRepository1 -> chatRepository1.findOne(chatId),
                "Failed to get chat with id: " + chatId);
    }

    public Iterable<Chat> getAllChats() {
        Iterable<Chat> chats;
        try {
            return chatRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all chats");
        }
    }

    public Chat createChat(List<User> users) {
        return execute(chatRepository1 ->
                        chatRepository1.save(new Chat(users)),
                "Failed To Create Chat");
    }

    public Chat addUserToChat(Long chatId, Long userId) {
        return execute(chatRepository1 -> {
            Chat chat = chatRepository.findOne(chatId);
            User user = userRepository.findOne(userId);
            if (chat != null && user != null) {
                chat.addUser(user);
                return chatRepository.save(chat);
            } else {
                throw new NoSearchResultException("Could not find user user with id: "
                        + userId + " or chat with id: " + chatId);
            }
        }, "Failed to add user to chat.");
    }

    public Chat removeUserFromChat(Long chatId, Long userId) {
        return execute(chatRepository1 -> {
            Chat chat = chatRepository.findOne(chatId);
            User user = userRepository.findOne(userId);
            if (chat != null && user != null) {
                chat.removeUser(user);
                return chatRepository.save(chat);
            } else {
                throw new NoSearchResultException("Could not find user user with id: "
                        + userId + " or chat with id: " + chatId);
            }
        }, "Failed to remove user from chat.");
    }

    public List<User> getAllUsersFromChat(Long chatId) {
        try {
            Chat chat = chatRepository.findOne(chatId);
            if (chat != null) {
                return chat.getUsers();
            } else {
                throw new NoSearchResultException("Could not find chat with id: " + chatId);
            }
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to add user to chat.");
        }
    }

    private Chat execute(Function<ChatRepository, Chat> operation, String dbExMsg) {
        try {
            return operation.apply(chatRepository);
        } catch (DataAccessException e) {
            throw new DatabaseException(dbExMsg);
        }
    }
}