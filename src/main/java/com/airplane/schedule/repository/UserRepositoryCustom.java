package com.airplane.schedule.repository;

import com.airplane.schedule.dto.request.UserSearchRequest;
import com.airplane.schedule.model.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> search(UserSearchRequest userSearchRequest);
    Long count(UserSearchRequest userSearchRequest);
}
