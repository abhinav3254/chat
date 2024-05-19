package com.abhinav3254.chat.repository;

import com.abhinav3254.chat.model.Message;
import com.abhinav3254.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findByReceiver(User user);

}
