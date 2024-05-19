package com.abhinav3254.chat.repository;

import com.abhinav3254.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
