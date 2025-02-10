package com.airplane.schedule.repository;

import com.airplane.schedule.model.UserActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Integer> {
}
