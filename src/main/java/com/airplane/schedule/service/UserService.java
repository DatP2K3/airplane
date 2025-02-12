package com.airplane.schedule.service;

import com.airplane.schedule.dto.PageApiResponse;
import com.airplane.schedule.dto.request.PasswordRequestDTO;
import com.airplane.schedule.dto.request.UserInforRequestDTO;
import com.airplane.schedule.dto.request.UserRequestDTO;
import com.airplane.schedule.dto.request.UserSearchRequest;
import com.airplane.schedule.dto.response.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserResponseDTO getUserById(int id);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO updateInfoUser(int id, UserInforRequestDTO userInforRequestDTO);
    void updatePassword(String email, PasswordRequestDTO passwordRequestDTO);
    String updateAvatar(int id, MultipartFile avatar);
    void deleteUser(int id);
    PageApiResponse<List<UserResponseDTO>> search(UserSearchRequest userRequest);
}
