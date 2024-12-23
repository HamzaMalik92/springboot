package com.example.demo.fcm.repo;

import com.example.demo.fcm.dto.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, String> {
}
